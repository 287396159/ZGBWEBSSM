package com.dmatek.zgb.report.statistical.warn.bean;

import com.dmatek.zgb.warn.bean.NotMoveWarn;

public class NotMoveStatisticalWarn extends BaseStatisticalWarn<NotMoveWarn> {
	private float totalTime;// 總時間，單位分鐘
	public NotMoveStatisticalWarn(String id) {
		super(id);
	}
	/**
	 * 統計汇总时间
	 */
	public void statistic(){
		this.totalTime = 0;
		for (NotMoveWarn warn : getStatisticalWarns()) {
			this.totalTime += warn.getCurNotMoveTime();
		}
	}
	public float getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(float totalTime) {
		this.totalTime = totalTime;
	}
}
