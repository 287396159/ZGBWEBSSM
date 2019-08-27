package com.dmatek.zgb.db.pojo.permission;

import java.util.Date;

import com.dmatek.zgb.db.pojo.setting.BaseSettingPojo;

public class Role extends BaseSettingPojo{
	private static final long serialVersionUID = 1L;
	private int id;
	private String name, des;
	public Role() {
		super();
	}
	public Role(Date createTime, Date updateTime, String createName, String updateName,
			int id, String name, String des) {
		super(createTime, updateTime, createName, updateName);
		this.id = id;
		this.name = name;
		this.des = des;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	@Override
	public String toString() {
		return "[角色ID: " + id + ", 角色名稱: " + name + ", 角色描述: " + des + "]";
	}
}
