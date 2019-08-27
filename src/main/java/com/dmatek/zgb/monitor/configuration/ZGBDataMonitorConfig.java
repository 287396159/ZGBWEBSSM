package com.dmatek.zgb.monitor.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmatek.zgb.controller.setting.node.UdpCommunicationContainer;
import com.dmatek.zgb.monitor.ZGBDataMonitor;
import com.dmatek.zgb.monitor.callback.ZGBMonitorCallBack;

@Configuration
public class ZGBDataMonitorConfig {
	@Bean
	public ZGBMonitorCallBack getZGBMonitorCallBack(){
		return new ZGBMonitorCallBack();
	}
	@Bean
	public  ZGBDataMonitor getZGBDataMonitor(UdpCommunicationContainer udpCommunicationContainer){
		return new ZGBDataMonitor(getZGBMonitorCallBack(), udpCommunicationContainer);
	}
}
