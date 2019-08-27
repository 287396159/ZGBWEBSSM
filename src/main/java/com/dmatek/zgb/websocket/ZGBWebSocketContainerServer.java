package com.dmatek.zgb.websocket;

import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.dmatek.zgb.websocket.container.WebSocketContainerManager;

@Component
@ServerEndpoint(value = "/websocket/{connectType}/{userName}")
public class ZGBWebSocketContainerServer extends WebSocketContainerManager {
	private static final Logger logger = Logger.getLogger(ZGBWebSocketContainerServer.class);
	/**
	 * 建立連接
	 * @param session
	 */
	@OnOpen
	public void onOpen(@PathParam("connectType") String connectType, @PathParam("userName") String userName, Session session) {
		getLogger().warn("新建立壹個連接【userName : " + userName + ", session : " + session.toString() + "】...");
		addUserToContainer(connectType, userName, session);
	}
	/**
	 * 接收消息
	 * @param userId
	 * @param message
	 */
	@OnMessage
	public void onMessage(@PathParam("connectType") String connectType, @PathParam("userName") String userName, String message){
		getLogger().warn("【userName : " + userName + "】：" + message);
		sendMessageToAllUser(connectType, message);
	}
	/**
	 * 斷開連接
	 */
	@OnClose
	public void onClose(@PathParam("connectType") String connectType, @PathParam("userName") String userName, Session session) {
		getLogger().warn("斷開壹個連接【userName : " + userName + ", session : " + session.toString() + "】...");
		removeToContainer(connectType, userName, session);
	}
	/**
	 * 異常
	 * @param userId
	 * @param session
	 */
	@OnError
	public void onError(@PathParam("connectType") String connectType,@PathParam("userName") String userName, Session session, Throwable throwable) {
		getLogger().error("WebSocket => Params" + "【 connectType: " + connectType+"， userName: " + userName+"，sessionId: " + session.getId()+"】");
		getLogger().error("WebSocket => onError : " + throwable);
		try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	// 关闭连接
        	removeToContainer(connectType, userName, session);
        }
        throwable.printStackTrace();
	}
	public static Logger getLogger() {
		return logger;
	}
}
