package com.dmatek.zgb.access;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.StringUtils;

import com.dmatek.zgb.access.bean.FillAccessRecord;
import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.access.offduty.LeaveAccessRecord;
import com.dmatek.zgb.access.onduty.EntryAccessRecord;
import com.dmatek.zgb.comparator.DescAccessComparator;
import com.dmatek.zgb.db.access.service.AccessService;
import com.dmatek.zgb.redis.cache.AccessRedisCache;
import com.dmatek.zgb.show.vo.AccessView;

public abstract class BaseAccessTool {
	private final static Logger logger = Logger.getLogger(BaseAccessTool.class);
	
	protected static final int OFFDUTY_TYPE = LeaveAccessRecord.OFFDUTY_TYPE;
	protected static final int ONDUTY_TYPE = EntryAccessRecord.ONDUTY_TYPE;
	private AccessRedisCache iRedisCache;
	private AccessService accessService;
	
	private DescAccessComparator baseAccessComparator;
	private IAccessDetailCapture iAccessDetailCapture;
	public BaseAccessTool(AccessRedisCache iRedisCache, 
			IAccessDetailCapture iAccessDetailCapture,
			DescAccessComparator baseAccessComparator,
			AccessService accessService){
		this.iRedisCache = iRedisCache;
		this.iAccessDetailCapture = iAccessDetailCapture;
		this.baseAccessComparator = baseAccessComparator;
		this.accessService = accessService;
	}
	/**
	 * 获取上班所有人员讯息
	 * @param groupId
	 * @param accessType
	 * @return
	 * @throws Exception
	 */
	public List<TagAccessRecord> getAccessOnDutyFromGroup(int groupId,
			Date accessTime) throws Exception{
		List<TagAccessRecord> accessRecords = getAccessFromGroup(groupId, ONDUTY_TYPE, accessTime);
		Collections.sort(accessRecords, getBaseAccessComparator());
		return accessRecords;
	}	
	/**
	 * 获取下班所有人员讯息
	 * @param groupId
	 * @param accessType
	 * @return
	 * @throws Exception
	 */
	public List<TagAccessRecord> getAccessOffDutyFromGroup(int groupId,
			Date accessTime) throws Exception{
		List<TagAccessRecord> accessRecords = getAccessFromGroup(groupId, OFFDUTY_TYPE, accessTime);
		Collections.sort(accessRecords, getBaseAccessComparator());
		return accessRecords;
	}
	/**
	 * 过滤出入统计的记录
	 * @return
	 * @throws Exception
	 */
	public List<TagAccessRecord> filterAccessRecords(List<TagAccessRecord> accessRecords, 
			String key) throws Exception {
		List<TagAccessRecord> filterRecords = new ArrayList<TagAccessRecord>();
		for (TagAccessRecord tagAccessRecord : accessRecords) {
			if(tagAccessRecord.getTagId().equalsIgnoreCase(key) || 
			   tagAccessRecord.getTagName().equalsIgnoreCase(key) || 
			   tagAccessRecord.getPersonNo().equalsIgnoreCase(key) || 
			   tagAccessRecord.getCompanyName().equalsIgnoreCase(key) || 
			   tagAccessRecord.getCompanyNo().equalsIgnoreCase(key) || 
			   tagAccessRecord.getJobTypeName().equalsIgnoreCase(key) || 
			   tagAccessRecord.getJobTypeNo().equalsIgnoreCase(key) || 
			   tagAccessRecord.getReferId().equalsIgnoreCase(key) || 
			   tagAccessRecord.getReferName().equalsIgnoreCase(key)){
				filterRecords.add(tagAccessRecord);
			}
		}
		return filterRecords;
	}
	
	public List<AccessView> filterAccessViewRecords(List<AccessView> accessRecords, 
			String key) throws Exception {
		List<AccessView> filterRecords = new ArrayList<AccessView>();
		for (AccessView accessView : accessRecords) {
			if(key.trim().equalsIgnoreCase(accessView.getTagId()) ||  
			   key.trim().equalsIgnoreCase(accessView.getTagName()) ||  
			   key.trim().equalsIgnoreCase(accessView.getEnterReferId()) ||  
			   key.trim().equalsIgnoreCase(accessView.getEnterReferName()) ||  
			   key.trim().equalsIgnoreCase(accessView.getLeaveReferId()) ||  
			   key.trim().equalsIgnoreCase(accessView.getLeaveReferName()) ||  
			   key.trim().equalsIgnoreCase(accessView.getJobTypeName()) ||  
			   key.trim().equalsIgnoreCase(accessView.getCompanyName())) {
				filterRecords.add(accessView);
			}
		}
		return filterRecords;
	}
	private List<TagAccessRecord> getAccessFromGroup(int groupId, int accessType, 
			Date accessTime) throws Exception {
		Map<String, TagAccessRecord> tagAccessMapRecords = new HashMap<String, TagAccessRecord>();
		//******************************获取数据库中的数据********************************//* 
		List<TagAccessRecord> dbAccessRecords = getAccessService().findGroupTypeTime(groupId, accessType, accessTime);
		for (TagAccessRecord tagAccessRecord : dbAccessRecords) {
			TagAccessRecord oldRecord = tagAccessMapRecords.get(tagAccessRecord.getTagId());
			// 判断两个时间哪一个在后面，在后面的需要重新添加
			if(null != oldRecord) {
				if(oldRecord.getAccessTime().compareTo(tagAccessRecord.getAccessTime()) <= 0) {
					tagAccessMapRecords.put(tagAccessRecord.getTagId(), tagAccessRecord);
				}
			} else {
				tagAccessMapRecords.put(tagAccessRecord.getTagId(), tagAccessRecord);
			}
		}
		// 获取缓存中的数据
		Map<String, TagAccessRecord> tagAccessCache = getiRedisCache().getCacheMap();
		Iterator<TagAccessRecord> iterator = tagAccessCache.values().iterator();
		while (iterator.hasNext()) {
			TagAccessRecord tagAccessRecord = iterator.next();
			if (tagAccessRecord.getGroupId() == groupId && 
					DateUtils.isSameDay(tagAccessRecord.getAccessTime(), accessTime) && 
					tagAccessRecord.getAccessType() == accessType) {
				TagAccessRecord oldRecord = tagAccessMapRecords.get(tagAccessRecord.getTagId());
				// 判断两个时间哪一个在后面，在后面的需要重新添加
				if(null != oldRecord) {
					if(oldRecord.getAccessTime().compareTo(tagAccessRecord.getAccessTime()) <= 0) {
						tagAccessMapRecords.put(tagAccessRecord.getTagId(), tagAccessRecord);
					}
				} else {
					tagAccessMapRecords.put(tagAccessRecord.getTagId(), tagAccessRecord);
				}
			}
		}
		return new ArrayList<TagAccessRecord>(tagAccessMapRecords.values());
	}
	public List<AccessView> getAccessFromGroup(int groupId, 
			Date accessTime) throws Exception {
		Map<String, AccessView> accessMap = new HashMap<String, AccessView>();
		// 获取缓存中的记录
		Map<String, TagAccessRecord> tagAccessCache = getiRedisCache().getCacheMap();
		/****************************访问数据库中的记录并添加到缓存中***************************/
		// 获取数据库中的数据
		List<TagAccessRecord> dbAccessRecords = getAccessService().findGroupTime(groupId, accessTime);
		// 将数据库中的记录添加到缓存里面
		for (int i = 0; i < dbAccessRecords.size(); i++) {
			String key = dbAccessRecords.get(i).getUuid();
			tagAccessCache.put(key, dbAccessRecords.get(i));
		}
		/*****************************遍历缓存中的记录***********************************************/
		Iterator<TagAccessRecord> iterator = tagAccessCache.values().iterator();
		while (iterator.hasNext()) {
			TagAccessRecord tagAccessRecord = iterator.next();
			// 获取当天的当前组别的所有记录
			if(tagAccessRecord.getGroupId() == groupId && 
			   DateUtils.isSameDay(tagAccessRecord.getAccessTime(), accessTime)){
				// 获取指定卡片ID
				AccessView accessView = accessMap.get(tagAccessRecord.getTagId());
				if(null != accessView) {
					// 缓存中已经存在这样的卡片，我们要根据时间选择最新的时间数据
					if(tagAccessRecord.getAccessType() == ONDUTY_TYPE){// 处在上班状态
						// 判断是当前的数据新还是已经保存的数据新
						if(null != accessView.getOnDutyTime()) {
							if (tagAccessRecord.getAccessTime().compareTo(
							accessView.getOnDutyTime()) > 0) {
								accessView.setEnterReferId(tagAccessRecord.getReferId());
								accessView.setEnterReferName(tagAccessRecord.getReferName());
								accessView.setOnDutyTime(tagAccessRecord.getAccessTime());
							}
						} else {
							accessView.setEnterReferId(tagAccessRecord.getReferId());
							accessView.setEnterReferName(tagAccessRecord.getReferName());
							accessView.setOnDutyTime(tagAccessRecord.getAccessTime());
						}
					} else {// 处在下班状态
						if(null != accessView.getOffDutyTime()) {
							if (tagAccessRecord.getAccessTime().compareTo(
							accessView.getOffDutyTime()) > 0) {
								accessView.setLeaveReferId(tagAccessRecord.getReferId());
								accessView.setLeaveReferName(tagAccessRecord.getReferName());
								accessView.setOffDutyTime(tagAccessRecord.getAccessTime());
							}
						} else {
							accessView.setLeaveReferId(tagAccessRecord.getReferId());
							accessView.setLeaveReferName(tagAccessRecord.getReferName());
							accessView.setOffDutyTime(tagAccessRecord.getAccessTime());
						}
					}
				} else {// 没有这一张卡片,可以直接向里面添加
					if(tagAccessRecord.getAccessType() == ONDUTY_TYPE) {// 处在上班状态
						accessView = new AccessView(tagAccessRecord.getReferId(),
							tagAccessRecord.getReferName(),null,null, tagAccessRecord.getTagId(),
							tagAccessRecord.getTagName(),tagAccessRecord.getCompanyName(),
							tagAccessRecord.getJobTypeName(), tagAccessRecord.getAccessTime(), 
							null, tagAccessRecord.getPersonNo(), tagAccessRecord.getResTime(),
							tagAccessRecord.getGroupName(), tagAccessRecord.getRegionName());
					} else {
						accessView = new AccessView(null, null, tagAccessRecord.getReferId(),
								tagAccessRecord.getReferName(), tagAccessRecord.getTagId(),
								tagAccessRecord.getTagName(),tagAccessRecord.getCompanyName(),
								tagAccessRecord.getJobTypeName(), null, 
								tagAccessRecord.getAccessTime(), tagAccessRecord.getPersonNo(), 
								tagAccessRecord.getResTime(), 
								tagAccessRecord.getGroupName(), tagAccessRecord.getRegionName());
					}
					accessMap.put(accessView.getTagId(), accessView);
				}
			}
		}
		List<AccessView> accessList = new ArrayList<AccessView>(accessMap.values());
		return accessList;
	}
	
	public boolean fillAccessInfor(FillAccessRecord accessRecord)
			throws Exception {
		// 1. 判断缓存中是否有上班记录
		String uuid = getiRedisCache().getAccessRecord(accessRecord.getTagId(), 
				accessRecord.getGroupId(), accessRecord.getAccessTime(),
				accessRecord.getAccessType());
		// 2. 判断数据库中是否有当天的上班记录
		if(null != uuid) {
			getiRedisCache().removeCacheObject(uuid);
		}
		TagAccessRecord record = getiAccessDetailCapture().obtainAccessRecord(accessRecord);
		getiRedisCache().addCacheObject(record.getUuid(), record);
		return true;
	}
	
	public void deleteAccessInfor(AccessView access,int groupId) throws Exception{
		/*****************************清除缓存记录**********************************/
		// 1.获取缓存中所有需要删除的uuid
		List<String> allEnterCacheUUIDs=null, allLeaveCacheUUIDS=null;
		
		if(!StringUtils.isEmpty(access.getEnterReferId())) {
			allEnterCacheUUIDs = getiRedisCache()
					.getAllAccessRecords(access.getTagId(), groupId,
							access.getOnDutyTime(), ONDUTY_TYPE);
		}
		if(!StringUtils.isEmpty(access.getLeaveReferId())) {
			allLeaveCacheUUIDS = getiRedisCache().getAllAccessRecords(
					access.getTagId(), groupId, access.getOffDutyTime(),
					OFFDUTY_TYPE);
		}
		// 删除所有进入缓存的记录
		if(null != allEnterCacheUUIDs) {
			for (String uuid : allEnterCacheUUIDs) {
				getiRedisCache().removeCacheObject(uuid);
			}
		}
		if(null != allLeaveCacheUUIDS) {
			for (String uuid : allLeaveCacheUUIDS) {
				getiRedisCache().removeCacheObject(uuid);
			}
		}
		/*****************************清除数据库记录**********************************/
		List<TagAccessRecord> allClearEnterAccessRecords = getAccessService()
				.findAllRecordOneDay(access.getTagId(), ONDUTY_TYPE, access.getOnDutyTime());
		List<TagAccessRecord> allClearLeaveAccessRecords = getAccessService()
				.findAllRecordOneDay(access.getTagId(), OFFDUTY_TYPE,
						access.getOffDutyTime());
		if(null != allClearEnterAccessRecords) {
			for (TagAccessRecord tagAccessRecord : allClearEnterAccessRecords) {
				getAccessService().deleteAccessRecord(tagAccessRecord.getUuid());
			}
		}
		if(null != allClearLeaveAccessRecords) {
			for (TagAccessRecord tagAccessRecord : allClearLeaveAccessRecords) {
				getAccessService().deleteAccessRecord(tagAccessRecord.getUuid());
			}
		}
	}
	
	/**
	 * 获取可进入时间
	 * @param sTime
	 * @return
	 * @throws ParseException
	 */
	public Date getAccessTime(String sTime) throws ParseException{
		if(Strings.isEmpty(sTime)){
			return new Date();
		}
		return new SimpleDateFormat("yyyy-MM-dd").parse(sTime);
	}
	public AccessRedisCache getiRedisCache() {
		return iRedisCache;
	}
	public void setiRedisCache(AccessRedisCache iRedisCache) {
		this.iRedisCache = iRedisCache;
	}
	public DescAccessComparator getBaseAccessComparator() {
		return baseAccessComparator;
	}
	public void setBaseAccessComparator(DescAccessComparator baseAccessComparator) {
		this.baseAccessComparator = baseAccessComparator;
	}
	public IAccessDetailCapture getiAccessDetailCapture() {
		return iAccessDetailCapture;
	}
	public void setiAccessDetailCapture(IAccessDetailCapture iAccessDetailCapture) {
		this.iAccessDetailCapture = iAccessDetailCapture;
	}
	public AccessService getAccessService() {
		return accessService;
	}
	public void setAccessService(AccessService accessService) {
		this.accessService = accessService;
	}
	public static Logger getLogger() {
		return logger;
	}
}
