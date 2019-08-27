package com.dmatek.zgb.controller.module;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@RestController
@RequestMapping("/module")
@PermissionName(name="功能模塊權限設置")
@RequiresPermissions(value="module:*")
public class AllModule {
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	/**
	 * 不做任何工作只是提供权限模块
	 * @return
	 */
	@GetMapping("/monitor")
	@PermissionName(name="開啟監聽功能模塊")
	@RequiresPermissions(value="module:monitor")
	public Result monitorModule(){
		return viewResultFactory.successResult();
	}
	/**
	 * 開啟報警管理功能模塊
	 * @return
	 */
	@GetMapping("/warmMsg")
	@PermissionName(name="開啟報警管理功能模塊權限")
	@RequiresPermissions(value="module:warmMsg")
	public Result warmModule(){
		return viewResultFactory.successResult();
	}
	/**
	 * 不做任何工作只是提供权限模块
	 * @return
	 */
	@GetMapping("/track")
	@PermissionName(name="開啟歷史數據功能模塊")
	@RequiresPermissions(value="module:track")
	private Result trackModule(){
		return viewResultFactory.successResult();
	}
	/**
	 * 不做任何工作只是提供权限模块
	 * @return
	 */
	@GetMapping("/access")
	@PermissionName(name="開啟上下班管理功能模塊")
	@RequiresPermissions(value="module:access")
	private Result accessModule(){
		return viewResultFactory.successResult();
	}
	/**
	 * 不做任何工作只是提供权限模块
	 * @return
	 */
	@GetMapping("/report")
	@PermissionName(name="開啟報表輸出功能模塊")
	@RequiresPermissions(value="module:report")
	private Result reportModule(){
		return viewResultFactory.successResult();
	}
	
	/**
	 * 不做任何工作只是提供权限模块
	 * @return
	 */
	@GetMapping("/permission")
	@PermissionName(name="開啟權限管理功能模塊")
	@RequiresPermissions(value="module:permission")
	private Result permissionModule(){
		return viewResultFactory.successResult();
	}
	/**
	 * 不做任何工作只是提供权限模块
	 * @return
	 */
	@GetMapping("/node")
	@PermissionName(name="開啟基站管理功能模塊")
	@RequiresPermissions(value="module:node")
	private Result nodeModule(){
		return viewResultFactory.successResult();
	}
	/**
	 * 不做任何工作只是提供权限模块
	 * @return
	 */
	@GetMapping("/jobType_Company")
	@PermissionName(name="開啟工種包商資料管理功能模塊")
	@RequiresPermissions(value="module:jobType_Company")
	private Result personModule(){
		return viewResultFactory.successResult();
	}
	
	/**
	 * 不做任何工作只是提供权限模块
	 * @return
	 */
	@GetMapping("/setting")
	@PermissionName(name="開啟設置管理功能模塊")
	@RequiresPermissions(value="module:setting")
	private Result settingModule(){
		return viewResultFactory.successResult();
	}
}
