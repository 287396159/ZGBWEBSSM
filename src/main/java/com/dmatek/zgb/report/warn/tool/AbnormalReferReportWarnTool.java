package com.dmatek.zgb.report.warn.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.thymeleaf.util.ListUtils;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.controller.setting.params.base.ShowSettingParamTool;
import com.dmatek.zgb.db.warn.service.AbnormalReferService;
import com.dmatek.zgb.report.statistical.warn.bean.AbnormalReferStatictisWarn;
import com.dmatek.zgb.warn.bean.AbnormalReferWarn;
import com.dmatek.zgb.warn.cache.base.BaseNodeWarnCache;

public class AbnormalReferReportWarnTool extends BaseNodeWarnReportTool<AbnormalReferWarn, AbnormalReferStatictisWarn>{
	private AbnormalReferService abnormalReferService;
	public AbnormalReferReportWarnTool(
			ShowSettingParamTool showSettingParamTool,
			BaseWarmComparator descendingComparator,BaseWarmComparator ascendingComparator,
			BaseNodeWarnCache<AbnormalReferWarn> baseWarnCache,
			AbnormalReferService abnormalReferService) {
		super(showSettingParamTool, descendingComparator, ascendingComparator,  baseWarnCache);
		this.abnormalReferService = abnormalReferService;
	}
	@Override
	public List<AbnormalReferWarn> getAllWarns() throws Exception {
		List<AbnormalReferWarn> dbList = abnormalReferService.findAll();
		List<AbnormalReferWarn> cacheList = getBaseWarnCache().getAllWarns();
		dbList.addAll(cacheList);
		// 按时间进行排序
		ListUtils.sort(dbList, getDescendingComparator());
		return dbList;
	}
	public AbnormalReferService getAbnormalReferService() {
		return abnormalReferService;
	}
	public void setAbnormalReferService(AbnormalReferService abnormalReferService) {
		this.abnormalReferService = abnormalReferService;
	}
	@Override
	public List<AbnormalReferStatictisWarn> getStatisticWarns(List<AbnormalReferWarn> matchWarns)
			throws Exception {
		Map<String, AbnormalReferStatictisWarn> statisticWarns = new HashMap<String, AbnormalReferStatictisWarn>();
		for (AbnormalReferWarn abnormalReferWarn : matchWarns) {
			AbnormalReferStatictisWarn currentWarn = statisticWarns
					.get(abnormalReferWarn.getNodeId());
			if (null == currentWarn) {
				currentWarn = new AbnormalReferStatictisWarn(
						abnormalReferWarn.getNodeId());
				statisticWarns.put(abnormalReferWarn.getNodeId(), currentWarn);
			}
			currentWarn.getStatisticalWarns().add(abnormalReferWarn);
		}
		Iterator<AbnormalReferStatictisWarn> iterator = statisticWarns.values()
				.iterator();
		while (iterator.hasNext()) {
			// 单独对每一个统计的警告讯息进行排序
			AbnormalReferStatictisWarn abnormalBaseStatictisWarn = iterator
					.next();
			abnormalBaseStatictisWarn.getStatisticalWarns().sort(
					getAscendingComparator());
		}
		return new ArrayList<AbnormalReferStatictisWarn>(statisticWarns.values());
	}
}
