package com.dmatek.zgb.controller.setting.params.base;

import org.springframework.stereotype.Component;

import com.dmatek.zgb.params.setting.cache.ParamsKey;
import com.dmatek.zgb.tool.check.bean.CheckedResult;

@Component
public class NetWorkSettingParamTool extends BaseSettingParamsTemplate{
	/**
	 * 獲取網絡參數值
	 * @param paramsKey
	 * @return
	 */
	public Object getNetWorkParamValue(ParamsKey paramsKey) {
		String val = (String) getSettingParams().get(paramsKey.getVal());
		switch (paramsKey) {
		case ip:
			return val;
		case port:
			int port = 51234;
			try {
				port = Integer.parseInt(val);
			} catch (Exception e) {
			}
			return port;
		default:
			break;
		}
		return null;
	}
	
	/**
	 * 檢查設置的網絡參數鍵對應的值是否有效
	 * @param key
	 * @param val
	 * @return
	 */
	@Override
	protected CheckedResult checkedSettingParams(String key, String val) {
		ParamsKey paramsKey = ParamsKey.getParamsKey(key);
		switch(paramsKey){
		case ip:
			return getCheckedTools().checkedIp(val);
		case port:
			return getCheckedTools().checkedPort(val);
		default:
			break;
		}
		return getCheckedResultFactory().errorResult("鍵值【key :" + paramsKey + ",val :" + val + "】不是設置網絡參數...");
	}
}
