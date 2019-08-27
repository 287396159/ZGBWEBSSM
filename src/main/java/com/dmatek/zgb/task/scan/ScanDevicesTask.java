package com.dmatek.zgb.task.scan;

import java.util.Iterator;
import java.util.Map;

import com.dmatek.zgb.cache.tool.detail.BaseCaptureCacheData;
import com.dmatek.zgb.monitor.bean.NodePack;
import com.dmatek.zgb.monitor.bean.ReferPack;
import com.dmatek.zgb.monitor.bean.TagPacket;
import com.dmatek.zgb.monitor.device.detail.NodeDetail;
import com.dmatek.zgb.monitor.device.detail.ReferDetail;
import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.monitor.listener.BaseAllWarmCacheManagement;
import com.dmatek.zgb.redis.service.RedisService;
/**
 * 扫描设备线程
 * @author zhangfu
 * @data 2019年1月18日 下午3:14:39
 * @Description
 */
public class ScanDevicesTask extends BaseScanTask {
	private static final int TAG_NOMOVE_TIME = 3;
	private int checkedTagdisK1, checkedTagdisK2,
				checkedDevdisK1, checkedDevdisK2;
	private BaseAllWarmCacheManagement warmCacheManagement;
	private RedisService redisService;
	private String tagCacheName, nodeCacheName, referCacheName;
	private BaseCaptureCacheData<TagDetail, TagPacket> baseTagDetailCapture;
	private BaseCaptureCacheData<NodeDetail, NodePack> baseNodeDetailCapture;
	private BaseCaptureCacheData<ReferDetail, ReferPack> baseReferDetailCapture;
	public ScanDevicesTask(int peroid, int checkedTagdisK1, 
			int checkedTagdisK2, int checkedDevdisK1,
			int checkedDevdisK2,RedisService redisService,
			String tagCacheName, String nodeCacheName, String referCacheName,
			BaseAllWarmCacheManagement warmCacheManagement,
			BaseCaptureCacheData<TagDetail, TagPacket> baseTagDetailCapture,
			BaseCaptureCacheData<NodeDetail, NodePack> baseNodeDetailCapture,
			BaseCaptureCacheData<ReferDetail, ReferPack> baseReferDetailCapture
			) {
		super(peroid);
		this.checkedTagdisK1 = checkedTagdisK1; this.checkedTagdisK2 = checkedTagdisK2;
		this.checkedDevdisK1 = checkedDevdisK1; this.checkedDevdisK2 = checkedDevdisK2;
		this.warmCacheManagement = warmCacheManagement;
		this.redisService = redisService;
		this.tagCacheName = tagCacheName;
		this.nodeCacheName = nodeCacheName;
		this.referCacheName = referCacheName;
		this.baseTagDetailCapture = baseTagDetailCapture;
		this.baseNodeDetailCapture = baseNodeDetailCapture;
		this.baseReferDetailCapture = baseReferDetailCapture;
	}
	@Override
	public void scanTask() throws Exception {
		// 扫描所有的卡片设备，    判断是否断开了连接
		scanAllTagDevices();
		// 扫描所有的节点设备，    判断是否断开了连接
		scanAllNodeDevices();
		// 扫描所有的参考点设备，判断是否断开了连接
		scanAllReferDevices();
	}
	/**
	 * 遍历卡片集合，查看卡片是否存在断开连接的状态
	 * @throws Exception 
	 */
	private void scanAllTagDevices() throws Exception {
		int sleepTime = 0;
		Map<Object, Object> tagsCache = redisService.getHmAll(getTagCacheName());
		Iterator<Object> iterator = tagsCache.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			TagDetail tagDetail = (TagDetail) tagsCache.get(key);
			// 判断当前的卡片是否移动
			sleepTime = tagDetail.getNotMoveTime() >= TAG_NOMOVE_TIME ? tagDetail
								.getStaticSleep() : tagDetail.getSleepTime();
			if (System.currentTimeMillis() - tagDetail.getTick() >= (checkedTagdisK1
					* sleepTime * 100 + checkedTagdisK2 * 1000)) {
				// 设置 状态为断开状态
				tagDetail.setStatus(false);
				tagDetail.setAbnormalTag(true);
				baseTagDetailCapture.getRedisService().setHmItem(getTagCacheName(), 
						tagDetail.getId(), tagDetail);
				// 获取当前距离上次上报的时间间隔，单位分钟
				int curDisTime = (int) ((System.currentTimeMillis() - tagDetail
						.getTick()) / (1000 * 60));
				warmCacheManagement.addAbnormalTagWarn(tagDetail, curDisTime,
						tagDetail.getNotMoveTime() >= TAG_NOMOVE_TIME ? 1 : 0);
			}
		}
	}	
	/**
	 *  遍历所有的参考点集合，查看参考点设备是否断开连接
	 * @throws Exception
	 */
	private void scanAllReferDevices() throws Exception{
		Map<Object,Object> refersCache = redisService.getHmAll(getReferCacheName());
		Iterator<Object> iterator = refersCache.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			ReferPack referPack = (ReferPack) refersCache.get(key);
			ReferDetail	referDetail = baseReferDetailCapture.getDetailCacheData(referPack);
			if (System.currentTimeMillis() - referDetail.getTick() >= (checkedDevdisK1
					* referDetail.getSleepTime() * 1000 + checkedDevdisK2 * 1000)) {
				int curDisTime = (int) ((System.currentTimeMillis() - referDetail
						.getTick()) / (1000 * 60));
				// 设置参考点状态
				baseReferDetailCapture.getRedisService()
					.deleteHmItem(baseReferDetailCapture.getCacheName(),
							referDetail.getId());
				warmCacheManagement.addAbnormalReferWarn(referDetail, curDisTime);
			}
		}
	}
	/**
	 * 遍历所有的参考点、节点集合，查看参考点和节点设备是否断开连接
	 * @throws Exception 
	 */
	private void scanAllNodeDevices() throws Exception{
		Map<Object, Object> nodesCache = redisService.getHmAll(getNodeCacheName());
		Iterator<Object> iterator = nodesCache.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			NodePack nodePack = (NodePack) nodesCache.get(key);
			NodeDetail nodeDetail = baseNodeDetailCapture.getDetailCacheData(nodePack);
			if (System.currentTimeMillis() - nodeDetail.getTick() >= (checkedDevdisK1
					* nodeDetail.getSleepTime() * 1000 + checkedDevdisK2 * 1000)) {
				int curDisTime = (int) ((System.currentTimeMillis() - nodeDetail
						.getTick()) / (1000 * 60));
				baseNodeDetailCapture.getRedisService().deleteHmItem(baseNodeDetailCapture.getCacheName(),
						nodeDetail.getId());
				warmCacheManagement.addAbnormalNodeWarn(nodeDetail, curDisTime);
			}
		}
	}
	public BaseAllWarmCacheManagement getWarmCacheManagement() {
		return warmCacheManagement;
	}
	public void setWarmCacheManagement(
			BaseAllWarmCacheManagement warmCacheManagement) {
		this.warmCacheManagement = warmCacheManagement;
	}
	public RedisService getRedisService() {
		return redisService;
	}
	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}
	public String getTagCacheName() {
		return tagCacheName;
	}
	public void setTagCacheName(String tagCacheName) {
		this.tagCacheName = tagCacheName;
	}
	public String getNodeCacheName() {
		return nodeCacheName;
	}
	public void setNodeCacheName(String nodeCacheName) {
		this.nodeCacheName = nodeCacheName;
	}
	public String getReferCacheName() {
		return referCacheName;
	}
	public void setReferCacheName(String referCacheName) {
		this.referCacheName = referCacheName;
	}
}
