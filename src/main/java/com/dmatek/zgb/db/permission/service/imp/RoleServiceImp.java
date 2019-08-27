package com.dmatek.zgb.db.permission.service.imp;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.db.permission.dao.RoleDao;
import com.dmatek.zgb.db.permission.service.RoleService;
import com.dmatek.zgb.db.pojo.permission.Role;
@Service("roleService")
public class RoleServiceImp implements RoleService {
	@Autowired
	private RoleDao roleDao;
	
	@Override
	public boolean addRole(Role role) throws Exception {
		if(null != role) {
			return roleDao.addRole(role);
		}
		return false;
	}
	@Override
	public boolean deleteRole(List<Integer> ids) throws Exception {
		if(null != ids && !ids.isEmpty()) {
			return roleDao.deleteRole(ids);
		}
		return false;
	}
	@Override
	public boolean updateRole(Role role) throws Exception {
		if(null != role) {
			return roleDao.updateRole(role);
		}
		return false;
	}
	@Override
	public Role findOne(int id) throws Exception {
		if(id > 0) {
			return roleDao.findOne(id);
		}
		return null;
	}
	@Override
	public List<Role> findAll() throws Exception {
		return roleDao.findAll();
	}
	@Override
	public boolean deleteRoleName(String name) throws Exception {
		if(!StringUtils.isEmpty(name)){
			return roleDao.deleteRoleName(name);
		}
		return false;
	}
	@Override
	public boolean updateRoleName(Role role) throws Exception {
		if(null != role){
			return roleDao.updateRoleName(role);
		}
		return false;
	}
	@Override
	public Role findOneName(String name) throws Exception {
		if(!Strings.isEmpty(name)) {
			return roleDao.findOneName(name);
		}
		return null;
	}
}
