package com.dmatek.zgb.controller.permission;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dmatek.zgb.controller.setting.validation.BaseSettingValidation;
import com.dmatek.zgb.db.permission.service.RoleService;
import com.dmatek.zgb.db.permission.service.Role_PermissionService;
import com.dmatek.zgb.db.pojo.permission.Role;
import com.dmatek.zgb.db.pojo.permission.Role_Permission;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@RestController
@RequestMapping("/role_permission")
public class RolePermissionController {
	@Autowired
	private Role_PermissionService rolePermissionService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private BaseSettingValidation<Integer, Role_Permission> baseSettingValidation;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	/**
	 * 添加角色權限
	 * @param rolePermission
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/")
	public Result addRolePermission(@RequestBody List<Role_Permission> rolePermissions) throws Exception {
		if(null == rolePermissions || rolePermissions.size() <= 0){
			return viewResultFactory.errorResult("角色权限讯息不能为空!");
		}
		// 查看当前是否是在修改管理员
		Role role = roleService.findOne(rolePermissions.get(0).getRole_id());
		if(null == role){
			return viewResultFactory.errorResult("角色【id: " + rolePermissions.get(0).getRole_id() + "】不能存在!");
		}
		if("super".equalsIgnoreCase(role.getName())){
			return viewResultFactory.errorResult("超级管理员【name: super】角色的权限不允许修改! ");
		}
		if(rolePermissionService.addRolePermissions(rolePermissions)){
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("添加角色權限失敗!");
	}
	/**
	 * 刪除角色的所有權限
	 * @param roleId
	 * @param permissionIds
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/{roleId}")
	public Result deleteRolePermissions(@PathVariable int roleId) throws Exception{
		if(roleId < 0){
			return viewResultFactory.errorResult("角色ID必須大於0!");
		}
		if(rolePermissionService.clearRolePermissions(roleId)){
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("刪除角色權限失敗!");
	}
	
	@GetMapping("/roleName/{roleName}")
	public Result findAllFromRoleName(@PathVariable String roleName) throws Exception {
		List<Role_Permission> rolePermissions = rolePermissionService.findAllFromRoleName(roleName);
		if(null != rolePermissions) {
			return viewResultFactory.successResult(rolePermissions);
		}
		return viewResultFactory.errorResult("查詢角色【name: " + roleName+"】的所有權限失敗!");
	}
	/**
	 * 查找權限的所有角色
	 * @param permissionId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{permissionId}")
	public Result findAllFromPermissionId(@PathVariable int permissionId) throws Exception{
		List<Role_Permission> rolePermissions = rolePermissionService.findAllFromPermissionId(permissionId);
		if(null != rolePermissions){
			return viewResultFactory.successResult(rolePermissions);
		}
		return viewResultFactory.errorResult("查詢權限【id: " + permissionId + "】的所有角色失敗!");
	}
}
