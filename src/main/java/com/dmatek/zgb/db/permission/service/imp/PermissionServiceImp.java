package com.dmatek.zgb.db.permission.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dmatek.zgb.db.permission.dao.PermissionDao;
import com.dmatek.zgb.db.permission.service.PermissionService;
import com.dmatek.zgb.db.pojo.permission.Permission;
@Service("permissionService")
public class PermissionServiceImp implements PermissionService {
	@Autowired
	private PermissionDao permissionDao;
	@Override
	public boolean addPermission(Permission permission) throws Exception {
		if(null != permission){
			return permissionDao.addPermission(permission);
		}
		return false;
	}

	@Override
	public boolean deletePermission(List<Integer> ids) throws Exception {
		if(null != ids && !ids.isEmpty()){
			return permissionDao.deletePermission(ids);
		}
		return false;
	}
	@Override
	public Permission findOne(int id) throws Exception {
		if(id > 0){
			return permissionDao.findOne(id);
		}
		return null;
	}

	@Override
	public List<Permission> findAll() throws Exception {
		return permissionDao.findAll();
	}

	@Override
	public Permission findName(String name) throws Exception {
		if(!StringUtils.isEmpty(name)) {
			return permissionDao.findName(name);
		}
		return null;
	}
}
