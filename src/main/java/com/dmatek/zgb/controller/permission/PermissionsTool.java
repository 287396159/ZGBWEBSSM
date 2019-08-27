package com.dmatek.zgb.controller.permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.dmatek.zgb.db.permission.service.PermissionService;
import com.dmatek.zgb.db.permission.service.RoleService;
import com.dmatek.zgb.db.permission.service.Role_PermissionService;
import com.dmatek.zgb.db.pojo.permission.Permission;
import com.dmatek.zgb.db.pojo.permission.Role;
import com.dmatek.zgb.db.pojo.permission.Role_Permission;
import com.dmatek.zgb.permission.anon.PermissionName;

public class PermissionsTool {
	private static final String DEFAULT_SUPER_NAME = "super";
	private RequestMappingHandlerMapping requestmapping;
	private PermissionService permissionService;
	private RoleService roleService;
	private Role_PermissionService rolePermissionService;
	public PermissionsTool(RequestMappingHandlerMapping requestmapping, PermissionService permissionService,
			RoleService roleService, Role_PermissionService rolePermissionService){
		this.requestmapping = requestmapping;
		this.permissionService = permissionService;
		this.roleService = roleService;
		this.rolePermissionService = rolePermissionService;
	}
	/**
	 * 加載所有的資源
	 * @throws Exception
	 */
	public void reloadAllResources() throws Exception{
		Map<String,String> resources = searchAllResources(requestmapping);
		Iterator<String> iterator = resources.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String val = resources.get(key);
			Permission oldPermission =	getPermissionService().findName(val);
			if(null == oldPermission) {
				oldPermission = new Permission(val, key);
				getPermissionService().addPermission(oldPermission);
			}
		}
		// 給管理員賬戶設置所有的權限
		loadSuperPermissions();
	}
	private void loadSuperPermissions() throws Exception{
		Role role = getRoleService().findOneName(DEFAULT_SUPER_NAME);
		if(null != role){
			List<Permission> permissions = getPermissionService().findAll();
			List<Role_Permission> role_Permissions = new ArrayList<Role_Permission>();
			for (Permission permission : permissions) {
				Role_Permission rolePermission = new Role_Permission();
				rolePermission.setRole_id(role.getId());
				rolePermission.setPermissionId(permission.getId());
				rolePermission.setCreateName(permission.getCreateName());
				role_Permissions.add(rolePermission);
			}
			getRolePermissionService().clearRolePermissions(role.getId());
			getRolePermissionService().addRolePermissions(role_Permissions);
		}
	}
	/**
	 * 搜索所有的權限資料
	 */
	private Map<String,String> searchAllResources(RequestMappingHandlerMapping requestmapping) {
		Map<RequestMappingInfo, HandlerMethod> allHandlerMethods = requestmapping
				.getHandlerMethods();
		Iterator<HandlerMethod> iterator = allHandlerMethods.values().iterator();
		Map<String,String> perResMap = new HashMap<String,String>();
		while (iterator.hasNext()) {
			HandlerMethod handlerMethod = (HandlerMethod) iterator.next();
			// 獲取當前方法類
			Class<?> clazz = handlerMethod.getMethod().getDeclaringClass();
			// 獲取類的註解
			RequiresPermissions rquiresPermissions = clazz
					.getAnnotation(RequiresPermissions.class);
			if(null != rquiresPermissions) {
				// 獲取資源字符串
				String permResource = rquiresPermissions.value()[0];
				PermissionName permissionName = clazz.getAnnotation(PermissionName.class);
				// 添加字符串到Map中
				if(null != permissionName) {
					perResMap.put(permResource, permissionName.name());
				}
			}
			// 獲取所有的資源
			RequiresPermissions reqPermissions = handlerMethod
					.getMethodAnnotation(RequiresPermissions.class);
			if(null != reqPermissions) {
				String resources = reqPermissions.value()[0];
				PermissionName permissionName = handlerMethod
						.getMethodAnnotation(PermissionName.class);
				if(null != permissionName) {
					perResMap.put(resources, permissionName.name());
				}
			}
		}
		return perResMap;
	}
	public RequestMappingHandlerMapping getRequestmapping() {
		return requestmapping;
	}
	public void setRequestmapping(RequestMappingHandlerMapping requestmapping) {
		this.requestmapping = requestmapping;
	}
	public PermissionService getPermissionService() {
		return permissionService;
	}
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public Role_PermissionService getRolePermissionService() {
		return rolePermissionService;
	}
	public void setRolePermissionService(
			Role_PermissionService rolePermissionService) {
		this.rolePermissionService = rolePermissionService;
	}
}
