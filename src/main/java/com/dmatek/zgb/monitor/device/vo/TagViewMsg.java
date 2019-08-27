package com.dmatek.zgb.monitor.device.vo;

import java.util.Date;

public class TagViewMsg {
	private byte bat, rssi; // 电池电量
	private int groupId, regionId, notMoveTime; // 组群ID,区域ID、未移动时间
	// 卡片ID、卡片名称、公司编号、公司名称、参考点ID、参考点名称、版本号
	private String id, name, companyNo, companyName, referId, referName;
	private boolean isPersonnelHelp, isNotMove, isLowPower, isAreaControl,
					isAbnormalTag;
	private Date reportTime;
	public TagViewMsg() {
		super();
	}
	public TagViewMsg(byte bat, byte rssi, int groupId, int regionId,
			int notMoveTime, String id, String name, String companyNo,
			String companyName, String referId, String referName,
			boolean isPersonnelHelp, boolean isNotMove,
			boolean isLowPower, boolean isAreaControl, boolean isAbnormalTag,
			Date reportTime) {
		super();
		this.bat = bat;
		this.rssi = rssi;
		this.groupId = groupId;
		this.regionId = regionId;
		this.notMoveTime = notMoveTime;
		this.id = id;
		this.name = name;
		this.companyNo = companyNo;
		this.companyName = companyName;
		this.referId = referId;
		this.referName = referName;
		this.isPersonnelHelp = isPersonnelHelp;
		this.isNotMove = isNotMove;
		this.isLowPower = isLowPower;
		this.isAreaControl = isAreaControl;
		this.isAbnormalTag = isAbnormalTag;
		this.reportTime = reportTime;
	}
	public byte getBat() {
		return bat;
	}
	public void setBat(byte bat) {
		this.bat = bat;
	}
	public byte getRssi() {
		return rssi;
	}
	public void setRssi(byte rssi) {
		this.rssi = rssi;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public int getNotMoveTime() {
		return notMoveTime;
	}
	public void setNotMoveTime(int notMoveTime) {
		this.notMoveTime = notMoveTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompanyNo() {
		return companyNo;
	}
	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getReferId() {
		return referId;
	}
	public void setReferId(String referId) {
		this.referId = referId;
	}
	public String getReferName() {
		return referName;
	}
	public void setReferName(String referName) {
		this.referName = referName;
	}
	public boolean isPersonnelHelp() {
		return isPersonnelHelp;
	}
	public void setPersonnelHelp(boolean isPersonnelHelp) {
		this.isPersonnelHelp = isPersonnelHelp;
	}
	public boolean isNotMove() {
		return isNotMove;
	}
	public void setNotMove(boolean isNotMove) {
		this.isNotMove = isNotMove;
	}
	public boolean isLowPower() {
		return isLowPower;
	}
	public void setLowPower(boolean isLowPower) {
		this.isLowPower = isLowPower;
	}
	public boolean isAreaControl() {
		return isAreaControl;
	}
	public void setAreaControl(boolean isAreaControl) {
		this.isAreaControl = isAreaControl;
	}
	public boolean isAbnormalTag() {
		return isAbnormalTag;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	public void setAbnormalTag(boolean isAbnormalTag) {
		this.isAbnormalTag = isAbnormalTag;
	}
	public Date getReportTime() {
		return reportTime;
	}
}
