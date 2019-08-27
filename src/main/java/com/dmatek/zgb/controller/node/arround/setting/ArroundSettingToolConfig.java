package com.dmatek.zgb.controller.node.arround.setting;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmatek.zgb.controller.setting.node.IPackTool;
import com.dmatek.zgb.controller.setting.node.UdpCommunicationContainer;
import com.dmatek.zgb.db.setting.service.NodeService;
import com.dmatek.zgb.monitor.ISendTool;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@Configuration
public class ArroundSettingToolConfig {
	@Bean
	public BaseArroundSettingTool getBaseArroundSettingTool(ISendTool iSendTool,
			UdpCommunicationContainer udpCommunicationContainer,
			BaseResultFactory<Result> viewResultFactory, IPackTool iPackTool,
			NodeService nodeService) {
		return new ArroundSettingTool(iSendTool, udpCommunicationContainer,
				viewResultFactory, iPackTool, nodeService);
	}
}
