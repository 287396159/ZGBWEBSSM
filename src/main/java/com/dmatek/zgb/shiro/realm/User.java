package com.dmatek.zgb.shiro.realm;

import java.util.Date;

import com.dmatek.zgb.db.pojo.permission.Account;

public class User extends Account {
	private static final long serialVersionUID = 1L;
	private String roleName;
	public User(String roleName) {
		super();
		this.roleName = roleName;
	}
	public User(Account account,String roleName) {
		super(account.getCreateTime(), account.getUpdateTime(), account
				.getCreateName(), account.getUpdateName(), account.getId(), account
				.getRoleId(), account.getStatus(), account.getName(), account
				.getPsw(), account.getReportTime());
		this.roleName = roleName;
	}
	public User(Date createTime, Date updateTime, String createName, String updateName,
			int id, int roleId, int status, String name, String psw,
			Date reportTime,String roleName) {
		super(createTime, updateTime, createName, updateName, id, roleId, status, name,
				psw, reportTime);
		this.roleName = roleName;
	}
	
	@Override
	public String getRoleName() {
		return roleName;
	}
	@Override
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@Override
	public String toString() {
		return "User [roleName=" + roleName + ", getId()=" + getId()
				+ ", getRoleId()=" + getRoleId() + ", getName()=" + getName()
				+ "]";
	}
}
