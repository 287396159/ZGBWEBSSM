package com.dmatek.zgb.access.base;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.time.DateUtils;

import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.redis.cache.RedisMapCache;

public abstract class BaseAccessWork {
	public static final byte OFFDUTY_TYPE = 2;
	public static final byte ONDUTY_TYPE = 1;
	private static final long DEFAULT_INTERVAL = 5 * 1000;// 默认同 一个人 记录间隔时间是5s
	private RedisMapCache<TagAccessRecord> accessRedisCache;
	public BaseAccessWork(RedisMapCache<TagAccessRecord> accessRedisCache) {
		super();
		this.accessRedisCache = accessRedisCache;
	}
	protected abstract byte getType();
	public TagAccessRecord createAccessRecord(TagDetail tagDetail)
			throws Exception {
		String uuid = UUID.randomUUID().toString();
		TagAccessRecord tagAccessRecord = new TagAccessRecord(uuid, 
				tagDetail.getrId(), tagDetail.getId(), 
				tagDetail.getTagNo(), tagDetail.getTagName(), 
				tagDetail.getReferName() ,tagDetail.getCompanyNo(), 
				tagDetail.getCompanyName(),
				tagDetail.getJobTypeNo(), tagDetail.getJobTypeName(), 
				tagDetail.getGroupId(), tagDetail.getRegionId(), 
				tagDetail.getGroupName(), tagDetail.getRegionName(), 
				getType(), DateUtils.round(tagDetail.getReportTime(), Calendar.SECOND),
				tagDetail.getImage(), tagDetail.getResTime(),
				tagDetail.isLowPower(), getType()==OFFDUTY_TYPE?"外出":"進入");
		return tagAccessRecord;
	}
	public void execute(TagDetail tagDetail) throws Exception {
		TagAccessRecord tagAccessRecord = createAccessRecord(tagDetail);
		if (null != tagAccessRecord && isSave(tagAccessRecord)) {
			saveAccessRecord(tagAccessRecord);
		}
	}
	/**
	 * 判断是否要保存
	 * 两个添加的记录时间间隔必须在30s异常才行
	 * @param tagDetail
	 * @return
	 * @throws Exception 
	 */
	private boolean isSave(TagAccessRecord tagAccessRecord) throws Exception {
	    Map<String, TagAccessRecord> maps = getAccessRedisCache().getCacheMap();
		Iterator<TagAccessRecord> iterators = maps.values().iterator();
		while (iterators.hasNext()) {
			TagAccessRecord val = (TagAccessRecord) iterators.next();
			if (val.getAccessType() == getType()
				&& Math.abs(tagAccessRecord.getAccessTime().getTime()
			  - val.getAccessTime().getTime()) <= DEFAULT_INTERVAL) {
				// 存在一个两个时间间隔在30s以内，我们就不应该再次向里面添加
				return false;
			}
		} 
		return true;
	}
	/**
	 * 保存进出记录
	 * @param tagAccessRecord
	 * @throws Exception 
	 */
	private void saveAccessRecord(TagAccessRecord tagAccessRecord) throws Exception {
		if(null != tagAccessRecord) {
			getAccessRedisCache().addCacheObject(tagAccessRecord.getUuid(), tagAccessRecord);
		}
	}
	public RedisMapCache<TagAccessRecord> getAccessRedisCache() {
		return accessRedisCache;
	}
	public void setAccessRedisCache(RedisMapCache<TagAccessRecord> accessRedisCache) {
		this.accessRedisCache = accessRedisCache;
	}
}
