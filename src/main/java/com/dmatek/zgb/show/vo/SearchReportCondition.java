package com.dmatek.zgb.show.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SearchReportCondition {
	private String name;
	private String companyNo, jobTypeNo, groupId;
	private Date start, end;
	public SearchReportCondition() {
		super();
	}
	public SearchReportCondition(String name, String companyNo,
			String jobTypeNo, String groupId, Date start, Date end) {
		super();
		this.name = name;
		this.companyNo = companyNo;
		this.jobTypeNo = jobTypeNo;
		this.groupId = groupId;
		this.start = start;
		this.end = end;
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
	public String getJobTypeNo() {
		return jobTypeNo;
	}
	public void setJobTypeNo(String jobTypeNo) {
		this.jobTypeNo = jobTypeNo;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
}
