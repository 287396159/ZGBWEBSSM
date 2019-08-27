package com.dmatek.zgb.report.warn.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.thymeleaf.util.ListUtils;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.controller.setting.params.base.ShowSettingParamTool;
import com.dmatek.zgb.db.warn.service.LowPowerService;
import com.dmatek.zgb.report.statistical.warn.bean.LowBatteryStatisticalWarn;
import com.dmatek.zgb.warn.bean.LowPowerWarn;
import com.dmatek.zgb.warn.cache.base.BaseTagWarnCache;

public class LowPowerReportWarnTool extends BaseTagWarnReportTool<LowPowerWarn, LowBatteryStatisticalWarn>{
	private LowPowerService lowPowerService;
	public LowPowerReportWarnTool(ShowSettingParamTool showSettingParamTool,
			BaseWarmComparator descendingComparator,BaseWarmComparator ascendingComparator,
			BaseTagWarnCache<LowPowerWarn> tagWarnCache,
			LowPowerService lowPowerService) {
		super(showSettingParamTool, descendingComparator,ascendingComparator, tagWarnCache);
		this.lowPowerService = lowPowerService;
	}
	@Override
	public List<LowPowerWarn> getAllWarns() throws Exception {
		List<LowPowerWarn> dbList = lowPowerService.findAll();
		List<LowPowerWarn> cacheList = getTagWarnCache().getAllWarns();
		dbList.addAll(cacheList);
		// 按时间进行排序
		ListUtils.sort(dbList, getDescendingComparator());
		return dbList;
	}
	public LowPowerService getLowPowerService() {
		return lowPowerService;
	}
	public void setLowPowerService(LowPowerService lowPowerService) {
		this.lowPowerService = lowPowerService;
	}
	@Override
	public List<LowBatteryStatisticalWarn> getStatisticWarns(List<LowPowerWarn> matchWarns) throws Exception {
		Map<String, LowBatteryStatisticalWarn> statisticWarns = new HashMap<String, LowBatteryStatisticalWarn>();
		for (LowPowerWarn lowPowerWarn : matchWarns) {
			LowBatteryStatisticalWarn currentWarn = statisticWarns.get(lowPowerWarn.getTagId());
			if(null == currentWarn) {
				currentWarn = new LowBatteryStatisticalWarn(lowPowerWarn.getTagId());
				statisticWarns.put(lowPowerWarn.getTagId(), currentWarn);
			}
			currentWarn.getStatisticalWarns().add(lowPowerWarn);
		}
		Iterator<LowBatteryStatisticalWarn> iterator = statisticWarns.values().iterator();
		while (iterator.hasNext()) {
			//单独对每一个统计的警告讯息进行排序
			LowBatteryStatisticalWarn lowBatteryStatisticalWarn = iterator
					.next();
			lowBatteryStatisticalWarn.getStatisticalWarns().sort(
					getAscendingComparator());
		}
		return new ArrayList<LowBatteryStatisticalWarn>(statisticWarns.values());
	}
}
