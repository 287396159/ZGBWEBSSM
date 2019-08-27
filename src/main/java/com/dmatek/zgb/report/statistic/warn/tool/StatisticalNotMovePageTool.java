package com.dmatek.zgb.report.statistic.warn.tool;

import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dmatek.zgb.report.statistical.warn.bean.NotMoveStatisticalWarn;
import com.dmatek.zgb.warn.bean.NotMoveWarn;
/**
 * 统计人员未移动的分页工具
 * @author Administrator
 * @data 2019年4月16日 下午4:19:40
 * @Description
 */
@Component
public class StatisticalNotMovePageTool extends
		BaseStatisticalTagWarnPageTool<NotMoveWarn, NotMoveStatisticalWarn> {
	@Override
	protected NotMoveStatisticalWarn instance(String id) {
		return new NotMoveStatisticalWarn(id);
	}
	@Override
	protected void after(Map<String, NotMoveStatisticalWarn> statisticMap) {
		Iterator<NotMoveStatisticalWarn> iterator = statisticMap.values().iterator();
		while (iterator.hasNext()) {
			iterator.next().statistic();
		}
	}
}
