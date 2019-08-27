package com.dmatek.zgb.redis.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.dmatek.zgb.redis.service.RedisService;

public abstract class RedisMapCache<V> implements IRedisMapCache<String, V>{
	private RedisService redisService;
	private String cacheName;
	public RedisMapCache(RedisService redisService, String cacheName) {
		this.redisService = redisService;
		this.cacheName = cacheName;
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
	public V getCacheItem(String key) throws Exception {
		Map<String, V> caches = getCacheMap();
		return caches.get(key);
	}
	@Override
	public Map<String, V> getCacheMap() throws Exception {
		Map<String, V> curMap = new HashMap<String, V>();
		Map<Object, Object> caches = getRedisService().getHmAll(getCacheName());
		if(null != caches){
			Iterator<Object> iterator = caches.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				@SuppressWarnings("unchecked")
				V obj = (V) caches.get(key);
				curMap.put(key, obj);
			}
		}
		return curMap;
	}
	/**
	 * 添加缓存对象
	 */
	@Override
	public boolean addCacheObject(String key, V object) throws Exception {
		getRedisService().setHmItem(getCacheName(), key, object);
		return true;
	}
	/**
	 * 删除缓存对象
	 */
	@Override
	public boolean removeCacheObject(String key) throws Exception {
		getRedisService().deleteHmItem(getCacheName(), key);
		return false;
	}
	/**
	 * 更新缓存对象
	 */
	@Override
	public boolean updateCacheObject(String key, V object) throws Exception {
		getRedisService().setHmItem(getCacheName(), key, object);
		return false;
	}
	/**
	 * 清空缓存对象
	 */
	@Override
	public boolean clearCache() throws Exception {
		getRedisService().deleteKeys(getCacheName());
		return false;
	}
}
