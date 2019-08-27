package com.dmatek.zgb.db.permission.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.db.pojo.permission.Role_Permission;

public interface Role_PermissionDao {
	boolean addRolePermissions(@Param("rolePermissions") List<Role_Permission> rolePermissions) throws Exception;
	boolean clearRolePermissions(@Param("roleId") int roleId) throws Exception;
	List<Role_Permission> findAllFromRoleId(@Param("roleId") int roleId) throws Exception;
	List<Role_Permission> findAllFromRoleName(@Param("roleName") String roleName) throws Exception;
	List<Role_Permission> findAllFromPermissionId(@Param("permissionId") int permissionId) throws Exception;
}
