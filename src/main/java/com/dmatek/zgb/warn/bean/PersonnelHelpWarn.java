package com.dmatek.zgb.warn.bean;
import com.dmatek.zgb.warn.bean.base.BaseTagWarnMessage;
/**
 * 人员求救警告讯息
 * @author zf
 * @data 2018年12月14日 下午4:23:34
 * @Description
 */
public class PersonnelHelpWarn extends BaseTagWarnMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PersonnelHelpWarn() {
		super();
	}
	public PersonnelHelpWarn(String tagId, String tagName, String tagNo,
			String companyNo, String companyName, String jobTypeNo,
			String jobTypeName, int groupId, int regionId, String groupName,
			String regionName, String referId, String referName) {
		super(tagId, tagName, tagNo, companyNo, companyName, jobTypeNo, jobTypeName,
			groupId, regionId, groupName, regionName, referId, referName);
	}
	@Override
	public String toString() {
		return "PersonnelHelpWarn []";
	}
	
}
