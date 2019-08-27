package com.dmatek.zgb.report.warn.tool;

import org.springframework.util.StringUtils;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.controller.setting.params.base.ShowSettingParamTool;
import com.dmatek.zgb.report.statistical.warn.bean.BaseStatisticalWarn;
import com.dmatek.zgb.show.warn.vo.TagWarnSearchInfo;
import com.dmatek.zgb.warn.bean.base.BaseTagWarnMessage;
import com.dmatek.zgb.warn.cache.base.BaseTagWarnCache;

public abstract class BaseTagWarnReportTool<T extends BaseTagWarnMessage, F extends BaseStatisticalWarn<?>> extends BaseRootReportWarnTool<T, TagWarnSearchInfo, F> {
	private BaseTagWarnCache<T> tagWarnCache;
	public BaseTagWarnReportTool(ShowSettingParamTool showSettingParamTool,
			BaseWarmComparator descendingComparator,BaseWarmComparator ascendingComparator,
			BaseTagWarnCache<T> tagWarnCache) {
		super(showSettingParamTool, descendingComparator, ascendingComparator);
		this.tagWarnCache = tagWarnCache;
	}
	@Override
	protected boolean isMatchCondition(TagWarnSearchInfo searchInfor,
			T warnMessage) {
		boolean isName, isCompany, isJobType, isGroup, isTimeInterval;
		isName = StringUtils.isEmpty(searchInfor.getName())
				|| warnMessage.getTagName().equalsIgnoreCase(searchInfor.getName());
		isCompany = StringUtils.isEmpty(searchInfor.getCompanyName())
				|| warnMessage.getCompanyName().equalsIgnoreCase(searchInfor.getCompanyName());
		isJobType = StringUtils.isEmpty(searchInfor.getJobTypeName())
				|| warnMessage.getJobTypeName().equalsIgnoreCase(searchInfor.getJobTypeName());
		isGroup = StringUtils.isEmpty(searchInfor.getGroupName())
				|| warnMessage.getGroupName().equalsIgnoreCase(searchInfor.getGroupName());
		isTimeInterval = (null == searchInfor.getStart() || null == searchInfor.getEnd())
				|| (warnMessage.getCreateTime().compareTo(searchInfor.getStart()) >= 0 
				&& warnMessage.getCreateTime().compareTo(searchInfor.getEnd()) < 0);
		if (isName && isCompany && isJobType && isGroup && isTimeInterval) {
			return true;
		}
		return false;
	}
	@Override
	protected boolean isEmptyCondition(TagWarnSearchInfo searchInfor) {
		if(StringUtils.isEmpty(searchInfor.getName()) && 
		   StringUtils.isEmpty(searchInfor.getCompanyName()) && 
		   StringUtils.isEmpty(searchInfor.getJobTypeName()) && 
		   StringUtils.isEmpty(searchInfor.getGroupName()) && 
		   null == searchInfor.getStart() && 
		   null == searchInfor.getEnd()) {
			return true;
		}
		return false;
	}
	public BaseTagWarnCache<T> getTagWarnCache() {
		return tagWarnCache;
	}
	public void setTagWarnCache(BaseTagWarnCache<T> tagWarnCache) {
		this.tagWarnCache = tagWarnCache;
	}
}
