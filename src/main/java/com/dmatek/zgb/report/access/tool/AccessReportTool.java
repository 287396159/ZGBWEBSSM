package com.dmatek.zgb.report.access.tool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.thymeleaf.util.ListUtils;

import com.dmatek.zgb.access.base.BaseAccessWork;
import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.comparator.AccessViewComparator;
import com.dmatek.zgb.comparator.IComparatorTool;
import com.dmatek.zgb.db.access.service.AccessService;
import com.dmatek.zgb.db.access.work.service.AccessWorkService;
import com.dmatek.zgb.redis.cache.AccessRedisCache;
import com.dmatek.zgb.show.vo.AccessView;

public class AccessReportTool extends BaseAccessReport {
	private final static byte ACCESS_ONDUTY = 1;
	private final static byte ACCESS_OFFDUTY = 2;
	private IDailyAccessTools<TagAccessRecord, AccessView> dailyAccessTools;
	// 日期类型时间格式
	private SimpleDateFormat dateSimpleDateFormat;
	// 时间类型时间格式
	private SimpleDateFormat timeSimpleDateFormat;
	public AccessReportTool(AccessRedisCache iRedisCache,
			AccessService accessService, 
			AccessWorkService accessWorkService,
			IDailyAccessTools<TagAccessRecord, AccessView> dailyAccessTools,
			IComparatorTool iComparatorTool) {
		super(iRedisCache, accessService, accessWorkService, iComparatorTool);
		this.dailyAccessTools = dailyAccessTools;
		dateSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		timeSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 搜索所有进出经过出入口基站的 所有记录
	 * @param name
	 * @param companyNo
	 * @param jobTypeNo
	 * @param groupId
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception 
	 */
	public List<AccessView> searchAllAccessBaseRecords(String name,
			String companyNo, String jobTypeNo, int groupId, String start,
			String end) throws Exception {
		Date startTime = getTimeSimpleDateFormat().parse(start), 
			  endTime  = getTimeSimpleDateFormat().parse(end);
		List<TagAccessRecord> records = searchAllAccessRecords(name,
				companyNo, jobTypeNo, groupId, startTime, endTime);
		/*allAccessRecords 这个列表中保存的是所有经过出入口参考点的所有出入记录，
		根据这些出入记录，生成所有经过出入口的当天上下班工时统计记录*/
		List<AccessView> allAccessViews = getDailyAccessTools().transform(
				records, startTime, endTime);
		//  排序
		AccessViewComparator AccessViewComparator = getiComparatorTool().obtain(AccessViewComparator.class);
		
		Collections.sort(allAccessViews, AccessViewComparator);
		return allAccessViews;
	}
	/**
	 * 获取没有经过上下班基站的记录
	 * @param name
	 * @param companyNo
	 * @param jobTypeNo
	 * @param groupId
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	private List<TagAccessRecord> obtainNoAccessRecords(String name,
			String companyNo, String jobTypeNo, int groupId, Date start,
			Date end) throws Exception{
		// 获取每天最早和最晚的记录
		List<TagAccessRecord> allWorkRecords = getAccessWorkService()
				.searchAccessRecords(name, companyNo, jobTypeNo, groupId,
						start, end);
		List<TagAccessRecord> records = new ArrayList<TagAccessRecord>();
		// 遍历所有 每天卡片 的最早记录和最晚记录
		for (TagAccessRecord tagAccessRecord : allWorkRecords) {
			// 应该根据当前记录搜索进过出入口的记录
			TagAccessRecord searchRecord = searchAccessRecord(
					tagAccessRecord.getTagId(),
					tagAccessRecord.getAccessType(),
					tagAccessRecord.getAccessTime());
			if (null == searchRecord) {// 说明当前记录没有经过上下班基站
				records.add(tagAccessRecord);
			}
		}
		return records;
	}
	
	/**
	 * 匹配上下班记录并合并到一个集合列表
	 * @param noAccessBaseRecords
	 * @return
	 * @throws Exception
	 */
	private List<TagAccessRecord> matchAccessRecord(List<TagAccessRecord> noAccessBaseRecords) throws Exception {
		List<TagAccessRecord> matchRecords = new ArrayList<TagAccessRecord>();
		// 这个是所有没有经过上下班参考点的记录
		for (TagAccessRecord record : noAccessBaseRecords) {
			matchRecords.add(record);
			byte type = ((record.getAccessType() == ACCESS_OFFDUTY) ? ACCESS_ONDUTY
					: ACCESS_OFFDUTY);
			TagAccessRecord searchRecord = searchAccessRecord(
					noAccessBaseRecords, record.getTagId(),
					type, record.getAccessTime());
			// 找一个经过上下班的记录
			if(null == searchRecord) {
			// 说明不存在，我们应该从
				searchRecord = searchAccessRecord(record.getTagId(), type, record.getAccessTime());
				if(null != searchRecord) {
					matchRecords.add(searchRecord);
				}
			}
		}
		return matchRecords;
	}
	/**
	 * 搜寻所有没有经过出入口基站的记录
	 * @param name
	 * @param companyNo
	 * @param jobTypeNo
	 * @param groupId
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception 
	 */
	public List<AccessView> searchAllNoAccessBaseRecords(String name,
			String companyNo, String jobTypeNo, int groupId, String start,
			String end) throws Exception {
		Date startTime = getTimeSimpleDateFormat().parse(start), endTime = getTimeSimpleDateFormat()
				.parse(end);
		List<TagAccessRecord> noAccessBaseRecords = obtainNoAccessRecords(name,
				companyNo, jobTypeNo, groupId, startTime, endTime);
		List<TagAccessRecord> matchRecords = matchAccessRecord(noAccessBaseRecords);
		
		List<AccessView> allAccessViews = getDailyAccessTools().transform(
				matchRecords, startTime, endTime);
		// 排序
		Collections.sort(allAccessViews,
				getiComparatorTool().obtain(AccessViewComparator.class));
		return allAccessViews;
	}
	
	private TagAccessRecord searchAccessRecord(String tagId, byte type, Date current) throws Exception {
		List<TagAccessRecord> records = searchOneDayAccessRecord(tagId, type, current);
		// 默认按升序进行排序
		if(ListUtils.isEmpty(records)) {
			return null;
		}
		int index = ((type == ACCESS_ONDUTY) ? (records.size() - 1):0);
		return records.get(index);
	}
	
	// 判断这里是应该找第一笔记录还是找最后一笔记录
	private TagAccessRecord searchAccessRecord(
			List<TagAccessRecord> allAccessRecords, String tagId, byte type, Date current) {
		TagAccessRecord searchAccessRecord = null;
		for (TagAccessRecord tagAccessRecord : allAccessRecords) {
			if(tagId.equalsIgnoreCase(tagAccessRecord.getTagId()) && 
			   type == tagAccessRecord.getAccessType() && 
			   DateUtils.isSameDay(current, tagAccessRecord.getAccessTime())) {
				if(null != searchAccessRecord) {
					// 上班就找最早的一条记录
					if(type == BaseAccessWork.ONDUTY_TYPE && 
					   searchAccessRecord.getAccessTime().compareTo(tagAccessRecord.getAccessTime()) > 0) {// 上班类型
						searchAccessRecord = tagAccessRecord;
					} else if(type == BaseAccessWork.OFFDUTY_TYPE && 
					   searchAccessRecord.getAccessTime().compareTo(tagAccessRecord.getAccessTime()) < 0) {// 下班类型
						// 下班就找最晚的一条记录
						searchAccessRecord = tagAccessRecord;
					}
				} else {
					searchAccessRecord = tagAccessRecord;
				}
			}
		}
		return searchAccessRecord;
	}
	public IDailyAccessTools<TagAccessRecord, AccessView> getDailyAccessTools() {
		return dailyAccessTools;
	}
	public void setDailyAccessTools(
			IDailyAccessTools<TagAccessRecord, AccessView> dailyAccessTools) {
		this.dailyAccessTools = dailyAccessTools;
	}
	public SimpleDateFormat getDateSimpleDateFormat() {
		return dateSimpleDateFormat;
	}
	public void setDateSimpleDateFormat(SimpleDateFormat dateSimpleDateFormat) {
		this.dateSimpleDateFormat = dateSimpleDateFormat;
	}
	public SimpleDateFormat getTimeSimpleDateFormat() {
		return timeSimpleDateFormat;
	}
	public void setTimeSimpleDateFormat(SimpleDateFormat timeSimpleDateFormat) {
		this.timeSimpleDateFormat = timeSimpleDateFormat;
	}
}
