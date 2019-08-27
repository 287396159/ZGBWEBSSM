package com.dmatek.zgb.controller.refer.data;

public class ReferNumber {
	private String id;
	private int total;
	public ReferNumber() {
		super();
	}
	public ReferNumber(String id, int total) {
		super();
		this.id = id;
		this.total = total;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}
