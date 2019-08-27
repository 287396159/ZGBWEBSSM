package com.dmatek.zgb.import_.excel;

import java.util.Map;
import org.apache.poi.ss.usermodel.Picture;
import com.dmatek.zgb.controller.setting.validation.BaseSettingValidation;
import com.dmatek.zgb.db.pojo.setting.Company;
import com.dmatek.zgb.db.setting.service.CompanyService;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

public class CompanyExcelImport extends BaseExcelImport<Company> {
	private CompanyService companyService;
	public CompanyExcelImport(BaseResultFactory<Result> viewResultFactory,
			BaseSettingValidation<String, Company> baseSettingValidation,
			CompanyService companyService) {
		super(viewResultFactory, baseSettingValidation);
		this.companyService = companyService;
	}
	@Override
	protected String getSheetName() {
		return "公司資料";
	}
	@Override
	protected Class<Company> getTClazz() {
		return Company.class;
	}

	@Override
	protected Result newInstance(Map<Integer, Object> map, Map<String, Picture> picturesMap,
			int row) throws Exception {
		Company company = new Company();
		Object oNo = map.get(0);
		if(null != oNo && oNo instanceof String) {
			company.setNo((String) oNo);
		}
		Object oName = map.get(1);
		if(null != oName && oName instanceof String) {
			company.setName((String) oName);
		}
		Object oPhone = map.get(2);
		if(null != oPhone && oPhone instanceof String) {
			company.setPhone((String) oPhone);
		}
		Object oAddress = map.get(3);
		if(null != oAddress && oAddress instanceof String) {
			company.setAddress((String) oAddress);
		}
		return getViewResultFactory().successResult(company);
	}
	@Override
	protected boolean isIncludedPicture() {
		return true;
	}
	@Override
	protected void addData(Company company) throws Exception {
		Company dbCompany = getCompanyService().findOne(company.getNo());
		if(null != dbCompany) {
			return;
		}
		getCompanyService().addCompany(company);
	}
	public CompanyService getCompanyService() {
		return companyService;
	}
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
}
