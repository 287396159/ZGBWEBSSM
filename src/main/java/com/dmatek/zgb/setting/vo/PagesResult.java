package com.dmatek.zgb.setting.vo;

public class PagesResult extends Result {
	private int pages;
	public PagesResult(int code, String msg, Object data, int pages) {
		super(code, msg, data);
		this.pages = pages;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
}
