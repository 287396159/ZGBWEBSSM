package com.dmatek.zgb.task.scan;
import com.dmatek.zgb.warn.bean.base.BaseWarnMessage;
import com.dmatek.zgb.warn.cache.base.BaseWarnCache;
/**
 * 自动清除已经被处理的警报
 * @author zf
 * @data 2018年12月20日 上午11:44:20
 * @Description
 */
public class ClearHandleWarnTask extends BaseScanTask {
	private BaseWarnCache<?>[] baseWarnCaches;
	private int overTime;
	public ClearHandleWarnTask(int peroid,int overTime,BaseWarnCache<?>...baseWarnCaches) {
		super(peroid);
		this.overTime = overTime;
		this.baseWarnCaches = baseWarnCaches;
	}
	@Override
	public void scanTask() throws Exception {
		long handleTimeMillis = 0;
		for (BaseWarnCache<?> baseWarnCache : baseWarnCaches) {
			// 遍历所有已经被处理，没有被清理的警告讯息
			for (BaseWarnMessage warn : baseWarnCache.getAllHandleNotClears()) {
				handleTimeMillis = warn.getHandleTime().getTime();
				// 当警报处理的时间超过指定的值时，我们需要将这条警报讯息清除掉
				if(System.currentTimeMillis() - handleTimeMillis >= overTime * 1000){
					baseWarnCache.clearWarn(warn.getUuid());
				}
			}
		}
	}
	public BaseWarnCache<?>[] getBaseWarnCaches() {
		return baseWarnCaches;
	}
	public void setBaseWarnCaches(BaseWarnCache<?>[] baseWarnCaches) {
		this.baseWarnCaches = baseWarnCaches;
	}
}
