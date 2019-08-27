package com.dmatek.zgb.warn.bean.base;

public abstract class BaseAbnormalNodeWarn extends BaseNodeWarnMessage {
	private static final long serialVersionUID = 1L;
	private int curDisTime, sleepTime;// 设备断开时间、休眠时间
	public BaseAbnormalNodeWarn() {
		super();
	}
	public BaseAbnormalNodeWarn(String nodeId, String nodeName, int groupId,
			int regionId, String groupName, String regionName, 
			int curDisTime, int sleepTime) {
		super(nodeId, nodeName, groupId, regionId, groupName, regionName);
		this.curDisTime = curDisTime;
		this.sleepTime = sleepTime;
	}
	public int getCurDisTime() {
		return curDisTime;
	}
	public void setCurDisTime(int curDisTime) {
		this.curDisTime = curDisTime;
	}
	public int getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}
}
