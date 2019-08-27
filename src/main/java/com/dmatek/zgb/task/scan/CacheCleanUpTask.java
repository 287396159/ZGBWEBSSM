package com.dmatek.zgb.task.scan;

import com.dmatek.zgb.warn.cache.base.BaseWarnCache;
/**
 * 警告缓存清除任务
 * @author zf
 * @data 2018年12月17日 上午10:06:19
 * @Description
 */
public class CacheCleanUpTask extends BaseScanTask {
	private BaseWarnCache<?>[] baseWarnCaches;
	public CacheCleanUpTask(int peroid, BaseWarnCache<?>... baseWarnCaches) {
		super(peroid);
		this.baseWarnCaches = baseWarnCaches;
	}
	@Override
	public void scanTask() throws Exception {
		for (BaseWarnCache<?> baseWarmCache : baseWarnCaches) {
			baseWarmCache.clearCache();
		}
	}
	public BaseWarnCache<?>[] getBaseWarnCaches() {
		return baseWarnCaches;
	}
	public void setBaseWarnCaches(BaseWarnCache<?>[] baseWarnCaches) {
		this.baseWarnCaches = baseWarnCaches;
	}
}
