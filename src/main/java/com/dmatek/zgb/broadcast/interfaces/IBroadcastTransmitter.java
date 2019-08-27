package com.dmatek.zgb.broadcast.interfaces;

public interface IBroadcastTransmitter {
	//这里的广播发送的是JSON字符串
	void sendBroadcast(String containerName, String message) throws Exception;
}
