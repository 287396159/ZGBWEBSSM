package com.dmatek.zgb.show.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SearchTrack {
	private String tagInfo;
	private Date start, end;
	private int page;
	public SearchTrack() {
		super();
	}
	public SearchTrack(String tagInfo, Date start, Date end, int page) {
		super();
		this.tagInfo = tagInfo;
		this.start = start;
		this.end = end;
		this.page = page;
	}
	public String getTagInfo() {
		return tagInfo;
	}
	public void setTagInfo(String tagInfo) {
		this.tagInfo = tagInfo;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
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
	@Override
	public String toString() {
		return "[卡片訊息: " + tagInfo + ", 開始時間: " + start
				+ ", 結束時間: " + end + ", 頁數: " + page + "]";
	}
	
}
