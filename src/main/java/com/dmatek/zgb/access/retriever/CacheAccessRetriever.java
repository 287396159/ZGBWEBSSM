package com.dmatek.zgb.access.retriever;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.thymeleaf.util.StringUtils;

import com.dmatek.zgb.access.IAccessDetailCapture;
import com.dmatek.zgb.access.bean.FillAccessRecord;
import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.redis.cache.AccessRedisCache;

public class CacheAccessRetriever extends BaseAccessRetriever {
	private AccessRedisCache iRedisCache;
	public CacheAccessRetriever(IAccessDetailCapture iAccessDetailCapture, AccessRedisCache iRedisCache){
		super(iAccessDetailCapture);
		this.iRedisCache = iRedisCache;
	}
	@Override
	public Map<String, TagAccessRecord> searchLatestAccessRecord(int groupId,
			int accessType, Date accessTime) throws Exception {
		Map<String, TagAccessRecord> accessMaps = new HashMap<String, TagAccessRecord>(10);
		// 获取缓存中的数据
		Map<String, TagAccessRecord> tagAccessCache = getiRedisCache()
				.getCacheMap();
		Iterator<TagAccessRecord> iterator = tagAccessCache.values().iterator();
		while (iterator.hasNext()) {
			TagAccessRecord tagAccessRecord = iterator.next();
			if (tagAccessRecord.getGroupId() == groupId
			 && DateUtils.isSameDay(tagAccessRecord.getAccessTime(), accessTime)
			 && tagAccessRecord.getAccessType() == accessType) {
				if(isMatch(tagAccessRecord, accessMaps)) {
					accessMaps.put(tagAccessRecord.getTagId(), tagAccessRecord);
				}
			}
		}
		return accessMaps;
	}
	@Override
	public boolean removeAccessRecord(String tagId, int groupId, int accessType,
			Date accessTime) throws Exception {
		String uuid = "";
		if(!StringUtils.isEmpty(tagId) && groupId > 0 && null != accessTime) {
			uuid = getiRedisCache().getAccessRecord(tagId, groupId, accessTime, accessType);
		}
		return getiRedisCache().removeCacheObject(uuid);
	}
	@Override
	public boolean addAccessRecord(FillAccessRecord accessRecord)
			throws Exception {
		// 1. 判断缓存中是否有上班记录
		String uuid = getiRedisCache().getAccessRecord(accessRecord.getTagId(), accessRecord.getGroupId(), accessRecord.getAccessTime(), accessRecord.getAccessType());
		// 2. 判断数据库中是否有当天的上班记录
		if (!StringUtils.isEmpty(uuid)) {
			getiRedisCache().removeCacheObject(uuid);
		}
		TagAccessRecord record = getiAccessDetailCapture().obtainAccessRecord(accessRecord);
		return getiRedisCache().addCacheObject(record.getUuid(), record);
	}
	public AccessRedisCache getiRedisCache() {
		return iRedisCache;
	}
	public void setiRedisCache(AccessRedisCache iRedisCache) {
		this.iRedisCache = iRedisCache;
	}
}
