package com.dmatek.zgb.shiro.realm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import com.dmatek.zgb.db.permission.service.AccountService;
import com.dmatek.zgb.db.permission.service.PermissionService;
import com.dmatek.zgb.db.permission.service.RoleService;
import com.dmatek.zgb.db.permission.service.Role_PermissionService;
import com.dmatek.zgb.db.pojo.permission.Account;
import com.dmatek.zgb.db.pojo.permission.Permission;
import com.dmatek.zgb.db.pojo.permission.Role;
import com.dmatek.zgb.db.pojo.permission.Role_Permission;

public class MyShiroRealm extends AuthorizingRealm {
	private final Logger logger = Logger.getLogger(MyShiroRealm.class);
	@Lazy
	@Autowired
	private AccountService accountService;
	@Lazy
	@Autowired
	private RoleService roleService;
	@Lazy
	@Autowired
	private PermissionService permissionService;
	@Lazy
	@Autowired
	private Role_PermissionService rolePermissionService;
	/**
	 * 權限驗證
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		SimpleAuthorizationInfo simpleAuthors = null;
		List<String> permissions = new ArrayList<String>();
		//查詢所有的權限
		User user = (User) principals.getPrimaryPrincipal();
		// 獲取當前登陸用戶的所有權限訊息
		List<Role_Permission> rolePermissions = null;
		if(null != user){
			try {
				rolePermissions = rolePermissionService
						.findAllFromRoleId(user.getRoleId());
			} catch (Exception e) {
				logger.error("查詢用戶【" + user.toString() + "】的角色權限訊息時出現異常...");
			}
			if(null != rolePermissions && !rolePermissions.isEmpty()) {
				Permission permission = null;
				try {
					for (Role_Permission role_Permission : rolePermissions) {
						permission = permissionService.findOne(role_Permission.getPermissionId());
						if(null != permission){
							permissions.add(permission.getResource());
						}
					}
				} catch (Exception e) {
					logger.error("查詢權限訊息【" + Arrays.toString(rolePermissions.toArray()) + "】時出現異常...");
				}
			}
			if("super".equalsIgnoreCase(user.getRoleName())){
				permissions.add("*:*");
			}
			simpleAuthors = new SimpleAuthorizationInfo();
			simpleAuthors.addStringPermissions(permissions);
		}
		return simpleAuthors;
	}
	/**
     * 驗證用戶身份
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String userName = null;
		if (null != token) {
			userName = (String) token.getPrincipal();
		}
		Account account = null;
		try {
			account = accountService.findOneFromName(userName);
		} catch (Exception e) {
			logger.error("查找用戶【userName：" + userName + "】時出現異常...");
		}
		SimpleAuthenticationInfo simpleInfo = null;
		// 說明當前的賬戶存在
		if (null != account) {
			Role role = null;
			try {
				role = roleService.findOne(account.getRoleId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			simpleInfo = new SimpleAuthenticationInfo(new User(account,
					null != role ? role.getName() : ""), account.getPsw(),
					ByteSource.Util.bytes(userName), getName());
			logger.warn("simpleInfo : " + simpleInfo.toString());
		}
		return simpleInfo;
	}
	@Override
	public String getName() {
		return super.getName();
	}
}
