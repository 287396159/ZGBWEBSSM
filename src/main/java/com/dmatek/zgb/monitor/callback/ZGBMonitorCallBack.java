package com.dmatek.zgb.monitor.callback;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import com.dmatek.zgb.access.base.IWorkAccess;
import com.dmatek.zgb.access.manager.IAccessManager;
import com.dmatek.zgb.cache.tool.detail.BaseCaptureCacheData;
import com.dmatek.zgb.controller.setting.params.base.SystemSettingParamTool;
import com.dmatek.zgb.monitor.bean.NodePack;
import com.dmatek.zgb.monitor.bean.ReferPack;
import com.dmatek.zgb.monitor.bean.TagPacket;
import com.dmatek.zgb.monitor.device.detail.NodeDetail;
import com.dmatek.zgb.monitor.device.detail.ReferDetail;
import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.monitor.listener.BaseAllWarmCacheManager;
import com.dmatek.zgb.params.setting.cache.ParamsKey;
import com.dmatek.zgb.rawstorage.ZGBStoreHistoryData;
import com.dmatek.zgb.rawstorage.time.ZGBRawTimeDataStorage;
import com.dmatek.zgb.redis.cache.IRedisMapCache;
import com.dmatek.zgb.redis.service.RedisService;
import com.dmatek.zgb.warn.bean.AbnormalBaseWarn;
import com.dmatek.zgb.warn.bean.AbnormalReferWarn;
import com.dmatek.zgb.warn.bean.AbnormalTagWarn;

/**
 * ZGB监听的回调函数
 * @author zhangfu
 * @data 2018年12月13日 下午6:02:10
 * @Description
 */
public class ZGBMonitorCallBack extends BaseAllWarmCacheManager implements MonitorCallBack {
	// redis 服务
	@Resource
	private RedisService redisService;
	// 获取卡片上报的详细讯息对象
	@Autowired
	private BaseCaptureCacheData<TagDetail, TagPacket> baseTagDetailCapture;
	@Autowired
	private BaseCaptureCacheData<NodeDetail, NodePack> baseNodeDetailCapture;
	@Autowired
	private BaseCaptureCacheData<ReferDetail, ReferPack> baseReferDetailCapture;
	@Autowired
	private SystemSettingParamTool systemSettingParamTool;
	@Autowired
	private ZGBStoreHistoryData iRawDataStorage;
	@Autowired
	private ZGBRawTimeDataStorage iRawTimeDateStorage;
	@Autowired
	private IRedisMapCache<String, TagDetail> iTagRedisCache;
	@Autowired
	private IRedisMapCache<String, NodePack> iNodeRedisCache;
	@Autowired
	private IRedisMapCache<String, ReferPack> iReferRedisCache;
	@Resource(name="accessManager")
	private IAccessManager<TagDetail> iAccessManager;
	@Resource(name="onDutyWorkAccess")
	private IWorkAccess<TagDetail> iWorkAccess;
	@Override
	public void TagCallBack(TagPacket tagpack) throws Exception {
		// 更新卡片的详细讯息
		TagDetail tagDetail = baseTagDetailCapture.getDetailCacheData(tagpack);
		// 检测卡片的警告讯息
		scanTagWarmMsg(tagDetail);
		// 处理异常的卡片警告讯息
		handleAbnormalTagWarn(tagDetail);
		// 检测出入统计记录
		if(null != iRawDataStorage) {// 保存卡片记录
			iRawDataStorage.saveOrigin(tagDetail);
		}
		// 按时间保存卡片上报的记录
		if(null != iRawTimeDateStorage) {
			iRawTimeDateStorage.saveOrigin(tagDetail);
		}
		// 每次上报一个Tag数据包我们将它放到redis中缓存起来
		if(null != iTagRedisCache) {
			iTagRedisCache.addCacheObject(tagDetail.getId(), tagDetail);
		}
		/**
		 * 根据出入记录管理器添加出入记录讯息
		 */
		if(null != iAccessManager) {
			iAccessManager.saveAccessRecord(tagDetail);
		}
		/**
		 * 记录当前卡片在当天最早的一笔记录
		 */
		boolean isAutoStatisticAccess = (boolean) systemSettingParamTool
				.getSysParamValue(ParamsKey.isAutoStatisticAccess);
		if (isAutoStatisticAccess && null != iWorkAccess) {
			String sStart = (String) systemSettingParamTool.getSettingParams()
					.get(ParamsKey.accessOndutyStartTime.getVal());
			String eStart = (String) systemSettingParamTool.getSettingParams()
					.get(ParamsKey.accessOndutyEndTime.getVal());
			// 设置时间参数
			iWorkAccess.setParams(sStart, eStart);
			iWorkAccess.execute(tagDetail);
		}
	}
	@Override
	public void NodeCallBack(NodePack nodePack) throws Exception {
		NodeDetail nodeDetail = baseNodeDetailCapture.getDetailCacheData(nodePack);
		handleAbnormalNodeDetail(nodeDetail);
		if(null != iNodeRedisCache) {
			iNodeRedisCache.addCacheObject(nodeDetail.getId(), nodeDetail);
		}
	}
	@Override
	public void ReferCallBack(ReferPack referPack) throws Exception {
		ReferDetail referDetail = baseReferDetailCapture.getDetailCacheData(referPack);
		handleAbnormalReferDetail(referDetail);
		if(null != iReferRedisCache) {
			iReferRedisCache.addCacheObject(referDetail.getId(), referDetail);
		}
	}
	/**
	 * 处理异常的参考点警报
	 * @param referDetail
	 * @throws Exception
	 */
	private void handleAbnormalReferDetail(ReferDetail referDetail) throws Exception {
		AbnormalReferWarn abnormalReferWarn = getAbnormalReferWarnCache().getNotHandleWarnFromDeviceId(referDetail.getId());
		if(null != abnormalReferWarn) {
			abnormalReferWarn.handle();
			redisService.setHmItem(getAbnormalReferWarnCache().getWarnName(), 
					abnormalReferWarn.getUuid(), abnormalReferWarn);
		}
	}
	/**
	 * 处理异常的节点警报
	 * @param nodeDetail
	 * @throws Exception
	 */
	private void handleAbnormalNodeDetail(NodeDetail nodeDetail) throws Exception {
		AbnormalBaseWarn abnormalBaseWarn = getAbnormalBaseWarnCache().getNotHandleWarnFromDeviceId(nodeDetail.getId());
		if(null != abnormalBaseWarn){
			abnormalBaseWarn.handle();
			redisService.setHmItem(getAbnormalBaseWarnCache().getWarnName(), 
					abnormalBaseWarn.getUuid(), abnormalBaseWarn);
		}
	}
	/**
	 * 处理异常卡片警告讯息
	 * @param tagDetail
	 * @throws Exception
	 */
	private void handleAbnormalTagWarn(TagDetail tagDetail) throws Exception{
		AbnormalTagWarn abnormalTagWarn = getAbnormalTagWarnCache().getNotHandleWarnFromDeviceId(tagDetail.getId());
		if(null != abnormalTagWarn) {// 说明存在未处理的警报
			abnormalTagWarn.handle();
			redisService.setHmItem(getAbnormalTagWarnCache().getWarnName(), 
					abnormalTagWarn.getUuid(), abnormalTagWarn);
		}
	}
	public BaseCaptureCacheData<TagDetail, TagPacket> getBaseTagDetailCapture() {
		return baseTagDetailCapture;
	}
	public void setBaseTagDetailCapture(
			BaseCaptureCacheData<TagDetail, TagPacket> baseTagDetailCapture) {
		this.baseTagDetailCapture = baseTagDetailCapture;
	}
	public BaseCaptureCacheData<NodeDetail, NodePack> getBaseNodeDetailCapture() {
		return baseNodeDetailCapture;
	}
	public void setBaseNodeDetailCapture(
			BaseCaptureCacheData<NodeDetail, NodePack> baseNodeDetailCapture) {
		this.baseNodeDetailCapture = baseNodeDetailCapture;
	}
	public BaseCaptureCacheData<ReferDetail, ReferPack> getBaseReferDetailCapture() {
		return baseReferDetailCapture;
	}
	public void setBaseReferDetailCapture(
			BaseCaptureCacheData<ReferDetail, ReferPack> baseReferDetailCapture) {
		this.baseReferDetailCapture = baseReferDetailCapture;
	}
	public ZGBStoreHistoryData getiRawDataStorage() {
		return iRawDataStorage;
	}
	public void setiRawDataStorage(ZGBStoreHistoryData iRawDataStorage) {
		this.iRawDataStorage = iRawDataStorage;
	}
	public ZGBRawTimeDataStorage getiRawTimeDateStorage() {
		return iRawTimeDateStorage;
	}
	public void setiRawTimeDateStorage(ZGBRawTimeDataStorage iRawTimeDateStorage) {
		this.iRawTimeDateStorage = iRawTimeDateStorage;
	}
}
