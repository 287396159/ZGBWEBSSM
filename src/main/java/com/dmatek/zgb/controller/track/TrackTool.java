package com.dmatek.zgb.controller.track;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmatek.zgb.db.person.service.PersonService;
import com.dmatek.zgb.db.pojo.person.Person;
import com.dmatek.zgb.db.pojo.setting.Node;
import com.dmatek.zgb.db.pojo.setting.Region;
import com.dmatek.zgb.db.setting.service.NodeService;
import com.dmatek.zgb.db.setting.service.RegionService;
import com.dmatek.zgb.monitor.bean.TrackPacket;
import com.dmatek.zgb.monitor.device.detail.TagDetail;

@Component
public class TrackTool {
	@Autowired
	private PersonService personService;
	@Autowired
	private NodeService nodeService;
	@Autowired
	private RegionService regionService;
	
	public Person getSearchPerson(String tagInfo) throws Exception{
		Person current = null; 
		// 查看卡片的名称、编号、ID
		current = personService.findOneFromName(tagInfo);
		if(null == current) {
			current = personService.findOneFromNo(tagInfo);
		}
		if(null == current) {
			current = personService.findOneFromTagId(tagInfo);
		}
		return current;
	}
	public List<TagDetail> getAllTagDetails(List<TrackPacket> trackPackets) throws Exception {
		List<TagDetail> allTagDetails = new ArrayList<TagDetail>();
		for (TrackPacket trackPacket : trackPackets) {
			allTagDetails.add(getTagDetail(trackPacket));
		}
		return allTagDetails;
	}
	public TagDetail getTagDetail(TrackPacket trackPacket) throws Exception{
		TagDetail tagDetail = new TagDetail();
		// 获取卡片的ID
		tagDetail.setId(trackPacket.getId());
		Person person = personService.findOneFromTagId(trackPacket.getId());
		// 卡片名称、卡片编号
		if(null != person){
			tagDetail.setTagName(person.getName());
			tagDetail.setTagNo(person.getNo());
			tagDetail.setImage(person.getImgPath());
			
			tagDetail.setCompanyNo(person.getCompany_No());
			tagDetail.setCompanyName(person.getCompany_Name());
			
			tagDetail.setJobTypeNo(person.getJobType_No());
			tagDetail.setJobTypeName(person.getJobType_Name());
		}
		// 获取参考点讯息
		Node node = nodeService.findOne(trackPacket.getrId());
		// 获取参考点ID
		tagDetail.setrId(trackPacket.getrId());
		if(null != node){
			tagDetail.setReferName(node.getName());
			tagDetail.setRegionId(node.getRegionId());
			Region region = regionService.findOne(node.getRegionId());
			if(null != region){
				tagDetail.setRegionName(region.getName());
				tagDetail.setGroupId(region.getGroupId());
				tagDetail.setGroupName(region.getGroupName());
			}
		}
		tagDetail.setPersonnelHelp(trackPacket.isPersonnelHelp());
		tagDetail.setNotMove(trackPacket.isNotMove());
		tagDetail.setLowPower(trackPacket.isLowPower());
		tagDetail.setAreaControl(trackPacket.isAreaControl());
		tagDetail.setAbnormalTag(trackPacket.isAbnormalTag());
		tagDetail.setIndex(trackPacket.getIndex());
		tagDetail.setAlarm(trackPacket.isAlarm());
		tagDetail.setBat(trackPacket.getBat());
		tagDetail.setNotMoveTime(trackPacket.getNotMoveTime());
		tagDetail.setSleepTime(trackPacket.getSleepTime());
		tagDetail.setRssi(trackPacket.getRssi());
		tagDetail.setReportTime(trackPacket.getReportTime());
		return tagDetail;
	}
}
