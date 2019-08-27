package com.dmatek.zgb.udp.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import org.apache.log4j.Logger;
/**
 * udp发送网络数据包的客户端  
 * @author zhangfu 
 * @data 2019年4月18日 上午11:39:19 
 * @Description 
 * 用于设置节点、参考点等参数  
 */
public class UdpClient {
	// 日志记录
	private static Logger logger = Logger.getLogger(UdpClient.class);
	public static void sendPacket(DatagramSocket socket, InetAddress address,
			int port, byte[] bytes) throws IOException {
		DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length,
				address, port);
		if (null != datagramPacket && null != socket) {
			socket.send(datagramPacket);
		}
	}
	public static Logger getLogger() {
		return logger;
	}
	public static void setLogger(Logger logger) {
		UdpClient.logger = logger;
	}
}
