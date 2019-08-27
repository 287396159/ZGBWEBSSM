package com.dmatek.zgb.show.vo;

public class TagCheckView {
	private String tagName, tagNo;// 卡片ID、卡片名称、卡片编号
	private String companyNo, jobTypeNo;// 公司编号、工种编号
	private int groupId, regionId;// 组群ID、区域ID
	private boolean isPersonnelHelp, isNotMove, isLowPower, isAreaControl,
	isAbnormalTag;
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getTagNo() {
		return tagNo;
	}
	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
	}
	public String getCompanyNo() {
		return companyNo;
	}
	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}
	public String getJobTypeNo() {
		return jobTypeNo;
	}
	public void setJobTypeNo(String jobTypeNo) {
		this.jobTypeNo = jobTypeNo;
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
	public void setAbnormalTag(boolean isAbnormalTag) {
		this.isAbnormalTag = isAbnormalTag;
	}
}
