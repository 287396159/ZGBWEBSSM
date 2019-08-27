package com.dmatek.zgb.db.pojo.setting;

import java.util.Date;
/**
 * 可靠近的卡片与基站
 * @author zhangfu
 * @data 2019年1月9日 下午5:47:20
 * @Description
 */
public class ReliablePerson_Node extends BaseSettingPojo{
	private static final long serialVersionUID = 1L;
	private String personNo, nodeId;
	public ReliablePerson_Node() {
		super();
	}
	public ReliablePerson_Node(Date createTime, Date updateTime, String createName,
			String updateName) {
		super(createTime, updateTime, createName, updateName);
	}
	public String getPersonNo() {
		return personNo;
	}
	public void setPersonNo(String personNo) {
		this.personNo = personNo;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	@Override
	public String toString() {
		return "[人員編號: " + personNo + ", 參考點ID: "
				+ nodeId + "]";
	}
}
