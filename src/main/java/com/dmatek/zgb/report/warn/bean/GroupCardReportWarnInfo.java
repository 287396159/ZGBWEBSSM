package com.dmatek.zgb.report.warn.bean;

public class GroupCardReportWarnInfo extends BaseCardReportWarnInfo {
	private static final long serialVersionUID = 1L;
	private int groupId;// 组别的ID
	private String groupName;// 组别的名称
	public GroupCardReportWarnInfo() {
		super();
	}
	public GroupCardReportWarnInfo(int groupId, String groupName) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
	}
	public GroupCardReportWarnInfo(int totalNumber, int handleNumber,
			int notHandleNumber) {
		super(totalNumber, handleNumber, notHandleNumber);
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
