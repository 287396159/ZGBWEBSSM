package com.dmatek.zgb.db.pojo.permission;

import java.util.Date;

import com.dmatek.zgb.db.pojo.setting.BaseSettingPojo;

public class Role_Permission extends BaseSettingPojo{
	private static final long serialVersionUID = 1L;
	private int role_id, permissionId;
	public Role_Permission(Date createTime, Date updateTime, String createName,
			String updateName, int role_id, int permissionId) {
		super(createTime, updateTime, createName, updateName);
		this.role_id = role_id;
		this.permissionId = permissionId;
	}
	public Role_Permission() {
		super();
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public int getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}
}
