package com.dmatek.zgb.import_.excel;

import java.util.Map;

import org.apache.poi.ss.usermodel.Picture;

import com.dmatek.zgb.controller.setting.validation.BaseSettingValidation;
import com.dmatek.zgb.db.pojo.setting.JobType;
import com.dmatek.zgb.db.setting.service.JobTypeService;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

public class JobTypeExcelImport extends BaseExcelImport<JobType> {
	private JobTypeService jobTypeService;
	public JobTypeExcelImport(BaseResultFactory<Result> viewResultFactory,
			BaseSettingValidation<String, JobType> baseSettingValidation,
			JobTypeService jobTypeService) {
		super(viewResultFactory, baseSettingValidation);
		this.jobTypeService = jobTypeService;
	}
	@Override
	protected void addData(JobType jobType) throws Exception {
		JobType dbJobType = getJobTypeService().findOne(jobType.getNo());
		if (null != dbJobType) {// 說明當前的記錄已經存在不需要再次添加
			return;
		}
		getJobTypeService().addJobType(jobType);
	}
	@Override
	public String getSheetName() {
		return "工種資料";
	}
	@Override
	protected Class<JobType> getTClazz() {
		return JobType.class;
	}
	public JobTypeService getJobTypeService() {
		return jobTypeService;
	}
	public void setJobTypeService(JobTypeService jobTypeService) {
		this.jobTypeService = jobTypeService;
	}
	@Override
	protected boolean isIncludedPicture() {
		return false;
	}
	@Override
	protected Result newInstance(Map<Integer, Object> map,
			Map<String, Picture> picturesMap, int row) throws Exception {
		JobType jobType = new JobType();
		Object oNo = map.get(0);
		if(null != oNo && oNo instanceof String) {
			jobType.setNo((String) oNo);
		}
		Object oName = map.get(1);
		if(null != oName && oName instanceof String){
			jobType.setName((String) oName);
		}
		Object oColor = map.get(2);
		if(null != oColor && oColor instanceof String) {
			jobType.setColor((String) oColor);
		}
		return getViewResultFactory().successResult(jobType);
	}
}
