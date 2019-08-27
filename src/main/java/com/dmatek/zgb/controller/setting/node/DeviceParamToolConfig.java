package com.dmatek.zgb.controller.setting.node;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmatek.zgb.monitor.ISendTool;
import com.dmatek.zgb.redis.service.RedisService;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@Configuration
public class DeviceParamToolConfig {
	
	@Bean
	public BaseDeviceParamTool getBaseDeviceParamTool(UdpCommunicationContainer udpCommunicationContainer,
			BaseResultFactory<Result> viewResultFactory,
			ISendTool iSendTool,
			IPackTool iPackTool){
		return new NodeParamTool(udpCommunicationContainer, viewResultFactory,
				iSendTool, iPackTool);
	}
	@Bean
	public UdpCommunicationContainer getUdpCommunicationContainer(RedisService redisService,
			IPackTool iPackTool) {
		return new UdpCommunicationContainer(redisService, iPackTool);
	}
}
