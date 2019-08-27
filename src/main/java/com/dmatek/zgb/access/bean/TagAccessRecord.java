package com.dmatek.zgb.access.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 卡片进出记录
 * @author zhangfu
 * @data 2019年1月21日 下午5:23:58
 * @Description
 */
public class TagAccessRecord implements Serializable{
	private static final long serialVersionUID = 1L;
	private String uuid,  referId, referName;// 统计记录的主键,  参考点ID
	private String tagId, personNo, tagName, image; // 卡片ID, 人员编号, 人员名称
	private String companyNo, companyName;// 公司编号，公司名称
	private String jobTypeNo, jobTypeName;// 公司名称
	private int groupId, regionId;// 工地ID， 区域ID
	private String groupName,regionName;// 工地名称、区域名称
	private byte accessType;// 进出统计类型，1：上班，2：下班
	private String accessTypeName;// 進出類型名稱
	private Date accessTime;// 进出统计时间
	private float resTime;// 固定休息时间
	private boolean isEol;// 是否低电量
	public TagAccessRecord() {
		super();
	}
	public TagAccessRecord(String uuid, String referId, String tagId,
			String personNo, String tagName,String referName, String companyNo,
			String companyName, String jobTypeNo, String jobTypeName,
			int groupId, int regionId, String groupName, String regionName,
			byte accessType, Date accessTime,String image, float resTime,
			boolean isEol,String accessTypeName) {
		super();
		this.uuid = uuid;
		this.referId = referId;
		this.tagId = tagId;
		this.personNo = personNo;
		this.tagName = tagName;
		this.referName = referName;
		this.companyNo = companyNo;
		this.companyName = companyName;
		this.jobTypeNo = jobTypeNo;
		this.jobTypeName = jobTypeName;
		this.groupId = groupId;
		this.regionId = regionId;
		this.groupName = groupName;
		this.regionName = regionName;
		this.accessType = accessType;
		this.accessTime = accessTime;
		this.image = image;
		this.resTime = resTime;
		this.isEol = isEol;
		this.accessTypeName = accessTypeName;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getReferId() {
		return referId;
	}
	public void setReferId(String referId) {
		this.referId = referId;
	}
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public String getPersonNo() {
		return personNo;
	}
	public void setPersonNo(String personNo) {
		this.personNo = personNo;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
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
	public byte getAccessType() {
		return accessType;
	}
	public void setAccessType(byte accessType) {
		this.accessType = accessType;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getAccessTime() {
		return accessTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}
	public String getReferName() {
		return referName;
	}
	public void setReferName(String referName) {
		this.referName = referName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public float getResTime() {
		return resTime;
	}
	public void setResTime(float resTime) {
		this.resTime = resTime;
	}
	public boolean isEol() {
		return isEol;
	}
	public void setEol(boolean isEol) {
		this.isEol = isEol;
	}
	public String getAccessTypeName() {
		return accessTypeName;
	}
	public void setAccessTypeName(String accessTypeName) {
		this.accessTypeName = accessTypeName;
	}
	@Override
	public String toString() {
		return "TagAccessRecord [uuid=" + uuid + ", tagId=" + tagId
				+ ", accessType=" + accessType + ", accessTime=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accessTime)
				+ "]";
	}
}
