package com.dmatek.zgb.controller.setting.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.db.permission.service.AccountService;
import com.dmatek.zgb.db.pojo.permission.Permission;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
@Component
public class PermissionValidation extends BaseSettingValidation<Integer, Permission> {
	@Autowired
	private AccountService accountService;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Override
	public Result validateValue(Permission value) throws Exception {
		if(null == value){
			return viewResultFactory.errorResult("權限訊息不能為空！");
		}
		if(StringUtils.isEmpty(value.getName())){
			return viewResultFactory.errorResult("權限名稱不能為空！");
		}
		if(StringUtils.isEmpty(value.getResource())){
			return viewResultFactory.errorResult("權限資源不能為空!");
		}
		return checkDataLength(value);
	}
	@Override
	public AccountService getAccountService() {
		return accountService;
	}
	
	private Result checkDataLength(Permission permission) throws Exception {
		if(permission.getName().length() > 50){
			return viewResultFactory.errorResult("權限名稱不能超過50個字符！");
		}
		if(permission.getResource().length() > 50){
			return viewResultFactory.errorResult("權限資源名稱不能超過50個字符！");
		}
		return null;
	}
}
