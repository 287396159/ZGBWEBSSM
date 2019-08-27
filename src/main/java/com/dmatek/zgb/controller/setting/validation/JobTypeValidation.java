package com.dmatek.zgb.controller.setting.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.db.permission.service.AccountService;
import com.dmatek.zgb.db.pojo.setting.JobType;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
@Component
public class JobTypeValidation extends BaseSettingValidation<String, JobType> {
	@Autowired
	private AccountService accountService;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Override
	public Result validateValue(JobType jobType) throws Exception {
		if(null == jobType){
			return viewResultFactory.errorResult("職位不能為空!");
		}
		if(StringUtils.isEmpty(jobType.getNo())){
			return viewResultFactory.errorResult("ID不能為空!");
		}
		if(jobType.getNo().length() <= 0 || jobType.getNo().length() > 4){
			return viewResultFactory.errorResult("工種編號長度小於等於4，且不能等於0");
		}
		if(StringUtils.isEmpty(jobType.getName())){
			return viewResultFactory.errorResult("名稱不能為空!");
		}
		return checkDataLength(jobType);
	}
	/**
	 * 校驗數據長度
	 */
	private Result checkDataLength(JobType jobType) throws Exception {
		if(jobType.getNo().length() > 4){
			return viewResultFactory.errorResult("工種編號不能超過4個字符!");
		}
		if(jobType.getName().length() > 20) {
			return viewResultFactory.errorResult("工種名稱不能超過20個字符!");
		}
		if(!StringUtils.isEmpty(jobType.getColor()) && 
			jobType.getColor().length() > 10) {
			return viewResultFactory.errorResult("工種顏色不能超過10個字符!");
		}
		return null;
	}
	
	@Override
	public AccountService getAccountService() {
		return accountService;
	}
}
