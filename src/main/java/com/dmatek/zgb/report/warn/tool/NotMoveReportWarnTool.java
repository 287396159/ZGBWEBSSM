package com.dmatek.zgb.report.warn.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.thymeleaf.util.ListUtils;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.controller.setting.params.base.ShowSettingParamTool;
import com.dmatek.zgb.db.warn.service.NotMoveService;
import com.dmatek.zgb.report.statistical.warn.bean.NotMoveStatisticalWarn;
import com.dmatek.zgb.warn.bean.NotMoveWarn;
import com.dmatek.zgb.warn.cache.base.BaseTagWarnCache;

public class NotMoveReportWarnTool extends BaseTagWarnReportTool<NotMoveWarn, NotMoveStatisticalWarn>{
	private NotMoveService notMoveService;
	public NotMoveReportWarnTool(ShowSettingParamTool showSettingParamTool,
			BaseWarmComparator descendingComparator,BaseWarmComparator ascendingComparator,
			BaseTagWarnCache<NotMoveWarn> tagWarnCache, 
			NotMoveService notMoveService) {
		super(showSettingParamTool, descendingComparator, ascendingComparator, tagWarnCache);
		this.notMoveService = notMoveService;
	}
	@Override
	public List<NotMoveWarn> getAllWarns() throws Exception {
		List<NotMoveWarn> dbList = notMoveService.findAll();
		List<NotMoveWarn> cacheList = getTagWarnCache().getAllWarns();
		dbList.addAll(cacheList);
		// 按时间进行排序
		ListUtils.sort(dbList, getDescendingComparator());
		return dbList;
	}
	@Override
	public List<NotMoveStatisticalWarn> getStatisticWarns(List<NotMoveWarn> matchWarns) throws Exception {
		Map<String, NotMoveStatisticalWarn> statisticWarns = new HashMap<String, NotMoveStatisticalWarn>();
		for (NotMoveWarn notMoveWarn : matchWarns) {
			NotMoveStatisticalWarn currentWarn = statisticWarns.get(notMoveWarn.getTagId());
			if(null == currentWarn) {
				currentWarn = new NotMoveStatisticalWarn(notMoveWarn.getTagId());
				statisticWarns.put(notMoveWarn.getTagId(), currentWarn);
			}
			currentWarn.getStatisticalWarns().add(notMoveWarn);
		}
		Iterator<NotMoveStatisticalWarn> iterators = statisticWarns.values().iterator();
		while (iterators.hasNext()) {
			NotMoveStatisticalWarn notMoveStatisticalWarn = iterators.next();
			notMoveStatisticalWarn.statistic();
			notMoveStatisticalWarn.getStatisticalWarns().sort(getAscendingComparator());
		}
		return new ArrayList<NotMoveStatisticalWarn>(statisticWarns.values());
	}
}
