package com.dmatek.zgb.db.pojo.setting;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 设置抽象类
 * @author zf
 * @data 2018年12月7日 下午3:31:01
 * @Description
 */
public abstract class BaseSettingPojo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Date createTime;
	private Date updateTime;
	private String createName, updateName;
	public BaseSettingPojo(Date createTime, Date updateTime, String createName,
			String updateName) {
		super();
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.createName = createName;
		this.updateName = updateName;
	}
	public BaseSettingPojo() {
		super();
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
	public Date getUpdateTime() {
		return updateTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
}
