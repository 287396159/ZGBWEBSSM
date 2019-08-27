package com.dmatek.zgb.monitor;

import java.net.DatagramSocket;
import com.dmatek.zgb.monitor.bean.NodePack;
import com.dmatek.zgb.monitor.bean.ReferPack;
import com.dmatek.zgb.redis.cache.IRedisMapCache;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.udp.client.UdpClient;

public class MonitorUdpSendTool implements ISendTool{
	private BaseDataMonitor baseDataMonitor;
	private IRedisMapCache<String, NodePack> iNodeRedisCache;
	private IRedisMapCache<String, ReferPack> iReferRedisCache;
	private BaseResultFactory<Result> viewFactory;
	public MonitorUdpSendTool(IRedisMapCache<String, NodePack> iNodeRedisCache, 
			IRedisMapCache<String, ReferPack> iReferRedisCache,
			BaseResultFactory<Result> viewFactory,
			BaseDataMonitor baseDataMonitor) {
		this.iNodeRedisCache = iNodeRedisCache;
		this.iReferRedisCache = iReferRedisCache;
		this.viewFactory = viewFactory;
		this.baseDataMonitor = baseDataMonitor;
	}
	@Override
	public NodePack findDevice(String id) throws Exception {
		// 1. 根据ID查询基站设备
		NodePack nodePack = getiNodeRedisCache().getCacheMap().get(id);
		if (null != nodePack) {
			return nodePack;
		}
		// 2. 根据ID查询参考点设备
		nodePack = getiReferRedisCache().getCacheMap().get(id);
		if (null != nodePack) {
			return nodePack;
		}
		return null;
	} 
	@Override
	public Result sendUdpPacket(String id, byte[] content) throws Exception {
		// 1. 获取基站上报的数据包，通过数据包获取节点Ip及端口讯息
		NodePack nodePack = findDevice(id);
		if(null == nodePack) {
			return getViewFactory().errorResult("節點【id: " + id + "】上報的數據包不存在");
		}
		// 2. 获取监听的数据套接字对象
		DatagramSocket datagramSocket = getBaseDataMonitor().getSocket();
		if(null == datagramSocket) {
			return getViewFactory().errorResult("網絡監聽出現異常，無法發送數據包");
		}
		// 3. 发送数据包给指定的网络端口
		UdpClient.sendPacket(datagramSocket, nodePack.getEndpoint(),
					nodePack.getPort(), content);
		return null;
	}
	public IRedisMapCache<String, NodePack> getiNodeRedisCache() {
		return iNodeRedisCache;
	}
	public void setiNodeRedisCache(IRedisMapCache<String, NodePack> iNodeRedisCache) {
		this.iNodeRedisCache = iNodeRedisCache;
	}
	public IRedisMapCache<String, ReferPack> getiReferRedisCache() {
		return iReferRedisCache;
	}
	public void setiReferRedisCache(
			IRedisMapCache<String, ReferPack> iReferRedisCache) {
		this.iReferRedisCache = iReferRedisCache;
	}
	public BaseResultFactory<Result> getViewFactory() {
		return viewFactory;
	}
	public void setViewFactory(BaseResultFactory<Result> viewFactory) {
		this.viewFactory = viewFactory;
	}
	public BaseDataMonitor getBaseDataMonitor() {
		return baseDataMonitor;
	}
	public void setBaseDataMonitor(BaseDataMonitor baseDataMonitor) {
		this.baseDataMonitor = baseDataMonitor;
	}
}
