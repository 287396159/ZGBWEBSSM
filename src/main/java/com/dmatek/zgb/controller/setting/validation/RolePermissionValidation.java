package com.dmatek.zgb.controller.setting.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmatek.zgb.db.permission.service.AccountService;
import com.dmatek.zgb.db.pojo.permission.Role_Permission;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@Component
public class RolePermissionValidation extends BaseSettingValidation<Integer, Role_Permission> {
	@Autowired
	private AccountService accountService;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Override
	public Result validateValue(Role_Permission value) throws Exception {
		if(null == value){
			viewResultFactory.errorResult("角色權限不能為空!");
		}
		return null;
	}
	@Override
	public AccountService getAccountService() {
		return accountService;
	}
}
