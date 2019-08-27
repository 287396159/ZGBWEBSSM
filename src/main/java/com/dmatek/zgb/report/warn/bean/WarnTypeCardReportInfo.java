package com.dmatek.zgb.report.warn.bean;

import java.util.HashMap;
import java.util.Map;

public class WarnTypeCardReportInfo extends BaseCardReportWarnInfo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WarnType warnType;
	private Map<Integer, GroupCardReportWarnInfo> groupCardMap;
	public WarnTypeCardReportInfo() {
		super();
	}
	public WarnTypeCardReportInfo(int totalNumber, int handleNumber,
			int notHandleNumber) {
		super(totalNumber, handleNumber, notHandleNumber);
	}
	public WarnTypeCardReportInfo(WarnType warnType,
			Map<Integer, GroupCardReportWarnInfo> groupCardMap) {
		super();
		this.warnType = warnType;
		this.groupCardMap = groupCardMap;
	}
	public WarnTypeCardReportInfo(WarnType warnType) {
		super();
		this.warnType = warnType;
		this.groupCardMap = new HashMap<Integer, GroupCardReportWarnInfo>();
	}
	public Map<Integer, GroupCardReportWarnInfo> getGroupCardMap() {
		return groupCardMap;
	}
	public void setGroupCardMap(Map<Integer, GroupCardReportWarnInfo> groupCardMap) {
		this.groupCardMap = groupCardMap;
	}
	public WarnType getWarnType() {
		return warnType;
	}
	public void setWarnType(WarnType warnType) {
		this.warnType = warnType;
	}
}
