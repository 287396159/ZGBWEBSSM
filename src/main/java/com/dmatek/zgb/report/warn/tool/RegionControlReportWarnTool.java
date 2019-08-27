package com.dmatek.zgb.report.warn.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.thymeleaf.util.ListUtils;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.controller.setting.params.base.ShowSettingParamTool;
import com.dmatek.zgb.db.warn.service.AreaControllerService;
import com.dmatek.zgb.report.statistical.warn.bean.AreaControlStatictisWarn;
import com.dmatek.zgb.warn.bean.AreaControlWarn;
import com.dmatek.zgb.warn.cache.base.BaseTagWarnCache;

public class RegionControlReportWarnTool extends BaseTagWarnReportTool<AreaControlWarn, AreaControlStatictisWarn>{
	private AreaControllerService areaControllerService;
	public RegionControlReportWarnTool(
			ShowSettingParamTool showSettingParamTool,
			BaseWarmComparator descendingComparator, BaseWarmComparator ascendingComparator,
			BaseTagWarnCache<AreaControlWarn> tagWarnCache,
			AreaControllerService areaControllerService) {
		super(showSettingParamTool, descendingComparator, ascendingComparator, tagWarnCache);
		this.areaControllerService = areaControllerService;
	}
	@Override
	public List<AreaControlWarn> getAllWarns() throws Exception {
		List<AreaControlWarn> dbList = areaControllerService.findAll();
		List<AreaControlWarn> cacheList = getTagWarnCache().getAllWarns();
		dbList.addAll(cacheList);
		// 按时间进行排序
		ListUtils.sort(dbList, getDescendingComparator());
		return dbList;
	}
	@Override
	public List<AreaControlStatictisWarn> getStatisticWarns(List<AreaControlWarn> matchWarns) throws Exception {
		Map<String, AreaControlStatictisWarn> statisticWarns = new HashMap<String, AreaControlStatictisWarn>();
		for (AreaControlWarn areaControlWarn : matchWarns) {
			AreaControlStatictisWarn currentWarn = statisticWarns.get(areaControlWarn.getTagId());
			if(null == currentWarn) {
				currentWarn = new AreaControlStatictisWarn(areaControlWarn.getTagId());
				statisticWarns.put(areaControlWarn.getTagId(), currentWarn);
			}
			currentWarn.getStatisticalWarns().add(areaControlWarn);
		}
		Iterator<AreaControlStatictisWarn> iterator = statisticWarns.values()
				.iterator();
		while(iterator.hasNext()){
			AreaControlStatictisWarn areaControlStatictisWarn = iterator.next();
			areaControlStatictisWarn.getStatisticalWarns().sort(
					getAscendingComparator());
		}
		return new ArrayList<AreaControlStatictisWarn>(statisticWarns.values());
	}
}
