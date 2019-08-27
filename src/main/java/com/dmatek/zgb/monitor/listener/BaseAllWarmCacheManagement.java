package com.dmatek.zgb.monitor.listener;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.dmatek.zgb.monitor.device.detail.NodeDetail;
import com.dmatek.zgb.monitor.device.detail.ReferDetail;
import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.warn.bean.AbnormalBaseWarn;
import com.dmatek.zgb.warn.bean.AbnormalReferWarn;
import com.dmatek.zgb.warn.bean.AbnormalTagWarn;
import com.dmatek.zgb.warn.bean.AreaControlWarn;
import com.dmatek.zgb.warn.bean.LowPowerWarn;
import com.dmatek.zgb.warn.bean.NotMoveWarn;
import com.dmatek.zgb.warn.bean.PersonnelHelpWarn;
import com.dmatek.zgb.warn.cache.base.BaseWarnCache;

public abstract class BaseAllWarmCacheManagement {
	private Logger logger = Logger.getLogger(BaseAllWarmCacheManagement.class);
	@Autowired
	private BaseWarnCache<PersonnelHelpWarn> personnelHelpWarnCache;
	@Autowired
	private BaseWarnCache<NotMoveWarn> notMoveWarnCache;
	@Autowired
	private BaseWarnCache<LowPowerWarn> lowPowerWarnCache;
	@Autowired
	private BaseWarnCache<AreaControlWarn> areaControlWarnCache;
	@Autowired
	private BaseWarnCache<AbnormalTagWarn> abnormalTagWarnCache;
	@Autowired
	private BaseWarnCache<AbnormalReferWarn> abnormalReferWarnCache;
	@Autowired
	private BaseWarnCache<AbnormalBaseWarn> abnormalBaseWarnCache;
	/**
	 * 清除所有的警告缓存
	 * @throws Exception 
	 */
	protected void clearWarnCache() throws Exception{
		logger.warn("清空所有的警告缓存");
		// 1.清除人员求救缓存
		if(null != personnelHelpWarnCache){
			personnelHelpWarnCache.clearCache();
		}
		// 2.清除未移动卡片缓存
		if(null != notMoveWarnCache){
			notMoveWarnCache.clearCache();
		}
		// 3.清除低电量缓存
		if(null != lowPowerWarnCache){
			lowPowerWarnCache.clearCache();
		}
		// 4.清除区域管制报警缓存
		if(null != areaControlWarnCache){
			areaControlWarnCache.clearCache();
		}
		// 5.清除异常卡片警报缓存
		if(null != abnormalTagWarnCache){
			abnormalTagWarnCache.clearCache();
		}
		// 6.清除异常参考点警报缓存
		if(null != abnormalReferWarnCache){
			abnormalReferWarnCache.clearCache();
		}
		// 7. 清除异常基站警告缓存
		if(null != abnormalBaseWarnCache){
			abnormalBaseWarnCache.clearCache();
		}
	}

	protected void addAreaControlWarn(TagDetail tagDetail) throws Exception{
		AreaControlWarn areaControlWarn = getAreaControlWarnCache()
				.getNotHandleWarnFromDeviceId(tagDetail.getId());
		if(null != areaControlWarn){
			areaControlWarn.setReferId(tagDetail.getrId());
			areaControlWarn.setReferName(tagDetail.getReferName());
			areaControlWarn.setRegionId(tagDetail.getRegionId());
			areaControlWarn.setRegionName(tagDetail.getRegionName());
			areaControlWarn.setGroupId(tagDetail.getGroupId());
			areaControlWarn.setGroupName(tagDetail.getGroupName());
		} else { 
			areaControlWarn = new AreaControlWarn(tagDetail.getId(), tagDetail.getTagName(),
				tagDetail.getTagNo(), tagDetail.getCompanyNo(), tagDetail.getCompanyName(), 
				tagDetail.getJobTypeNo(), tagDetail.getJobTypeName(),  tagDetail.getGroupId(),
				tagDetail.getRegionId(), tagDetail.getGroupName(), tagDetail.getRegionName(),
				tagDetail.getrId(), tagDetail.getReferName());
		}
		getAreaControlWarnCache().addWarn(areaControlWarn);
	}
	
	/**
	 * 添加卡片低电量警报讯息
	 * @param tagDetail
	 * @param minBattery
	 * @throws Exception
	 */
	protected void addLowBatteryWarn(TagDetail tagDetail,byte minBattery) throws Exception{
		LowPowerWarn lowPowerWarm = getLowPowerWarnCache()
				.getNotHandleWarnFromDeviceId(tagDetail.getId());
		if(null != lowPowerWarm){
			lowPowerWarm.setCurBattery(tagDetail.getBat());
			lowPowerWarm.setReferId(tagDetail.getrId());
			lowPowerWarm.setReferName(tagDetail.getReferName());
			lowPowerWarm.setRegionId(tagDetail.getRegionId());
			lowPowerWarm.setRegionName(tagDetail.getRegionName());
			lowPowerWarm.setGroupId(tagDetail.getGroupId());
			lowPowerWarm.setGroupName(tagDetail.getGroupName());
		}else{
			
			lowPowerWarm  = new LowPowerWarn(tagDetail.getId(), tagDetail.getTagName(), 
					tagDetail.getTagNo(), tagDetail.getCompanyNo(), tagDetail.getCompanyName(),
					tagDetail.getJobTypeNo(), tagDetail.getJobTypeName(), tagDetail.getGroupId(), 
					tagDetail.getRegionId(), tagDetail.getGroupName(), tagDetail.getRegionName(), 
					tagDetail.getrId(), tagDetail.getReferName(),
					tagDetail.getBat(), minBattery);
		}
		getLowPowerWarnCache().addWarn(lowPowerWarm);
	}
	
	/**
	 * 添加卡片未移动警报讯息
	 * @param tagDetail
	 * @param curStopTime
	 * @param stopTime
	 * @throws Exception
	 */
	protected void addNotMoveWarn(TagDetail tagDetail,int curStopTime, int stopTime) throws Exception{
		NotMoveWarn notMoveWarn = getNotMoveWarnCache()
				.getNotHandleWarnFromDeviceId(tagDetail.getId());
		if(null != notMoveWarn){
			// 此时我们应该重新设置未移动时间的警告讯息
			notMoveWarn.setCurNotMoveTime(curStopTime);
			notMoveWarn.setReferId(tagDetail.getrId());
			notMoveWarn.setReferName(tagDetail.getReferName());
			notMoveWarn.setRegionId(tagDetail.getRegionId());
			notMoveWarn.setRegionName(tagDetail.getRegionName());
			notMoveWarn.setGroupId(tagDetail.getGroupId());
			notMoveWarn.setGroupName(tagDetail.getGroupName());
		}else{
			notMoveWarn = new NotMoveWarn(tagDetail.getId(), tagDetail.getTagName(), 
					tagDetail.getTagNo(), tagDetail.getCompanyNo(), tagDetail.getCompanyName(),
					tagDetail.getJobTypeNo(), tagDetail.getJobTypeName(), tagDetail.getGroupId(), 
					tagDetail.getRegionId(), tagDetail.getGroupName(), tagDetail.getRegionName(), 
					tagDetail.getrId(), tagDetail.getReferName(),
					curStopTime, stopTime);
		}
		getNotMoveWarnCache().addWarn(notMoveWarn);
	}
	/**
	 * 添加人员求救警报讯息
	 * @param tagDetail
	 * @throws Exception
	 */
	protected void addPersonHelpWarn(TagDetail tagDetail) throws Exception{
		PersonnelHelpWarn personHelpWarn = new PersonnelHelpWarn(tagDetail.getId(), tagDetail.getTagName(), 
				tagDetail.getTagNo(), tagDetail.getCompanyNo(), tagDetail.getCompanyName(),
				tagDetail.getJobTypeNo(), tagDetail.getJobTypeName(), tagDetail.getGroupId(), 
				tagDetail.getRegionId(), tagDetail.getGroupName(), tagDetail.getRegionName(), 
				tagDetail.getrId(), tagDetail.getReferName());
		personnelHelpWarnCache.addWarn(personHelpWarn);
	}
	/**
	 * 添加数据节点警报讯息
	 * @param nodeDetail
	 * @param curDisTime
	 * @throws Exception
	 */
	public void addAbnormalNodeWarn(NodeDetail nodeDetail,int curDisTime) throws Exception{
		AbnormalBaseWarn curAbnormalBaseWarn = abnormalBaseWarnCache
				.getNotHandleWarnFromDeviceId(nodeDetail.getId());
		if(null != curAbnormalBaseWarn){
			curAbnormalBaseWarn.setCurDisTime(curDisTime);
			// 需要我们重新将当前的警告讯息设置到Redis的缓存中
		}else{
			curAbnormalBaseWarn = new AbnormalBaseWarn(nodeDetail.getId(),
					nodeDetail.getReferName(), nodeDetail.getGroupId(),
					nodeDetail.getRegionId(), nodeDetail.getGroupName(),
					nodeDetail.getRegionName(), curDisTime,
					nodeDetail.getSleepTime());
			// 说明此时节点已经断开连接，需要产生节点断开的警报讯息
			getLogger().debug("数据节点【ID: " + nodeDetail.getId() + "】断开连接...");
		}
		abnormalBaseWarnCache.addWarn(curAbnormalBaseWarn);
	}
	
	/**
	 * 添加参考点异常的警报讯息
	 * @param tagDetail
	 * @throws Exception
	 */
	public void addAbnormalReferWarn(ReferDetail referDetail,int curDisTime) throws Exception{
		AbnormalReferWarn curAbnormalReferWarn = abnormalReferWarnCache
				.getNotHandleWarnFromDeviceId(referDetail.getId());
		if (null != curAbnormalReferWarn) {
			curAbnormalReferWarn.setCurDisTime(curDisTime);
		} else {
			curAbnormalReferWarn = new AbnormalReferWarn(referDetail.getId(),
					referDetail.getReferName(), referDetail.getGroupId(),
					referDetail.getRegionId(), referDetail.getGroupName(),
					referDetail.getRegionName(), curDisTime,
					referDetail.getSleepTime());
			// 说明此时参考点已经断开，需要产生参考点断开的警报讯息
			getLogger().debug("参考点【ID: " + referDetail.getId() + "】断开连接...");
		}
		abnormalReferWarnCache.addWarn(curAbnormalReferWarn);
	}
	/**
	 * 添加卡片异常断开讯息
	 * @param tagDetail
	 * @throws Exception 
	 */
	public void addAbnormalTagWarn(TagDetail tagDetail,int curDisTime,int sleepMode) throws Exception{
		AbnormalTagWarn curAbnormalTagWarn = abnormalTagWarnCache
				.getNotHandleWarnFromDeviceId(tagDetail.getId());
		if (null != curAbnormalTagWarn) {// 说明当前卡片断开的信息已经存在，我们只需要重新更新里面的信息即可
			curAbnormalTagWarn.setCurDisTime(curDisTime);
			curAbnormalTagWarn.setSleepTime(tagDetail.getSleepTime());
			curAbnormalTagWarn.setSleepMode(sleepMode);
			curAbnormalTagWarn.setReferId(tagDetail.getrId());
			curAbnormalTagWarn.setReferName(tagDetail.getReferName());
			curAbnormalTagWarn.setRegionId(tagDetail.getRegionId());
			curAbnormalTagWarn.setRegionName(tagDetail.getRegionName());
			curAbnormalTagWarn.setGroupId(tagDetail.getGroupId());
			curAbnormalTagWarn.setGroupName(tagDetail.getGroupName());
		} else {// 说明当前的卡片信息不存在，我们需要重新添加卡片信息
			curAbnormalTagWarn = new AbnormalTagWarn(
					tagDetail.getId(), tagDetail.getTagName(), tagDetail.getTagNo(),
					tagDetail.getCompanyNo(), tagDetail.getCompanyName(),
					tagDetail.getJobTypeNo(), tagDetail.getJobTypeName(),
					tagDetail.getGroupId(), tagDetail.getRegionId(),
					tagDetail.getGroupName(), tagDetail.getRegionName(),
					tagDetail.getrId(), tagDetail.getReferName(), curDisTime,
					sleepMode == 1 ? tagDetail.getStaticSleep() : tagDetail
							.getSleepTime(), sleepMode);
			// 说明此时卡片已经断开连接，需要产生卡片断开的警报讯息
			getLogger().debug("卡片【ID: " + tagDetail.getId() + "】断开连接...");
		}
		abnormalTagWarnCache.addWarn(curAbnormalTagWarn);
	}
	/**
	 * 获取所有不需要清理的警告
	 * @return
	 * @throws Exception 
	 */
	public List<PersonnelHelpWarn> getAllPersonnelHelpWarns() throws Exception{
		return personnelHelpWarnCache.getAllWarns();
	}
	
	public BaseWarnCache<PersonnelHelpWarn> getPersonnelHelpWarnCache() {
		return personnelHelpWarnCache;
	}
	public void setPersonnelHelpWarnCache(
			BaseWarnCache<PersonnelHelpWarn> personnelHelpWarnCache) {
		this.personnelHelpWarnCache = personnelHelpWarnCache;
	}
	public BaseWarnCache<NotMoveWarn> getNotMoveWarnCache() {
		return notMoveWarnCache;
	}
	public void setNotMoveWarnCache(BaseWarnCache<NotMoveWarn> notMoveWarnCache) {
		this.notMoveWarnCache = notMoveWarnCache;
	}
	public BaseWarnCache<LowPowerWarn> getLowPowerWarnCache() {
		return lowPowerWarnCache;
	}
	public void setLowPowerWarnCache(BaseWarnCache<LowPowerWarn> lowPowerWarnCache) {
		this.lowPowerWarnCache = lowPowerWarnCache;
	}
	public BaseWarnCache<AreaControlWarn> getAreaControlWarnCache() {
		return areaControlWarnCache;
	}
	public void setAreaControlWarnCache(BaseWarnCache<AreaControlWarn> areaControlWarnCache) {
		this.areaControlWarnCache = areaControlWarnCache;
	}
	public BaseWarnCache<AbnormalTagWarn> getAbnormalTagWarnCache() {
		return abnormalTagWarnCache;
	}
	public void setAbnormalTagWarnCache(BaseWarnCache<AbnormalTagWarn> abnormalTagWarnCache) {
		this.abnormalTagWarnCache = abnormalTagWarnCache;
	}
	public BaseWarnCache<AbnormalReferWarn> getAbnormalReferWarnCache() {
		return abnormalReferWarnCache;
	}
	public void setAbnormalReferWarnCache(
			BaseWarnCache<AbnormalReferWarn> abnormalReferWarnCache) {
		this.abnormalReferWarnCache = abnormalReferWarnCache;
	}
	public BaseWarnCache<AbnormalBaseWarn> getAbnormalBaseWarnCache() {
		return abnormalBaseWarnCache;
	}
	public void setAbnormalBaseWarnCache(BaseWarnCache<AbnormalBaseWarn> abnormalBaseWarnCache) {
		this.abnormalBaseWarnCache = abnormalBaseWarnCache;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
