package com.dmatek.zgb.monitor.device.detail;
import com.dmatek.zgb.monitor.bean.ReferPack;

/**
 * 参考点的详细讯息
 * @author Administrator
 * @data 2018年12月19日 下午4:54:14
 * @Description
 */
public class ReferDetail extends ReferPack{
	private int groupId, regionId;// 组群ID、区域ID
	private String groupName, regionName;// 组群名称、区域名称
	private String referName;// 参考点ID、参考点名称
	public ReferDetail() {
		super();
	}
	public ReferDetail(int groupId, int regionId, String groupName,
			String regionName, String referName, ReferPack referPack) {
		super(referPack.getId(), referPack.getbId(),referPack.getSleepTime(), 
			  referPack.getTick(), referPack.isStatus(), 
			  referPack.getReportTime(), referPack.getVersion(),
			  referPack.getEndpoint(), referPack.getPort());
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
