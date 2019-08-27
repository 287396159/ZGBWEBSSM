package com.dmatek.zgb.controller.permission;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.dmatek.zgb.db.permission.service.PermissionService;
import com.dmatek.zgb.db.permission.service.RoleService;
import com.dmatek.zgb.db.permission.service.Role_PermissionService;

@Configuration
public class PermissionsConfig {
	@Bean
	public PermissionsTool getPermissionsTool(RequestMappingHandlerMapping requestmapping, PermissionService permissionService, 
		 RoleService roleService, Role_PermissionService rolePermissionService){
		return new PermissionsTool(requestmapping, permissionService, roleService, rolePermissionService);
	}
}
