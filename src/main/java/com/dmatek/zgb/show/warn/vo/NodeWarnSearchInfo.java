package com.dmatek.zgb.show.warn.vo;

public class NodeWarnSearchInfo extends BaseWarnSearchInfo{
	private String groupName;
	public NodeWarnSearchInfo() {
		super();
	}
	public NodeWarnSearchInfo(String groupName) {
		super();
		this.groupName = groupName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
