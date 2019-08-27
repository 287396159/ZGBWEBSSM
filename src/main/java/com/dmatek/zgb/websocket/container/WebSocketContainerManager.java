package com.dmatek.zgb.websocket.container;

import java.util.List;
import java.util.Vector;

import javax.websocket.Session;
/**
 * WebSocket 的容器管理器
 * @author zhangfu
 * @data 2019年3月15日 下午3:45:01
 * @Description
 */
public class WebSocketContainerManager {
	private static Vector<BaseWebSocketContainer> webSocketContainers;
	public WebSocketContainerManager(){
		webSocketContainers = new Vector<BaseWebSocketContainer>();
		webSocketContainers.add(new WarnWebSocketContainer());
		webSocketContainers.add(new WebSocketContainer());
	}
	/**
	 * 添加用戶到Session
	 * @param userName
	 * @param session
	 */
	public void addUserToContainer(String containerName, String userName, Session session){
		for (BaseWebSocketContainer baseWebSocketContainer : webSocketContainers) {
			if(containerName.equalsIgnoreCase(baseWebSocketContainer.getContainerName())) {
				baseWebSocketContainer.addUser(userName, session);
				baseWebSocketContainer.displayContainerMessage();
			}
		}
	}
	/**
	 * 刪除User到緩存中
	 * @param containerName
	 * @param userName
	 * @param session
	 */
	public void removeToContainer(String containerName, String userName, Session session) {
		for (BaseWebSocketContainer baseWebSocketContainer : webSocketContainers) {
			if(containerName.equalsIgnoreCase(baseWebSocketContainer.getContainerName())) {
				baseWebSocketContainer.removeUser(userName, session);
				baseWebSocketContainer.displayContainerMessage();
			}
		}
	}
	public void sendMessageToUser(String containerName, String userName, String message) {
		for (BaseWebSocketContainer baseWebSocketContainer : webSocketContainers) {
			if(containerName.equalsIgnoreCase(baseWebSocketContainer.getContainerName())) {
				baseWebSocketContainer.sendMessage(userName, message);
				baseWebSocketContainer.displayContainerMessage();
			}
		}
	}
	public void sendMessageToAllUser(String containerName, String message) {
		for (BaseWebSocketContainer baseWebSocketContainer : webSocketContainers) {
			if(containerName.equalsIgnoreCase(baseWebSocketContainer.getContainerName())) {
				baseWebSocketContainer.sendMessageToAll(message);
			}
		}
	}
	/**
	 * 获取所有在线用户的session
	 * @param containerName
	 * @param userName
	 * @return
	 */
	public List<Session> getAllUsers(String containerName, String userName) {
		for (BaseWebSocketContainer baseWebSocketContainer : webSocketContainers) {
			if(containerName.equalsIgnoreCase(baseWebSocketContainer.getContainerName())) {
				return baseWebSocketContainer.getOnLineUsers().get(userName);
			}
		}
		return null;
	}
}
