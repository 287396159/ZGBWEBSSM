package com.dmatek.zgb.report.warn.tool;

import java.util.ArrayList;
import java.util.List;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.controller.setting.params.base.ShowSettingParamTool;
import com.dmatek.zgb.report.statistical.warn.bean.BaseStatisticalWarn;
import com.dmatek.zgb.show.warn.vo.BaseWarnSearchInfo;
import com.dmatek.zgb.warn.bean.base.BaseWarnMessage;

public abstract class BaseRootReportWarnTool<T extends BaseWarnMessage,
			V extends BaseWarnSearchInfo, F extends BaseStatisticalWarn<?>> extends BaseReportWarnTool{
	public BaseRootReportWarnTool(ShowSettingParamTool showSettingParamTool,
			BaseWarmComparator descendingComparator,BaseWarmComparator ascendingComparator) {
		super(showSettingParamTool, descendingComparator, ascendingComparator);
	}
	/**
	 * 获取所有匹配的警告讯息
	 * @param searchInfor
	 * @return
	 * @throws Exception
	 */
	public List<T> getAllMatchWarns(V searchInfor) throws Exception {
		if(isEmptyCondition(searchInfor)){
			return getAllWarns();
		}
		List<T> matchWarns = new ArrayList<T>();
		for (T t : getAllWarns()) {
			if(isMatchCondition(searchInfor, t)){
				matchWarns.add(t);
			}
		}
		
		return matchWarns;
	}
	/**
	 * 判断搜索条件是否都为空
	 * @param searchInfor
	 * @return
	 */
	protected abstract boolean isEmptyCondition(V searchInfor);
	/**
	 * 判断搜索条件与报警讯息是否匹配
	 * @param searchInfor
	 * @param warnMessage
	 * @return
	 */
	protected abstract boolean isMatchCondition(V searchInfor, T warnMessage);
	/**
	 * 获取所有的警告讯息
	 * @return
	 * @throws Exception
	 */
	public abstract List<T> getAllWarns() throws Exception;
	/**
	 * 获取统计的警告讯息
	 * @return
	 * @throws Exception
	 */
	public abstract List<F> getStatisticWarns(List<T> matchWarns) throws Exception;
}
