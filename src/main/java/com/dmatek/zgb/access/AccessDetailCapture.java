package com.dmatek.zgb.access;

import java.util.UUID;

import com.dmatek.zgb.access.bean.FillAccessRecord;
import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.access.onduty.workaccess.BaseWorkAccess;
import com.dmatek.zgb.db.person.service.PersonService;
import com.dmatek.zgb.db.pojo.person.Person;
import com.dmatek.zgb.db.pojo.setting.Company;
import com.dmatek.zgb.db.pojo.setting.Group;
import com.dmatek.zgb.db.pojo.setting.JobType;
import com.dmatek.zgb.db.pojo.setting.Node;
import com.dmatek.zgb.db.pojo.setting.Region;
import com.dmatek.zgb.db.setting.service.CompanyService;
import com.dmatek.zgb.db.setting.service.GroupService;
import com.dmatek.zgb.db.setting.service.JobTypeService;
import com.dmatek.zgb.db.setting.service.NodeService;
import com.dmatek.zgb.db.setting.service.RegionService;

public class AccessDetailCapture implements IAccessDetailCapture { 
	private TagDetailCapture tagDetailCapture;
	public AccessDetailCapture(TagDetailCapture tagDetailCapture) {
		this.tagDetailCapture = tagDetailCapture;
	}
	@Override
	public TagAccessRecord obtainAccessRecord(FillAccessRecord fillAccessRecord)
			throws Exception {
		// 获取人员讯息
		Person person = null;
		PersonService personService = tagDetailCapture.getPersonService();
		if(null != personService) {
			person = personService.findOneFromTagId(fillAccessRecord.getTagId());
		}
		// 获取公司和工种讯息
		Company company = null;
		JobType jobType = null;
		if(null != person) {
			CompanyService companyService = tagDetailCapture.getCompanyService();
			if(null != companyService) {
				company = companyService.findOne(person.getCompany_No());
			}
			JobTypeService jobTypeService = tagDetailCapture.getJobTypeService();
			if(null != jobTypeService){
				jobType = jobTypeService.findOne(person.getJobType_No());
			}
		}
		// 获取节点讯息
		Node node = null;
		NodeService nodeService = tagDetailCapture.getNodeService();
		if(null != nodeService) {
			node = nodeService.findOne(fillAccessRecord.getReferId());
		}
		// 获取区域讯息
		Region region = null;
		RegionService regionService = tagDetailCapture.getRegionService();
		if(null != node && null != regionService) {
			region = regionService.findOne(node.getRegionId());
		}
		// 获取组别讯息
		Group group = null;
		GroupService groupService = tagDetailCapture.getGroupService();
		if(null != region && null != groupService){
			group = groupService.findOne(region.getGroupId());
		}
		return newInstance(fillAccessRecord, person, company, jobType, node,
				region, group);
	}
	private TagAccessRecord newInstance(FillAccessRecord fillAccessRecord, Person person, 
			Company company, JobType jobType, Node node, Region region, Group group){
		String uuid = UUID.randomUUID().toString();
		return new TagAccessRecord(uuid, fillAccessRecord.getReferId(),
				fillAccessRecord.getTagId(), null != person ? person.getNo()
						: "", null != person ? person.getName() : "",
				null != node ? node.getName() : "",
				null != person ? person.getCompany_No() : "",
				null != company ? company.getName() : "",
				null != person ? person.getJobType_No() : "",
				null != jobType ? jobType.getName() : "",
				null != region ? region.getGroupId() : 0,
				null != region ? region.getId() : 0,
				null != group ? group.getName() : "",
				null != region ? region.getName() : "",
				(byte) fillAccessRecord.getAccessType(),
				fillAccessRecord.getAccessTime(),
				null != person ? person.getImgPath() : "",
				fillAccessRecord.getResTime(),
				false,// 默认没有eol（低电量报警）
				fillAccessRecord.getAccessType() == BaseWorkAccess
						.getOffdutyType() ? "外出" : "進入");
	}
	public TagDetailCapture getTagDetailCapture() {
		return tagDetailCapture;
	}
	public void setTagDetailCapture(TagDetailCapture tagDetailCapture) {
		this.tagDetailCapture = tagDetailCapture;
	}
}
