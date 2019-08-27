package com.dmatek.zgb.controller.setting.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.db.permission.service.AccountService;
import com.dmatek.zgb.db.pojo.permission.Role;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
@Component
public class RoleValidation extends BaseSettingValidation<Integer, Role> {
	@Autowired
	private AccountService AccountService;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Override
	public Result validateValue(Role value) throws Exception {
		if(null == value){
			return viewResultFactory.errorResult("角色訊息不能為空!");
		}
		if(StringUtils.isEmpty(value.getName())){
			return viewResultFactory.errorResult("角色名稱不能為空!");
		}
		return checkDataLength(value);
	}
	@Override
	public AccountService getAccountService() {
		return AccountService;
	}
	
	private Result checkDataLength(Role role) throws Exception {
		if(role.getName().length() > 20) {
			return viewResultFactory.errorResult("角色名稱不能超過20個字符!");
		}
		if(!StringUtils.isEmpty(role.getDes()) && 
			role.getDes().length() > 50) {
			return viewResultFactory.errorResult("角色描述字段不能超過50個字符！");
		}
		return null;
	}
}
