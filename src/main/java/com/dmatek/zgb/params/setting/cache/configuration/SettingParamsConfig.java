package com.dmatek.zgb.params.setting.cache.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmatek.zgb.params.setting.cache.SettingParams;

@Configuration
public class SettingParamsConfig {
	@Bean
	public SettingParams getSettingParams(){
		return new SettingParams();
	}
}
