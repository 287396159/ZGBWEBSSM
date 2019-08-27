package com.dmatek.zgb.controller.sort;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmatek.zgb.monitor.device.vo.TagViewMsg;
import com.dmatek.zgb.warn.bean.base.BaseWarnMessage;

@Configuration
public class SorterConfig {	
	@Bean
	public Sorter<BaseWarnMessage> getWarnSorter(){
		return new Sorter<BaseWarnMessage>();
	}
	@Bean
	public Sorter<TagViewMsg> getTagViewSorter(){
		return new Sorter<TagViewMsg>();
	}
}
