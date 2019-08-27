package com.dmatek.zgb.monitor.bean;

import java.net.InetAddress;
import java.util.Date;

import com.dmatek.zgb.utils.StringUtil;
/**
 * 基站上报的数据包
 * @author zf
 * @data 2018年12月13日 上午9:53:18
 * @Description
 */
public class NodePack extends BaseDevicePack{
	private InetAddress endPoint;
	private int port;
	private String textVersion;
	public NodePack() {
		super();
	}
	public NodePack(String id,int sleepTime, long tick,
			boolean status, Date reportTime, int version,InetAddress endPoint, int port) {
		super(id,sleepTime, tick, status, reportTime, version);
		this.endPoint = endPoint;
		this.port = port;
		this.textVersion = StringUtil.convertVersion(version);
	}
	public InetAddress getEndpoint() {
		return endPoint;
	}
	public void setEndpoint(InetAddress endPoint) {
		this.endPoint = endPoint;
	}
	public String getTextVersion() {
		return textVersion;
	}
	public void setTextVersion(String textVersion) {
		this.textVersion = textVersion;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	@Override
	public String toString() {
		return "NodePack [getId()=" + getId() + ", getSleepTime()="
				+ getSleepTime() + ", getReportTime()=" + getReportTime() + "]";
	}
}
