package com.dmatek.zgb.controller.setting.node;

import com.dmatek.zgb.monitor.ISendTool;
import com.dmatek.zgb.setting.vo.Result;

public abstract class BaseDeviceParamTool {
	private ISendTool iSendTool;
	public BaseDeviceParamTool(ISendTool iSendTool){
		this.iSendTool = iSendTool;
	}
	public ISendTool getiSendTool() {
		return iSendTool;
	}
	public void setiSendTool(ISendTool iSendTool) {
		this.iSendTool = iSendTool;
	}
	/**
	 * 读取指定设备ID、指定类型的参数
	 * @param id
	 * @param paramKey
	 * @throws Exception
	 */
	public abstract Result readParam(String id, String paramKey) throws Exception;
	/**
	 * 搜索指定设备ID、指定类型参数及值
	 * （如搜索基站与指定Wifi间的信号强度，此时Wifi名称就是参数值）
	 * @param id
	 * @param paramKey
	 * @param val
	 * @return
	 * @throws Exception
	 */
	public abstract Result readParam(String id, String paramKey, String val) throws Exception;
	/**
	 * 设置指定设备ID、指定类型，指定参数值的参数
	 * @param id
	 * @param paramKey
	 * @param paramVal
	 * @throws Exception
	 */
	public abstract Result setParam(String id, String paramKey, String paramVal) throws Exception;
}
