package com.dmatek.zgb.db.pojo.setting;

import java.util.Date;

public class GroupTagNumber extends Group {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int number;
	public GroupTagNumber(Date createDate, Date updateDate, String createName,
			String updateName, int id, String name, String des, int number) {
		super(createDate, updateDate, createName, updateName, id, name, des);
		this.number = number;
	}
	public GroupTagNumber() {
		super();
	}
	public GroupTagNumber(int number,Group group) {
		super(group.getCreateTime(), group.getUpdateTime(), group.getCreateName(), 
			  group.getUpdateName(), group.getId(), group.getName(), group.getDes());
		this.number = number;
	}
	public GroupTagNumber(Date createDate, Date updateDate, String createName,
			String updateName) {
		super(createDate, updateDate, createName, updateName);
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	};
}
