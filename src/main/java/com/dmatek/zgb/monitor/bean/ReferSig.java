package com.dmatek.zgb.monitor.bean;
/**
 * 距离指定基站信号强度类
 * @author zf
 * @data 2018年12月13日 上午9:15:41
 * @Description
 */
public class ReferSig {
	private String id; // 基站ID
	private byte rssi; // 距离基站的信号强度
	public ReferSig() {
		super();
	}
	public ReferSig(String id, byte rssi) {
		super();
		this.id = id;
		this.rssi = rssi;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public byte getRssi() {
		return rssi;
	}
	public void setRssi(byte rssi) {
		this.rssi = rssi;
	}
	@Override
	public String toString() {
		return "ReferSig [id=" + id + ", rssi=" + rssi + "]";
	}
}
