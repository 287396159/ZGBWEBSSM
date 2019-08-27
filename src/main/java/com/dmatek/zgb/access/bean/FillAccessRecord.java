package com.dmatek.zgb.access.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FillAccessRecord {
	private String no, name, tagId, referId;
	private int groupId, accessType;
	private Date accessTime;
	private float resTime;
	public FillAccessRecord() {
		super();
	}
	public FillAccessRecord(String no, String name, String tagId, int groupId,
			String referId, int accessType, Date accessTime, float resTime) {
		super();
		this.no = no;
		this.name = name;
		this.tagId = tagId;
		this.groupId = groupId;
		this.referId = referId;
		this.accessType = accessType;
		this.accessTime = accessTime;
		this.resTime = resTime;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getReferId() {
		return referId;
	}
	public void setReferId(String referId) {
		this.referId = referId;
	}
	public int getAccessType() {
		return accessType;
	}
	public void setAccessType(int accessType) {
		this.accessType = accessType;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	public Date getAccessTime() {
		return accessTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}
	public float getResTime() {
		return resTime;
	}
	public void setResTime(float resTime) {
		this.resTime = resTime;
	}
}
