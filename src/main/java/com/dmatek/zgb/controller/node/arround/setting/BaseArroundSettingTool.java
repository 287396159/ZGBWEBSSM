package com.dmatek.zgb.controller.node.arround.setting;

import com.dmatek.zgb.monitor.ISendTool;
import com.dmatek.zgb.setting.vo.Result;

public abstract class BaseArroundSettingTool {
	private ISendTool iSendTool;
	public BaseArroundSettingTool(ISendTool iSendTool) {
		this.iSendTool = iSendTool;
	}
	public ISendTool getiSendTool() {
		return iSendTool;
	}
	public void setiSendTool(ISendTool iSendTool) {
		this.iSendTool = iSendTool;
	}
	/**
	 * 搜索周围设备訊息
	 * @param id
	 * @param keyName
	 * @return
	 * @throws Exception
	 */
	public abstract Result searchArroundDevices(String id, String keyName) throws Exception;
	/**
	 * 查詢指定ID，指定鍵名的值
	 * @param id
	 * @param keyName
	 * @return
	 * @throws Exception
	 */
	public abstract Result readReferParam(String id, String keyName, String routerId) throws Exception;
	/**
	 * 設置指定ID，指定鍵名的值
	 * @param id
	 * @param keyName
	 * @param routerId
	 * @return
	 * @throws Exception
	 */
	public abstract Result setReferParam(String id, String keyName, String paramValue , String routerId) throws Exception;
	/**
	 * 讀取指定ID，指定鍵名的值
	 * @param id
	 * @param keyName
	 * @param routerId
	 * @param channel
	 * @return
	 * @throws Exception
	 */
	public abstract Result readNodeParam(String id, String keyName, String routerId, int channel) throws Exception;
	/**
	 * 讀取指定ID，指定鍵名，指定參數的值
	 * @param id
	 * @param keyName
	 * @param keyParam
	 * @param routerId
	 * @param channel
	 * @return
	 * @throws Exception
	 */
	public abstract Result readNodeParam(String id, String keyName, String keyParam, String routerId, int channel) throws Exception;
	/**
	 * 讀取指定ID、指定鍵值，指定參數，指定超時時間的值
	 * @param id
	 * @param keyName
	 * @param timeOut
	 * @param keyParam
	 * @param routerId
	 * @param channel
	 * @return
	 * @throws Exception
	 */
	public abstract Result readNodeParam(String id, String keyName, int timeOut, String keyParam, String routerId, int channel) throws Exception;
	/**
	 * 設置指定ID，指定鍵名的值的基站參數
	 * @param id
	 * @param keyName
	 * @param routerId
	 * @param channel
	 * @return
	 * @throws Exception
	 */
	public abstract Result setNodeParam(String id, String keyName, String keyParam, String routerId, int channel) throws Exception;
}
