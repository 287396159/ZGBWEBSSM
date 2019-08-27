package com.dmatek.zgb.controller.tool.page;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PageToolConfig {
	@Bean
	public PageTool getWarnMessagePageTool(){
		return new PageTool();
	}
}
