package com.dmatek.zgb.warn.bean;

import com.dmatek.zgb.warn.bean.base.BaseTagWarnMessage;
/**
 * 低电量警告
 * @author zf
 * @data 2018年12月14日 下午4:51:42
 * @Description
 */
public class LowPowerWarn extends BaseTagWarnMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte curBattery, minLowBattery;// 当前卡片电量、最低电量
	public LowPowerWarn() {
		super();
	}
	public LowPowerWarn(byte curBattery, byte minLowBattery) {
		super();
		this.curBattery = curBattery;
		this.minLowBattery = minLowBattery;
	}
	public LowPowerWarn(String tagId, String tagName, String tagNo,
			String companyNo, String companyName, String jobTypeNo,
			String jobTypeName, int groupId, int regionId, String groupName,
			String regionName, String referId, String referName,
			byte curBattery,byte minLowBattery) {
		super(tagId, tagName, tagNo, companyNo, companyName, jobTypeNo, jobTypeName,
				groupId, regionId, groupName, regionName, referId, referName);
		this.curBattery = curBattery;
		this.minLowBattery = minLowBattery;
	}
	public byte getCurBattery() {
		return curBattery;
	}

	public void setCurBattery(byte curBattery) {
		this.curBattery = curBattery;
	}

	public byte getMinLowBattery() {
		return minLowBattery;
	}

	public void setMinLowBattery(byte minLowBattery) {
		this.minLowBattery = minLowBattery;
	}
}
