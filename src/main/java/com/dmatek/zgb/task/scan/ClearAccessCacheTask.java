package com.dmatek.zgb.task.scan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.db.access.service.AccessService;
import com.dmatek.zgb.redis.cache.IRedisMapCache;

public class ClearAccessCacheTask extends BaseScanTask {
	private IRedisMapCache<String,TagAccessRecord> accessCache;
	private AccessService accessService;
	public ClearAccessCacheTask(int peroid, IRedisMapCache<String,TagAccessRecord> accessCache, 
		AccessService accessService) {
		super(peroid);
		this.accessCache = accessCache;
		this.accessService = accessService;
		
	}
	@Override
	public void scanTask() throws Exception {
		List<TagAccessRecord> accessRecords = getRecycleCacheRecords();
		// 这里所有的出入记录都是需要重新添加到数据库中
		for (TagAccessRecord tagAccessRecord : accessRecords) {
			// 查看数据库中是否已经存在当前记录
			TagAccessRecord dbAccessRecord = getAccessService().findOne(tagAccessRecord.getTagId(), 
					tagAccessRecord.getGroupId(),
					tagAccessRecord.getAccessType(), 
					tagAccessRecord.getAccessTime());
			if(null != dbAccessRecord) {
				// 删除库中的记录
				getAccessService().deleteAccessRecord(dbAccessRecord.getUuid());
			}
			// 向数据库中添加新的记录,防止数据库与历史记录中存在1s的差距
			tagAccessRecord.setAccessTime(DateUtils.round(tagAccessRecord.getAccessTime(), Calendar.SECOND));
			getAccessService().addAccessRecord(tagAccessRecord);
			// 从缓存中清除当前记录
			getAccessCache().removeCacheObject(tagAccessRecord.getUuid());
		}
	}
	/**
	 * 获取需要回收的缓存记录
	 * 指的是记录值不是当天产生的
	 * @return
	 * @throws Exception
	 */
	private List<TagAccessRecord> getRecycleCacheRecords() throws Exception{
		Date current = new Date();
		List<TagAccessRecord> removes = new ArrayList<TagAccessRecord>();
		Map<String, TagAccessRecord> accessRecordCache = getAccessCache().getCacheMap();
		Iterator<TagAccessRecord> accessList = accessRecordCache.values().iterator();
		while (accessList.hasNext()) {
			TagAccessRecord tagAccessRecord = accessList.next();
			if(!DateUtils.isSameDay(current, tagAccessRecord.getAccessTime())){
				// 不是处在同一天, 我们需要回收记录
				removes.add(tagAccessRecord);
			}
		}
		return removes;
	}
	public IRedisMapCache<String,TagAccessRecord> getAccessCache() {
		return accessCache;
	}
	public void setAccessCache(IRedisMapCache<String,TagAccessRecord> accessCache) {
		this.accessCache = accessCache;
	}
	public AccessService getAccessService() {
		return accessService;
	}
	public void setAccessService(AccessService accessService) {
		this.accessService = accessService;
	}
}
