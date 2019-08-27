package com.dmatek.zgb.websocket.container;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.Session;

public class WebSocketContainer extends BaseWebSocketContainer{
	public static final String CONTAINERNAME = "WebSocketContainer";
	private static Map<String, List<Session>> onLineUsers = new ConcurrentHashMap<String, List<Session>>();
	public WebSocketContainer(String containerName) {
		super(containerName);
	}
	public WebSocketContainer() {
		super(CONTAINERNAME);
	}
	public Map<String, List<Session>> getOnLineUsers() {
		return onLineUsers;
	}
}
