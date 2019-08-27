package com.dmatek.zgb.redis.cache;

import com.dmatek.zgb.monitor.bean.NodePack;
import com.dmatek.zgb.redis.service.RedisService;

public class NodePackRedisCache extends RedisMapCache<NodePack> {
	private static final String NODES_CACHE_NAME = "nodesCache";
	public NodePackRedisCache(RedisService redisService) {
		super(redisService, NODES_CACHE_NAME);
	}
}
