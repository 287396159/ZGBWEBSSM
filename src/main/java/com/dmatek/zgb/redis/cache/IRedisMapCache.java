package com.dmatek.zgb.redis.cache;

import java.util.Map;

public interface IRedisMapCache<T,V> {
	boolean addCacheObject(T key, V object) throws Exception;
	boolean removeCacheObject(T key) throws Exception;
	boolean updateCacheObject(T key, V object) throws Exception;
	Map<T,V> getCacheMap() throws Exception;
	boolean clearCache() throws Exception;
}
