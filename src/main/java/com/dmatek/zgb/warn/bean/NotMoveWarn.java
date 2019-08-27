package com.dmatek.zgb.warn.bean;

import com.dmatek.zgb.warn.bean.base.BaseTagWarnMessage;
/**
 * 未移动警告
 * @author zf
 * @data 2018年12月14日 下午4:32:48
 * @Description
 */
public class NotMoveWarn extends BaseTagWarnMessage{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float curNotMoveTime, fixedNotMoveTime;// 当前未移动时间、固定未移动时间
	public NotMoveWarn() {
		super();
	}
	public NotMoveWarn(int curNotMoveTime, int fixedNotMoveTime) {
		super();
		this.curNotMoveTime = curNotMoveTime;
		this.fixedNotMoveTime = fixedNotMoveTime;
	}
	public NotMoveWarn(String tagId, String tagName, String tagNo,
			String companyNo, String companyName, String jobTypeNo,
			String jobTypeName, int groupId, int regionId, String groupName,
			String regionName, String referId, String referName, 
			int curNotMoveTime, int fixedNotMoveTime) {
		super(tagId, tagName, tagNo, companyNo, companyName, jobTypeNo, jobTypeName,
			  groupId, regionId, groupName, regionName, referId, referName);
		this.curNotMoveTime = curNotMoveTime;
		this.fixedNotMoveTime = fixedNotMoveTime;
	}
	public float getCurNotMoveTime() {
		return curNotMoveTime;
	}

	public void setCurNotMoveTime(float curNotMoveTime) {
		this.curNotMoveTime = curNotMoveTime;
	}

	public float getFixedNotMoveTime() {
		return fixedNotMoveTime;
	}

	public void setFixedNotMoveTime(float fixedNotMoveTime) {
		this.fixedNotMoveTime = fixedNotMoveTime;
	}
}
