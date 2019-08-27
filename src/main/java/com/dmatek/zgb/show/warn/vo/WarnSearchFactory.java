package com.dmatek.zgb.show.warn.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.util.StringUtils;

public class WarnSearchFactory {
	private SimpleDateFormat getRiqiPattern(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	public NodeWarnSearchInfo getNodeWarnSearchInfo(String name, 
			String group, String sStart, String sEnd) throws ParseException{
		NodeWarnSearchInfo nodeWarnSearchInfo = new NodeWarnSearchInfo();
		if(!StringUtils.isEmpty(name)){
			nodeWarnSearchInfo.setName(name);
		}
		if(!StringUtils.isEmpty(group)){
			nodeWarnSearchInfo.setGroupName(group);
		}
		SimpleDateFormat searchDateFormat = getRiqiPattern();
		if(!StringUtils.isEmpty(sStart)){
			nodeWarnSearchInfo.setStart(searchDateFormat.parse(sStart));
		}
		if(!StringUtils.isEmpty(sEnd)){
			nodeWarnSearchInfo.setEnd(searchDateFormat.parse(sEnd));
		}
		return nodeWarnSearchInfo;
	}
	
	public TagWarnSearchInfo getTagWarnSearchInfo(String name,
			String company, String jobType, 
			String group,String sStart, String sEnd) throws ParseException{
		TagWarnSearchInfo tagWarnSearchInfo = new TagWarnSearchInfo();
		if(!StringUtils.isEmpty(name)){
			tagWarnSearchInfo.setName(name);
		}
		if(!StringUtils.isEmpty(company)){
			tagWarnSearchInfo.setCompanyName(company);
		}
		if(!StringUtils.isEmpty(jobType)){
			tagWarnSearchInfo.setJobTypeName(jobType);
		}
		if(!StringUtils.isEmpty(group)){
			tagWarnSearchInfo.setGroupName(group);
		}
		SimpleDateFormat searchDateFormat = getRiqiPattern();
		if(!StringUtils.isEmpty(sStart)){
			tagWarnSearchInfo.setStart(searchDateFormat.parse(sStart));
		}
		if(!StringUtils.isEmpty(sEnd)){
			tagWarnSearchInfo.setEnd(searchDateFormat.parse(sEnd));
		}
		return tagWarnSearchInfo;
	}
}
