package com.dmatek.zgb.controller.setting.params;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dmatek.zgb.controller.setting.params.base.WarmSettingParamTool;
import com.dmatek.zgb.log.anno.Operation;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.setting.vo.ResultCode;
/**
 * 警告参数设置
 * @author zf
 * @data 2018年12月18日 上午9:07:24
 * @Description
 */
@RestController
@RequestMapping("/warmparams")
@PermissionName(name="設置警告參數權限")
@RequiresPermissions(value="warmparams:*")
public class WarmParamsController {
	@Autowired
	private WarmSettingParamTool warmSettingParamTool;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	/**
	 * 设置参数值讯息
	 * @param key
	 * @param val
	 * @return
	 * @throws IOException 
	 */
	@PostMapping("/key_enable/")
	@PermissionName(name="警告參數設置權限")
	@RequiresPermissions(value="warmparams:set")
	@Operation(description="設置警告參數")
	public Result setParams(@RequestParam(value="key") String key,
			@RequestParam(value="value") String val,
			HttpServletRequest req) throws IOException {
		return warmSettingParamTool.setParamMethod(key, val, req);
	}
	/**
	 * 设置参数值讯息
	 * @param key1
	 * @param isVal
	 * @param key2
	 * @param val
	 * @param req
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/key_enable_value/")
	@PermissionName(name="警告參數設置權限")
	@RequiresPermissions(value="warmparams:set")
	@Operation(description="設置警告參數")
	public Result setParams(@RequestParam("key1") String key1, 
			@RequestParam("enable") String isVal,
			@RequestParam("key2") String key2,
			@RequestParam("value") String val,
			HttpServletRequest req) throws IOException {
		Result result1 = warmSettingParamTool.setParamMethod(key1, isVal, req);
		Result result2 = warmSettingParamTool.setParamMethod(key2, val, req);
		if(result1.getCode() != ResultCode.SUCCESS_CODE) {
			return result1;
		}
		if(result2.getCode() != ResultCode.SUCCESS_CODE) {
			return result2;
		}
		return viewResultFactory.successResult();
	}
	/**
	 * 设置参数值讯息
	 * @param key1
	 * @param model
	 * @param key2
	 * @param val
	 * @return
	 * @throws IOException 
	 */
	@PostMapping("/key_model_value/")
	@PermissionName(name="警告參數設置權限")
	@RequiresPermissions(value="warmparams:set")
	@Operation(description="設置警告參數")
	public Result setModelParams(@RequestParam("key1") String key1,
			@RequestParam("model") String model,
			@RequestParam("key2") String key2, 
			@RequestParam("val") String val, 
			HttpServletRequest req) throws IOException{
		Result result1 = warmSettingParamTool.setParamMethod(key1, model, req);
		Result result2 = warmSettingParamTool.setParamMethod(key2, val, req);
		if(result1.getCode() != ResultCode.SUCCESS_CODE) {
			return result1;
		}
		if(result2.getCode() != ResultCode.SUCCESS_CODE) {
			return result2;
		}
		return viewResultFactory.successResult();
	}
}
