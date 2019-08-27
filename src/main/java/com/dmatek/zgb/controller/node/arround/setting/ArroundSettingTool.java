package com.dmatek.zgb.controller.node.arround.setting;

import java.util.ArrayList;
import java.util.List;

import com.dmatek.zgb.controller.setting.node.IPackTool;
import com.dmatek.zgb.controller.setting.node.UdpCommunicationContainer;
import com.dmatek.zgb.db.pojo.setting.Node;
import com.dmatek.zgb.db.setting.service.NodeService;
import com.dmatek.zgb.monitor.ISendTool;
import com.dmatek.zgb.monitor.bean.NodePack;
import com.dmatek.zgb.monitor.bean.ReferPack;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeTypeVersionUdpPack;
import com.dmatek.zgb.setting.arround.refer.read.pack.ReadArroundReferTypeVersionUdpPack;
import com.dmatek.zgb.setting.pack.abstract_.BaseArroundReadUdpPack;
import com.dmatek.zgb.setting.pack.abstract_.BaseArroundSettingUdpPack;
import com.dmatek.zgb.setting.pack.abstract_.BaseSearchArroundUdpPack;
import com.dmatek.zgb.setting.pack.abstract_.BaseUdpPack;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.setting.vo.SearchDevicePo;
import com.dmatek.zgb.setting.vo.SearchNodePo;
import com.dmatek.zgb.utils.StringUtil;

public class ArroundSettingTool extends BaseArroundSettingTool {
	private UdpCommunicationContainer udpCommunicationContainer;
	private BaseResultFactory<Result> viewResultFactory;
	private IPackTool iPackTool;
	private NodeService nodeService;
	public ArroundSettingTool(ISendTool iSendTool, UdpCommunicationContainer udpCommunicationContainer,
			BaseResultFactory<Result> viewResultFactory, IPackTool iPackTool, 
			NodeService nodeService) {
		super(iSendTool);
		this.udpCommunicationContainer = udpCommunicationContainer;
		this.viewResultFactory = viewResultFactory;
		this.iPackTool = iPackTool;
		this.nodeService = nodeService;
	}
	public UdpCommunicationContainer getUdpCommunicationContainer() {
		return udpCommunicationContainer;
	}
	public void setUdpCommunicationContainer(UdpCommunicationContainer udpCommunicationContainer) {
		this.udpCommunicationContainer = udpCommunicationContainer;
	}
	public BaseResultFactory<Result> getViewResultFactory() {
		return viewResultFactory;
	}
	public void setViewResultFactory(BaseResultFactory<Result> viewResultFactory) {
		this.viewResultFactory = viewResultFactory;
	}
	@Override
	public Result readReferParam(String id, String keyName, String routerId) throws Exception {
		BaseUdpPack baseUdpPack = getUdpCommunicationContainer().instance(keyName);
		if(null != baseUdpPack) {
			baseUdpPack.init(StringUtil.hexStringToByteArray(id), null);
			getUdpCommunicationContainer().saveRedisUdpPack(keyName, baseUdpPack);
			Result result = getiSendTool().sendUdpPacket(routerId,
					baseUdpPack.getBytes());
			if(null != result) {
				return result;
			}
			baseUdpPack = getUdpCommunicationContainer().delayUdpBackPack(
					keyName);
			if(baseUdpPack instanceof BaseArroundReadUdpPack) {
				if (baseUdpPack instanceof ReadArroundReferTypeVersionUdpPack) {
					SearchDevicePo searchDevicePo = ((ReadArroundReferTypeVersionUdpPack) baseUdpPack)
							.parse();
					if(null != searchDevicePo && null != getNodeService()) {
						Node node = getNodeService().findOne(searchDevicePo.getId());
						searchDevicePo.setName(null != node?node.getName():"");
					}
					return getViewResultFactory().successResult(searchDevicePo);
				} else {
					return getViewResultFactory().successResult(
							((BaseArroundReadUdpPack) baseUdpPack).parse());
				}
			}
		}
		return null;
	}
	/**
	 * 搜索周围设备
	 */
	@Override
	public Result searchArroundDevices(String id, String keyName) throws Exception {
		// 1. 生成指定的数据包
		BaseUdpPack baseUdpPack = getUdpCommunicationContainer().instance(keyName);
		if(null != baseUdpPack) {
			// 2. 初始化数据包
			baseUdpPack.init(null, null);
			getUdpCommunicationContainer().saveRedisUdpPack(keyName, baseUdpPack);
			// 3. 发送指定的数据包
			Result result = getiSendTool().sendUdpPacket(id, baseUdpPack.getBytes());
			if(null != result) {
				return result;
			}
			// 4. 等待設備回復
			baseUdpPack = getUdpCommunicationContainer().delayUdpBackPack(keyName);
			if (baseUdpPack instanceof BaseSearchArroundUdpPack) {
				// 5. 获取搜索到的设备讯息
				SearchDevicePo[] searchDevices = ((BaseSearchArroundUdpPack) baseUdpPack).parse();
				List<Result> results = new ArrayList<Result>();
				for (SearchDevicePo searchDevicePo : searchDevices) {
					// 6. 判断当前的设备类型
					Result searchResult = null;
					NodePack nodePack = getiSendTool().findDevice(searchDevicePo.getId());
					if (nodePack instanceof ReferPack) {
						searchResult = readReferParam(searchDevicePo.getId(),
								"ReadArroundReferTypeVersionUdpPack", id);
					} else {
						searchResult = readNodeParam(searchDevicePo.getId(),
								"ReadArroundNodeTypeVersionUdpPack", id,
								((SearchNodePo) searchDevicePo).getChannel());
					}
					if (null != searchResult && searchResult.getCode() == 600) {
						results.add(searchResult);
					}
				}
				return getViewResultFactory().successResult(results);
			}
		}
		return null;
	}
	public IPackTool getiPackTool() {
		return iPackTool;
	}
	public void setiPackTool(IPackTool iPackTool) {
		this.iPackTool = iPackTool;
	}
	@Override
	public Result setReferParam(String id, String keyName, String paramValue,
			String routerId) throws Exception {
		BaseUdpPack baseUdpPack = udpCommunicationContainer
				.instance(keyName);
		if(null != baseUdpPack) {
			byte[] bytes = getiPackTool().parseParamBytes(keyName, paramValue);
			if(null == bytes) {
				return getViewResultFactory().errorResult("獲取參數值失敗");
			}
			// 獲取
			baseUdpPack.init(StringUtil.hexStringToByteArray(id), bytes);
			getUdpCommunicationContainer().saveRedisUdpPack(keyName, baseUdpPack);
			Result result = getiSendTool().sendUdpPacket(routerId, baseUdpPack.getBytes());
			if(null != result) {
				return result;
			}
			baseUdpPack = getUdpCommunicationContainer().delayUdpBackPack(keyName);
			if (baseUdpPack instanceof BaseArroundSettingUdpPack) {
				return getViewResultFactory().successResult();
			}
		}
		return getViewResultFactory().errorResult("獲取數據包失敗");
	}	
	private byte[] joinIdChannel(String id, int channel) {
		byte[] ids = StringUtil.hexStringToByteArray(id);
		byte[] ids_channel = new byte[3];
		System.arraycopy(ids, 0, ids_channel, 0, 2);
		ids_channel[2] = (byte) channel;
		return ids_channel;
	}
	@Override
	public Result readNodeParam(String id, String keyName, String routerId,
			int channel) throws Exception {
		BaseUdpPack baseUdpPack = getUdpCommunicationContainer().instance(keyName);
		if(null != baseUdpPack) {
			baseUdpPack.init(joinIdChannel(id, channel), null);
			getUdpCommunicationContainer().saveRedisUdpPack(keyName, baseUdpPack);
			Result result = getiSendTool().sendUdpPacket(routerId,
					baseUdpPack.getBytes());
			if(null != result) {
				return result;
			}
			baseUdpPack = getUdpCommunicationContainer().delayUdpBackPack(
					keyName);
			if(baseUdpPack instanceof BaseArroundReadUdpPack) {
				if (baseUdpPack instanceof ReadArroundNodeTypeVersionUdpPack) {
					// 转化出搜索的对象
					SearchNodePo searchNodePo = ((ReadArroundNodeTypeVersionUdpPack)baseUdpPack).parse();
					if(null != searchNodePo && null != getNodeService()) {
						Node node = getNodeService().findOne(searchNodePo.getId());
						searchNodePo.setName(null!=node?node.getName():"");
					}
					return getViewResultFactory().successResult(searchNodePo);
				} else {
					return getViewResultFactory().successResult(
							((BaseArroundReadUdpPack) baseUdpPack).parse());
				}
			}
		}
		return null;
	}
	@Override
	public Result setNodeParam(String id, String keyName, String keyParam,
			String routerId, int channel) throws Exception {
		BaseUdpPack baseUdpPack = udpCommunicationContainer.instance(keyName);
		if(null != baseUdpPack) {
			byte[] bytes = getiPackTool().parseParamBytes(keyName, keyParam);
			if(null == bytes) {
				return getViewResultFactory().errorResult("獲取參數值失敗");
			}
			baseUdpPack.init(joinIdChannel(id, channel), bytes);
			getUdpCommunicationContainer().saveRedisUdpPack(keyName, baseUdpPack);
			Result result = getiSendTool().sendUdpPacket(routerId, baseUdpPack.getBytes());
			if(null != result) {
				return result;
			}
			baseUdpPack = getUdpCommunicationContainer().delayUdpBackPack(keyName);
			if (baseUdpPack instanceof BaseArroundSettingUdpPack) {
				return getViewResultFactory().successResult();
			}
		}
		return getViewResultFactory().errorResult("獲取數據包失敗");
	}
	@Override
	public Result readNodeParam(String id, String keyName, String keyParam,
			String routerId, int channel) throws Exception {
		BaseUdpPack baseUdpPack = udpCommunicationContainer
				.instance(keyName);
		if(null != baseUdpPack) {
			baseUdpPack.init(StringUtil.hexStringToByteArray(id), keyParam.getBytes());
			// 需要我們重新設置
			getUdpCommunicationContainer().saveRedisUdpPack(keyName, baseUdpPack);
			Result result = getiSendTool().sendUdpPacket(routerId, baseUdpPack.getBytes());
			if(null != result) {
				return result;
			}
			baseUdpPack = getUdpCommunicationContainer().delayUdpBackPack(keyName);
			if (baseUdpPack instanceof BaseArroundReadUdpPack) {
				return getViewResultFactory().successResult(((BaseArroundReadUdpPack)baseUdpPack).parse());
			}
		}
		return getViewResultFactory().errorResult("獲取數據包失敗");
	}
	private byte[] joinTimeOut(int timeOut, String keyParam) {
		byte[] timeOutParams = new byte[92];
		byte[] timeOutBytes = new byte[2];
		timeOutBytes[0] = (byte)(timeOut >> 8);
		timeOutBytes[1] = (byte)(timeOut);
		System.arraycopy(timeOutBytes, 0, timeOutParams, 0, 2);
		if(!keyParam.endsWith("\r\n")) {
			keyParam += "\r\n";
		}
		System.arraycopy(keyParam.getBytes(), 0, timeOutParams, 2, keyParam.getBytes().length);
		return timeOutParams;
	}
	@Override
	public Result readNodeParam(String id, String keyName, int timeOut,
			String keyParam, String routerId, int channel) throws Exception {
		BaseUdpPack baseUdpPack = udpCommunicationContainer
				.instance(keyName);
		if(null != baseUdpPack) {
			baseUdpPack.init(joinIdChannel(id, channel), joinTimeOut(timeOut, keyParam));
			// 需要我們重新設置
			getUdpCommunicationContainer().saveRedisUdpPack(keyName, baseUdpPack);
			Result result = getiSendTool().sendUdpPacket(routerId, baseUdpPack.getBytes());
			if(null != result) {
				return result;
			}
			baseUdpPack = getUdpCommunicationContainer().delayUdpBackPack(keyName, timeOut);
			if (baseUdpPack instanceof BaseArroundReadUdpPack) {
				return getViewResultFactory().successResult(((BaseArroundReadUdpPack)baseUdpPack).parse());
			}
		}
		return getViewResultFactory().errorResult("獲取數據包失敗");
	}
	public NodeService getNodeService() {
		return nodeService;
	}
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}
	
	
}
