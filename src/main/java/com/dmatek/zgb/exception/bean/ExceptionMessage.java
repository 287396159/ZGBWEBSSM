package com.dmatek.zgb.exception.bean;

import java.io.Serializable;

public class ExceptionMessage extends RuntimeException implements Serializable {
	private static final long serialVersionUID = 1L;
	private String msg;
    private int code = 500;
	public ExceptionMessage() {
		super();
	}
	public ExceptionMessage(int code) {
		super();
		this.code = code;
	}
	public ExceptionMessage(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	public ExceptionMessage(String message, Throwable cause) {
		super(message, cause);
	}
	public ExceptionMessage(String message) {
		super(message);
	}
	public ExceptionMessage(Throwable cause) {
		super(cause);
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
}
