package com.dmatek.zgb.redis.cache;

import com.dmatek.zgb.monitor.bean.ReferPack;
import com.dmatek.zgb.redis.service.RedisService;

public class ReferPackRedisCache extends RedisMapCache<ReferPack> {
	private static final String REFERS_CACHE_NAME = "refersCache";
	public ReferPackRedisCache(RedisService redisService) {
		super(redisService, REFERS_CACHE_NAME);
	}
}
