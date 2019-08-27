package com.dmatek.zgb.websocket.container;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.Session;

public class WarnWebSocketContainer extends BaseWebSocketContainer {
	public static final String CONTAINERNAME = "WarnWebSocketContainer";
	private static Map<String, List<Session>> onLineUsers = new ConcurrentHashMap<String, List<Session>>();
	public WarnWebSocketContainer(String containerName) {
		super(containerName);
	}
	public WarnWebSocketContainer() {
		super(CONTAINERNAME);
	}
	public Map<String, List<Session>> getOnLineUsers() {
		return onLineUsers;
	}
}
