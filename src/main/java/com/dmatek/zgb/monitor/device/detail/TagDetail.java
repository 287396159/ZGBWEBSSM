package com.dmatek.zgb.monitor.device.detail;
import com.dmatek.zgb.monitor.bean.TagPacket;
/**
 * 卡片详细信息
 * @author zf
 * @data 2018年12月19日 上午9:35:55
 * @Description
 */
public class TagDetail extends TagPacket {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tagName, tagNo, image;// 卡片ID、卡片名称、卡片编号
	private String companyNo, companyName, 
				   jobTypeNo, jobTypeName;// 公司编号、公司名称、工种编号、工种名称
	private int groupId, regionId;// 组群ID、区域ID
	private String groupName, regionName;// 组群名称、区域名称
	private String referName;// 参考点ID、参考点名称
	private float resTime, stopTime;// 休息时间、滞留时间
	private boolean isPersonnelHelp, isNotMove, 
					isLowPower, isAreaControl, isAbnormalTag;
	private boolean isClear = false;
	public TagDetail() {
		super();
	}
	public TagDetail(String tagName, String tagNo, String companyNo,
			String companyName, String jobTypeNo, String jobTypeName,
			int groupId, int regionId, String groupName, String regionName,
			String referName, float resTime, float stopTime,TagPacket tagPacket,
			String image) {
		super(tagPacket.getId(), tagPacket.getSleepTime(), tagPacket.getTick(), 
			  tagPacket.isStatus(), tagPacket.getReportTime(), tagPacket.getVersion(),
			  tagPacket.getrId(), tagPacket.getIndex(),
			  tagPacket.isAlarm(), tagPacket.getRssi(), tagPacket.getBat(), tagPacket.getStaticSleep(),
			  tagPacket.getNotMoveTime(), tagPacket.getReferNum(),
			  tagPacket.getCurrefsigs(), tagPacket.getJumpnum(), tagPacket.getLastjumpId());
		this.tagName = tagName;
		this.tagNo = tagNo;
		this.companyNo = companyNo;
		this.companyName = companyName;
		this.jobTypeNo = jobTypeNo;
		this.jobTypeName = jobTypeName;
		this.groupId = groupId;
		this.regionId = regionId;
		this.groupName = groupName;
		this.regionName = regionName;
		this.referName = referName;
		this.resTime = resTime;
		this.stopTime = stopTime;
		this.image = image;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getTagNo() {
		return tagNo;
	}
	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
	}
	public String getCompanyNo() {
		return companyNo;
	}
	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getJobTypeNo() {
		return jobTypeNo;
	}
	public void setJobTypeNo(String jobTypeNo) {
		this.jobTypeNo = jobTypeNo;
	}
	public String getJobTypeName() {
		return jobTypeName;
	}
	public void setJobTypeName(String jobTypeName) {
		this.jobTypeName = jobTypeName;
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
	public float getResTime() {
		return resTime;
	}
	public void setResTime(float resTime) {
		this.resTime = resTime;
	}
	public float getStopTime() {
		return stopTime;
	}
	public void setStopTime(float stopTime) {
		this.stopTime = stopTime;
	}
	public boolean isPersonnelHelp() {
		return isPersonnelHelp;
	}
	public void setPersonnelHelp(boolean isPersonnelHelp) {
		this.isPersonnelHelp = isPersonnelHelp;
	}
	public boolean isNotMove() {
		return isNotMove;
	}
	public void setNotMove(boolean isNotMove) {
		this.isNotMove = isNotMove;
	}
	public boolean isLowPower() {
		return isLowPower;
	}
	public void setLowPower(boolean isLowPower) {
		this.isLowPower = isLowPower;
	}
	public boolean isAreaControl() {
		return isAreaControl;
	}
	public void setAreaControl(boolean isAreaControl) {
		this.isAreaControl = isAreaControl;
	}
	public boolean isAbnormalTag() {
		return isAbnormalTag;
	}
	public void setAbnormalTag(boolean isAbnormalTag) {
		this.isAbnormalTag = isAbnormalTag;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public boolean isClear() {
		return isClear;
	}
	public void setClear(boolean isClear) {
		this.isClear = isClear;
	}
	@Override
	public String toString() {
		return "TagDetail [tagName=" + tagName + ", groupId=" + groupId
				+ ", regionId=" + regionId + ", groupName=" + groupName
				+ ", regionName=" + regionName + ", referName=" + referName
				+ ", isPersonnelHelp=" + isPersonnelHelp + ", isNotMove="
				+ isNotMove + ", isLowPower=" + isLowPower + ", isAreaControl="
				+ isAreaControl + ", isAbnormalTag=" + isAbnormalTag + "]";
	}
}
