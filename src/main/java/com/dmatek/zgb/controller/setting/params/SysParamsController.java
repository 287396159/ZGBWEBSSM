package com.dmatek.zgb.controller.setting.params;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dmatek.zgb.controller.setting.params.base.SystemSettingParamTool;
import com.dmatek.zgb.log.anno.Operation;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.setting.vo.ResultCode;

@RestController
@RequestMapping("/sysparams")
@PermissionName(name="設置系統參數權限")
@RequiresPermissions(value="sysparams:*")
public class SysParamsController {
	@Autowired
	private SystemSettingParamTool systemSettingParamTool;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	/**
	 * 设置参数值讯息
	 * @param key
	 * @param val
	 * @return 
	 * @throws IOException 
	 */
	@PostMapping("/key_value/")
	@PermissionName(name="系統參數設置權限")
	@RequiresPermissions(value="sysparams:set")
	@Operation(description="設置系統參數")
	public Result setParams(@RequestParam("key") String key, @RequestParam("value") String val,
							HttpServletRequest req) throws IOException {
		return systemSettingParamTool.setParamMethod(key, val, req);
	}
	/**
	 * 设置参数值属性
	 * @param key1
	 * @param val1
	 * @param key2
	 * @param val2
	 * @return
	 * @throws IOException 
	 */
	@PostMapping("/key_param/")
	@PermissionName(name="系統參數設置權限")
	@RequiresPermissions(value="sysparams:set")
	@Operation(description="設置系統參數")
	public Result setParams(@RequestParam("key1") String key1, @RequestParam("param1") String param1, 
			 				@RequestParam("key2") String key2, @RequestParam("param2") String param2,
			 				HttpServletRequest req) throws IOException {
		Result result1 = systemSettingParamTool.setParamMethod(key1, param1, req);
		Result result2 = systemSettingParamTool.setParamMethod(key2, param2, req);
		if(result1.getCode() != ResultCode.SUCCESS_CODE) {
			return result1;
		} else if(result2.getCode() != ResultCode.SUCCESS_CODE) {
			return result2;
		}
		return viewResultFactory.successResult();
	}
}
