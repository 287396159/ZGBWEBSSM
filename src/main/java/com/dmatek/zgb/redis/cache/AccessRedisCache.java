package com.dmatek.zgb.redis.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.redis.service.RedisService;

public class AccessRedisCache extends RedisMapCache<TagAccessRecord> {
	private static final String ACCESS_CACHE_NAME = "accessRecordCache";
	public AccessRedisCache(RedisService redisService) {
		super(redisService, ACCESS_CACHE_NAME);
	}
	public String getAccessRecord(String tagId, int groupId ,Date time, int accessType) throws Exception {
		Map<Object, Object> accessMap = getRedisService().getHmAll(ACCESS_CACHE_NAME);
		Iterator<Object> iterator = accessMap.keySet().iterator();
		while(iterator.hasNext()){
			String key = (String) iterator.next();
			TagAccessRecord accessRecord = (TagAccessRecord) accessMap.get(key);
			if(accessRecord.getGroupId() == groupId && 
			   accessRecord.getTagId().equalsIgnoreCase(tagId) && 
			   DateUtils.isSameDay(accessRecord.getAccessTime(), time) && 
			   accessRecord.getAccessType() ==  accessType){
			   return key;
			}
		}
		return null;
	}
	public List<String> getAllAccessRecords(String tagId, int groupId,Date time, int accessType) throws Exception {
		List<String> allAccessRecords = new ArrayList<String>();
		Map<Object, Object> accessMap = getRedisService().getHmAll(ACCESS_CACHE_NAME);
		Iterator<Object> iterator = accessMap.keySet().iterator();
		while(iterator.hasNext()){
			String key = (String) iterator.next();
			TagAccessRecord accessRecord = (TagAccessRecord) accessMap.get(key);
			if(accessRecord.getGroupId() == groupId && 
			   accessRecord.getTagId().equalsIgnoreCase(tagId) && 
			   DateUtils.isSameDay(accessRecord.getAccessTime(), time) && 
			   accessRecord.getAccessType() ==  accessType) {
				allAccessRecords.add(key);
			}
		}
		return allAccessRecords;
	} 
}
