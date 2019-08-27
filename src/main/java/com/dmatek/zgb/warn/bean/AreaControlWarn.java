package com.dmatek.zgb.warn.bean;

import com.dmatek.zgb.warn.bean.base.BaseTagWarnMessage;
/**
 * 区域管制警告
 * @author zf
 * @data 2018年12月14日 下午4:51:53
 * @Description
 */
public class AreaControlWarn extends BaseTagWarnMessage{
	private static final long serialVersionUID = 1L;
	public AreaControlWarn() {
		super();
	}
	public AreaControlWarn(String tagId, String tagName, String tagNo,
			String companyNo, String companyName, String jobTypeNo,
			String jobTypeName, int groupId, int regionId, String groupName,
			String regionName, String referId, String referName) {
		super(tagId, tagName, tagNo, companyNo, companyName, jobTypeNo, jobTypeName,
				groupId, regionId, groupName, regionName, referId, referName);
	}
}
