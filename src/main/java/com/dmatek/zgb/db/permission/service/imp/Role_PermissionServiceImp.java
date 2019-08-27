package com.dmatek.zgb.db.permission.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dmatek.zgb.db.permission.dao.Role_PermissionDao;
import com.dmatek.zgb.db.permission.service.Role_PermissionService;
import com.dmatek.zgb.db.pojo.permission.Role_Permission;
@Service("rolePermissionService")
public class Role_PermissionServiceImp implements Role_PermissionService {
	@Autowired
	private Role_PermissionDao rolePermissioDao;
	@Override
	public boolean addRolePermissions(List<Role_Permission> rolePermissions)
			throws Exception {
		if(null != rolePermissions && rolePermissions.size() > 0){
			// 添加之前我们需要先清除掉原来的权限讯息
			rolePermissioDao.clearRolePermissions(rolePermissions.get(0).getRole_id());
			return rolePermissioDao.addRolePermissions(rolePermissions);
		}
		return false;
	}
	@Override
	public List<Role_Permission> findAllFromRoleId(int roleId) throws Exception {
		if(roleId > 0){
			return rolePermissioDao.findAllFromRoleId(roleId);
		}
		return null;
	}
	@Override
	public List<Role_Permission> findAllFromPermissionId(int permissionId)
			throws Exception {
		if(permissionId > 0){
			return rolePermissioDao.findAllFromPermissionId(permissionId);
		}
		return null;
	}
	@Override
	public List<Role_Permission> findAllFromRoleName(String roleName)
			throws Exception {
		if(!StringUtils.isEmpty(roleName)){
			return rolePermissioDao.findAllFromRoleName(roleName);
		}
		return null;
	}
	@Override
	public boolean clearRolePermissions(int roleId) throws Exception {
		if(roleId > 0){
			return rolePermissioDao.clearRolePermissions(roleId);
		}
		return false;
	}
}
