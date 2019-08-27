package com.dmatek.zgb.warn.bean.base;

public class BaseTagWarnMessage extends BaseWarnMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tagId, tagName, tagNo;// 卡片ID、卡片名称、卡片编号
	private String companyNo, companyName, jobTypeNo, jobTypeName;// 公司编号、公司名称、工种编号、工种名称
	private String referId, referName;// 参考点ID、参考点名称
	public BaseTagWarnMessage() {
		super();
	}
	public BaseTagWarnMessage(String tagId, String tagName, String tagNo,
			String companyNo, String companyName, String jobTypeNo,
			String jobTypeName, int groupId, int regionId, String groupName,
			String regionName, String referId, String referName) {
		super(groupId, regionId, groupName, regionName);
		this.tagId = tagId;
		this.tagName = tagName;
		this.tagNo = tagNo;
		this.companyNo = companyNo;
		this.companyName = companyName;
		this.jobTypeNo = jobTypeNo;
		this.jobTypeName = jobTypeName;
		this.referId = referId;
		this.referName = referName;
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
	public String getJobTypeNo() {
		return jobTypeNo;
	}
	public void setJobTypeNo(String jobTypeNo) {
		this.jobTypeNo = jobTypeNo;
	}
	public String getJobTypeName() {
		return jobTypeName;
	}
	public void setJobTypeName(String jobTypeName) {
		this.jobTypeName = jobTypeName;
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
}
