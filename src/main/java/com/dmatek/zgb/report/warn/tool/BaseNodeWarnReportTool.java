package com.dmatek.zgb.report.warn.tool;

import org.springframework.util.StringUtils;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.controller.setting.params.base.ShowSettingParamTool;
import com.dmatek.zgb.report.statistical.warn.bean.BaseStatisticalWarn;
import com.dmatek.zgb.show.warn.vo.NodeWarnSearchInfo;
import com.dmatek.zgb.warn.bean.base.BaseNodeWarnMessage;
import com.dmatek.zgb.warn.cache.base.BaseNodeWarnCache;

public abstract class BaseNodeWarnReportTool<T extends BaseNodeWarnMessage, F extends BaseStatisticalWarn<?>> 
			extends BaseRootReportWarnTool<T, NodeWarnSearchInfo, F> {
	private BaseNodeWarnCache<T> baseWarnCache;
	public BaseNodeWarnReportTool(ShowSettingParamTool showSettingParamTool,
			BaseWarmComparator descendingComparator,BaseWarmComparator ascendingComparator,
			BaseNodeWarnCache<T> baseWarnCache) {
		super(showSettingParamTool, descendingComparator, ascendingComparator);
		this.baseWarnCache = baseWarnCache;
	}
	@Override
	protected boolean isMatchCondition(NodeWarnSearchInfo searchInfor, T warnInfor) {
		boolean isName, isGroup, isTimeInterval;
		isName = StringUtils.isEmpty(searchInfor.getName())
				|| warnInfor.getNodeName().equalsIgnoreCase(searchInfor.getName());
		isGroup = StringUtils.isEmpty(searchInfor.getGroupName())
				|| warnInfor.getGroupName().equalsIgnoreCase(searchInfor.getGroupName());
		isTimeInterval = (null == searchInfor.getStart() || null == searchInfor.getEnd())
				|| (warnInfor.getCreateTime().compareTo(searchInfor.getStart()) >= 0 && 
				warnInfor.getCreateTime().compareTo(searchInfor.getEnd()) < 0);
		if (isName && isGroup && isTimeInterval) {
			return true;
		}
		return false;
	}
	@Override
	protected boolean isEmptyCondition(NodeWarnSearchInfo searchInfor) {
		if(StringUtils.isEmpty(searchInfor.getName())
				&& StringUtils.isEmpty(searchInfor.getGroupName())
				&& null == searchInfor.getStart()
				&& null == searchInfor.getEnd()) {
			return true;
		}
		return false;
	}
	public BaseNodeWarnCache<T> getBaseWarnCache() {
		return baseWarnCache;
	}
	public void setBaseWarnCache(BaseNodeWarnCache<T> baseWarnCache) {
		this.baseWarnCache = baseWarnCache;
	}
}
