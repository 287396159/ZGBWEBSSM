package com.dmatek.zgb.report.access.tool;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.show.vo.AccessView;

public class DailyAccessTools implements IDailyAccessTools<TagAccessRecord, AccessView> {
	private final static int ACCESS_ONDUTY = 1;
	private final static int ACCESS_OFFDUTY = 2;
	private final static int INITIAL_CAPACITY = 20;
	private final static int SINGLE_HOUR_MS = 60 * 60 * 1000;
	private DateFormat simpleDataFormat;
	private DecimalFormat df = null;
	public DailyAccessTools(){
		super();
		simpleDataFormat = new SimpleDateFormat("yyyy-MM-dd");
		df = new DecimalFormat("0.00");
	}
	/**
	 * 更新AccessView實例
	 * @param record
	 * @param accessView
	 */
	private void updateInstance(TagAccessRecord record, AccessView accessView) {
		if(record.getAccessType() == ACCESS_ONDUTY) {// 1. 表示上班
		    if (StringUtils.isEmpty(accessView.getEnterReferId()) || accessView.getOnDutyTime().compareTo(record.getAccessTime()) > 0) {
				// 说明还没有进行赋值操作
				accessView.setEnterReferId(record.getReferId());
				accessView.setEnterReferName(record.getReferName());
				accessView.setOnDutyTime(record.getAccessTime());
			}
		} else if(record.getAccessType() == ACCESS_OFFDUTY) {//2. 表示下班
			if(StringUtils.isEmpty(accessView.getLeaveReferId()) || accessView.getOffDutyTime().compareTo(record.getAccessTime()) < 0) {
				// 说明不存在
				accessView.setLeaveReferId(record.getReferId());
				accessView.setLeaveReferName(record.getReferName());
				accessView.setOffDutyTime(record.getAccessTime());
			}
		}
	}
	/**
	 * 生成AccessView實例
	 * @param record
	 * @return
	 */
	private AccessView instance(TagAccessRecord record) {
		if(record.getAccessType() == ACCESS_ONDUTY) {// 上班
			return new AccessView(record.getReferId(), record.getReferName(), 
				null, null, record.getTagId(), record.getTagName(), record.getCompanyName(),
				record.getJobTypeName(), record.getAccessTime(), null, record.getPersonNo(),
				record.getResTime(), record.getGroupName(), record.getRegionName());
		} else if(record.getAccessType() == ACCESS_OFFDUTY) {// 下班
			return new AccessView(null, null,
				record.getReferId(), record.getReferName(), 
				record.getTagId(), record.getTagName(),
				record.getCompanyName(),record.getJobTypeName(), 
				null, record.getAccessTime(), record.getPersonNo(),
				record.getResTime(), 
				record.getGroupName(), record.getRegionName());
		}
		return null;
	}
	
	/**
	 * 將所有出入統計記錄按<年+月+日,<tagId, 當天記錄>>生成 Map
	 * @param records
	 * @param start
	 * @param end
	 * @return
	 */
	private Map<String, Map<String, AccessView>> produceCompositeMaps(List<TagAccessRecord> records, Date start, Date end) {
		Map<String, Map<String, AccessView>> compositeMaps = new HashMap<String, Map<String, AccessView>>(
				INITIAL_CAPACITY);
		for (TagAccessRecord record : records) {
			if (record.getAccessTime() == null
					|| record.getAccessTime().compareTo(start) < 0
					|| record.getAccessTime().compareTo(end) > 0) {
				continue;
			}
			if(record.getAccessType() != ACCESS_ONDUTY && 
			   record.getAccessType() != ACCESS_OFFDUTY) {
				continue;
			}
			// 1. 获取日期主键
			String ymdKey = getSimpleDataFormat().format(record.getAccessTime());
			Map<String, AccessView> accessMaps =  compositeMaps.get(ymdKey);
			if(null == accessMaps) {// 需要我们进行重新生成
				accessMaps = new HashMap<String, AccessView>();
				accessMaps.put(record.getTagId(), instance(record));
				// 添加记录
				compositeMaps.put(ymdKey, accessMaps);
			} else {// 说明当前的日期已经存在，根据日期已经获取到对应的缓存
				AccessView accessView = accessMaps.get(record.getTagId());
				if(null == accessView) {
					accessView = instance(record);
					accessMaps.put(accessView.getTagId(), accessView);
				} else { // 说明当前缓存记录已经存在， 我们应该根据类型判断
					updateInstance(record, accessView);
				}
			}
		}
		return compositeMaps;
	}
	/**
	 * 將所有的出入記錄統計成每天的出入記錄
	 */
	@Override
	public List<AccessView> transform(List<TagAccessRecord> records, Date start, Date end)
			throws Exception {
		// 外面的 String 是year + month + day作为主键， 里面的String 是tagId作为主键
		Map<String, Map<String, AccessView>> compositeMaps = produceCompositeMaps(
				records, start, end);
		// compositeMaps转化为List 
		List<AccessView> accessView = new ArrayList<AccessView>();
		Iterator<Map<String, AccessView>> iterator = compositeMaps.values().iterator();
		while (iterator.hasNext()) {
			Map<String, AccessView> map = iterator.next();
			accessView.addAll(map.values());
		}
		compositeMaps = null;
		return accessView;
	}

	/**
	 * 將每天的出入記錄中不包含外出記錄或不包含進入記錄的內容剔除掉
	 */
	@Override
	public List<AccessView> filter(List<AccessView> records) throws Exception {
		List<AccessView> filterRecords = new ArrayList<AccessView>();
		for (AccessView accessView : records) {
			if(!StringUtils.isEmpty(accessView.getEnterReferId()) 
			&& !StringUtils.isEmpty(accessView.getLeaveReferId())) {
				// 当两个都不为空时才是我们需要的出入讯息
				filterRecords.add(accessView);
			}
		}
		records = null;
		return filterRecords;
	}
	/**
	 * 獲取當天的工作時間
	 * @param accessView
	 * @return
	 */
	public float calcWorkHours(AccessView accessView) {
		float hours = (float)(accessView.getOffDutyTime().getTime() - accessView.getOnDutyTime().getTime())/SINGLE_HOUR_MS;
		// 当计算的总工作时间大于休息时间，直接相减，否则我们不应该计算休息时间，而是采用总的工作时间
		return hours > accessView.getResTime()?hours - accessView.getResTime():hours;
	}
	@Override
	public void statisticalWorkHours(List<AccessView> records)
			throws Exception {
		for (AccessView accessView : records) {
			if( null != accessView.getOnDutyTime() && null != accessView.getOffDutyTime()) {
				float tt = calcWorkHours(accessView);
				accessView.setTaskTime( tt>0?getDf().format(tt):"0.00");
			}
		}
	}
	public DateFormat getSimpleDataFormat() {
		return simpleDataFormat;
	}
	public void setSimpleDataFormat(DateFormat simpleDataFormat) {
		this.simpleDataFormat = simpleDataFormat;
	}
	public DecimalFormat getDf() {
		return df;
	}
	public void setDf(DecimalFormat df) {
		this.df = df;
	}
}
