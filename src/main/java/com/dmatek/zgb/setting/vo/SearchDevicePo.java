package com.dmatek.zgb.setting.vo;

public class SearchDevicePo {
	private String id;
	private String name;
	private String type;// 设备类型
	private String version;// 版本
	public SearchDevicePo(String id) {
		super();
		this.id = id;
	}
	public SearchDevicePo (String id, String type, String version) {
		super();
		this.id = id;
		this.type = type;
		this.version = version;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
}
