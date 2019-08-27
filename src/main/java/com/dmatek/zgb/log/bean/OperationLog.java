package com.dmatek.zgb.log.bean;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OperationLog implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userName, des, ip;
	private Date logTime;
	private String args;
	public OperationLog() {
		super();
	}
	public OperationLog(String userName, String des, String ip, Date logTime,
			String args) {
		super();
		this.userName = userName;
		this.des = des;
		this.ip = ip;
		this.logTime = logTime;
		this.args = args;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getLogTime() {
		return logTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	public String getArgs() {
		return args;
	}
	public void setArgs(String args) {
		this.args = args;
	}
}
