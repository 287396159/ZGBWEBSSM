package com.dmatek.zgb.db.permission.service;

import java.util.List;
import com.dmatek.zgb.db.pojo.permission.Permission;

public interface PermissionService {
	boolean addPermission(Permission permission) throws Exception;
	boolean deletePermission(List<Integer> ids) throws Exception;
	Permission findOne(int id) throws Exception;
	Permission findName(String name) throws Exception;
	List<Permission> findAll() throws Exception;
}
