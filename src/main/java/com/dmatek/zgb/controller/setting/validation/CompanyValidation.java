package com.dmatek.zgb.controller.setting.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.db.permission.service.AccountService;
import com.dmatek.zgb.db.pojo.setting.Company;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
@Component
public class CompanyValidation extends BaseSettingValidation<String,Company> {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Override
	public Result validateValue(Company company) throws Exception {
		if(null == company){
			return viewResultFactory.errorResult("公司訊息不能為空!");
		}
		if(StringUtils.isEmpty(company.getNo())){
			return viewResultFactory.errorResult("公司編號不能為空!");
		}
		if(StringUtils.isEmpty(company.getName())){
			return viewResultFactory.errorResult("公司名稱不能為空!");
		}
		return checkDataLength(company);
	}
	@Override
	public AccountService getAccountService() {
		return accountService;
	}
	
	private Result checkDataLength(Company company) throws Exception {
		if(company.getNo().length() > 20) {
			return viewResultFactory.errorResult("公司編號長度不能超過20個字符！");
		}
		if(company.getName().length() > 50){
			return viewResultFactory.errorResult("公司名稱長度不能超過50個字符！");
		}
		if(!StringUtils.isEmpty(company.getPhone()) && 
			company.getPhone().length() > 20){
			return viewResultFactory.errorResult("公司號碼長度不能超過20個字符！");
		}
		if(!StringUtils.isEmpty(company.getAddress()) && 
			company.getAddress().length() > 200){
			return viewResultFactory.errorResult("公司地址長度不能超過200個字符！");
		}
		return null;
	}
}
