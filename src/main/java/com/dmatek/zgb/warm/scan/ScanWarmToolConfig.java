package com.dmatek.zgb.warm.scan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.dmatek.zgb.controller.setting.params.base.WarmSettingParamTool;
import com.dmatek.zgb.db.setting.service.ReliablePerson_NodeService;

@Configuration
public class ScanWarmToolConfig {
	@Bean
	public ScanTagWarmTool getScanTagWarmTool(WarmSettingParamTool warmSettingParamTool,
			ReliablePerson_NodeService reliablePerson_NodeService){
		return new ScanTagWarmTool(warmSettingParamTool, reliablePerson_NodeService);
	}
}
