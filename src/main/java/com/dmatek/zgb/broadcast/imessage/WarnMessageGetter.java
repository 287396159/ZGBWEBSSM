package com.dmatek.zgb.broadcast.imessage;

import java.util.HashMap;
import java.util.Map;

import com.dmatek.zgb.broadcast.vo.BroadCastVo;
import com.dmatek.zgb.warn.cache.base.BaseWarnCache;

public class WarnMessageGetter extends BaseMessageGetter{
	private static int BROADCAST_WARN_TYPE = 1;
	private static String WARMMESSAGE = "所有的警告訊息廣播";
	// 獲取警告消息訊息
	private BaseWarnCache<?>[] baseWarnCaches;
	public WarnMessageGetter(BaseWarnCache<?>...baseWarnCaches){
		super();
		this.baseWarnCaches = baseWarnCaches;
	}
	@Override
	public BroadCastVo getBroadCastVo() throws Exception {
		Map<String,Integer> warnMap = new HashMap<String,Integer>();
		for (BaseWarnCache<?> baseWarnCache : baseWarnCaches) {
			String key = baseWarnCache.getWarnName();
			int number = baseWarnCache.getAllNotHandles().size();
			warnMap.put(key, number);
		}
		return new BroadCastVo(BROADCAST_WARN_TYPE, WARMMESSAGE, warnMap);
	}
	public BaseWarnCache<?>[] getBaseWarnCaches() {
		return baseWarnCaches;
	}
	public void setBaseWarnCaches(BaseWarnCache<?>[] baseWarnCaches) {
		this.baseWarnCaches = baseWarnCaches;
	}
}
