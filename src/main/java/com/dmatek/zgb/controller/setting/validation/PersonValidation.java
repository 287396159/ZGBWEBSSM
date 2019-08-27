package com.dmatek.zgb.controller.setting.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dmatek.zgb.db.permission.service.AccountService;
import com.dmatek.zgb.db.pojo.person.Person;
import com.dmatek.zgb.db.pojo.setting.Company;
import com.dmatek.zgb.db.pojo.setting.JobType;
import com.dmatek.zgb.db.setting.service.CompanyService;
import com.dmatek.zgb.db.setting.service.JobTypeService;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@Component
public class PersonValidation extends BaseSettingValidation<String, Person> {
	@Autowired
	private AccountService accountService;
	@Autowired
	private JobTypeService jobTypeService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	// 驗證人員新的人員訊息是否有效
	@Override
	public Result validateValue(Person person) throws Exception {
		// 人員編號、姓名、卡片ID是否滿足要求
		if(StringUtils.isEmpty(person.getNo())){
			return viewResultFactory.errorResult("人員編號不能為空!");
		}
		if(StringUtils.isEmpty(person.getName())){
			return viewResultFactory.errorResult("人員姓名不能為空!");
		}
		if(StringUtils.isEmpty(person.getTagId())){
			return viewResultFactory.errorResult("人員佩戴的卡片ID不能為空!");
		}
		// 人員所屬公司編號是否滿足要求
		if(StringUtils.isEmpty(person.getCompany_No())){
			return viewResultFactory.errorResult("人員所屬的公司編號不能為空!");
		}
		Company company = companyService.findOne(person.getCompany_No());
		if(null == company){
			return viewResultFactory.errorResult("人員所屬的公司編號必須存在!");
		}
		// 人員所屬的工種編號是否滿足要求
		if(StringUtils.isEmpty(person.getJobType_No())){
			return viewResultFactory.errorResult("人員所屬的工種編號不能為空!");
		}
		JobType jobType = jobTypeService.findOne(person.getJobType_No());
		if(null == jobType){
			return viewResultFactory.errorResult("人員所屬的工種必須存在!");
		}
		return checkDataLength(person);
	}
	@Override
	public AccountService getAccountService() {
		return accountService;
	}
	
	private Result checkDataLength(Person person) throws Exception {
		if( person.getNo().length() > 30){
			return viewResultFactory.errorResult("人員編號長度不能超過30！");
		}
		if(person.getName().length() > 20){
			return viewResultFactory.errorResult("人員姓名長度不能超過20！");
		}
		if(person.getTagId().length() != 4){
			return viewResultFactory.errorResult("卡片編號只能為4個字符！");
		}
		if(!StringUtils.isEmpty(person.getImgPath()) && person.getImgPath().length() > 50){
			return viewResultFactory.errorResult("人員圖片地址不能超過50個字符!");
		}
		if(!StringUtils.isEmpty(person.getDes()) && person.getDes().length() > 120){
			return viewResultFactory.errorResult("人員描述不能超過120個字符!");
		}
		if(person.getStopTime() > 12 * 60){
			return viewResultFactory.errorResult("人員休息時間不能超過12個小時!");
		}
		return null;
	}
}
