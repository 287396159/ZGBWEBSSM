package com.dmatek.zgb.db.pojo.setting;

import java.util.Date;
/**
 * 安全数据节点
 * @author zf
 * @data 2018年12月10日 上午9:53:39
 * @Description
 */
public class SafeNode extends BaseSettingPojo{
	private static final long serialVersionUID = 1L;
	private String id;
	private boolean isAllTrig, isPeriodTrig;
	private Date trig_BeginTime, trig_EndTime;
	private boolean isSigThreshold;
	private int maxSigThreshold;
	public SafeNode() {
		super();
	}
	public SafeNode(Date createTime, Date updateTime, String createName,
			String updateName, String id, boolean isAllTrig, boolean isPeriodTrig,
			Date trig_BeginTime, Date trig_EndTime, boolean isSigThreshold,
			int maxSigThreshold) {
		super(createTime, updateTime, createName, updateName);
		this.id = id;
		this.isAllTrig = isAllTrig;
		this.isPeriodTrig = isPeriodTrig;
		this.trig_BeginTime = trig_BeginTime;
		this.trig_EndTime = trig_EndTime;
		this.isSigThreshold = isSigThreshold;
		this.maxSigThreshold = maxSigThreshold;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isAllTrig() {
		return isAllTrig;
	}
	public void setAllTrig(boolean isAllTrig) {
		this.isAllTrig = isAllTrig;
	}
	public boolean isPeriodTrig() {
		return isPeriodTrig;
	}
	public void setPeriodTrig(boolean isPeriodTrig) {
		this.isPeriodTrig = isPeriodTrig;
	}
	public Date getTrig_BeginTime() {
		return trig_BeginTime;
	}
	public void setTrig_BeginTime(Date trig_BeginTime) {
		this.trig_BeginTime = trig_BeginTime;
	}
	public Date getTrig_EndTime() {
		return trig_EndTime;
	}
	public void setTrig_EndTime(Date trig_EndTime) {
		this.trig_EndTime = trig_EndTime;
	}
	public boolean isSigThreshold() {
		return isSigThreshold;
	}
	public void setSigThreshold(boolean isSigThreshold) {
		this.isSigThreshold = isSigThreshold;
	}
	public int getMaxSigThreshold() {
		return maxSigThreshold;
	}
	public void setMaxSigThreshold(int maxSigThreshold) {
		this.maxSigThreshold = maxSigThreshold;
	}
}
