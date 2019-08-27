package com.dmatek.zgb.access.offduty;

import com.dmatek.zgb.access.base.BaseAccessWork;
import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.redis.cache.RedisMapCache;

public class LeaveAccessRecord extends BaseAccessWork {
	public LeaveAccessRecord(RedisMapCache<TagAccessRecord> accessRedisCache) {
		super(accessRedisCache);
	}
	@Override
	protected byte getType() {
		return OFFDUTY_TYPE;
	}
}
