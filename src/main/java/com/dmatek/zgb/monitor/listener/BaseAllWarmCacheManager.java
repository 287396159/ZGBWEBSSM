package com.dmatek.zgb.monitor.listener;

import org.springframework.beans.factory.annotation.Autowired;

import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.warm.scan.ScanTagWarmTool;
import com.dmatek.zgb.warn.bean.AreaControlWarn;
/**
 * 
 * @author zf
 * @data 2018年12月18日 上午11:17:30
 * @Description
 */
public abstract class BaseAllWarmCacheManager extends BaseAllWarmCacheManagement {
	@Autowired
	private ScanTagWarmTool scanTagWarmTool;
	/**
	 * 检查卡片警告讯息
	 * @param tagPacket
	 * @throws Exception 
	 */
	protected void scanTagWarmMsg(TagDetail tagDetail) throws Exception{
		// 检测是否是人员求救报警
		if(scanTagWarmTool.checkPersonHelpWarm(tagDetail)){
			tagDetail.setPersonnelHelp(true);
			addPersonHelpWarn(tagDetail);
		}
		// 检测是否是未移动报警
		if(scanTagWarmTool.checkNotMoveWarn(tagDetail)){
			tagDetail.setNotMove(true);
			addNotMoveWarn(tagDetail, (int)scanTagWarmTool.getTagCurStopTime(tagDetail), 
					(int)scanTagWarmTool.getTagStopTime(tagDetail));
		}
		// 检测是否是低电量报警
		if(scanTagWarmTool.checkLowBatteryWarm(tagDetail)){
			tagDetail.setLowPower(true);
			addLowBatteryWarn(tagDetail, scanTagWarmTool.getMinLowBattery(tagDetail));
		}
		// 检测是否是区域警告
		if(scanTagWarmTool.checkAreaControlWarm(tagDetail)){
			tagDetail.setAreaControl(true);
			addAreaControlWarn(tagDetail);
		} else {
			AreaControlWarn areaControlWarn = getAreaControlWarnCache()
					.getNotHandleWarnFromDeviceId(tagDetail.getId());
			if(null != areaControlWarn) {
				areaControlWarn.handle();
				getAreaControlWarnCache().addWarn(areaControlWarn);
			}
		}
	}
}
