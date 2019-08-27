package com.dmatek.zgb.result.factory.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmatek.zgb.result.factory.base.CheckedResultFactory;
import com.dmatek.zgb.result.factory.base.ViewResultFactory;

@Configuration
public class FactoryConfig {
	@Bean
	public CheckedResultFactory getCheckedResultFactory(){
		return new CheckedResultFactory();
	}
	@Bean
	public ViewResultFactory getResultTools(){
		return new ViewResultFactory();
	}
}
