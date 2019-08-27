package com.dmatek.zgb.monitor.callback;

import com.dmatek.zgb.monitor.bean.NodePack;
import com.dmatek.zgb.monitor.bean.ReferPack;
import com.dmatek.zgb.monitor.bean.TagPacket;
/**
 * 数据监控的回调函数接口
 * @author zf
 * @data 2018年12月13日 上午9:09:33
 * @Description
 */
public interface MonitorCallBack {
	public void TagCallBack(TagPacket tagpack) throws Exception;
	public void NodeCallBack(NodePack nodePack) throws Exception;
	public void ReferCallBack(ReferPack referPack) throws Exception;
}
