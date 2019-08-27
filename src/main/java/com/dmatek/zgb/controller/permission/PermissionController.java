package com.dmatek.zgb.controller.permission;

import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dmatek.zgb.controller.setting.validation.BaseSettingValidation;
import com.dmatek.zgb.db.permission.service.PermissionService;
import com.dmatek.zgb.db.pojo.permission.Permission;
import com.dmatek.zgb.log.anno.Operation;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@RestController
@RequestMapping("/permission")
@PermissionName(name="權限設置")
@RequiresPermissions(value="permission:*")
public class PermissionController {
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private BaseSettingValidation<Integer, Permission> baseSettingValidation;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	public PermissionsTool permissionsTool;
	/**
	 * 加载所有的权限资料
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/load/")
	@PermissionName(name="加載所有的權限")
	@RequiresPermissions(value="permission:load")
	@Operation(description="加載所有的權限")
	public Result reloadAllPermissions() throws Exception {
		permissionsTool.reloadAllResources();
		return viewResultFactory.successResult( permissionService.findAll() );
	}
	/**
	 * 添加權限
	 * @param permission
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/")
	@PermissionName(name="添加權限的權限")
	@RequiresPermissions("permission:save")
	@Operation(description="添加權限")
	public Result savePermission(@RequestBody Permission permission) throws Exception {
		Result result = baseSettingValidation.saveValidate(permission);
		if(null != result) {
			return result;
		}
		if(permissionService.addPermission(permission)) {
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("添加權限失敗!");
	}
	/**
	 * 刪除權限
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/{ids}")
	@PermissionName(name="刪除權限的權限")
	@RequiresPermissions("permission:delete")
	@Operation(description="刪除權限")
	public Result deletePermission(@PathVariable Integer[] ids) throws Exception {
		if(null == ids || ids.length <= 0){
			return viewResultFactory.errorResult("刪除訊息ID不能為空!");
		}
		if(permissionService.deletePermission(Arrays.asList(ids))) {
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("刪除權限失敗!");
	}
	/**
	 * 根據ID查詢權限訊息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{id}")
	@PermissionName(name="查詢權限的權限")
	@RequiresPermissions("permission:find")
	public Result findOnePermission(@PathVariable int id) throws Exception {
		Permission permission = permissionService.findOne(id);
		if(null != permission) {
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("沒有權限【id: " + id + "】的任何訊息!");
	}
	/**
	 * 查找所有的權限訊息
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/")
	@PermissionName(name="查詢權限的權限")
	@RequiresPermissions("permission:find")
	public Result findAllPermission() throws Exception {
		List<Permission> permissions = permissionService.findAll();
		if(null != permissions && !permissions.isEmpty()) {
			return viewResultFactory.successResult(permissions);
		}
		return viewResultFactory.errorResult("沒有找到任何權限訊息!");
	}
}
