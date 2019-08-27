package com.dmatek.zgb.monitor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;

import com.dmatek.zgb.monitor.bean.TagPacket;
import com.dmatek.zgb.monitor.callback.MonitorCallBack;

public abstract class BaseDataMonitor implements Runnable {
	private static final int INIT_TAGSBOX_SIZE = 200;// 卡片数据包初始容器大小
	private static final int DATAPACK_BUFFSIZE = 2014;// 接收数据包的缓存大小
	private byte[] dataPackBuff = new byte[DATAPACK_BUFFSIZE];
	private Thread monitorThread = null;
	private DatagramSocket socket = null;
	private DatagramPacket packes = null;
	private HashMap<String, TagPacket> tagsBox = null;
	// 数据包回调函数
	private MonitorCallBack zgbMonitorCallBack;
	private boolean mark = false;
	public BaseDataMonitor(MonitorCallBack zgbMonitorCallBack) {
		this.zgbMonitorCallBack = zgbMonitorCallBack;
		tagsBox = new HashMap<String, TagPacket>(INIT_TAGSBOX_SIZE);
	}
	/**
	 * 初始化网络监控
	 * @throws UnknownHostException 
	 * @throws SocketException 
	 */
	public void initParam(String ip, int port, int... params) throws UnknownHostException, SocketException {
		InetAddress inetAddress = InetAddress.getByName(ip);
		socket = new DatagramSocket(port, inetAddress);
		packes = new DatagramPacket(dataPackBuff, 0, dataPackBuff.length);
		initOptimizes(params);
	}
	protected abstract void initOptimizes(int...optimizes);
	/**
	 * 清除接收容器中的定位数据包
	 */
	private void clearBox() {
		if(null != tagsBox) {
			tagsBox.clear();
		}
	}
	/**
	 * 开启网络监控
	 * @throws SocketException 
	 */
	public void start() throws SocketException {
		clearBox();
		mark = true;
		monitorThread = new Thread(this);
		monitorThread.start();
	}
	/**
	 * 关闭网络监控
	 */
	public void stop() {
		mark = false;
		if(null != monitorThread) {
			monitorThread.interrupt();
			monitorThread = null;
		}
		if(null != socket) {
			socket.close();
			socket = null;
		}
		packes = null;
	}
	/**
	 * 获取当前的网络监听状态
	 * @return
	 */
	public boolean getState() {
		return mark;
	}
	public synchronized DatagramSocket getSocket() {
		return socket;
	}
	public void setSocket(DatagramSocket socket) {
		this.socket = socket;
	}
	public DatagramPacket getPackes() {
		return packes;
	}
	public void setPackes(DatagramPacket packes) {
		this.packes = packes;
	}
	public HashMap<String, TagPacket> getTagsBox() {
		return tagsBox;
	}
	public void setTagsBox(HashMap<String, TagPacket> tagsBox) {
		this.tagsBox = tagsBox;
	}
	public MonitorCallBack getZgbMonitorCallBack() {
		return zgbMonitorCallBack;
	}
	public void setZgbMonitorCallBack(MonitorCallBack zgbMonitorCallBack) {
		this.zgbMonitorCallBack = zgbMonitorCallBack;
	}
	public boolean isMark() {
		return mark;
	}
	public void setMark(boolean mark) {
		this.mark = mark;
	}
	public static int getDatapackBuffsize() {
		return DATAPACK_BUFFSIZE;
	}
}
