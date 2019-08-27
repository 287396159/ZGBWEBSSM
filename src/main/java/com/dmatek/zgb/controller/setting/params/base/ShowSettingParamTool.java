package com.dmatek.zgb.controller.setting.params.base;

import org.springframework.stereotype.Component;

import com.dmatek.zgb.params.setting.cache.ParamsKey;
import com.dmatek.zgb.tool.check.bean.CheckedResult;

@Component
public class ShowSettingParamTool extends BaseSettingParamsTemplate {
	
	public Object getShowParamsValue(ParamsKey paramsKey) {
		String val = (String) getSettingParams().get(paramsKey.getVal());
		switch (paramsKey) {
		case isBsVisible:
			boolean isBsVisible = false;
			if("true".equalsIgnoreCase(val)){
				isBsVisible = true;
			}else{
				isBsVisible = false;
			}
			return isBsVisible;
		case isTagVisible:
			boolean isTagVisible = false;
			if("true".equalsIgnoreCase(val)){
				isTagVisible = true;
			} else {
				isTagVisible = false;
			}
			return isTagVisible;
		case tagVisibleOverTime:
			int tagVisibleOverTime = 30;
			try {
				tagVisibleOverTime = Integer.parseInt(val);
			} catch (Exception e) {
			}
			return tagVisibleOverTime;
		case reportWarnInfoTime:
			int reportWarnInfoTime = 30;
			try {
				reportWarnInfoTime = Integer.parseInt(val);
			} catch (Exception e) {
			}
			return reportWarnInfoTime;
		case isShowDebug:
			boolean isShowDebug = false;
			if("true".equalsIgnoreCase(val)){
				isShowDebug = true;
			} else {
				isShowDebug = false;
			}
			return isShowDebug;
		default:
			break;
		}
		return null;
	}
	
	@Override
	protected CheckedResult checkedSettingParams(String key, String val) {
		ParamsKey paramsKey = ParamsKey.getParamsKey(key);
		switch (paramsKey) {
		case isBsVisible:// 基站可見性
			return getCheckedTools().checkedNodeVisible(val);
		case isTagVisible:// 卡片超時未接收時是否可見
			return getCheckedTools().checkedTagOverVisible(val);
		case tagVisibleOverTime:
			return getCheckedTools().checkedTagOverTime(val);
		case isNodeManagerShow:
			return getCheckedTools().checkedNodeShowReport(val);
		case reportWarnInfoTime:
			return getCheckedTools().checkedReportWarnTime(val);
		case isRefreshTreeTime:
			return getCheckedTools().checkedTreeIsAutoRefresh(val);
		case refreshTreeTime:
			return getCheckedTools().checkedTreeRefreshTime(val);
		case isShowMonitorRegionNumber:
			return getCheckedTools().checkedShowMonitorRegionNumber(val);
		case isShowDebug:
			return getCheckedTools().checkedShowDebug(val);
		default:
			break;
		}
		return getCheckedResultFactory().errorResult("鍵值【key :" + paramsKey + ", val :" + val + "】不是顯示參數...");
	}

}
