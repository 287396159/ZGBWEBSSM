package com.dmatek.zgb.controller.setting.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.db.permission.service.AccountService;
import com.dmatek.zgb.db.pojo.permission.Account;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
@Component
public class AccountValidation extends BaseSettingValidation<Integer, Account> {
	@Autowired
	private AccountService accountService;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Override
	public Result validateValue(Account account) throws Exception {
		if(null == account){
			return viewResultFactory.errorResult("賬戶不能為空！");
		}
		if(StringUtils.isEmpty(account.getName())){
			return viewResultFactory.errorResult("賬戶名稱不能等於空！");
		}
		if(StringUtils.isEmpty(account.getPsw())){
			return viewResultFactory.errorResult("賬戶密碼不能等於空！");
		}
		if(account.getPsw().contains(account.getName())){
			return viewResultFactory.errorResult("密碼不能包含用戶名! ");
		}
		if(!account.getPsw().matches(".*\\d+.*")){
			return  viewResultFactory.errorResult("密碼必須包含數字! ");
		}
		if(!account.getPsw().matches(".*[a-zA-Z]+.*")){
			return viewResultFactory.errorResult("密碼必須包含字母! ");
		}
		if(account.getPsw().matches(".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*")){
			return viewResultFactory.errorResult("密碼不能包含特殊符號! ");
		}
		return checkDataLength(account);
	}
	@Override
	public AccountService getAccountService() {
		return accountService;
	}
	
	private Result checkDataLength(Account account) throws Exception {
		if(account.getName().length() > 20){
			return viewResultFactory.errorResult("賬戶名稱不能超過20個字符！");
		}
		if(account.getPsw().length() > 12 || account.getPsw().length() < 6){
			return viewResultFactory.errorResult("密碼長度必須大於等於6，且小於等於12! ");
		}
		return null;
	}
}
