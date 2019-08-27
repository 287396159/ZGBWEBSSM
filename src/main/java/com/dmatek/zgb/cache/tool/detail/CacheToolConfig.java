package com.dmatek.zgb.cache.tool.detail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmatek.zgb.redis.service.RedisService;
import com.dmatek.zgb.redis.util.RedisCacheName;

@Configuration
public class CacheToolConfig {
	@Bean
	public CaptureTagCacheData getCaptureTagCacheData(RedisService redisService){
		return new CaptureTagCacheData(redisService, RedisCacheName.TAGS_CACHE_NAME);
	}
	@Bean
	public CaptureNodeCacheData getCaptureNodeCacheData(RedisService redisService){
		return new CaptureNodeCacheData(redisService, RedisCacheName.NODES_CACHE_NAME);
	}
	@Bean
	public CaptureReferCacheData getCaptureReferCacheData(RedisService redisService){
		return new CaptureReferCacheData(redisService, RedisCacheName.REFERS_CACHE_NAME);
	}
}
