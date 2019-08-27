package com.dmatek.zgb.monitor;

import java.net.InetAddress;
import java.util.Date;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.controller.setting.node.UdpCommunicationContainer;
import com.dmatek.zgb.monitor.bean.NodePack;
import com.dmatek.zgb.monitor.bean.ReferPack;
import com.dmatek.zgb.monitor.bean.ReferSig;
import com.dmatek.zgb.monitor.bean.TagPacket;
import com.dmatek.zgb.monitor.callback.MonitorCallBack;
import com.dmatek.zgb.monitor.utils.DataProtocol;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeAtCommandUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeGateWayUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeLanIpModeUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeLanStaticIpUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeLastState;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeMaskUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeServerIpUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeServerPortUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeTypeVersionUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeWifiIpModeUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeWifiNameUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeWifiPswUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeWifiRssiUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ReadArroundNodeWifiStaticIpUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.ResetArroundNodeUdpPack;
import com.dmatek.zgb.setting.arround.node.read.pack.SearchArroundNodesUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeGateWayUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeLanIpModeUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeLanIpUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeServerIpUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeServerPortUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeSubMaskUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeWifiIpModelUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeWifiNameUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeWifiPswUdpPack;
import com.dmatek.zgb.setting.arround.node.setting.pack.SetArroundNodeWifiStaticIpUdpPack;
import com.dmatek.zgb.setting.arround.refer.read.pack.ReadArroundBetweenSigUdpPack;
import com.dmatek.zgb.setting.arround.refer.read.pack.ReadArroundReferSigKUdpPack;
import com.dmatek.zgb.setting.arround.refer.read.pack.ReadArroundReferSigThresholdUdpPack;
import com.dmatek.zgb.setting.arround.refer.read.pack.ReadArroundReferTypeVersionUdpPack;
import com.dmatek.zgb.setting.arround.refer.read.pack.ResetArroundReferUdpPack;
import com.dmatek.zgb.setting.arround.refer.read.pack.SearchArroundRefersUdpPack;
import com.dmatek.zgb.setting.arround.refer.setting.pack.SetArroundReferSigKUdpPack;
import com.dmatek.zgb.setting.arround.refer.setting.pack.SetArroundReferSigThresholdUdp;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadIPUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadIpModeUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadNodeArroundWifiRssiUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadNodeChannelUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadNodeGateWayUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadNodeIpUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadNodeMaskUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadNodeTypeVersionUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadNodeWifiIpModeUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadNodeWifiStaticIpUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadPortUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadWifiNameUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ReadWifiPswUdpPack;
import com.dmatek.zgb.setting.directly.node.read.pack.ResetNodePack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetNodeIpModeUdpPack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetNodeSelfGateWayUdpPack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetNodeSelfIPUdpPack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetNodeSelfMaskUdpPack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetNodeServerIpUdpPack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetNodeServerPortUdpPack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetWifiIpModeUdpPack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetWifiNameUdpPack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetWifiPswUdpPack;
import com.dmatek.zgb.setting.directly.node.set.pack.SetWifiStaticIpUdpPack;
import com.dmatek.zgb.setting.directly.refer.read.pack.ReadReferReceiveSignUdpPack;
import com.dmatek.zgb.setting.directly.refer.read.pack.ReadReferSignKUdpPack;
import com.dmatek.zgb.setting.directly.refer.read.pack.ReadReferTypeVersionUdpPack;
import com.dmatek.zgb.setting.directly.refer.read.pack.ResetReferUdpPack;
import com.dmatek.zgb.setting.directly.refer.set.pack.SetReferReceiveSignUdpPack;
import com.dmatek.zgb.setting.directly.refer.set.pack.SetReferSignKUdpPack;

public abstract class BaseParseDataMonitor extends BaseDataMonitor implements IDataParse {
	private boolean isOptimized = true;
	private int optmodelval = 2;// 两次信号差值超过2db才改变参考点
	private int optionmodel = 1;
	private UdpCommunicationContainer udpCommunicationContainer;
	public BaseParseDataMonitor(MonitorCallBack zgbMonitorCallBack, 
			UdpCommunicationContainer udpCommunicationContainer) {
		super(zgbMonitorCallBack);
		this.udpCommunicationContainer = udpCommunicationContainer;
	}
	@Override
	protected void initOptimizes(int... optimizes) {
		if(null != optimizes && optimizes.length >= 2) {
			optionmodel = optimizes[0];
			optmodelval = optimizes[1];
		}
	}
	@Override
	public int packParse(byte[] cache, int cachePos,InetAddress inetAddress, int port) throws Exception {
		int start = 0,len = 0;
		short cs = 0;
		while(cachePos >= DataProtocol.ZGB_MINPACK_SIZE) {
			// 1. 处理正常上报卡片、基站、参考点的数据包
			start = DataProtocol.FindChar(cache, DataProtocol.tag_head, 0, cachePos);
			if(start >= 0 && start <= cachePos) {
				if(start + 1 < cachePos - 1) {
					if(cache[start + 1] == DataProtocol.tag_type1 || cache[start + 1] == DataProtocol.tag_type2){
						if(start + 12 < cachePos - 1) {
							//一个Tag上报包的长度len
							len = cache[start + 12] * 3 + 15;
							if(start + len <= cachePos) {
								if(cache[start + len - 1] == DataProtocol.tag_end) {
									// 判断校验码
									cs = 0;
									for (int i = start; i < start + len - 2; i++) {
										cs += cache[i];
									}
									cs &= 0xFF;
									if((byte)cs == cache[start + len - 2]){
										// 校验成功
										parseTagPack(cache, start, cachePos, inetAddress);
									}else{
										// 数据校验失败，说明当前数据包有误，我们应该舍弃当前的这一个数据包
										System.out.println("这是一个校验失败的数据包");
									}
									cachePos -=  (start + len);
									System.arraycopy(cache, start + len, cache, 0, cachePos);
								}else{ // 说明当前的数据包的包尾不正确, 我们应该继续向后面查找
									cachePos = cachePos - start - 1;
									System.arraycopy(cache, start + 1, cache, 0, cachePos);
								}
								continue;
							}
						}
					}else if(cache[start + 1] == DataProtocol.refer_type) {
						// 这是一个参考点类型的数据包
						if(start + DataProtocol.SINGLE_REFERPACK_SIZE <= cachePos) {
							if(cache[start + DataProtocol.SINGLE_REFERPACK_SIZE - 1] == DataProtocol.refer_end){
								cs = 0;
								for (int i = start; i < start + DataProtocol.SINGLE_REFERPACK_SIZE - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + DataProtocol.SINGLE_REFERPACK_SIZE - 2]){
									// 参考点解析
									parseReferPack(cache, start, cachePos, inetAddress, port);
								}else{
									// 校验失败
									System.out.println("参考点校验失败!");
								}
								cachePos -= (start + DataProtocol.SINGLE_REFERPACK_SIZE);
			                	System.arraycopy(cache, start + DataProtocol.SINGLE_REFERPACK_SIZE, cache, 0, cachePos);
							}else{
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					}else if(cache[start + 1] == DataProtocol.node_type){
						// 这是一个数据节点类型数据包
						if(start + DataProtocol.SINGLE_NODEPACK_SIZE <= cachePos) {
							if(cache[start + DataProtocol.SINGLE_NODEPACK_SIZE - 1] == DataProtocol.refer_end) {
								cs = 0;
								for (int i = start; i < start + DataProtocol.SINGLE_NODEPACK_SIZE - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + DataProtocol.SINGLE_NODEPACK_SIZE - 2]) {
									// 校验成功
									parseNodePack(cache, start, cachePos,inetAddress,port);
								} else {
									// 校验失败
									System.out.println("数据节点校验失败!");
								}
								cachePos -= (start + DataProtocol.SINGLE_NODEPACK_SIZE);
			                	System.arraycopy(cache, start + DataProtocol.SINGLE_NODEPACK_SIZE, cache, 0, cachePos);
							} else {
								cachePos = cachePos - start - 1;
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else {
						// 凑巧中间一包数据是包头，我们将前面的数据包清除掉
						cachePos = (start + 1);
						System.arraycopy(cache, start + 1, cache, 0, cachePos);
						continue;
					}
				}
			}
			/*2. 处理直接设置参考点和基站的数据包*/
			start = DataProtocol.FindChar(cache, DataProtocol.tag_setting_head, 0, cachePos);
			if(start >= 0 && start <= cachePos) {
				if(start + 1 < cachePos - 1) {
					if(cache[start + 1] == DataProtocol.type_reset) {
						// 判断是否是复位节点的数据包
						if(start + 6 <= cachePos) {
							if(cache[start + 5] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 6 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 6 - 2]) {// 校验相同
									getUdpCommunicationContainer().setUdpPackState(ResetNodePack.KEY_NAME);
								} 
								cachePos -= (start + 6);
			                	System.arraycopy(cache, start + 6, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_read_ip) {
						if(start + 10 <= cachePos) {
							if(cache[start + 9] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(
													ReadIPUdpPack.KEY_NAME,
													cache, start + 4);
								} 
								cachePos -= (start + 10);
			                	System.arraycopy(cache, start + 10, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_read_port) {
						if(start + 8 <= cachePos) {
							if(cache[start + 7] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 8 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 8 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(
													ReadPortUdpPack.KEY_NAME,
													cache, start + 4);
								} 
								cachePos -= (start + 8);
			                	System.arraycopy(cache, start + 8, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_read_wifiName) {
						if(start + 38 <= cachePos) {
							if(cache[start + 37] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 38 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 38 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(
													ReadWifiNameUdpPack.KEY_NAME,
													cache, start + 4);
								} 
								cachePos -= (start + 38);
			                	System.arraycopy(cache, start + 38, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_read_wifiPsw) {
						if(start + 38 <= cachePos) {
							if(cache[start + 37] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 38 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 38 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(
													ReadWifiPswUdpPack.KEY_NAME,
													cache, start + 4);
								} 
								cachePos -= (start + 38);
			                	System.arraycopy(cache, start + 38, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_read_ipMode) {
						if(start + 7 <= cachePos) {
							if(cache[start + 6] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 5; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 5]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(
													ReadIpModeUdpPack.KEY_NAME,
													cache, start + 4);
								} 
								cachePos -= (start + 7);
			                	System.arraycopy(cache, start + 7, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_read_nodeip){
						if(start + 10 <= cachePos) {
							if(cache[start + 9] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(
													ReadNodeIpUdpPack.KEY_NAME,
													cache, start + 4);
								} 
								cachePos -= (start + 10);
			                	System.arraycopy(cache, start + 10, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_read_nodemask) {
						if(start + 10 <= cachePos) {
							if(cache[start + 9] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(
													ReadNodeMaskUdpPack.KEY_NAME,
													cache, start + 4);
								} 
								cachePos -= (start + 10);
			                	System.arraycopy(cache, start + 10, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_read_nodegateway) {
						if(start + 10 <= cachePos) {
							if(cache[start + 9] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(
													ReadNodeGateWayUdpPack.KEY_NAME,
													cache, start + 4);
								} 
								cachePos -= (start + 10);
			                	System.arraycopy(cache, start + 10, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_read_dev_type_version){
						if(start + 11 <= cachePos) {
							if(cache[start + 10] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 11 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 11 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(
													ReadNodeTypeVersionUdpPack.KEY_NAME,
													cache, start + 2);
								} 
								cachePos -= (start + 11);
			                	System.arraycopy(cache, start + 11, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_read_nodechannel) {
						if(start + 7 <= cachePos) {
							if(cache[start + 6] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 7 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 7 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(
													ReadNodeChannelUdpPack.KEY_NAME,
													cache, start + 4);
								} 
								cachePos -= (start + 7);
			                	System.arraycopy(cache, start + 7, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_read_nodearroundrssi) {
						if(start + 7 <= cachePos) {
							if(cache[start + 6] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 7 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 7 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(
													ReadNodeArroundWifiRssiUdpPack.KEY_NAME,
													cache, start + 4);
								} 
								cachePos -= (start + 7);
			                	System.arraycopy(cache, start + 7, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_read_nodewifiipmode){
						if(start + 7 <= cachePos) {
							if(cache[start + 6] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 7 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 7 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(
													ReadNodeWifiIpModeUdpPack.KEY_NAME,
													cache, start + 4);
								} 
								cachePos -= (start + 7);
			                	System.arraycopy(cache, start + 7, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_read_nodewifistaticip) {
						if(start + 10 <= cachePos) {
							if(cache[start + 9] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(
													ReadNodeWifiStaticIpUdpPack.KEY_NAME,
													cache, start + 4);
								} 
								cachePos -= (start + 10);
			                	System.arraycopy(cache, start + 10, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_set_serverip) {
						if(start + 10 <= cachePos) {
							if(cache[start + 9] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer().setUdpPackState(SetNodeServerIpUdpPack.KEY_NAME);
								} 
								cachePos -= (start + 10);
			                	System.arraycopy(cache, start + 10, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_set_serverport) {// 设置Server端口
						if(start + 8 <= cachePos) {
							if(cache[start + 7] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 8 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 8 - 2]) {// 校验相同
									getUdpCommunicationContainer().setUdpPackState(SetNodeServerPortUdpPack.KEY_NAME);
								} 
								cachePos -= (start + 8);
			                	System.arraycopy(cache, start + 8, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_set_wifiname){
						if(start + 38 <= cachePos) {
							if(cache[start + 37] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 38 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 38 - 2]) {// 校验相同
									getUdpCommunicationContainer().setUdpPackState(SetWifiNameUdpPack.KEY_NAME);
								} 
								cachePos -= (start + 38);
			                	System.arraycopy(cache, start + 38, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					}else if(cache[start + 1] == DataProtocol.type_set_wifipsw) {
						if(start + 38 <= cachePos) {
							if(cache[start + 37] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 38 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 38 - 2]) {// 校验相同
									getUdpCommunicationContainer().setUdpPackState(SetWifiPswUdpPack.KEY_NAME);
								} 
								cachePos -= (start + 38);
			                	System.arraycopy(cache, start + 38, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_set_ipmode) {
						if(start + 7 <= cachePos) {
							if(cache[start + 6] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 7 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 7 - 2]) {// 校验相同
									getUdpCommunicationContainer().setUdpPackState(SetNodeIpModeUdpPack.KEY_NAME);
								} 
								cachePos -= (start + 7);
			                	System.arraycopy(cache, start + 7, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_set_nodeselfip) {
						if(start + 10 <= cachePos) {
							if(cache[start + 9] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer().setUdpPackState(SetNodeSelfIPUdpPack.KEY_NAME);
								} 
								cachePos -= (start + 10);
			                	System.arraycopy(cache, start + 10, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_set_nodeselfmask) {
						if(start + 10 <= cachePos) {
							if(cache[start + 9] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer().setUdpPackState(SetNodeSelfMaskUdpPack.KEY_NAME);
								} 
								cachePos -= (start + 10);
			                	System.arraycopy(cache, start + 10, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_set_nodeselfgateway) {
						if(start + 10 <= cachePos) {
							if(cache[start + 9] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer().setUdpPackState(SetNodeSelfGateWayUdpPack.KEY_NAME);
								} 
								cachePos -= (start + 10);
			                	System.arraycopy(cache, start + 10, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_set_wifiipmode){
						if(start + 7 <= cachePos) {
							if(cache[start + 6] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 7 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 7 - 2]) {// 校验相同
									getUdpCommunicationContainer().setUdpPackState(SetWifiIpModeUdpPack.KEY_NAME);
								} 
								cachePos -= (start + 7);
			                	System.arraycopy(cache, start + 7, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						} 
					} else if(cache[start + 1] == DataProtocol.type_set_wifistaticip) {
						if(start + 10 <= cachePos) {
							if(cache[start + 9] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer().setUdpPackState(SetWifiStaticIpUdpPack.KEY_NAME);
								} 
								cachePos -= (start + 10);
			                	System.arraycopy(cache, start + 10, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						} 
					} else if(cache[start + 1] == DataProtocol.type_refer_reset){// 对参考点进行复位
						if(start + 6 <= cachePos) {
							if(cache[start + 5] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 6 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 6 - 2]) {// 校验相同
									getUdpCommunicationContainer().setUdpPackState(ResetReferUdpPack.KEY_NAME);
								} 
								cachePos -= (start + 6);
			                	System.arraycopy(cache, start + 6, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_read_refertypeversion) {
						if(start + 13 <= cachePos) {
							if(cache[start + 12] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 13 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 13 - 2]) {// 校验相同
									getUdpCommunicationContainer().setUdpPackVal(ReadReferTypeVersionUdpPack.KEY_NAME, 
											cache, start + 4);
								} 
								cachePos -= (start + 13);
			                	System.arraycopy(cache, start + 13, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_read_referreceivesig) {
						if(start + 9 <= cachePos) {
							if(cache[start + 8] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 9 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 9 - 2]) {// 校验相同
									getUdpCommunicationContainer().setUdpPackVal(ReadReferReceiveSignUdpPack.KEY_NAME, 
											cache, start + 6);
								} 
								cachePos -= (start + 9);
			                	System.arraycopy(cache, start + 9, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_read_refersigk) {
						if(start + 9 <= cachePos) {
							if(cache[start + 8] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 9 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 9 - 2]) {// 校验相同
									getUdpCommunicationContainer().setUdpPackVal(ReadReferSignKUdpPack.KEY_NAME, 
											cache, start + 6);
								} 
								cachePos -= (start + 9);
			                	System.arraycopy(cache, start + 9, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_set_referreceivesig) {
						if(start + 9 <= cachePos) {
							if(cache[start + 8] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 9 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 9 - 2]) {// 校验相同
									getUdpCommunicationContainer().setUdpPackState(SetReferReceiveSignUdpPack.KEY_NAME);
								} 
								cachePos -= (start + 9);
			                	System.arraycopy(cache, start + 9, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_set_refersigk) {
						if(start + 9 <= cachePos) {
							if(cache[start + 8] == DataProtocol.tag_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 9 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if((byte)cs == cache[start + 9 - 2]) {// 校验相同
									getUdpCommunicationContainer().setUdpPackState(SetReferSignKUdpPack.KEY_NAME);
								} 
								cachePos -= (start + 9);
			                	System.arraycopy(cache, start + 9, cache, 0, cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0, cachePos);
							}
							continue;
						}
					} else {
						// 凑巧中间一包数据是包头，我们将前面的数据包清除掉
						cachePos = (start + 1);
						System.arraycopy(cache, start + 1, cache, 0, cachePos);
						continue;
					}
				}
			}
			/*3. 通过基站设置周围的设备*/
			start = DataProtocol.FindChar(cache, DataProtocol.type_arround_setting_head, 0, cachePos);
			if (start >= 0 && start <= cachePos) {
				if(start + 1 <= cachePos - 1) {
					if (cache[start + 1] == DataProtocol.type_arround_searchnodes) {
						// 搜索節點設備
						int count = cache[start + 2];
						int size = count * 3 + 1 + 4;
						// 判断是否是复位节点的数据包
						if (start + size <= cachePos) {
							if (cache[start + size - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + size - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + size - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(
													SearchArroundNodesUdpPack.KEY_NAME,
													cache, start + 2);
								}
								cachePos -= (start + size);
								System.arraycopy(cache, start + size, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if (cache[start + 1] == DataProtocol.type_arround_searchrefers) {
						// 搜索節點設備
						int count = cache[start + 2];
						int size = count * 2 + 1 + 4;
						// 判断是否是复位节点的数据包
						if (start + size <= cachePos) {
							if (cache[start + size - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + size - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + size - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(
													SearchArroundRefersUdpPack.KEY_NAME,
													cache, start + 2);
								}
								cachePos -= (start + size);
								System.arraycopy(cache, start + size, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_readrefertypeversion) {
						if (start + 11 <= cachePos) {
							if (cache[start + 11 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 11 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 11 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ReadArroundReferTypeVersionUdpPack.KEY_NAME,
													cache, start + 2);
								}
								cachePos -= (start + 11);
								System.arraycopy(cache, start + 11, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_readrefersigthreshold) {
						if (start + 7 <= cachePos) {
							if (cache[start + 7 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 7 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 7 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ReadArroundReferSigThresholdUdpPack.KEY_NAME,
													cache, start + 4);
								}
								cachePos -= (start + 7);
								System.arraycopy(cache, start + 7, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_readrefersigK) {
						if (start + 7 <= cachePos) {
							if (cache[start + 7 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 7 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 7 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ReadArroundReferSigKUdpPack.KEY_NAME,
													cache, start + 4);
								}
								cachePos -= (start + 7);
								System.arraycopy(cache, start + 7, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_readbetweenrssi) {
						if (start + 8 <= cachePos) {
							if (cache[start + 8 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 8 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 8 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ReadArroundBetweenSigUdpPack.KEY_NAME,
													cache, start + 4);
								}
								cachePos -= (start + 8);
								System.arraycopy(cache, start + 8, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_resetrefer) {
						if (start + 6 <= cachePos) {
							if (cache[start + 6 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 6 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 6 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ResetArroundReferUdpPack.KEY_NAME,
													cache, start + 4);
								}
								cachePos -= (start + 6);
								System.arraycopy(cache, start + 6, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_setrefersigthreshold){
						if (start + 7 <= cachePos) {
							if (cache[start + 7 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 7 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 7 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackState(SetArroundReferSigThresholdUdp.KEY_NAME);
								}
								cachePos -= (start + 7);
								System.arraycopy(cache, start + 7, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_setrefersigk){
						if (start + 7 <= cachePos) {
							if (cache[start + 7 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 7 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 7 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackState(SetArroundReferSigKUdpPack.KEY_NAME);
								}
								cachePos -= (start + 7);
								System.arraycopy(cache, start + 7, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_readnodetypeversion) {
						if (start + 12 <= cachePos) {
							if (cache[start + 12 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 12 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 12 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ReadArroundNodeTypeVersionUdpPack.KEY_NAME,
													cache, start + 2);
								}
								cachePos -= (start + 12);
								System.arraycopy(cache, start + 12, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_readnodeserverip) {
						if (start + 10 <= cachePos) {
							if (cache[start + 10 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ReadArroundNodeServerIpUdpPack.KEY_NAME,
													cache, start + 4);
								}
								cachePos -= (start + 10);
								System.arraycopy(cache, start + 10, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_readnodeserverport) {
						if (start + 8 <= cachePos) {
							if (cache[start + 8 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 8 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 8 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ReadArroundNodeServerPortUdpPack.KEY_NAME,
													cache, start + 4);
								}
								cachePos -= (start + 8);
								System.arraycopy(cache, start + 8, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_readnodewifiname){
						if (start + 38 <= cachePos) {
							if (cache[start + 38 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 38 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 38 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ReadArroundNodeWifiNameUdpPack.KEY_NAME,
													cache, start + 4);
								}
								cachePos -= (start + 38);
								System.arraycopy(cache, start + 38, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_readnodewifipsw) {
						if (start + 38 <= cachePos) {
							if (cache[start + 38 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 38 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 38 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ReadArroundNodeWifiPswUdpPack.KEY_NAME,
													cache, start + 4);
								}
								cachePos -= (start + 38);
								System.arraycopy(cache, start + 38, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_readnodelanipmode){
						if (start + 7 <= cachePos) {
							if (cache[start + 7 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 7 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 7 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ReadArroundNodeLanIpModeUdpPack.KEY_NAME,
													cache, start + 4);
								}
								cachePos -= (start + 7);
								System.arraycopy(cache, start + 7, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_readnodelanstaticip) {
						if (start + 10 <= cachePos) {
							if (cache[start + 10 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ReadArroundNodeLanStaticIpUdpPack.KEY_NAME,
													cache, start + 4);
								}
								cachePos -= (start + 10);
								System.arraycopy(cache, start + 10, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_readnodelanmask) {
						if (start + 10 <= cachePos) {
							if (cache[start + 10 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ReadArroundNodeMaskUdpPack.KEY_NAME,
													cache, start + 4);
								}
								cachePos -= (start + 10);
								System.arraycopy(cache, start + 10, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_readnodelangateway) {
						if (start + 10 <= cachePos) {
							if (cache[start + 10 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ReadArroundNodeGateWayUdpPack.KEY_NAME,
													cache, start + 4);
								}
								cachePos -= (start + 10);
								System.arraycopy(cache, start + 10, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_readnodelaststate) {
						if (start + 7 <= cachePos) {
							if (cache[start + 7 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 7 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 7 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ReadArroundNodeLastState.KEY_NAME,
													cache, start + 4);
								}
								cachePos -= (start + 7);
								System.arraycopy(cache, start + 7, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_readnodewifiipmode) {
						if (start + 7 <= cachePos) {
							if (cache[start + 7 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 7 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 7 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ReadArroundNodeWifiIpModeUdpPack.KEY_NAME,
													cache, start + 4);
								}
								cachePos -= (start + 7);
								System.arraycopy(cache, start + 7, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_readnodewifistaticip) {
						if (start + 10 <= cachePos) {
							if (cache[start + 10 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ReadArroundNodeWifiStaticIpUdpPack.KEY_NAME,
													cache, start + 4);
								}
								cachePos -= (start + 10);
								System.arraycopy(cache, start + 10, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_readnodereset) {
						if (start + 6 <= cachePos) {
							if (cache[start + 6 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 6 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 6 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ResetArroundNodeUdpPack.KEY_NAME,
													cache, start + 4);
								}
								cachePos -= (start + 6);
								System.arraycopy(cache, start + 6, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_setnodeserverip) {
						if (start + 10 <= cachePos) {
							if (cache[start + 10 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackState(SetArroundNodeServerIpUdpPack.KEY_NAME);
								}
								cachePos -= (start + 10);
								System.arraycopy(cache, start + 10, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_setnodeserverport) {
						if (start + 8 <= cachePos) {
							if (cache[start + 8 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 8 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 8 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackState(SetArroundNodeServerPortUdpPack.KEY_NAME);
								}
								cachePos -= (start + 8);
								System.arraycopy(cache, start + 8, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_setnodelanipmode) {
						if (start + 7 <= cachePos) {
							if (cache[start + 7 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 7 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 7 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackState(SetArroundNodeLanIpModeUdpPack.KEY_NAME);
								}
								cachePos -= (start + 7);
								System.arraycopy(cache, start + 7, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_setnodesubmask) {
						if (start + 10 <= cachePos) {
							if (cache[start + 10 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackState(SetArroundNodeSubMaskUdpPack.KEY_NAME);
								}
								cachePos -= (start + 10);
								System.arraycopy(cache, start + 10, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_setnodegateway) {
						if (start + 10 <= cachePos) {
							if (cache[start + 10 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackState(SetArroundNodeGateWayUdpPack.KEY_NAME);
								}
								cachePos -= (start + 10);
								System.arraycopy(cache, start + 10, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_setnodelanip) {
						if (start + 10 <= cachePos) {
							if (cache[start + 10 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackState(SetArroundNodeLanIpUdpPack.KEY_NAME);
								}
								cachePos -= (start + 10);
								System.arraycopy(cache, start + 10, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_setnodewifiname) {
						if (start + 38 <= cachePos) {
							if (cache[start + 38 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 38 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 38 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackState(SetArroundNodeWifiNameUdpPack.KEY_NAME);
								}
								cachePos -= (start + 38);
								System.arraycopy(cache, start + 38, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_setnodewifipsw) {
						if (start + 38 <= cachePos) {
							if (cache[start + 38 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 38 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 38 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackState(SetArroundNodeWifiPswUdpPack.KEY_NAME);
								}
								cachePos -= (start + 38);
								System.arraycopy(cache, start + 38, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_setnodewifiipmodel) {
						if (start + 7 <= cachePos) {
							if (cache[start + 7 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 7 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 7 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackState(SetArroundNodeWifiIpModelUdpPack.KEY_NAME);
								}
								cachePos -= (start + 7);
								System.arraycopy(cache, start + 7, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_setnodewifistaticip) {
						if (start + 10 <= cachePos) {
							if (cache[start + 10 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 10 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 10 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackState(SetArroundNodeWifiStaticIpUdpPack.KEY_NAME);
								}
								cachePos -= (start + 10);
								System.arraycopy(cache, start + 10, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_searchnodewifirssi) {
						if (start + 7 <= cachePos) {
							if (cache[start + 7 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 7 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 7 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ReadArroundNodeWifiRssiUdpPack.KEY_NAME, 
													cache, start + 4);
								}
								cachePos -= (start + 7);
								System.arraycopy(cache, start + 7, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else if(cache[start + 1] == DataProtocol.type_arround_readatcommand) {
						if (start + 96 <= cachePos) {
							if (cache[start + 96 - 1] == DataProtocol.type_arround_setting_end) {
								// 判断数据包的包尾是否正确
								cs = 0;
								for (int i = start; i < start + 96 - 2; i++) {
									cs += cache[i];
								}
								cs &= 0xFF;
								if ((byte) cs == cache[start + 96 - 2]) {// 校验相同
									getUdpCommunicationContainer()
											.setUdpPackVal(ReadArroundNodeAtCommandUdpPack.KEY_NAME, 
													cache, start + 4);
								}
								cachePos -= (start + 96);
								System.arraycopy(cache, start + 96, cache, 0,
										cachePos);
							} else {
								cachePos -= (start + 1);
								System.arraycopy(cache, start + 1, cache, 0,
										cachePos);
							}
							continue;
						}
					} else {
						// 凑巧中间一包数据是包头，我们将前面的数据包清除掉
						cachePos = (start + 1);
						System.arraycopy(cache, start + 1, cache, 0, cachePos);
						continue;
					}
				}
			} else {
				// 说明此时没有任何包头是符合的
				cachePos = 0;
			}
		}
		return start;
	}
	/**
	 * 连续跳点次数优化
	 * @param tagPacket
	 * @param minReferSig
	 * @param minRssi
	 * @param lastReferId
	 * @param curReferId
	 */
	private void hopsOptimizeModel(TagPacket tagPacket, ReferSig minReferSig,
			byte minRssi, String lastReferId, String curReferId){
		if(null != lastReferId && lastReferId.equals(curReferId)){
			// 两次都跳点到相同的位置，可以直接更新
			if(null != minReferSig){
				tagPacket.setRssi(minRssi);
				tagPacket.setJumpnum(0);
			}
		}else{
			if(tagPacket.getJumpnum() >= optmodelval){
				//大于跳点的阀值，需要我们进行更改位置
				if(null != minReferSig){
					tagPacket.setRssi(minRssi);
					tagPacket.setrId(minReferSig.getId());
				}
				tagPacket.setJumpnum(0);
			} else {
				String lastJumpId = tagPacket.getLastjumpId();
				if(StringUtils.isEmpty(lastJumpId)){
					// 说明是第一次发生跳点
					if(null != minReferSig) {
						tagPacket.setJumpnum(tagPacket.getJumpnum() + 1);
						tagPacket.setLastjumpId(minReferSig.getId());
					}else{
						tagPacket.setJumpnum(0);
					}
				}else{
					if(null != lastJumpId){
						 if(lastJumpId.equals(curReferId)){
							 tagPacket.setJumpnum(tagPacket.getJumpnum() + 1);
						 }else{
							 if(null != minReferSig){
								 tagPacket.setLastjumpId(minReferSig.getId());
							 } 
							 tagPacket.setJumpnum(0);
						 }
					 }
				}
			}
		}
	}
	/**
	 * 信号强度优化
	 * @param tagPacket
	 * @param minReferSig
	 * @param minRssi
	 * @param lastReferId
	 * @param curReferId
	 */
	private void rssiOptimizeModel(TagPacket tagPacket, ReferSig minReferSig,
			byte minRssi, String lastReferId, String curReferId) {
		/**
		 * 1.判断当前的Tag是否处于静止状态 # 静止状态 新的位置，信号比原来的好，则更新新的位置 # 移动状态 比较两次基站是否相同
		 * 相同：没有掉包 直接更新 不相同：这次可能存在丢包的 判断是否丢包 连续两次丢包，则更新 否则不更新
		 */
		if (tagPacket.getNotMoveTime() > 3) {// 静止状态
			// 比较当前基站信号是否比之前信号好
			if (minRssi < tagPacket.getRssi()) {
				tagPacket.setRssi(minRssi);
				tagPacket.setrId(curReferId);
			}
		} else {// 移动状态
			// 比较两次基站是否相同
			if (null != lastReferId && lastReferId.equals(curReferId)) {
				// 说明两次基站相同, 不可能发生掉包
				tagPacket.setRssi(minRssi);
				tagPacket.setJumpnum(0);
			} else {
				// 两次基站不相同，可能最强点存在丢包的情况
				ReferSig lastbestincur = tagPacket.getCurrefsigs().get(
						lastReferId);
				if (null != lastbestincur) {
					// 说明上一次的最强点没有丢包
					if (lastbestincur.getRssi() - minRssi >= optmodelval) {
						tagPacket.setrId(minReferSig.getId());
						tagPacket.setRssi(minReferSig.getRssi());
					} else {
						tagPacket.setRssi(lastbestincur.getRssi());
					}
					tagPacket.setJumpnum(0);
				} else {
					// 可能存在丢包情况
					// 上一次的最强点丢包
					if (tagPacket.getJumpnum() >= 1) {
						if (null != minReferSig) {
							tagPacket.setrId(minReferSig.getId());
							tagPacket.setRssi(minReferSig.getRssi());
						}
						tagPacket.setJumpnum(0);
					} else {
						tagPacket.setJumpnum(tagPacket.getJumpnum() + 1);
					}
				}
			}
		}
	}
	/**
	 * 解析数据节点数据包
	 * @param cache
	 * @param start
	 * @param cachePos
	 * @throws Exception 
	 */
	private void parseNodePack(byte[] cache,int start,int cachePos,InetAddress inetAddress, int port) throws Exception{
		String nodeId = String.format("%02X", cache[start + 2])
				+ String.format("%02X", cache[start + 3]);
		NodePack nodePack = new NodePack();
		nodePack.setId(nodeId);
		// 设置休眠时间
		int sleepTime = (cache[start + 8] & 0xFF << 8) | (cache[start + 9] & 0xFF);
		nodePack.setSleepTime(sleepTime);
		// 设置版本号
		int version = ((cache[start + 4] & 0xFF) << 24 | (cache[start + 5] & 0xFF) << 16 | (cache[start + 6] & 0xFF) << 8 | cache[start + 7]);
		nodePack.setVersion(version);
		// 设置上报时间
		nodePack.setReportTime(new Date());
		// 设置计数值
		nodePack.setTick(System.currentTimeMillis());
		// 设置基站网络地址
		nodePack.setEndpoint(inetAddress);
		nodePack.setPort(port);
		nodePack.setStatus(true);
		if(null != getZgbMonitorCallBack()){
			getZgbMonitorCallBack().NodeCallBack(nodePack);
		}
	}
	/**
	 * 解析参考点数据包
	 * @param cache
	 * @param start
	 * @param cachePos
	 * @throws Exception 
	 */
	private void parseReferPack(byte[] cache,int start,int cachePos,InetAddress inetAddress, int port) throws Exception{
		String referId = String.format("%02X", cache[start + 2])
				+ String.format("%02X", cache[start + 3]);
		ReferPack referPack = new ReferPack();
		referPack.setId(referId);
		// 设置休眠时间
		int sleepTime = (cache[start + 8] & 0xFF) << 8 | (cache[start + 9] & 0xFF);
		referPack.setSleepTime(sleepTime);
		// 设置上报这个参考点数据包的基站ID
		String bId = String.format("%02X", cache[start + 10]) + String.format("%02X", cache[start + 11]);
		referPack.setbId(bId);
		// 设置版本讯息
		int version = ((cache[start + 4] & 0xFF) << 24 | (cache[start + 5] & 0xFF) << 16 | (cache[start + 6] & 0xFF) << 8 | cache[start + 7]);
		referPack.setVersion(version);
		// 设置上报时间
		referPack.setReportTime(new Date());
		// 设置计数值
		referPack.setTick(System.currentTimeMillis());
		// 设置网络地址
		referPack.setEndpoint(inetAddress);
		referPack.setPort(port);
		referPack.setStatus(true);
		if(null != getZgbMonitorCallBack()){
			getZgbMonitorCallBack().ReferCallBack(referPack);
		}
	}
	/**
	 * 解析卡片数据包
	 * @param cache
	 * @param start
	 * @param cachePos
	 * @throws Exception 
	 */
	private void parseTagPack(byte[] cache,int start,int cachePos,InetAddress inetAddress) throws Exception{
		String tagId = String.format("%02X", cache[start + 2])
				+ String.format("%02X", cache[start + 3]);
		TagPacket tagPacket = null;
		// 判断当前的集合中是否已经存在这样的数据包
		if(getTagsBox().containsKey(tagId)) {
			// 集合中已经存在了指定的 数据包，我们更新当前集合中的数据包
			tagPacket = getTagsBox().get(tagId);
			// 判断两次序列号是否相同
			if(tagPacket.getIndex() == cache[start + 11]) {
				// 说明此时是另外一个基站上报当前卡片讯息
				String referId = null;
				ReferSig referSig = null;
				for (int i = 0; i < cache[start + 12]; i++) {
					referId = String.format("%02X", cache[start + 13 + i * 3])
							+ String.format("%02X", cache[start + 14 + i * 3]);
					referSig = new ReferSig(referId, cache[start + 15 + i * 3]);
					if (!tagPacket.getCurrefsigs().containsKey(referId)) {
						tagPacket.getCurrefsigs().put(referId, referSig);
						tagPacket.setReferNum(tagPacket.getReferNum() + 1);
					}
				}
			} else {
				// 序列号不相同，说明又来了一个新的数据包
				// 找出里面信号强度最强的一个基站讯息
				byte minRssi = Byte.MAX_VALUE;
				ReferSig minReferSig = null;
				for (ReferSig referSig : tagPacket.getCurrefsigs().values()) {
					if(referSig.getRssi() < minRssi){
						minReferSig = referSig;
						minRssi = referSig.getRssi();
					}
				}
				String lastReferId = null, curReferId = null;
				lastReferId = tagPacket.getrId();
				if(null != minReferSig){
					curReferId = minReferSig.getId();
				}
				if(null != lastReferId && isOptimized()){
					// 判断是否需要对靠近的参考点进行优化
					if(getOptionmodel() == 0){
						// 信号强度优化
						rssiOptimizeModel(tagPacket, minReferSig, minRssi, lastReferId, curReferId);
					} else {
						// 连续跳点次数
						hopsOptimizeModel(tagPacket, minReferSig, minRssi, lastReferId, curReferId);
					}
				} else {
					tagPacket.setrId(curReferId);
					tagPacket.setRssi(minRssi);
				}
				// 将数据包抛给上位应用
				if(null != getZgbMonitorCallBack()){
					getZgbMonitorCallBack().TagCallBack(tagPacket);
				}
				// 清理参考点信号缓存
				tagPacket.getCurrefsigs().clear();
				tagPacket.setReferNum(0);
				// 是否进行人员求救
				if(cache[start + 1] == DataProtocol.tag_type1){
					tagPacket.setAlarm(true);
				}else {
					tagPacket.setAlarm(false);
				}
				// 设置未移动时间
				int notMoveTime = (cache[start + 4] & 0xFF) << 8 | (cache[start + 5] & 0xFF);
				tagPacket.setNotMoveTime(notMoveTime);
				// 设置卡片电量
				tagPacket.setBat(cache[start + 6]);
				// 设置休眠时间
				int sleepTime = (cache[start + 7] & 0xFF) << 8 | (cache[start + 8] & 0xFF);
				tagPacket.setSleepTime(sleepTime);
				// 设置静止时的休眠时间
				int staticSleepTime = (cache[start + 9] & 0xFF) << 8 | (cache[start + 10] & 0xFF);
				tagPacket.setStaticSleep(staticSleepTime);
				// 设置参考点信号缓存
				String referId = null;
				ReferSig referSig = null;
				for (int i = 0; i < cache[start + 12]; i++) {
					referId = String.format("%02X", cache[start + 13 + i * 3])
							+ String.format("%02X", cache[start + 14 + i * 3]);
					referSig = new ReferSig(referId, cache[start + 15 + i * 3]);
					if (!tagPacket.getCurrefsigs().containsKey(referId)) {
						tagPacket.getCurrefsigs().put(referId, referSig);
						tagPacket.setReferNum(tagPacket.getReferNum() + 1);
					}
				}
				// 设置序列号
				tagPacket.setIndex(cache[start + 11]);
				// 设置上报时间
				tagPacket.setReportTime(new Date());
				// 设置计数值
				tagPacket.setTick(System.currentTimeMillis());
			}
		}else{
			tagPacket = new TagPacket();
			tagPacket.setId(tagId);
			//是否是人员求救警报讯息
			if(cache[start + 1] == DataProtocol.tag_type1){
				tagPacket.setAlarm(true);
			}else{
				tagPacket.setAlarm(false);
			}
			// 未移动时间
			int notMoveTime = (cache[start + 4] & 0xFF) << 8 | (cache[start + 5] & 0xFF);
			tagPacket.setNotMoveTime(notMoveTime);
			// 电量
			tagPacket.setBat(cache[start + 6]);
			// 移动时休眠时间
			int sleep = (cache[start + 7] & 0xFF) << 8 | (cache[start + 8] & 0xFF);
			tagPacket.setSleepTime(sleep);
			// 静止时休眠时间
			int staticSleep = (cache[start + 9] & 0xFF) << 8 | (cache[start + 10] & 0xFF);
			tagPacket.setStaticSleep(staticSleep);
			// 序列号
			tagPacket.setIndex(cache[start + 11]);
			String referId = null;
			byte rssi = 0;
			ReferSig referSig = null;
			for (int i = 0; i < cache[start + 12]; i++) {
				referId = String.format("%02X", cache[start + 13 + i * 3])
						+ String.format("%02X", cache[start + 14 + i * 3]);
				rssi = cache[start + 13 + i * 3 + 2];
				referSig = new ReferSig(referId, rssi);
				if (!tagPacket.getCurrefsigs().containsKey(referId)) {
					tagPacket.getCurrefsigs().put(referId, referSig);
					tagPacket.setReferNum(tagPacket.getReferNum() + 1);
				}
			}
			// 获取参考点信号中的最强值
			byte minRssi = Byte.MAX_VALUE;
			String minReferId = null;
			for (ReferSig curReferSig : tagPacket.getCurrefsigs().values()) {
				if(curReferSig.getRssi() <= minRssi){
					minReferId = curReferSig.getId();
					minRssi = curReferSig.getRssi();
				}
			}
			// 设置当前靠近的参考点ID
			tagPacket.setrId(minReferId);
			// 设置上报时间
			tagPacket.setReportTime(new Date());
			// 设置时钟计数值
			tagPacket.setTick(System.currentTimeMillis());
			// 设置连续跳点次数
			tagPacket.setJumpnum(0);
			// 将当前的Tag数据包添加到集合里面
			getTagsBox().put(tagId, tagPacket);
		}
	}
	public boolean isOptimized() {
		return isOptimized;
	}
	public void setOptimized(boolean isOptimized) {
		this.isOptimized = isOptimized;
	}
	public int getOptmodelval() {
		return optmodelval;
	}
	public void setOptmodelval(int optmodelval) {
		this.optmodelval = optmodelval;
	}
	public int getOptionmodel() {
		return optionmodel;
	}
	public void setOptionmodel(int optionmodel) {
		this.optionmodel = optionmodel;
	}
	public UdpCommunicationContainer getUdpCommunicationContainer() {
		return udpCommunicationContainer;
	}
	public void setUdpCommunicationContainer(
			UdpCommunicationContainer udpCommunicationContainer) {
		this.udpCommunicationContainer = udpCommunicationContainer;
	}
}
