package com.dmatek.zgb.db.pojo.setting;

import java.util.Date;
/**
 * 危险数据节点
 * @author zf
 * @data 2018年12月10日 上午9:57:14
 * @Description
 */
public class DangerNode extends BaseSettingPojo{
	private static final long serialVersionUID = 1L;
	private String id;
	private boolean isSigThreshold;
	private int maxSigThreshold;
	public DangerNode() {
		super();
	}
	public DangerNode(Date createTime, Date updateTime, String createName,
			String updateName) {
		super(createTime, updateTime, createName, updateName);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
