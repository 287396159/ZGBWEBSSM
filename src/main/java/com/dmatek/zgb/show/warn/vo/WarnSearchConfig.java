package com.dmatek.zgb.show.warn.vo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WarnSearchConfig {
	@Bean
	public WarnSearchFactory getWarnSearchFactory(){
		return new WarnSearchFactory();
	}
}
