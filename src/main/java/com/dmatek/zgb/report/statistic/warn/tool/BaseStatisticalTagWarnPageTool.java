package com.dmatek.zgb.report.statistic.warn.tool;
import com.dmatek.zgb.report.statistical.warn.bean.BaseStatisticalWarn;
import com.dmatek.zgb.warn.bean.base.BaseTagWarnMessage;
/**
 * 统计卡片警告的分页抽象类工具
 * @author zhangfu
 * @data 2019年4月16日 下午3:54:26
 * @Description
 */
public abstract class BaseStatisticalTagWarnPageTool<V extends BaseTagWarnMessage, T extends BaseStatisticalWarn<V>>
		extends BaseStatisticalWarnPageTool<V, T> {
	@Override
	protected String getId(V v) {
		return v.getTagId();
	}
}
