package com.dmatek.zgb.controller.setting.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.db.permission.service.AccountService;
import com.dmatek.zgb.db.pojo.setting.Group;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
@Component
public class GroupValidation extends BaseSettingValidation<Integer,Group>{
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Override
	public Result validateValue(Group group) throws Exception {
		if(null == group){
			return viewResultFactory.errorResult("工地訊息不能為空...");
		}
		if(group.getId() < 0){
			return viewResultFactory.errorResult("工地ID不能小於0！");
		}
		if(StringUtils.isEmpty(group.getName())){
			return viewResultFactory.errorResult("工地名稱不能為空！");
		}
		return checkDataLength(group);
	}
	@Override
	public AccountService getAccountService() {
		return accountService;
	}
	
	private Result checkDataLength(Group group) throws Exception {
		if(group.getName().length() > 30) {
			return viewResultFactory.errorResult("組別名稱不能超過30個字符！");
		}
		if(!StringUtils.isEmpty(group.getDes()) && 
			group.getDes().length() > 120) {
			return viewResultFactory.errorResult("組別描述字段不能超過120個字符！");
		}
		return null;
	}
}
