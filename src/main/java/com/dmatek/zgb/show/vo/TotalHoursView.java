package com.dmatek.zgb.show.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TotalHoursView {
	private String tagId, tagName, tagNo, companyName, 
				   jobTypeName, groupName; // 卡片ID, 人员编号, 人员名称
	private Date start, end;
	private float totalHours;
	private String dfTotalHours;
	public TotalHoursView() {
		super();
	}
	public TotalHoursView(String tagId, String tagName, String tagNo,
			String companyName, String jobTypeName, String groupName,
			Date start, Date end, float totalHours) {
		super();
		this.tagId = tagId;
		this.tagName = tagName;
		this.tagNo = tagNo;
		this.companyName = companyName;
		this.jobTypeName = jobTypeName;
		this.groupName = groupName;
		this.totalHours = totalHours;
		this.start = start;
		this.end = end;
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
	public String getTagNo() {
		return tagNo;
	}
	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
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
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getStart() {
		return start;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public void setStart(Date start) {
		this.start = start;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getEnd() {
		return end;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public void setEnd(Date end) {
		this.end = end;
	}
	public float getTotalHours() {
		return totalHours;
	}
	public void setTotalHours(float totalHours) {
		this.totalHours = totalHours;
	}
	public String getDfTotalHours() {
		return dfTotalHours;
	}
	public void setDfTotalHours(String dfTotalHours) {
		this.dfTotalHours = dfTotalHours;
	}
}
