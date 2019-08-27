package com.dmatek.zgb.db.pojo.setting;

import java.util.Date;
/**
 * 区域讯息
 * @author zf
 * @data 2018年12月7日 下午5:01:59
 * @Description
 */
public class Region extends BaseSettingPojo{
	private static final long serialVersionUID = 1L;
	private int id,groupId;
	private String name,image,groupName;
	private int number;
	public Region() {
		super();
	}
	public Region(Date createTime, Date updateTime, String createName, String updateName,
			int id, int groupId,String groupName ,String name, String image,float width,float height) {
		super(createTime, updateTime, createName, updateName);
		this.id = id;
		this.groupId = groupId;
		this.name = name;
		this.image = image;
		this.groupName = groupName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGroupId() {
		return groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "[主鍵: " + id + ", 組別: " + groupId + ", 區域名稱: " + name
				+ ", 地圖路徑: " + image + ", 組別名稱: " + groupName + "]";
	}
}
