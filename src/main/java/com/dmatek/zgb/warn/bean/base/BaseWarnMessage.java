package com.dmatek.zgb.warn.bean.base;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 警报讯息的抽象类
 * @author zf
 * @data 2018年12月14日 下午4:10:54
 * @Description
 */
public abstract class BaseWarnMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uuid;// 警告的uuid
	private boolean isHandle, isClear;// 是否处理、是否清楚
	private Date createTime, handleTime, clearTime;// 警报产生时间、警报处理时间、警报清除时间
	private int groupId, regionId;// 组群ID、区域ID
	private String groupName, regionName;// 组群名称、区域名称
	public BaseWarnMessage() {
		super();
		uuid = UUID.randomUUID().toString();
		createTime = new Date();
	}
	public BaseWarnMessage(int groupId, int regionId, String groupName,
			String regionName) {
		super();
		this.groupId = groupId;
		this.regionId = regionId;
		this.groupName = groupName;
		this.regionName = regionName;
		uuid = UUID.randomUUID().toString();
		createTime = new Date();
	}

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public boolean isHandle() {
		return isHandle;
	}
	public void setHandle(boolean isHandle) {
		this.isHandle = isHandle;
	}
	public boolean isClear() {
		return isClear;
	}
	public void setClear(boolean isClear) {
		this.isClear = isClear;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getCreateTime() {
		return createTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getHandleTime() {
		return handleTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getClearTime() {
		return clearTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public void setClearTime(Date clearTime) {
		this.clearTime = clearTime;
	}
	/**
	 * 处理警报讯息
	 */
	public void handle(){
		if(!isHandle){
			this.isHandle = true;
			this.handleTime = new Date();
		}
	}
	/**
	 * 清除警报讯息
	 */
	public void clear(){
		if(isHandle && !isClear){
			this.isClear = true;
			this.clearTime = new Date();
		}
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
}
