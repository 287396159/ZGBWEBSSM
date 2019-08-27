package com.dmatek.zgb.broadcast.interfaces.imp;
import com.dmatek.zgb.broadcast.interfaces.IBroadcastTransmitter;
import com.dmatek.zgb.websocket.container.WebSocketContainerManager;
/**
 * 广播信息发送器
 * @author zf
 * @data 2018年12月21日 下午4:00:20
 * @Description
 */
public class BroadCastTransmitter implements IBroadcastTransmitter{
	private WebSocketContainerManager webSocketContainerManager;
	public BroadCastTransmitter(WebSocketContainerManager webSocketContainerManager){
		this.webSocketContainerManager = webSocketContainerManager;
	}
	@Override
	public void sendBroadcast(String containerName, String message) throws Exception {
		webSocketContainerManager.sendMessageToAllUser(containerName, message);
	}
	public WebSocketContainerManager getWebSocketContainerManager() {
		return webSocketContainerManager;
	}
	public void setWebSocketContainerManager(
			WebSocketContainerManager webSocketContainerManager) {
		this.webSocketContainerManager = webSocketContainerManager;
	}
}
