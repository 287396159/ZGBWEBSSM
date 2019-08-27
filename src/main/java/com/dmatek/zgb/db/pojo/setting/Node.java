package com.dmatek.zgb.db.pojo.setting;

import java.util.Date;
/**
 * 基站讯息
 * @author zf
 * @data 2018年12月7日 下午5:43:41
 * @Description
 */
public class Node extends BaseSettingPojo{
	private static final long serialVersionUID = 1L;
	private String id,name,typeName;
	private int type,mapX,mapY,regionId;
	private float width, height;
	public Node() {
		super();
	}
	public Node(Date createTime, Date updateTime, String createName, String updateName,
			String id, String name, int type, int mapX, int mapY,
			float width,float height,int regionId) {
		super(createTime, updateTime, createName, updateName);
		this.id = id;
		this.name = name;
		this.type = type;
		this.mapX = mapX;
		this.mapY = mapY;
		this.width = width;
		this.height = height;
		this.regionId = regionId;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getMapX() {
		return mapX;
	}
	public void setMapX(int mapX) {
		this.mapX = mapX;
	}
	public int getMapY() {
		return mapY;
	}
	public void setMapY(int mapY) {
		this.mapY = mapY;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	@Override
	public String toString() {
		return "[ID: " + id + ", 名稱: " + name + ", 類型: " + typeName
				+ ", 地圖X坐標: " + mapX + ", 地圖Y坐標: " + mapY
				+ ", 區域ID: " + regionId + "]";
	}
	
}
