package com.dmatek.zgb.report.warn.tool;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.controller.setting.params.base.ShowSettingParamTool;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.AbnormalBaseWarn;
import com.dmatek.zgb.warn.bean.AbnormalReferWarn;
import com.dmatek.zgb.warn.bean.AbnormalTagWarn;
import com.dmatek.zgb.warn.bean.AreaControlWarn;
import com.dmatek.zgb.warn.bean.LowPowerWarn;
import com.dmatek.zgb.warn.bean.NotMoveWarn;
import com.dmatek.zgb.warn.bean.PersonnelHelpWarn;
import com.dmatek.zgb.warn.cache.base.BaseNodeWarnCache;
import com.dmatek.zgb.warn.cache.base.BaseTagWarnCache;

public class ReportWarnTool extends BaseReportWarnTool{
	private BaseTagWarnCache<PersonnelHelpWarn> personHelpCache;
	private BaseTagWarnCache<AreaControlWarn> areaControllerCache;
	private BaseTagWarnCache<LowPowerWarn> lowPowerWarnCache;
	private BaseTagWarnCache<NotMoveWarn> notMoveWarnCache;
	private BaseTagWarnCache<AbnormalTagWarn> abnormalTagWarnCache;
	private BaseNodeWarnCache<AbnormalReferWarn> abnormalReferWarnCache;
	private BaseNodeWarnCache<AbnormalBaseWarn> abnormalBaseWarnCache;
	public ReportWarnTool(ShowSettingParamTool showSettingParamTool, 
			BaseTagWarnCache<PersonnelHelpWarn> personHelpCache,
			BaseTagWarnCache<AreaControlWarn> areaControllerCache,
			BaseTagWarnCache<LowPowerWarn> lowPowerWarnCache,
			BaseTagWarnCache<NotMoveWarn> notMoveWarnCache,
			BaseTagWarnCache<AbnormalTagWarn> abnormalTagWarnCache,
			BaseNodeWarnCache<AbnormalReferWarn> abnormalReferWarnCache,
			BaseNodeWarnCache<AbnormalBaseWarn> abnormalBaseWarnCache,
			BaseWarmComparator descendingComparator,BaseWarmComparator ascendingComparator) {
		super(showSettingParamTool, descendingComparator, ascendingComparator);
		this.personHelpCache = personHelpCache;
		this.areaControllerCache = areaControllerCache;
		this.lowPowerWarnCache = lowPowerWarnCache;
		this.notMoveWarnCache = notMoveWarnCache;
		this.abnormalTagWarnCache = abnormalTagWarnCache;
		this.abnormalReferWarnCache = abnormalReferWarnCache;
		this.abnormalBaseWarnCache = abnormalBaseWarnCache;
	}
	/**
	 * 获取人员求救的警报汇总讯息
	 * @return
	 * @throws Exception
	 */
	public WarnTypeCardReportInfo getPersonCardReportInfor() throws Exception{
		int warnReportTime = getReportWarnTime();
		return personHelpCache.getAllWarnTypeCardReportInfor(warnReportTime);
	}
	/**
	 * 获取区域管制的警报汇总讯息
	 * @return
	 * @throws Exception
	 */
	public WarnTypeCardReportInfo getAreaControllerCardReportInfor() throws Exception {
		int warnReportTime = getReportWarnTime();
		return areaControllerCache.getAllWarnTypeCardReportInfor(warnReportTime);
	}
	/**
	 * 获取低电量的警报汇总讯息
	 * @return
	 * @throws Exception
	 */
	public WarnTypeCardReportInfo getLowerPowerCardReportInfor() throws Exception {
		int warnReportTime = getReportWarnTime();
		return lowPowerWarnCache.getAllWarnTypeCardReportInfor(warnReportTime);
	}
	/**
	 * 获取未移动的警报汇总讯息
	 * @return
	 * @throws Exception
	 */
	public WarnTypeCardReportInfo getNotMoveCardReportInfor() throws Exception {
		int warnReportTime = getReportWarnTime();
		return notMoveWarnCache.getAllWarnTypeCardReportInfor(warnReportTime);
	}
	/**
	 * 获取卡片异常的警报汇总讯息
	 * @return
	 * @throws Exception
	 */
	public WarnTypeCardReportInfo getAbnormalTagCardReportInfor() throws Exception {
		int warnReportTime = getReportWarnTime();
		return abnormalTagWarnCache.getAllWarnTypeCardReportInfor(warnReportTime);
	}
	
	/**
	 * 获取参考点异常的警报汇总讯息
	 * @return
	 * @throws Exception
	 */
	public WarnTypeCardReportInfo getAbnormalReferCardReportInfor() throws Exception {
		int warnReportTime = getReportWarnTime();
		return abnormalReferWarnCache.getAllWarnTypeCardReportInfor(warnReportTime);
	}
	
	/**
	 * 获取参考点异常的警报汇总讯息
	 * @return
	 * @throws Exception
	 */
	public WarnTypeCardReportInfo getAbnormalBaseCardReportInfor() throws Exception {
		int warnReportTime = getReportWarnTime();
		return abnormalBaseWarnCache.getAllWarnTypeCardReportInfor(warnReportTime);
	}
	public BaseTagWarnCache<PersonnelHelpWarn> getPersonHelpCache() {
		return personHelpCache;
	}
	public void setPersonHelpCache(
			BaseTagWarnCache<PersonnelHelpWarn> personHelpCache) {
		this.personHelpCache = personHelpCache;
	}
	public BaseTagWarnCache<AreaControlWarn> getAreaControllerCache() {
		return areaControllerCache;
	}
	public void setAreaControllerCache(
			BaseTagWarnCache<AreaControlWarn> areaControllerCache) {
		this.areaControllerCache = areaControllerCache;
	}
	public BaseTagWarnCache<LowPowerWarn> getLowPowerWarnCache() {
		return lowPowerWarnCache;
	}
	public void setLowPowerWarnCache(
			BaseTagWarnCache<LowPowerWarn> lowPowerWarnCache) {
		this.lowPowerWarnCache = lowPowerWarnCache;
	}
	public BaseTagWarnCache<NotMoveWarn> getNotMoveWarnCache() {
		return notMoveWarnCache;
	}
	public void setNotMoveWarnCache(BaseTagWarnCache<NotMoveWarn> notMoveWarnCache) {
		this.notMoveWarnCache = notMoveWarnCache;
	}
	public BaseTagWarnCache<AbnormalTagWarn> getAbnormalTagWarnCache() {
		return abnormalTagWarnCache;
	}
	public void setAbnormalTagWarnCache(
			BaseTagWarnCache<AbnormalTagWarn> abnormalTagWarnCache) {
		this.abnormalTagWarnCache = abnormalTagWarnCache;
	}
	public BaseNodeWarnCache<AbnormalReferWarn> getAbnormalReferWarnCache() {
		return abnormalReferWarnCache;
	}
	public void setAbnormalReferWarnCache(
			BaseNodeWarnCache<AbnormalReferWarn> abnormalReferWarnCache) {
		this.abnormalReferWarnCache = abnormalReferWarnCache;
	}
	public BaseNodeWarnCache<AbnormalBaseWarn> getAbnormalBaseWarnCache() {
		return abnormalBaseWarnCache;
	}
	public void setAbnormalBaseWarnCache(
			BaseNodeWarnCache<AbnormalBaseWarn> abnormalBaseWarnCache) {
		this.abnormalBaseWarnCache = abnormalBaseWarnCache;
	}
}
