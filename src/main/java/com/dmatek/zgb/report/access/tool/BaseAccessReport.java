package com.dmatek.zgb.report.access.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.StringUtils;
import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.comparator.DescAccessComparator;
import com.dmatek.zgb.comparator.IComparatorTool;
import com.dmatek.zgb.db.access.service.AccessService;
import com.dmatek.zgb.db.access.work.service.AccessWorkService;
import com.dmatek.zgb.redis.cache.AccessRedisCache;

public abstract class BaseAccessReport {
	private AccessRedisCache iRedisCache;// 缓存
	private AccessService accessService;// 服务
	private AccessWorkService accessWorkService;
	private IComparatorTool iComparatorTool;
	public BaseAccessReport(AccessRedisCache iRedisCache,
			AccessService accessService, 
			AccessWorkService accessWorkService, 
			IComparatorTool iComparatorTool) {
		this.iRedisCache = iRedisCache;
		this.accessService = accessService;
		this.accessWorkService = accessWorkService;
		this.iComparatorTool = iComparatorTool;
	}
	/**
	 * 搜索缓存里面满足条件的所有出入记录
	 * @return
	 * @throws Exception
	 */
	protected List<TagAccessRecord> searchCacheAccessBaseRecords(String name,
			String companyNo, String jobTypeNo, int groupId, Date start,
			Date end) throws Exception {
		Map<String, TagAccessRecord> allMaps = new HashMap<String, TagAccessRecord>();
		/* 獲取所有緩存記錄 */
		Iterator<TagAccessRecord> iterators = getiRedisCache().getCacheMap()
				.values().iterator();
		// 将里面不满足条件
		while (iterators.hasNext()) {
			TagAccessRecord tagAccessRecord = iterators.next();
			// 判断缓存记录是否满足条件
			// 1. 卡片名称是否满足条件 
			boolean isName = false;
			if(StringUtils.isEmpty(name) || 
			  (!StringUtils.isEmpty(name) && name.equalsIgnoreCase(tagAccessRecord.getTagName()))) {
				isName = true;
			}
			// 2. 公司编号是否满足条件
			boolean isCompany = false;
			if (StringUtils.isEmpty(companyNo) || 
			   (!StringUtils.isEmpty(companyNo) && companyNo.equalsIgnoreCase(tagAccessRecord.getCompanyNo()))) {
				isCompany = true;
			}
			// 3. 工种编号是否满足条件
			boolean isJobType = false;
			if(StringUtils.isEmpty(jobTypeNo) || 
			  (!StringUtils.isEmpty(jobTypeNo) && jobTypeNo.equalsIgnoreCase(tagAccessRecord.getJobTypeNo()))) {
				isJobType = true;
			}
			// 4. 组别是否满足条件
			boolean isGroup = false;
			if(groupId <= 0 || 
			  (groupId > 0 && tagAccessRecord.getGroupId() == groupId)) {
				isGroup = true;
			}
			// 5. 是否在开始时间和结束时间之间
			boolean isDate = false;
			if((null == start || null == end) || 
			   (start.compareTo(tagAccessRecord.getAccessTime()) <= 0 && 
			    end.compareTo(tagAccessRecord.getAccessTime()) > 0)) {
				isDate = true;
			}
			// 所有条件都满足时，我们可以进行添加记录
			if (isName && isCompany && isJobType && isGroup && isDate) {
				// 判断Map是否已经存在当前记录
				allMaps.put(tagAccessRecord.getUuid(), tagAccessRecord);
			}
		}
		return Arrays.asList(allMaps.values().toArray(new TagAccessRecord[0]));
	}
	/**
	 * 搜索所有出入基站記錄
	 * @param name
	 * @param companyNo
	 * @param jobTypeNo
	 * @param groupId
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<TagAccessRecord> searchAllAccessRecords(String name,
			String companyNo, String jobTypeNo, int groupId, Date start,
			Date end) throws Exception {
		List<TagAccessRecord> dbBaseRecords = getAccessService()
				.searchTagAccessRecords(name, companyNo, jobTypeNo, groupId, start, end);
		List<TagAccessRecord> cacheRecords = searchCacheAccessBaseRecords(name,
				companyNo, jobTypeNo, groupId, start, end);
		// 排序
		return ListUtils.union(dbBaseRecords, cacheRecords);
	}
	protected List<TagAccessRecord> searchOneDayAccessRecord(String tagId, int type, Date accessTime) throws Exception {
		// 获取指定卡片，指定进出类型，指定天数的数据库记录
		List<TagAccessRecord> records =	getAccessService().findAllRecordOneDay(tagId, type, accessTime);
		records = (null != records ? records : new ArrayList<TagAccessRecord>());
		// 获取指定卡片，指定进出类型，指定天数的 缓存记录
		Iterator<TagAccessRecord> iterators = getiRedisCache().getCacheMap()
				.values().iterator();
		while (iterators.hasNext()) {
			TagAccessRecord record = (TagAccessRecord) iterators.next();
			if(tagId.equalsIgnoreCase(record.getTagId()) && 
			   type == record.getAccessType() && 
			   DateUtils.isSameDay(accessTime, record.getAccessTime())) {
				records.add(record);
			}
		}
		// 排序
		Collections.sort(records,
				iComparatorTool.obtain(DescAccessComparator.class));
		return records;
	}
	
	public AccessRedisCache getiRedisCache() {
		return iRedisCache;
	}
	public void setiRedisCache(AccessRedisCache iRedisCache) {
		this.iRedisCache = iRedisCache;
	}
	public AccessService getAccessService() {
		return accessService;
	}
	public void setAccessService(AccessService accessService) {
		this.accessService = accessService;
	}
	public AccessWorkService getAccessWorkService() {
		return accessWorkService;
	}
	public void setAccessWorkService(AccessWorkService accessWorkService) {
		this.accessWorkService = accessWorkService;
	}
	public IComparatorTool getiComparatorTool() {
		return iComparatorTool;
	}
	public void setiComparatorTool(IComparatorTool iComparatorTool) {
		this.iComparatorTool = iComparatorTool;
	}
}
