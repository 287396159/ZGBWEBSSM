package com.dmatek.zgb.controller.setting.params;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dmatek.zgb.controller.setting.params.base.ShowSettingParamTool;
import com.dmatek.zgb.log.anno.Operation;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.setting.vo.ResultCode;

@RestController
@RequestMapping("/showparams")
@PermissionName(name="設置顯示參數權限")
@RequiresPermissions(value="showparams:*")
public class ShowParamsController {
	@Autowired
	private ShowSettingParamTool showSettingParamTool;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	/**
	 * 设置参数值讯息
	 * @param key : 参数的键
	 * @param val : 参数的值
	 * @return 
	 * @throws IOException 
	 */
	@PostMapping("/key_value/")
	@PermissionName(name="顯示參數設置權限")
	@RequiresPermissions(value="showparams:set")
	@Operation(description="設置顯示參數")
	public Result setParams(@RequestParam("key") String key,
							@RequestParam("value") String val,
							HttpServletRequest req) throws IOException {
		return showSettingParamTool.setParamMethod(key, val, req);
	}
	/**
	 * 设置参数值讯息
	 * @param key
	 * @param isVal
	 * @param val
	 * @param req
	 * @return
	 * @throws IOException 
	 */
	@PostMapping("/key_enable_value/")
	@PermissionName(name="顯示參數設置權限")
	@RequiresPermissions(value="showparams:set")
	@Operation(description="設置顯示參數")
	public Result setParams(@RequestParam("key1") String key1,
			@RequestParam("enable") String isVal,
			@RequestParam("key2") String key2,
			@RequestParam("value") String val,
			HttpServletRequest req) throws IOException{
		Result result1 = showSettingParamTool.setParamMethod(key1, isVal, req);
		Result result2 = showSettingParamTool.setParamMethod(key2, val, req);
		if(result1.getCode() != ResultCode.SUCCESS_CODE){
			return result1;
		}else if(result2.getCode() != ResultCode.SUCCESS_CODE){
			return result2;
		} else {
			return viewResultFactory.successResult();
		}
	}
}
