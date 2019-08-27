package com.dmatek.zgb.controller.setting.params;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dmatek.zgb.controller.setting.params.base.NetWorkSettingParamTool;
import com.dmatek.zgb.log.anno.Operation;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.setting.vo.Result;

@RestController
@RequestMapping("/networkparams")
@PermissionName(name="設置網絡參數權限")
@RequiresPermissions(value="netparams:*")
public class NetWorkParamsController {
	@Autowired
	private NetWorkSettingParamTool netWorkSettingParamTool;
	/**
	 * 设置参数值讯息
	 * @param key
	 * @param val
	 * @return
	 * @throws IOException 
	 */
	@PostMapping("/key_value/")
	@PermissionName(name="網絡參數設置權限")
	@RequiresPermissions(value="netparams:set")
	@Operation(description="設置網絡參數")
	public Result setParams(@RequestParam("key") String key,
							@RequestParam("value") String val,
							HttpServletRequest req) throws IOException {
		return netWorkSettingParamTool.setParamMethod(key, val, req);
	}
}
