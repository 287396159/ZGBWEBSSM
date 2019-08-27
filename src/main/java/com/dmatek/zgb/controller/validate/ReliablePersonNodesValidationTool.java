package com.dmatek.zgb.controller.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dmatek.zgb.controller.setting.validation.BaseSettingValidation;
import com.dmatek.zgb.db.permission.service.AccountService;
import com.dmatek.zgb.db.pojo.setting.ReliablePerson_Node;
import com.dmatek.zgb.setting.vo.Result;

@Component
public class ReliablePersonNodesValidationTool extends BaseSettingValidation<String, ReliablePerson_Node> {
	@Autowired
	private AccountService accountService;
	@Override
	public Result validateValue(ReliablePerson_Node value) throws Exception {
		return null;
	}
	@Override
	public AccountService getAccountService() {
		return accountService;
	}
	public Result validate_Create(String personNo, String[] ids, String createName){
		if(StringUtils.isEmpty(personNo)){
			return getViewResultFactory().errorResult("編號為空...");
		}
		if(StringUtils.isEmpty(createName)){
			return getViewResultFactory().errorResult("人員名稱為空");
		}
		return null;
	}
}
