package com.dmatek.zgb.report.statistic.warn.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.dmatek.zgb.report.statistical.warn.bean.BaseStatisticalWarn;
import com.dmatek.zgb.warn.bean.base.BaseWarnMessage;

public abstract class BaseStatisticalWarnPageTool <V extends BaseWarnMessage, T extends BaseStatisticalWarn<V>> implements IStatisticalWarnPageTool<V, T>{
	@Override
	public int total(List<T> totals) throws Exception {
		// 1. 将所有的统计对象里面的 警告记录放到一个集合中
		LinkedList<V> sortedWarns = new LinkedList<V>();
		for (T warn : totals) {
			sortedWarns.addAll(warn.getStatisticalWarns());
		}
		return sortedWarns.size();
	}
	@Override
	public List<T> operate(List<T> totals, int page, int limit)
			throws Exception {
		// 1. 将所有的统计对象里面的 警告记录放到一个集合中
		LinkedList<V> sortedWarns = new LinkedList<V>();
		for (T warn : totals) {
			sortedWarns.addAll(warn.getStatisticalWarns());
		}
		// 2. 获取分页后，应该获取的讯息的序列号
		int fromIndex = (page - 1) * limit >= sortedWarns.size() ? sortedWarns
				.size() : (page - 1) * limit;
		int toIndex = page * limit >= sortedWarns.size() ? sortedWarns.size()
				: page * limit;
		Map<String, T> statisticMap = new HashMap<String, T>();
		// 3. 重新生产分页后的统计警告对象
		for (int i = fromIndex; i < toIndex; i++) {
			V warn = sortedWarns.get(i);
			if(null == warn) {// 说明记录已经超出，应该直接退出循环
				break;
			}
			// 获取statisticWarn对象
			T statisticWarn = statisticMap.get(getId(warn));
			if(null == statisticWarn) {// 之前已经存在，需要我们添加警告
				statisticWarn = instance(getId(warn));
				statisticWarn.getStatisticalWarns().add(warn);
				statisticMap.put(getId(warn), statisticWarn);
			} else {
				statisticWarn.getStatisticalWarns().add(warn);
			}
		}
		after(statisticMap);
		return new ArrayList<T>(statisticMap.values());
	}
	protected abstract void after(Map<String, T> statisticMap);
	protected abstract T instance(String id);
	protected abstract String getId(V v);
}
