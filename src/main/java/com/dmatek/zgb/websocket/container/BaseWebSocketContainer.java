package com.dmatek.zgb.websocket.container;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.websocket.Session;
import org.apache.log4j.Logger;
import org.apache.tomcat.websocket.WsSession;

public abstract class BaseWebSocketContainer {
	private Logger logger = Logger.getLogger(BaseWebSocketContainer.class);
	private String containerName;
	public BaseWebSocketContainer(String containerName) {
		this.containerName = containerName;
	}
	/**
	 * 添加用户讯息
	 * @param id
	 * @param session
	 */
	public void addUser(String userName, Session session) {
		List<Session> sessions = getOnLineUsers().get(userName);
		if(null != sessions) {// 當session不為空時
			sessions.add(session);
		} else {// 當session為空時
			sessions = new ArrayList<Session>();
			sessions.add(session);
		}
		getOnLineUsers().put(userName, sessions);
	}
	/**
	 * 删除用户讯息
	 * @param id
	 */
	public void removeUser(String userName, Session session) {
		List<Session> sessions = getOnLineUsers().get(userName);
		if(null != sessions) {
			sessions.remove(session);
		} 
		if(null == sessions || sessions.size() <= 0) {
			getOnLineUsers().remove(userName);
		} else {
			getOnLineUsers().put(userName, sessions);
		}
	}
	/**
	 * 发送消息给指定的Session
	 * @param id
	 * @param message
	 */
	public void sendMessage(String userName, String message){
		List<Session> sessions = getOnLineUsers().get(userName);
		if(null != sessions) {
			for (int i = 0; i < sessions.size(); i++) {
				sessions.get(i).getAsyncRemote().sendText(message);
			}
		}
	}
	/**
	 * 发送消息给所有的用户
	 * @param message
	 */
	public void sendMessageToAll(String message){
		Iterator<String> iterator = getOnLineUsers().keySet().iterator();
		while (iterator.hasNext()) {
			String userId = iterator.next();
			sendMessage(userId, message);
		}
	}
	public int getTotalSize(){
		Iterator<String> iterators = getOnLineUsers().keySet().iterator();
		int total = 0;
		while (iterators.hasNext()) {
			String userName = (String) iterators.next();
			List<Session> sessions = getOnLineUsers().get(userName);
			total += sessions.size();
		}
		return total;
	}
	public abstract Map<String, List<Session>> getOnLineUsers();
	/**
	 * 打印容器的 所有訊息
	 */
	public void displayContainerMessage() {
		Iterator<String> iterators = getOnLineUsers().keySet().iterator();
		getLogger().warn("===================== print Container Information========================");
		getLogger().warn("--- container Name: " + getContainerName());
		getLogger().warn("--- container size: " + getTotalSize());
		while (iterators.hasNext()) {
			String userName = (String) iterators.next();
			getLogger().warn("   userName : " + userName);
			List<Session> sessions = getOnLineUsers().get(userName);
			for (Session session : sessions) {
				getLogger().warn("   httpSessionId: " + ((WsSession)session).getHttpSessionId());
				getLogger().warn("   sessionId: " + session.getId());
				getLogger().warn("---memory address" + session);
			}
		}
	}
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}
	public String getContainerName() {
		return containerName;
	}
}
