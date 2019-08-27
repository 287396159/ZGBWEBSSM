package com.dmatek.zgb.report.statistic.warn.tool;

import com.dmatek.zgb.report.statistical.warn.bean.BaseStatisticalWarn;
import com.dmatek.zgb.warn.bean.base.BaseAbnormalNodeWarn;

public abstract class BaseStatisticalBaseWarnPageTool<V extends BaseAbnormalNodeWarn, T extends BaseStatisticalWarn<V>> 
			extends BaseStatisticalWarnPageTool<V, T> {
	@Override
	protected String getId(V v) {
		return v.getNodeId();
	}
}
