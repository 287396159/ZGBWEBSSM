package com.dmatek.zgb.monitor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmatek.zgb.monitor.bean.NodePack;
import com.dmatek.zgb.monitor.bean.ReferPack;
import com.dmatek.zgb.redis.cache.IRedisMapCache;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@Configuration
public class SendToolConfig {
	
	@Bean
	public ISendTool getISendTool(
			IRedisMapCache<String, NodePack> iNodeRedisCache,
			IRedisMapCache<String, ReferPack> iReferRedisCache,
			BaseResultFactory<Result> viewFactory,
			BaseDataMonitor baseDataMonitor) {
		return new MonitorUdpSendTool(iNodeRedisCache, iReferRedisCache,
				viewFactory, baseDataMonitor);
	}
}
