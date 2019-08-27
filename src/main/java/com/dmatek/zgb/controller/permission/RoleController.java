package com.dmatek.zgb.controller.permission;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.controller.setting.validation.BaseSettingValidation;
import com.dmatek.zgb.controller.tool.page.PageTool;
import com.dmatek.zgb.db.permission.service.RoleService;
import com.dmatek.zgb.db.pojo.permission.Role;
import com.dmatek.zgb.log.anno.Operation;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@RestController
@RequestMapping("/role")
@PermissionName(name="角色設置的權限")
@RequiresPermissions("role:*")
public class RoleController {
	// 超級用戶
	private final static String SUPER_NAME = "super";
	@Autowired
	private RoleService roleService;
	@Autowired
	private BaseSettingValidation<Integer, Role> baseSettingValidation;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private PageTool pageTool;
	/**
	 * 添加角色訊息
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/")
	@PermissionName(name="添加角色的權限")
	@RequiresPermissions("role:add")
	@Operation(description="添加角色資料")
	public Result saveRole(@RequestBody Role role) throws Exception {
		Result result = baseSettingValidation.saveValidate(role);
		if(null != result) {
			return result;
		}
		Role currentRole = roleService.findOneName(role.getName());
		if(null != currentRole) {
			return viewResultFactory.errorResult("添加的角色【name: " + role.getName() + "】已經存在！");
		}
		if(roleService.addRole(role)) {
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("添加角色失敗！");
	}
	/**
	 * 刪除角色訊息
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/delete/{name}")
	@PermissionName(name="刪除角色的權限")
	@RequiresPermissions("role:delete")
	@Operation(description="刪除角色資料")
	public Result deleteRoleName(@PathVariable String name) throws Exception {
		if(StringUtils.isEmpty(name)) {
			return viewResultFactory.errorResult("角色權限名稱不能為空！");
		}
		if("super".equalsIgnoreCase(name)){
			return viewResultFactory.errorResult("管理員角色【name: " + SUPER_NAME + "】不能刪除!");
		} 
		if(roleService.deleteRoleName(name)) {
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("刪除訊息失敗! ");
	}
	/**
	 * 修改角色訊息
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/")
	@PermissionName(name="修改角色的權限")
	@RequiresPermissions("role:update")
	@Operation(description="修改角色資料")
	public Result updateRole(@RequestBody Role role) throws Exception {
		Result result = baseSettingValidation.updateValidate(role);
		if(null != result) {
			return result;
		}
		Role currentRole = roleService.findOneName(role.getName());
		if(null == currentRole) {
			return viewResultFactory.errorResult("角色的名稱【name：" + role.getName() + "】不能修改！");
		}
		if(roleService.updateRoleName(role)) {
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("修改角色失敗! ");
	}
	/**
	 * 根據角色ID查找角色訊息
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{roleId}")
	@PermissionName(name="查找角色的權限")
	@RequiresPermissions("role:find")
	public Result findOneRole(@PathVariable int roleId) throws Exception{
		if(roleId < 0) {
			Role role = roleService.findOne(roleId);
			if(null != role) {
				return viewResultFactory.successResult(role);
			}
		}
		return viewResultFactory.errorResult("查找角色【id: " + roleId+"】失敗！");
	}
	/**
	 * 查找所有的角色訊息
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/")
	@PermissionName(name="查找角色的權限")
	@RequiresPermissions("role:find")
	public Result findAllRole(@RequestParam("page") int page,
			@RequestParam("limit") int limit) throws Exception{
		List<Role> roles = roleService.findAll();
		List<Role> pages = pageTool.operate(roles, page, limit);
		if(null != roles) {
			return viewResultFactory.successResult(pages, roles.size());
		}
		return viewResultFactory.errorResult("查找所有角色訊息失敗!");
	}
	/**
	 * 獲取所有的角色
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/all")
	@PermissionName(name="查找角色的權限")
	@RequiresPermissions("role:find")
	public Result findAll() throws Exception{
		List<Role> roles = roleService.findAll();
		if(null != roles) {
			return viewResultFactory.successResult(roles);
		}
		return viewResultFactory.errorResult("查找所有角色訊息失敗!");
	}
}
