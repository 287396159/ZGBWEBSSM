package com.dmatek.zgb.export_.excel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmatek.zgb.file.update.TempUploadProperties;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@Configuration
public class ExcelExportConfig {
	@Bean
	public JobTypeExcelExport getJobTypeExcelExport(BaseResultFactory<Result> viewResultFactory,
			TempUploadProperties tempUploadProperties) {
		return new JobTypeExcelExport(viewResultFactory, tempUploadProperties);
	}
	@Bean
	public CompanyExcelExport getCompanyExcelExport(BaseResultFactory<Result> viewResultFactory,
			TempUploadProperties tempUploadProperties){
		return new CompanyExcelExport(viewResultFactory, tempUploadProperties);
	}
	@Bean
	public PersonExcelExport getPersonExcelExport(BaseResultFactory<Result> viewResultFactory,
			TempUploadProperties tempUploadProperties) {
		return new PersonExcelExport(viewResultFactory, tempUploadProperties);
	}
}
