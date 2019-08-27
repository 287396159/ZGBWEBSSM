package com.dmatek.zgb.monitor;

import com.dmatek.zgb.monitor.bean.NodePack;
import com.dmatek.zgb.setting.vo.Result;
// 发送工具
public interface ISendTool {
	Result sendUdpPacket(String routerId, byte[] content) throws Exception;
	NodePack findDevice(String id) throws Exception;
}
