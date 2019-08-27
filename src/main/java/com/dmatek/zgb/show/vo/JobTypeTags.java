package com.dmatek.zgb.show.vo;

import java.util.List;

public class JobTypeTags {
	// 工种编号、工种名称、工种颜色
	private String no, name, color;
	// 改工种的总人数
	private int total;
	private List<TagCheckView> tagCheckes;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<TagCheckView> getTagCheckes() {
		return tagCheckes;
	}
	public void setTagCheckes(List<TagCheckView> tagCheckes) {
		this.tagCheckes = tagCheckes;
	}
}
