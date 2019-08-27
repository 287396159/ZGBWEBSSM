package com.dmatek.zgb.import_.excel;
import java.util.Date;
import java.util.Map;

import org.apache.poi.ss.usermodel.Picture;
import org.springframework.util.StringUtils;

import com.dmatek.zgb.controller.setting.validation.BaseSettingValidation;
import com.dmatek.zgb.db.person.service.PersonService;
import com.dmatek.zgb.db.pojo.person.Person;
import com.dmatek.zgb.db.pojo.setting.Company;
import com.dmatek.zgb.db.pojo.setting.Group;
import com.dmatek.zgb.db.pojo.setting.JobType;
import com.dmatek.zgb.db.setting.service.CompanyService;
import com.dmatek.zgb.db.setting.service.GroupService;
import com.dmatek.zgb.db.setting.service.JobTypeService;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

public class PersonExcelImport extends BaseExcelImport<Person> {
	private String staffDir;
	private PersonService personService;
	private CompanyService companyService;
	private JobTypeService jobTypeService;
	private GroupService groupService;
	public PersonExcelImport(BaseResultFactory<Result> viewResultFactory,
			BaseSettingValidation<String, Person> baseSettingValidation,
			PersonService personService,
			CompanyService companyService,
			JobTypeService jobTypeService,
			GroupService groupService, 
			String staffDir) {
		super(viewResultFactory, baseSettingValidation);
		this.personService = personService;
		this.companyService = companyService;
		this.jobTypeService = jobTypeService;
		this.groupService = groupService;
		this.staffDir = staffDir;
	}
	@Override
	protected String getSheetName() {
		return "人員資料";
	}
	@Override
	protected Class<Person> getTClazz() {
		return Person.class;
	}
	
	@Override
	protected Result newInstance(Map<Integer, Object> map, Map<String, Picture> picturesMap, int row) throws Exception {
		Person person = new Person();
		
		// 設置人員頭像
		// 獲取坐標
		String key = row + "_" + 0;
		Picture picture = picturesMap.get(key);
		if(StringUtils.isEmpty(staffDir)) {
			return getViewResultFactory().errorResult("人員頭像目錄沒有指定");
		}
		if(null != picture) {
			// 將當前的圖片保存起來
			person.setImgPath(savePicture(picture, staffDir));
		}
		// 设置编号
		Object oNo = map.get(1);
		if (null != oNo && oNo instanceof String) {
			person.setNo((String) oNo);
		}
		// 设置名称
		Object oName = map.get(2);
		if(null != oName && oName instanceof String) {
			person.setName((String) oName);
		} 
		// 设置卡片ID
		Object oTagId = map.get(3);
		if(null != oTagId && oTagId instanceof String) {
			person.setTagId((String) oTagId);
		}
		// 设置公司名称
		Object oCompanyName = map.get(4);
		if(null != oCompanyName && oCompanyName instanceof String) {
			person.setCompany_Name((String) oCompanyName);
			Company company = getCompanyService().findName((String) oCompanyName);
			if(null != company) {
				person.setCompany_No(company.getNo());
			} else {// 說明公司不存在
				return getViewResultFactory().errorResult(
						"公司【name: " + (String) oCompanyName + "】不存在...");
			}
		}
		// 工地名稱
		Object oGroupName = map.get(5);
		if (null != oGroupName && oGroupName instanceof String) {
			person.setGroup_Name((String)oGroupName);
			Group group = getGroupService().findName((String)oGroupName);
			if(null != group) {
				person.setGroup_id(group.getId());
			} else {
				return getViewResultFactory().errorResult(
						"工地【name: " + (String) oGroupName + "】不存在...");
			}
		}
		// 设置工种名称
		Object oJobType_Name = map.get(6);
		if(null != oJobType_Name && oJobType_Name instanceof String) {
			person.setJobType_Name((String) oJobType_Name);
			JobType jobType = getJobTypeService().findName((String) oJobType_Name);
			if(null != jobType) {
				person.setJobType_No(jobType.getNo());
			} else {
				return getViewResultFactory().errorResult(
						"工種【name: " + (String) oJobType_Name + "】不存在...");
			}
		}
		// 設置血型
		Object oBloodType = map.get(7);
		if(null != oBloodType && oBloodType instanceof String) {
			String sBloodType = (String) oBloodType;
			// 判斷血型類型
			if(!StringUtils.isEmpty(oBloodType) && !validateBloodType(sBloodType)) {
				return getViewResultFactory().errorResult(oName + "的血型設置有誤，血型只能選擇A、B、AB、O四種");
			}
			person.setBloodType(sBloodType);
		}
		
		// 设置出生日期
		Object oBirthDay = map.get(8);
		if(null != oBirthDay && oBirthDay instanceof Date) {
			person.setBirthDay((Date) oBirthDay);
		}
		// 设置休息时间
		Object oRestime = map.get(9);
		if(null != oRestime && oRestime instanceof Double) {
			double resTime = (double) oRestime;
			person.setResTime((float)resTime);
		}
		// 设置滞留时间
		Object oStopTime = map.get(10);
		if(null != oStopTime && oStopTime instanceof Double) {
			double stopTime = (double) oStopTime;
			person.setStopTime((float)stopTime);
		}
		Object oDes = map.get(11);
		if(null != oDes && oDes instanceof String) {
			person.setDes((String) oDes);
		}
		return getViewResultFactory().successResult(person);
	}
	
	private boolean validateBloodType(String bloodType) {
		if("A".equals(bloodType) || "B".equals(bloodType) || 
			"AB".equals(bloodType) || "O".equals(bloodType)) {
			return true;
		}
		return false;
	}
	
	@Override
	protected boolean isIncludedPicture() {
		return true;
	}
	@Override
	protected void addData(Person person) throws Exception {
		Person dbPerson = getPersonService().findOneFromNo(person.getNo());
		if(null != dbPerson) {
			return;
		}
		getPersonService().addPerson(person);
	}
	public PersonService getPersonService() {
		return personService;
	}
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	public CompanyService getCompanyService() {
		return companyService;
	}
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	public JobTypeService getJobTypeService() {
		return jobTypeService;
	}
	public void setJobTypeService(JobTypeService jobTypeService) {
		this.jobTypeService = jobTypeService;
	}
	public GroupService getGroupService() {
		return groupService;
	}
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	public String getStaffDir() {
		return staffDir;
	}
	public void setStaffDir(String staffDir) {
		this.staffDir = staffDir;
	}
}
