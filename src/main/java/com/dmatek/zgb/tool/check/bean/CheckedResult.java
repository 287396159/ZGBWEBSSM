package com.dmatek.zgb.tool.check.bean;

public class CheckedResult {
	private boolean isOk;
	private String msg;
	private Object data;
	public CheckedResult(boolean isOk, String msg) {
		super();
		this.isOk = isOk;
		this.msg = msg;
	}
	public CheckedResult(boolean isOk, String msg,Object data) {
		super();
		this.isOk = isOk;
		this.msg = msg;
		this.data = data;
	}
	public CheckedResult() {
		super();
	}
	public boolean isOk() {
		return isOk;
	}
	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
