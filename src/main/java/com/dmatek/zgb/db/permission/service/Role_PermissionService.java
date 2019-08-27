package com.dmatek.zgb.db.permission.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.db.pojo.permission.Role_Permission;

public interface Role_PermissionService {
	boolean addRolePermissions(List<Role_Permission> role_Permissions) throws Exception;
	boolean clearRolePermissions(int roleId) throws Exception;
	List<Role_Permission> findAllFromRoleId(int roleId) throws Exception;
	List<Role_Permission> findAllFromRoleName(@Param("roleName") String roleName) throws Exception;
	List<Role_Permission> findAllFromPermissionId(int permissionId) throws Exception;
}
