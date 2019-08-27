package com.dmatek.zgb.import_.excel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmatek.zgb.controller.setting.validation.BaseSettingValidation;
import com.dmatek.zgb.db.person.service.PersonService;
import com.dmatek.zgb.db.pojo.person.Person;
import com.dmatek.zgb.db.pojo.setting.Company;
import com.dmatek.zgb.db.pojo.setting.JobType;
import com.dmatek.zgb.db.setting.service.CompanyService;
import com.dmatek.zgb.db.setting.service.GroupService;
import com.dmatek.zgb.db.setting.service.JobTypeService;
import com.dmatek.zgb.file.update.StaffUploadProperties;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@Configuration
public class ExcelImportConfig {
	@Bean
	public JobTypeExcelImport getJobTypeExcelImport(BaseResultFactory<Result> viewResultFactory,
			BaseSettingValidation<String, JobType> baseSettingValidation, 
			JobTypeService jobTypeService) {
		return new JobTypeExcelImport(viewResultFactory, baseSettingValidation, 
				jobTypeService);
	}
	@Bean
	public CompanyExcelImport getCompanyExcelImport(BaseResultFactory<Result> viewResultFactory,
			BaseSettingValidation<String, Company> baseSettingValidation, 
			CompanyService companyService) {
		return new CompanyExcelImport(viewResultFactory, baseSettingValidation, companyService);
	}
	@Bean
	public PersonExcelImport getPersonExcelImport(BaseResultFactory<Result> viewResultFactory,
			BaseSettingValidation<String, Person> baseSettingValidation,
			PersonService personService, CompanyService companyService,
			JobTypeService jobTypeService, GroupService groupService, StaffUploadProperties staffProperties) {
		return new PersonExcelImport(viewResultFactory, baseSettingValidation, personService, companyService, 
				jobTypeService, groupService, staffProperties.getUploadFolder());
	}
}
