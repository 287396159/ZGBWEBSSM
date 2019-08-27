package com.dmatek.zgb.db.permission.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.db.pojo.permission.Role;

public interface RoleDao {
	boolean addRole(@Param("role") Role role) throws Exception;
	boolean deleteRole(@Param("ids") List<Integer> ids) throws Exception;
	boolean deleteRoleName(@Param("name") String name) throws Exception;
	boolean updateRole(@Param("role") Role role) throws Exception;
	boolean updateRoleName(@Param("role") Role role) throws Exception;
	Role findOne(@Param("id") int id) throws Exception;
	Role findOneName(@Param("name") String name) throws Exception;
	List<Role> findAll() throws Exception;
}
