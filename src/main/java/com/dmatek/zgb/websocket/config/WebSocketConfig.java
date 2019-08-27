package com.dmatek.zgb.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * webSocket的配置类
 * @author zf
 * @data 2018年12月21日 下午2:13:18
 * @Description
 */
@Configuration
public class WebSocketConfig {
	@Bean
	public ServerEndpointExporter getServerEndpointExporter(){
		return new ServerEndpointExporter();
	}
}
