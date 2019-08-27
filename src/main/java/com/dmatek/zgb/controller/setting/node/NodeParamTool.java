package com.dmatek.zgb.controller.setting.node;

import com.dmatek.zgb.monitor.ISendTool;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlyReadUdpPack;
import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlySettingUdpPack;
import com.dmatek.zgb.setting.pack.abstract_.BaseUdpPack;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.utils.StringUtil;

public class NodeParamTool extends BaseDeviceParamTool {
	private UdpCommunicationContainer udpCommunicationContainer;
	private BaseResultFactory<Result> viewResultFactory;
	private IPackTool iPackTool;
	public NodeParamTool(UdpCommunicationContainer udpCommunicationContainer,
			BaseResultFactory<Result> viewResultFactory,
			ISendTool iSendTool,
			IPackTool iPackTool) {
		super(iSendTool);
		this.viewResultFactory = viewResultFactory;
		this.udpCommunicationContainer = udpCommunicationContainer;
		this.iPackTool = iPackTool;
	}
	@Override
	public Result readParam(String id, String paramKey, String val)
			throws Exception {
		BaseUdpPack baseUdpPack = udpCommunicationContainer
				.instance(paramKey);
		if(null != baseUdpPack) {
			baseUdpPack.init(StringUtil.hexStringToByteArray(id), val.getBytes());
			// 需要我們重新設置
			getUdpCommunicationContainer().saveRedisUdpPack(paramKey, baseUdpPack);
			Result result = getiSendTool().sendUdpPacket(id, baseUdpPack.getBytes());
			if(null != result) {
				return result;
			}
			baseUdpPack = getUdpCommunicationContainer().delayUdpBackPack(paramKey);
			if (baseUdpPack instanceof BaseDirectlyReadUdpPack) {
				return getViewResultFactory().successResult(((BaseDirectlyReadUdpPack)baseUdpPack).parse());
			}
		}
		return getViewResultFactory().errorResult("獲取數據包失敗");
	}
	@Override
	public Result readParam(String id, String paramKey) 
			throws Exception {
		BaseUdpPack baseUdpPack = udpCommunicationContainer
				.instance(paramKey);
		if(null != baseUdpPack) {
			baseUdpPack.init(StringUtil.hexStringToByteArray(id), null);
			getUdpCommunicationContainer().saveRedisUdpPack(paramKey, baseUdpPack);
			Result result = getiSendTool().sendUdpPacket(id, baseUdpPack.getBytes());
			if(null != result) {
				return result;
			}
			baseUdpPack = getUdpCommunicationContainer().delayUdpBackPack(paramKey);
			if (baseUdpPack instanceof BaseDirectlyReadUdpPack) {
				return getViewResultFactory().successResult(
						((BaseDirectlyReadUdpPack) baseUdpPack).parse());
			}
		}
		return getViewResultFactory().errorResult("獲取數據包失敗");
	}
	@Override
	public Result setParam(String id, String paramKey, String paramVal)
			throws Exception {
		BaseUdpPack baseUdpPack = udpCommunicationContainer
				.instance(paramKey);
		if(null != baseUdpPack) {
			byte[] bytes = getiPackTool().parseParamBytes(paramKey, paramVal);
			if(null == bytes) {
				return getViewResultFactory().errorResult("獲取參數值失敗");
			}
			// 獲取
			baseUdpPack.init(StringUtil.hexStringToByteArray(id), bytes);
			getUdpCommunicationContainer().saveRedisUdpPack(paramKey, baseUdpPack);
			Result result = getiSendTool().sendUdpPacket(id, baseUdpPack.getBytes());
			if(null != result) {
				return result;
			}
			baseUdpPack = getUdpCommunicationContainer().delayUdpBackPack(paramKey);
			if (baseUdpPack instanceof BaseDirectlySettingUdpPack) {
				return getViewResultFactory().successResult();
			}
		}
		return getViewResultFactory().errorResult("獲取數據包失敗");
	}
	public UdpCommunicationContainer getUdpCommunicationContainer() {
		return udpCommunicationContainer;
	}
	public void setUdpCommunicationContainer(
			UdpCommunicationContainer udpCommunicationContainer) {
		this.udpCommunicationContainer = udpCommunicationContainer;
	}
	public BaseResultFactory<Result> getViewResultFactory() {
		return viewResultFactory;
	}
	public void setViewResultFactory(BaseResultFactory<Result> viewResultFactory) {
		this.viewResultFactory = viewResultFactory;
	}
	public IPackTool getiPackTool() {
		return iPackTool;
	}
	public void setiPackTool(IPackTool iPackTool) {
		this.iPackTool = iPackTool;
	}
}
