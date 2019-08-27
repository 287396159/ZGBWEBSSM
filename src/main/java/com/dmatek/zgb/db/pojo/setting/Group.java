package com.dmatek.zgb.db.pojo.setting;

import java.util.Date;
/**
 * 组群/工地资料
 * @author zf
 * @data 2018年12月7日 下午3:30:39
 * @Description
 */
public class Group extends BaseSettingPojo{
	private static final long serialVersionUID = 1L;
	private int id;
	private String name, des;
	public Group(Date createDate, Date updateDate, String createName, String updateName,
			int id, String name, String des) {
		super(createDate, updateDate, createName, updateName);
		this.id = id;
		this.name = name;
		this.des = des;
	}
	public Group() {
		super();
	}
	public Group(Date createDate, Date updateDate, String createName, String updateName) {
		super(createDate, updateDate, createName, updateName);
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
		return "[主鍵: " + id + ", 名稱 : " + name + ", 描述: " + des + "]";
	}
}
