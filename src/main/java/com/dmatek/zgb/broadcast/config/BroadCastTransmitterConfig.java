package com.dmatek.zgb.broadcast.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.dmatek.zgb.broadcast.interfaces.IBroadcastTransmitter;
import com.dmatek.zgb.broadcast.interfaces.imp.BroadCastTransmitter;
import com.dmatek.zgb.websocket.container.WebSocketContainerManager;

@Configuration
public class BroadCastTransmitterConfig {
	@Autowired
	private WebSocketContainerManager webSocketContainerManager;
	public IBroadcastTransmitter getBroadCastTransmitter() {
		return new BroadCastTransmitter(webSocketContainerManager);
	}
}
