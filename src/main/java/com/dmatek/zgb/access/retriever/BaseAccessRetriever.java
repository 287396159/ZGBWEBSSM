package com.dmatek.zgb.access.retriever;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.dmatek.zgb.access.IAccessDetailCapture;
import com.dmatek.zgb.access.base.BaseAccessWork;
import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.show.vo.AccessView;

public abstract class BaseAccessRetriever implements IAccessRetriever {
	private IAccessDetailCapture iAccessDetailCapture;
	public BaseAccessRetriever(IAccessDetailCapture iAccessDetailCapture) {
		this.iAccessDetailCapture = iAccessDetailCapture;
	}
	/**
	 * 判断当前出入记录是否已经存在于Map中
	 * 若已经存在，查看两个记录哪一个先，若当前记录最新，我们就返回true, 否则返回false
	 * 若不存在，返回true, 否则返回false
	 * @param current : 当前记录
	 * @param accessMap: map 容器
	 * @return
	 */
	protected boolean isMatch(TagAccessRecord current, Map<String, TagAccessRecord> accessMap) {
		TagAccessRecord beforeRecord = accessMap.get(current.getTagId());
		// 判断两个时间哪一个在后面，在后面的需要重新添加
		if(null == beforeRecord) {
			return true;
		}
		if (beforeRecord.getAccessTime().compareTo(current.getAccessTime()) <= 0) {
			return true;
		}
		return false;
	}
	/**
	 * 将指定组别、日期的出入记录转化为上下班记录
	 *   注： 所有出入记录都是取最新的记录
	 * @param groupId
	 * @param accessTime
	 * @param accessRecordMap
	 * @return
	 */
	protected Map<String, AccessView> transformAccessRecord(int groupId, Date accessTime, 
			Map<String, TagAccessRecord> accessRecordMap) {
		Map<String, AccessView> accessViewMap = new HashMap<String, AccessView>();
		// 开始遍历AccessRecord容器中的所有记录
		Iterator<TagAccessRecord> iterator = accessRecordMap.values().iterator();
		while (iterator.hasNext()) {
			TagAccessRecord tagAccessRecord = iterator.next();
			// 判断组别是否相同、是否是要找的那一天的记录
			if(DateUtils.isSameDay(tagAccessRecord.getAccessTime(), accessTime)
			   && tagAccessRecord.getGroupId() == groupId) {
				// 获取  AccessViewMap 中是否存在指定卡片ID的记录
				AccessView accessView = accessViewMap.get(tagAccessRecord.getTagId());
				if(null != accessView) {
					// AccessViewMap 中已经存在指定卡片ID的记录
					if (tagAccessRecord.getAccessType() == BaseAccessWork.ONDUTY_TYPE) {// 处在上班状态
						// 需要我们更新里面的上班记录
						if(null != accessView.getOnDutyTime()) {
							if (tagAccessRecord.getAccessTime().compareTo(
									accessView.getOnDutyTime()) > 0) {
								accessView.setEnterReferId(tagAccessRecord
										.getReferId());
								accessView.setEnterReferName(tagAccessRecord
										.getReferName());
								accessView.setOnDutyTime(tagAccessRecord
										.getAccessTime());
							}
						} else {
							accessView.setEnterReferId(tagAccessRecord
									.getReferId());
							accessView.setEnterReferName(tagAccessRecord
									.getReferName());
							accessView.setOnDutyTime(tagAccessRecord
									.getAccessTime());
						}
					} else {// 处在下班状态
						if(null != accessView.getOffDutyTime()) {
							if (tagAccessRecord.getAccessTime().compareTo(
									accessView.getOffDutyTime()) > 0) {
								accessView.setLeaveReferId(tagAccessRecord
										.getReferId());
								accessView.setLeaveReferName(tagAccessRecord
										.getReferName());
								accessView.setOffDutyTime(tagAccessRecord
										.getAccessTime());
							}
						} else {
							accessView.setLeaveReferId(tagAccessRecord
									.getReferId());
							accessView.setLeaveReferName(tagAccessRecord
									.getReferName());
							accessView.setOffDutyTime(tagAccessRecord
									.getAccessTime());
						}
					}
				} else {
					// 没有这一张卡片, 可以直接向里面添加
					accessView = newInstance(tagAccessRecord);
					if (null != accessView) {
						accessViewMap.put(accessView.getTagId(), accessView);
					}
				}
			}
		}
		return accessViewMap;
	} 
	/**
	 * 根据出入记录生成当天的上下班记录
	 * @param tagAccessRecord
	 * @return
	 */
	private AccessView newInstance(TagAccessRecord tagAccessRecord) {
		AccessView accessView = null;
		if(tagAccessRecord.getAccessType() == BaseAccessWork.ONDUTY_TYPE) {// 处在上班状态
			accessView = new AccessView(tagAccessRecord.getReferId(),
				tagAccessRecord.getReferName(),null,null, tagAccessRecord.getTagId(),
				tagAccessRecord.getTagName(),tagAccessRecord.getCompanyName(),
				tagAccessRecord.getJobTypeName(), tagAccessRecord.getAccessTime(), 
				null, tagAccessRecord.getPersonNo(), tagAccessRecord.getResTime(),
				tagAccessRecord.getGroupName(), tagAccessRecord.getRegionName());
		} if(tagAccessRecord.getAccessType() == BaseAccessWork.OFFDUTY_TYPE) {// 处在下班状态
			accessView = new AccessView(null, null, tagAccessRecord.getReferId(),
					tagAccessRecord.getReferName(), tagAccessRecord.getTagId(),
					tagAccessRecord.getTagName(),tagAccessRecord.getCompanyName(),
					tagAccessRecord.getJobTypeName(), null, 
					tagAccessRecord.getAccessTime(), tagAccessRecord.getPersonNo(), 
					tagAccessRecord.getResTime(), 
					tagAccessRecord.getGroupName(), tagAccessRecord.getRegionName());
		} else {
			
		}
		return accessView;
	}
	public IAccessDetailCapture getiAccessDetailCapture() {
		return iAccessDetailCapture;
	}
	public void setiAccessDetailCapture(IAccessDetailCapture iAccessDetailCapture) {
		this.iAccessDetailCapture = iAccessDetailCapture;
	}
}
