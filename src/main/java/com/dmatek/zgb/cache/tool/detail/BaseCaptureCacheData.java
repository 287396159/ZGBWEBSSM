package com.dmatek.zgb.cache.tool.detail;

import java.util.Map;

import com.dmatek.zgb.redis.service.RedisService;

public abstract class BaseCaptureCacheData<T, V> {
	private RedisService redisService;
	private String cacheName;
	public BaseCaptureCacheData(RedisService redisService,String cacheName){
		this.redisService = redisService;
		this.cacheName = cacheName;
	}
	public abstract T getDetailCacheData(V obj) throws Exception;
	public Map<Object,Object> getCacheData() throws Exception{
		return getRedisService().getHmAll(cacheName);
	}
	public RedisService getRedisService() {
		return redisService;
	}
	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}
	public String getCacheName() {
		return cacheName;
	}
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
}
