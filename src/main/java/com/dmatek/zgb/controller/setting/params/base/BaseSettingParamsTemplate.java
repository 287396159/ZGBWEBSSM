package com.dmatek.zgb.controller.setting.params.base;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.tool.check.bean.CheckedResult;

public abstract class BaseSettingParamsTemplate extends BaseSettingParams{
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	protected abstract CheckedResult checkedSettingParams(String key, String val);
	public Result setParamMethod(String key, String val,HttpServletRequest req) throws IOException{
		// 判斷當前的鍵值是否是否有效
		if(!checkedKey(key)){
			return viewResultFactory.errorResult("當前設置的參數鍵值無效...");
		}
		// 判斷當前鍵對應的值val是否有效
		CheckedResult checkResult = checkedSettingParams(key,val);
		if(!checkResult.isOk()){
			return viewResultFactory.errorResult(checkResult.getMsg());
		}
		// 更新參數
		updateParams(key, val, req.getSession());
		return viewResultFactory.successResult();
	}
}
