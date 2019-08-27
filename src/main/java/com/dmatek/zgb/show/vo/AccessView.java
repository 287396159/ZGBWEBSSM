package com.dmatek.zgb.show.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AccessView {
	private String enterReferId, enterReferName, 
				   leaveReferId, leaveReferName;// 统计记录的主键,  参考点ID
	private String tagId, tagName, tagNo; // 卡片ID, 人员编号, 人员名称
	private String companyName;// 公司编号，公司名称
	private String jobTypeName;// 公司名称
	private String groupName, regionName;
	private Date onDutyTime, offDutyTime;// 上下班时间
	private String taskTime;// 工时
	private float resTime;// 固定休息时间
	public AccessView() {
		super();
	}
	public AccessView(String enterReferId, String enterReferName,
			String leaveReferId, String leaveReferName,
			String tagId, String tagName, String companyName, 
			String jobTypeName, Date onDutyTime, Date offDutyTime, 
			String tagNo, float resTime, String groupName, String regionName) {
		super();
		this.enterReferId = enterReferId;
		this.enterReferName = enterReferName;
		this.leaveReferId = leaveReferId;
		this.leaveReferName = leaveReferName;
		this.tagId = tagId;
		this.tagName = tagName;
		this.companyName = companyName;
		this.jobTypeName = jobTypeName;
		this.onDutyTime = onDutyTime;
		this.offDutyTime = offDutyTime;
		this.resTime = resTime;
		this.groupName = groupName;
		this.regionName = regionName;
	}
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getJobTypeName() {
		return jobTypeName;
	}
	public void setJobTypeName(String jobTypeName) {
		this.jobTypeName = jobTypeName;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getOnDutyTime() {
		return onDutyTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public void setOnDutyTime(Date onDutyTime) {
		this.onDutyTime = onDutyTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getOffDutyTime() {
		return offDutyTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public void setOffDutyTime(Date offDutyTime) {
		this.offDutyTime = offDutyTime;
	}
	public String getEnterReferId() {
		return enterReferId;
	}
	public void setEnterReferId(String enterReferId) {
		this.enterReferId = enterReferId;
	}
	public String getEnterReferName() {
		return enterReferName;
	}
	public void setEnterReferName(String enterReferName) {
		this.enterReferName = enterReferName;
	}
	public String getLeaveReferId() {
		return leaveReferId;
	}
	public void setLeaveReferId(String leaveReferId) {
		this.leaveReferId = leaveReferId;
	}
	public String getLeaveReferName() {
		return leaveReferName;
	}
	public void setLeaveReferName(String leaveReferName) {
		this.leaveReferName = leaveReferName;
	}
	public String getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}
	public String getTagNo() {
		return tagNo;
	}
	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
	}
	public float getResTime() {
		return resTime;
	}
	public void setResTime(float resTime) {
		this.resTime = resTime;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	@Override
	public String toString() {
		return "[進入參考點ID: " + enterReferId + ", 進入參考點名稱: "
				+ enterReferName + ", 離開參考點ID: " + leaveReferId
				+ ", 離開參考點名稱: " + leaveReferName + ", 卡片ID: " + tagId
				+ ", 卡片名稱: " + tagName + ", 卡片編號: " + tagNo
				+ ", 公司名稱: " + companyName + ", 工種名稱: "
				+ jobTypeName + ", 上班時間: " + onDutyTime + ", 下班時間: "
				+ offDutyTime + ", 工作時間: " + taskTime + ", 休息時間: "
				+ resTime + "]";
	}
	
}
