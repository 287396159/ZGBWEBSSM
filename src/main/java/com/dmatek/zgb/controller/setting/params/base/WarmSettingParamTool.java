package com.dmatek.zgb.controller.setting.params.base;

import org.springframework.stereotype.Component;

import com.dmatek.zgb.params.setting.cache.ParamsKey;
import com.dmatek.zgb.tool.check.bean.CheckedResult;

@Component
public class WarmSettingParamTool extends BaseSettingParamsTemplate {
	/**
	 * 獲取警告參數值
	 * @param paramsKey
	 * @return
	 */
	public Object getWarmParamValue(ParamsKey paramsKey) {
		String val = (String) getSettingParams().get(paramsKey.getVal());
		switch (paramsKey) {
		case isTagDisAlarm:
			boolean isTagDisAlarm = false;
			try {
				isTagDisAlarm = Boolean.parseBoolean(val);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return isTagDisAlarm;
		case isPersonHelp:
			boolean isPersonHelp = true;
			try {
				isPersonHelp = Boolean.parseBoolean(val);
			} catch (Exception e) {
			}
			return isPersonHelp;
		case lowBat:
			int lowBattery = 10;
			try {
				lowBattery = Integer.parseInt(val);
			} catch (Exception e) {
			}
			return lowBattery;
		case isLowBat:
			boolean islowBat = true;
			try {
				islowBat = Boolean.parseBoolean(val);
			} catch (Exception e) {
			}
			return islowBat;
		case isAreaAlarm:
			boolean isAreaAlarm = true;
			try {
				isAreaAlarm = Boolean.parseBoolean(val);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return isAreaAlarm;
		case resTime:
			float resTime = 1;
			try {
				resTime = Float.parseFloat(val);
			} catch (Exception e) {
			}
			return resTime;
		case resWarmMode:
			int resWarmMode = 1;
			try {
				resWarmMode = Integer.parseInt(val);
			} catch (Exception e) {
			}
			return resWarmMode;
		case autoClearHandleWarn:
			boolean isAutoClear = false;
			try {
				isAutoClear = Boolean.parseBoolean(val);
			} catch (Exception e) {
			}
			return isAutoClear;
		case autoClearHandleWarnTime:
			int autoClearTime = 30;
			try {
				autoClearTime = Integer.parseInt(val);
			} catch (Exception e) {
			}
			return autoClearTime;
		default:
			break;
		}
		return null;
	}

	/**
	 * 檢查警告參數的值是否有效
	 * @param key
	 * @param val
	 * @return
	 */
	@Override
	protected CheckedResult checkedSettingParams(String key, String val) {
		ParamsKey paramsKey = ParamsKey.getParamsKey(key);
		switch (paramsKey) {
		case isNodeDisAlarm:
			return getCheckedTools().checkedAlarmEnabled(val);
		case nodeDisAlarmTime:
			return getCheckedTools().checkedDisAlarmTime(val);
		case TagDisAlarmTime:
			return getCheckedTools().checkedDisAlarmTime(val);
		case isTagDisAlarm:
			return getCheckedTools().checkedAlarmEnabled(val);
		case isPersonHelp:
			return getCheckedTools().checkedAlarmEnabled(val);
		case isAreaAlarm:
			return getCheckedTools().checkedAlarmEnabled(val);
		case isLowBat:
			return getCheckedTools().checkedLowBatMode(val);
		case lowBat:
			return getCheckedTools().checkedLowBat(val);
		case resTime:
			return getCheckedTools().checkedResWarmTime(val);
		case resWarmMode:
			return getCheckedTools().checkedResWarmModel(val);
		case autoClearHandleWarn:
			return getCheckedTools().checkedIsAutoClear(val);
		case autoClearHandleWarnTime:
			return getCheckedTools().checkedAutoClearTime(val);
		default:
			break;
		}
		return getCheckedResultFactory().errorResult(
				"鍵值【key :" + paramsKey + ", val :" + val + "】不是設置警告參數...");
	}
}
