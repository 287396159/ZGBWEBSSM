package com.dmatek.zgb.access.onduty;

import com.dmatek.zgb.access.base.BaseAccessWork;
import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.redis.cache.RedisMapCache;

public class EntryAccessRecord extends BaseAccessWork{
	public EntryAccessRecord(RedisMapCache<TagAccessRecord> accessRedisCache) {
		super(accessRedisCache);
	}
	@Override
	protected byte getType() {
		return ONDUTY_TYPE;
	}
}
