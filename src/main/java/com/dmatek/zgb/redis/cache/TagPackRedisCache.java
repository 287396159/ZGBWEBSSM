package com.dmatek.zgb.redis.cache;

import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.redis.service.RedisService;

public class TagPackRedisCache extends RedisMapCache<TagDetail> {
	private static final String TAGS_CACHE_NAME = "tagsCache";
	public TagPackRedisCache(RedisService redisService) {
		super(redisService, TAGS_CACHE_NAME);
	}
}
