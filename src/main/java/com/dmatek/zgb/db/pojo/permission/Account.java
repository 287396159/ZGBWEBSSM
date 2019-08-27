package com.dmatek.zgb.db.pojo.permission;

import java.util.Date;

import com.dmatek.zgb.db.pojo.setting.BaseSettingPojo;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Account extends BaseSettingPojo{
	private static final long serialVersionUID = 1L;
	private int id, status, number;
	private int roleId;
	private String roleName;
	private String name;
	private String psw;
	private Date reportTime;
	public Account() {
		super();
	}
	public Account(Date createTime, Date updateTime, String createName,
			String updateName, int id, int roleId, int status, String name,
			String psw, Date reportTime) {
		super(createTime, updateTime, createName, updateName);
		this.id = id;
		this.roleId = roleId;
		this.status = status;
		this.name = name;
		this.psw = psw;
		this.reportTime = reportTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getReportTime() {
		return reportTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "[角色名稱: " + roleName + ", 賬戶名稱: " + name + ", 密碼: "
				+ psw + "]";
	}
}
