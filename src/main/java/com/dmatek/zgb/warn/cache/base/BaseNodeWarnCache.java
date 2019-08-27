package com.dmatek.zgb.warn.cache.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.redis.service.RedisService;
import com.dmatek.zgb.warn.bean.base.BaseNodeWarnMessage;

public abstract class BaseNodeWarnCache<T extends BaseNodeWarnMessage> extends BaseWarnCache<T>{
	public BaseNodeWarnCache(RedisService redisService,
			BaseWarmComparator warmDefaultComparator) {
		super(redisService, warmDefaultComparator);
	}
	@Override
	public List<T> getWarnFromDeviceId(String id) throws Exception {
		List<T> nodeWarms = new ArrayList<T>();
		Iterator<T> iterators = getCache().values().iterator();
		T nodeWarm = null;
		while(iterators.hasNext()){
			nodeWarm = iterators.next();
			if(null != nodeWarm && nodeWarm.getNodeId().equals(id)){
				nodeWarms.add(nodeWarm);
			}
		}
		return nodeWarms;
	}
}
