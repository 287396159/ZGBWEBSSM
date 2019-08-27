package com.dmatek.zgb.warn.cache.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.redis.service.RedisService;
import com.dmatek.zgb.warn.bean.base.BaseTagWarnMessage;

public abstract class BaseTagWarnCache<T extends BaseTagWarnMessage> extends BaseWarnCache<T>{
	
	public BaseTagWarnCache(RedisService redisService,
			BaseWarmComparator warmDefaultComparator) {
		super(redisService, warmDefaultComparator);
	}
	@Override
	public List<T> getWarnFromDeviceId(String id) throws Exception {
		List<T> tagWarms = new ArrayList<T>();
		Iterator<T> iterators = getCache().values().iterator();
		T tagWarm = null;
		while(iterators.hasNext()){
			tagWarm = iterators.next();
			if(null != tagWarm && tagWarm.getTagId().equals(id)){
				tagWarms.add(tagWarm);
			}
		}
		return tagWarms;
	}
	
	
}
