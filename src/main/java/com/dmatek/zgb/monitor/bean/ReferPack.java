package com.dmatek.zgb.monitor.bean;

import java.net.InetAddress;
import java.util.Date;
/**
 * 参考点上报的数据包
 * @author Administrator
 * @data 2018年12月13日 上午9:53:03
 * @Description
 */
public class ReferPack extends NodePack{
	private String bId;// 将参考点上报的基站ID
	public ReferPack() {
		super();
	}
	public ReferPack(String id,String bid, int sleepTime, long tick, boolean status,
			Date reportTime, int version, InetAddress endPoint, int port) {
		super(id, sleepTime, tick, status, reportTime, version, endPoint, port);
		this.bId = bid;
	}
	public String getbId() {
		return bId;
	}
	public void setbId(String bId) {
		this.bId = bId;
	}
}
