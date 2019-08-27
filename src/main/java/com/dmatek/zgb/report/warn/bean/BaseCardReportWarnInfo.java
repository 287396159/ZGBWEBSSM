package com.dmatek.zgb.report.warn.bean;

import java.io.Serializable;

public abstract class BaseCardReportWarnInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 报警的总数量
	private int totalNumber;
	// 已处理的数量
	private int handleNumber;
	// 为处理的数量
	private int notHandleNumber;
	public BaseCardReportWarnInfo() {
		super();
	}
	public BaseCardReportWarnInfo(int totalNumber, int handleNumber,
			int notHandleNumber) {
		super();
		this.totalNumber = totalNumber;
		this.handleNumber = handleNumber;
		this.notHandleNumber = notHandleNumber;
	}
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public int getHandleNumber() {
		return handleNumber;
	}
	public void setHandleNumber(int handleNumber) {
		this.handleNumber = handleNumber;
	}
	public int getNotHandleNumber() {
		return notHandleNumber;
	}
	public void setNotHandleNumber(int notHandleNumber) {
		this.notHandleNumber = notHandleNumber;
	}
}
