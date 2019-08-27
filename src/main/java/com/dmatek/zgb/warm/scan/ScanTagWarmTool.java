package com.dmatek.zgb.warm.scan;

import java.util.List;
import com.dmatek.zgb.controller.setting.params.base.WarmSettingParamTool;
import com.dmatek.zgb.db.pojo.setting.Node;
import com.dmatek.zgb.db.setting.service.ReliablePerson_NodeService;
import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.params.setting.cache.ParamsKey;

public class ScanTagWarmTool extends BaseScanTagWarm<TagDetail>{
	private ReliablePerson_NodeService reliablePerson_NodeService;
	private static final int RESWARMMODE_SYS = 2;
	private static final int RESWARMMODE_PERSON = 1;
	public ScanTagWarmTool(WarmSettingParamTool warmSettingParamTool, 
			ReliablePerson_NodeService reliablePerson_NodeService) {
		super(warmSettingParamTool);
		this.reliablePerson_NodeService = reliablePerson_NodeService;
	}
	@Override
	public boolean checkPersonHelpWarm(TagDetail tagPkt) throws Exception {
		boolean isPersonHelp = (boolean) getWarmSettingParamTool().getWarmParamValue(ParamsKey.isPersonHelp);
		if(isPersonHelp && tagPkt.isAlarm()){
			return true;
		}
		return false;
	}
	/**
	 * 获取当前卡片的滞留时间
	 * @param tagPkt
	 * @return
	 */
	public float getTagCurStopTime(TagDetail tagPkt){
		float curStopTime;
		curStopTime = (float) tagPkt.getNotMoveTime() / 60;
		return curStopTime;
	}
	/**
	 * 获取卡片设置的滞留时间阀值
	 * @param tagPkt
	 * @return
	 */
	public float getTagStopTime(TagDetail tagPkt){
		float stopTime;
		int ResWarmMode = (int) getWarmSettingParamTool().getWarmParamValue(ParamsKey.resWarmMode);
		if (ResWarmMode == RESWARMMODE_SYS) {// 系统设置滞留报警时间模式
			stopTime = (float) getWarmSettingParamTool().getWarmParamValue(ParamsKey.resTime);
		} else if (ResWarmMode == RESWARMMODE_PERSON) {
			stopTime =tagPkt.getStopTime();
		} else {
			stopTime = 0;
		}
		return stopTime;
	}
	@Override
	public boolean checkNotMoveWarn(TagDetail tagPkt) throws Exception {
		float stopTime, curStopTime;
		curStopTime = (float) tagPkt.getNotMoveTime() / 60;
		// 获取设置的滞留报警模式
		int ResWarmMode = (int) getWarmSettingParamTool().getWarmParamValue(ParamsKey.resWarmMode);
		if (ResWarmMode == RESWARMMODE_SYS) {// 系统设置滞留报警时间模式
			stopTime = (float) getWarmSettingParamTool().getWarmParamValue(ParamsKey.resTime);
		} else if (ResWarmMode == RESWARMMODE_PERSON) {
			stopTime = tagPkt.getStopTime();
		} else {// 不启用
			stopTime = 0;
		}
		if (stopTime > 0 && curStopTime > stopTime) {// 说明此时应该产生人员滞留报警
			return true;
		}
		return false;
	}
	/**
	 * 获取当前设置的最低电量
	 * @param tagPkt
	 * @return
	 */
	public byte getMinLowBattery(TagDetail tagPkt){
		int lowBat = (int) getWarmSettingParamTool()
				.getWarmParamValue(ParamsKey.lowBat);
		return (byte) lowBat;
	}
	@Override
	public boolean checkLowBatteryWarm(TagDetail tagPkt) throws Exception {
		// 判断是否启用卡片低电量报警
		boolean isLowBat = (boolean) getWarmSettingParamTool()
				.getWarmParamValue(ParamsKey.isLowBat);
		int lowBat = (int) getWarmSettingParamTool()
				.getWarmParamValue(ParamsKey.lowBat);
		if (isLowBat && tagPkt.getBat() <= lowBat) {
			return true;
		} 
		return false;
	}

	@Override
	public boolean checkAreaControlWarm(TagDetail tagPkt) throws Exception {
		boolean isAreaAlarm = (boolean) getWarmSettingParamTool().getWarmParamValue(ParamsKey.isAreaAlarm);
		// 根据人员编号查看
		List<Node> nodes = getReliablePerson_NodeService().selectOne(tagPkt.getTagNo());
		if(null != nodes) {
			boolean isEnter = false;
			for (Node node : nodes) {
				if(tagPkt.getrId().equalsIgnoreCase(node.getId())){
					isEnter = true;
				}
			}
			if(isAreaAlarm && !isEnter){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkAbnormalTagWarm(TagDetail tagPkt) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	public ReliablePerson_NodeService getReliablePerson_NodeService() {
		return reliablePerson_NodeService;
	}
	public void setReliablePerson_NodeService(
			ReliablePerson_NodeService reliablePerson_NodeService) {
		this.reliablePerson_NodeService = reliablePerson_NodeService;
	}
}
