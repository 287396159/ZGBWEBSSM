package com.dmatek.zgb.monitor.bean;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 设备上报数据包的抽象类
 * @author Administrator
 * @data 2018年12月13日 上午9:53:43
 * @Description
 */
public abstract class BaseDevicePack {
	private String id;// 设备ID
	private int sleepTime;// 设备上报的间隔时间
	private long tick;// 上报时的计数值
	private boolean status = true;// 设备当前的状态,连接/断开状态
	private Date reportTime;// 上报时间
	private int version;// 设备版本号
	public BaseDevicePack() {
		super();
	}
	public BaseDevicePack(String id,int sleepTime, long tick,
			boolean status, Date reportTime, int version) {
		super();
		this.id = id;
		this.sleepTime = sleepTime;
		this.tick = tick;
		this.status = status;
		this.reportTime = reportTime;
		this.version = version;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}
	public long getTick() {
		return tick;
	}
	public void setTick(long tick) {
		this.tick = tick;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getReportTime() {
		return reportTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
}
