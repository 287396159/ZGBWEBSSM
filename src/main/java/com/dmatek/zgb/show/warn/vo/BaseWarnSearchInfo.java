package com.dmatek.zgb.show.warn.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public abstract class BaseWarnSearchInfo {
	private String name;
	private Date start, end;
	public BaseWarnSearchInfo() {
		super();
	}
	public BaseWarnSearchInfo(String name, Date start, Date end) {
		super();
		this.name = name;
		this.start = start;
		this.end = end;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getStart() {
		return start;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public void setStart(Date start) {
		this.start = start;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getEnd() {
		return end;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public void setEnd(Date end) {
		this.end = end;
	}
}
