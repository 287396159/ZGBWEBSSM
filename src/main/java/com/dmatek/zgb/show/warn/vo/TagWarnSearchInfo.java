package com.dmatek.zgb.show.warn.vo;

public class TagWarnSearchInfo extends BaseWarnSearchInfo {
	private String companyName, jobTypeName;
	private String groupName;
	public TagWarnSearchInfo() {
		super();
	}
	public TagWarnSearchInfo(String companyName, String jobTypeName,
			String groupName) {
		super();
		this.companyName = companyName;
		this.jobTypeName = jobTypeName;
		this.groupName = groupName;
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
}
