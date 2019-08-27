package com.dmatek.zgb.monitor.device.detail;
import com.dmatek.zgb.monitor.bean.NodePack;
/**
 * 节点的详细讯息
 * @author zf
 * @data 2018年12月19日 下午4:55:37
 * @Description
 */
public class NodeDetail extends NodePack {
	private int groupId, regionId;// 组群ID、区域ID
	private String groupName, regionName;// 组群名称、区域名称
	private String referName;// 节点ID、节点名称
	public NodeDetail() {
		super();
	}
	public NodeDetail(int groupId, int regionId, String groupName,
			String regionName, String referName, NodePack nodePack) {
		super(nodePack.getId(), nodePack.getSleepTime(), nodePack.getTick(),
				nodePack.isStatus(), nodePack.getReportTime(),
				nodePack.getVersion(), nodePack.getEndpoint(),
				nodePack.getPort());
		this.groupId = groupId;
		this.regionId = regionId;
		this.groupName = groupName;
		this.regionName = regionName;
		this.referName = referName;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getReferName() {
		return referName;
	}
	public void setReferName(String referName) {
		this.referName = referName;
	}
}
