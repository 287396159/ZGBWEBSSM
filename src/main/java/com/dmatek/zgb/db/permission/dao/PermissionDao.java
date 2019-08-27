package com.dmatek.zgb.db.permission.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.db.pojo.permission.Permission;

public interface PermissionDao {
	boolean addPermission(@Param("permission") Permission permission) throws Exception;
	boolean deletePermission(@Param("ids") List<Integer> ids) throws Exception;
	Permission findOne(@Param("id") int id) throws Exception;
	Permission findName(@Param("name") String name) throws Exception;
	List<Permission> findAll() throws Exception;
}
