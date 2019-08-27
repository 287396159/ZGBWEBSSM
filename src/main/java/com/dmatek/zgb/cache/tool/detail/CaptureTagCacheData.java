package com.dmatek.zgb.cache.tool.detail;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.druid.util.StringUtils;
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
import com.dmatek.zgb.monitor.bean.TagPacket;
import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.redis.service.RedisService;

public class CaptureTagCacheData extends BaseCaptureCacheData<TagDetail, TagPacket>{
	@Autowired
	private PersonService personService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private JobTypeService jobTypeService;
	@Autowired
	private NodeService nodeService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private GroupService groupService;
	private Person person = null;
	private Company company = null;
	private JobType jobType = null;
	private Node node = null;
	private Region region = null;
	private Group group = null;
	public CaptureTagCacheData(RedisService redisService, String cacheName) {
		super(redisService, cacheName);
	}
	@Override
	public TagDetail getDetailCacheData(TagPacket obj)
			throws Exception {
		company = null;jobType = null;
		region = null;group = null;
		node = nodeService.findOne(obj.getrId());
		person = personService.findOneFromTagId(obj.getId());
		if(null != person){
			if(!StringUtils.isEmpty(person.getCompany_No())){
				company = companyService.findOne(person.getCompany_No());
			}
			if(!StringUtils.isEmpty(person.getJobType_No())){
				jobType = jobTypeService.findOne(person.getJobType_No());
			}
		}
		if(null != node){
			region = regionService.findOne(node.getRegionId());
		}
		if(null != region){
			group = groupService.findOne(region.getGroupId());
		}
		TagDetail tagDetail = new TagDetail(null != person?person.getName():"", 
				null != person?person.getNo():"",       null != person?person.getCompany_No():"", 
				null != company?company.getName():"",   null != person?person.getJobType_No():"", 
				null != jobType?jobType.getName():"",   null != region?region.getGroupId():-1, 
				null != region?region.getId():-1,       null != group?group.getName():"",
				null != region?region.getName():"",     null != node?node.getName():"",
				null != person?person.getResTime():-1f, null != person?person.getStopTime():-1f,
				obj,	null != person?person.getImgPath():"");
		return tagDetail;
	}
}
