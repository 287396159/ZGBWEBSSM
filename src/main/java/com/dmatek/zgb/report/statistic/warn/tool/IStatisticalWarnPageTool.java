package com.dmatek.zgb.report.statistic.warn.tool;

import java.util.List;

import com.dmatek.zgb.report.statistical.warn.bean.BaseStatisticalWarn;
import com.dmatek.zgb.warn.bean.base.BaseWarnMessage;
/**
 * 统计警告的 分页 工具
 * @author zhangfu
 * @data 2019年4月16日 下午3:58:37
 * @Description
 */
public interface IStatisticalWarnPageTool<F extends BaseWarnMessage, T extends BaseStatisticalWarn<F>> {
	public List<T> operate(List<T> totals, int page, int limit) throws Exception;
	public int total(List<T> totals) throws Exception;
}
