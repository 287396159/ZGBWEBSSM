package com.dmatek.zgb.broadcast.vo;

import net.sf.json.JSONObject;

public class BroadCastVo {
	private int type;
	private String msg;
	private Object data;
	public BroadCastVo() {
		super();
	}
	public BroadCastVo(int type, String msg, Object data) {
		super();
		this.type = type;
		this.msg = msg;
		this.data = data;
	}
	public String transformJson() {
		return JSONObject.fromBean(this).toString();
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
