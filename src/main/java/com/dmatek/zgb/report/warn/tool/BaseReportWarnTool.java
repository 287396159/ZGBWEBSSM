package com.dmatek.zgb.report.warn.tool;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.controller.setting.params.base.ShowSettingParamTool;
import com.dmatek.zgb.params.setting.cache.ParamsKey;

public abstract class BaseReportWarnTool {
	private ShowSettingParamTool showSettingParamTool;
	private BaseWarmComparator descendingComparator, ascendingComparator;
	public BaseReportWarnTool(ShowSettingParamTool showSettingParamTool, 
			BaseWarmComparator descendingComparator, BaseWarmComparator ascendingComparator){
		this.showSettingParamTool = showSettingParamTool;
		this.descendingComparator = descendingComparator;
		this.ascendingComparator = ascendingComparator;
	}
	public int getReportWarnTime(){
		return (int) showSettingParamTool.getShowParamsValue(ParamsKey.reportWarnInfoTime);
	}
	public ShowSettingParamTool getShowSettingParamTool() {
		return showSettingParamTool;
	}
	public void setShowSettingParamTool(ShowSettingParamTool showSettingParamTool) {
		this.showSettingParamTool = showSettingParamTool;
	}
	public BaseWarmComparator getDescendingComparator() {
		return descendingComparator;
	}
	public void setDescendingComparator(BaseWarmComparator descendingComparator) {
		this.descendingComparator = descendingComparator;
	}
	public BaseWarmComparator getAscendingComparator() {
		return ascendingComparator;
	}
	public void setAscendingComparator(BaseWarmComparator ascendingComparator) {
		this.ascendingComparator = ascendingComparator;
	}
}
