package com.dmatek.zgb.db.permission.service;

import java.util.List;
import com.dmatek.zgb.db.pojo.permission.Role;

public interface RoleService {
	boolean addRole(Role role) throws Exception;
	boolean deleteRole(List<Integer> ids) throws Exception;
	boolean deleteRoleName(String name) throws Exception;
	boolean updateRole(Role role) throws Exception;
	boolean updateRoleName(Role role) throws Exception;
	Role findOne(int id) throws Exception;
	// 根据名称 查找角色
	Role findOneName(String name) throws Exception;
	List<Role> findAll() throws Exception;
}
