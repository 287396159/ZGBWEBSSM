package com.dmatek.zgb.controller.setting.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.dmatek.zgb.db.permission.service.AccountService;
import com.dmatek.zgb.db.pojo.setting.BaseSettingPojo;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

public abstract class BaseSettingValidation<K,V extends BaseSettingPojo>{
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	public Result validateCreateUser(V value) throws Exception {
		AccountService accountService = getAccountService();
		if(null != accountService){
			if(null == accountService.findOneFromName(value.getCreateName())){
				return viewResultFactory.errorResult("添加的賬戶不存在！");
			}
		}
		return null;
	}
	public Result validateUpdateUser(V value) throws Exception {
		AccountService accountService = getAccountService();
		if(null != accountService){
			if(null == accountService.findOneFromName(value.getUpdateName())){
				return viewResultFactory.errorResult("修改的賬戶不存在！");
			}
		}
		return null;
	}
	/**
	 * 添加驗證
	 * @param value
	 * @return
	 * @throws Exception 
	 */
	public Result saveValidate(V value) throws Exception{
		Result result = validateValue(value);
		if(null != result){
			return result;
		}
		if(StringUtils.isEmpty(value.getCreateName())){
			return viewResultFactory.errorResult("添加信息的賬戶不能為空!");
		}
		return validateCreateUser(value);
	}
	/**
	 * 更新驗證
	 * @param value
	 * @return
	 * @throws Exception 
	 */
	public Result updateValidate(V value) throws Exception{
		Result result = validateValue(value);
		if(null != result) {
			return result;
		}
		if(StringUtils.isEmpty(value.getUpdateName())){
			return viewResultFactory.errorResult("更新信息的賬戶不能為空!");
		}
		return validateUpdateUser(value);
	}
	public abstract Result validateValue(V value) throws Exception;
	public abstract AccountService getAccountService();
	public BaseResultFactory<Result> getViewResultFactory() {
		return viewResultFactory;
	}
	public void setViewResultFactory(BaseResultFactory<Result> viewResultFactory) {
		this.viewResultFactory = viewResultFactory;
	}
}
