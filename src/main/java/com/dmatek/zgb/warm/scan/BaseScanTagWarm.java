package com.dmatek.zgb.warm.scan;

import com.dmatek.zgb.controller.setting.params.base.WarmSettingParamTool;
import com.dmatek.zgb.monitor.bean.TagPacket;

public abstract class BaseScanTagWarm<T extends TagPacket> {
	private WarmSettingParamTool warmSettingParamTool;
	public BaseScanTagWarm(WarmSettingParamTool warmSettingParamTool){
		this.warmSettingParamTool = warmSettingParamTool;
	}
	public abstract boolean checkPersonHelpWarm(T tagPkt) throws Exception;
	public abstract boolean checkNotMoveWarn(T tagPkt) throws Exception;
	public abstract boolean checkLowBatteryWarm(T tagPkt) throws Exception;
	public abstract boolean checkAreaControlWarm(T tagPkt) throws Exception;
	public abstract boolean checkAbnormalTagWarm(T tagPkt) throws Exception;
	public WarmSettingParamTool getWarmSettingParamTool() {
		return warmSettingParamTool;
	}
	public void setWarmSettingParamTool(WarmSettingParamTool warmSettingParamTool) {
		this.warmSettingParamTool = warmSettingParamTool;
	}
}
