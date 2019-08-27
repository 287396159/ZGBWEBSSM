package com.dmatek.zgb.warn.bean;

import com.dmatek.zgb.warn.bean.base.BaseTagWarnMessage;
/**
 * 卡片断开警告
 * @author zf
 * @data 2018年12月14日 下午4:52:05
 * @Description
 */
public class AbnormalTagWarn extends BaseTagWarnMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int curDisTime, sleepTime;// 设备断开时间、休眠时间
	private int sleepMode;// 0: 表示移动休眠，1：表示静止休眠
	public AbnormalTagWarn() {
		super();
	}
	public AbnormalTagWarn(int curDisTime, int sleepTime) {
		super();
		this.curDisTime = curDisTime;
		this.sleepTime = sleepTime;
	}
	public AbnormalTagWarn(String tagId, String tagName, String tagNo,
			String companyNo, String companyName, String jobTypeNo,
			String jobTypeName, int groupId, int regionId, String groupName,
			String regionName, String referId, String referName,
			int curDisTime, int sleepTime, int sleepMode) {
		super(tagId, tagName, tagNo, companyNo, companyName, jobTypeNo, jobTypeName,
				groupId, regionId, groupName, regionName, referId, referName);
		this.curDisTime = curDisTime;
		this.sleepTime = sleepTime;
		this.sleepMode = sleepMode;
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
	public int getSleepMode() {
		return sleepMode;
	}
	public void setSleepMode(int sleepMode) {
		this.sleepMode = sleepMode;
	}
}
