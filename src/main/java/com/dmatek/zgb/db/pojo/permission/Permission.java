package com.dmatek.zgb.db.pojo.permission;

import com.dmatek.zgb.db.pojo.setting.BaseSettingPojo;

public class Permission extends BaseSettingPojo{
	private static final long serialVersionUID = 1L;
	private int id;
	private String name, resource;
	public Permission() {
		super();
	}
	public Permission(String name, String resource) {
		super();
		this.name = name;
		this.resource = resource;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "[權限ID: " + id + ", 權限名稱: " + name + ", 權限資源："
				+ resource + "]";
	}
	
}
