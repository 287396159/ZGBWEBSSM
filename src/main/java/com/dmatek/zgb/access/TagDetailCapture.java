package com.dmatek.zgb.access;

import com.dmatek.zgb.db.person.service.PersonService;
import com.dmatek.zgb.db.setting.service.CompanyService;
import com.dmatek.zgb.db.setting.service.GroupService;
import com.dmatek.zgb.db.setting.service.JobTypeService;
import com.dmatek.zgb.db.setting.service.NodeService;
import com.dmatek.zgb.db.setting.service.RegionService;

public class TagDetailCapture {
	private PersonService personService;
	private CompanyService companyService;
	private JobTypeService jobTypeService;
	private NodeService nodeService;
	private GroupService groupService;
	private RegionService regionService;
	public TagDetailCapture(PersonService personService, CompanyService companyService, 
		JobTypeService jobTypeService, NodeService nodeService, GroupService groupService,
		RegionService regionService) {
		this.personService = personService;
		this.companyService = companyService;
		this.jobTypeService = jobTypeService;
		this.nodeService = nodeService;
		this.groupService = groupService;
		this.regionService = regionService;
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
	public NodeService getNodeService() {
		return nodeService;
	}
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}
	public GroupService getGroupService() {
		return groupService;
	}
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	public RegionService getRegionService() {
		return regionService;
	}
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}
}
