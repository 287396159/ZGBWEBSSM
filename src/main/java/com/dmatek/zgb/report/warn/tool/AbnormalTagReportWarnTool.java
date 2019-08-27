package com.dmatek.zgb.report.warn.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.thymeleaf.util.ListUtils;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.controller.setting.params.base.ShowSettingParamTool;
import com.dmatek.zgb.db.warn.service.AbnormalTagService;
import com.dmatek.zgb.report.statistical.warn.bean.AbnormalTagStatictisWarn;
import com.dmatek.zgb.warn.bean.AbnormalTagWarn;
import com.dmatek.zgb.warn.cache.base.BaseTagWarnCache;

public class AbnormalTagReportWarnTool extends BaseTagWarnReportTool<AbnormalTagWarn, AbnormalTagStatictisWarn>{
	private AbnormalTagService abnormalTagService;
	public AbnormalTagReportWarnTool(ShowSettingParamTool showSettingParamTool,
			BaseWarmComparator descendingComparator, BaseWarmComparator ascendingComparator,
			BaseTagWarnCache<AbnormalTagWarn> tagWarnCache, 
			AbnormalTagService abnormalTagService) {
		super(showSettingParamTool, descendingComparator, ascendingComparator,  tagWarnCache);
		this.abnormalTagService = abnormalTagService;
	}
	@Override
	public List<AbnormalTagWarn> getAllWarns() throws Exception {
		List<AbnormalTagWarn> dbList = abnormalTagService.findAll();
		List<AbnormalTagWarn> cacheList = getTagWarnCache().getAllWarns();
		dbList.addAll(cacheList);
		// 按时间进行排序
		ListUtils.sort(dbList, getDescendingComparator());
		return dbList;
	}
	public AbnormalTagService getAbnormalTagService() {
		return abnormalTagService;
	}
	public void setAbnormalTagService(AbnormalTagService abnormalTagService) {
		this.abnormalTagService = abnormalTagService;
	}
	@Override
	public List<AbnormalTagStatictisWarn> getStatisticWarns(List<AbnormalTagWarn> matchWarns) throws Exception {
		Map<String, AbnormalTagStatictisWarn> statisticWarns = new HashMap<String, AbnormalTagStatictisWarn>();
		for (AbnormalTagWarn abnormalTagWarn : matchWarns) {
			AbnormalTagStatictisWarn currentWarn = statisticWarns
					.get(abnormalTagWarn.getTagId());
			if (null == currentWarn) {
				currentWarn = new AbnormalTagStatictisWarn(
						abnormalTagWarn.getTagId());
				statisticWarns.put(abnormalTagWarn.getTagId(), currentWarn);
			}
			currentWarn.getStatisticalWarns().add(abnormalTagWarn);
		}
		Iterator<AbnormalTagStatictisWarn> iterator = statisticWarns.values()
				.iterator();
		while (iterator.hasNext()) {
			// 单独对每一个统计的警告讯息进行排序
			AbnormalTagStatictisWarn abnormalBaseStatictisWarn = iterator
					.next();
			abnormalBaseStatictisWarn.getStatisticalWarns().sort(
					getAscendingComparator());
		}
		return new ArrayList<AbnormalTagStatictisWarn>(statisticWarns.values());
	}
}
