package com.dmatek.zgb.report.warn.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.thymeleaf.util.ListUtils;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.controller.setting.params.base.ShowSettingParamTool;
import com.dmatek.zgb.db.warn.service.AbnormalBaseService;
import com.dmatek.zgb.report.statistical.warn.bean.AbnormalBaseStatictisWarn;
import com.dmatek.zgb.warn.bean.AbnormalBaseWarn;
import com.dmatek.zgb.warn.cache.base.BaseNodeWarnCache;

public class AbnormalNodeReportWarnTool extends BaseNodeWarnReportTool<AbnormalBaseWarn, AbnormalBaseStatictisWarn>{
	private AbnormalBaseService abnormalBaseService;
	public AbnormalNodeReportWarnTool(
			ShowSettingParamTool showSettingParamTool,
			BaseWarmComparator descendingComparator,BaseWarmComparator ascendingComparator,
			BaseNodeWarnCache<AbnormalBaseWarn> baseWarnCache, 
			AbnormalBaseService abnormalBaseService) {
		super(showSettingParamTool, descendingComparator, ascendingComparator , baseWarnCache);
		this.abnormalBaseService = abnormalBaseService;
	}
	@Override
	public List<AbnormalBaseWarn> getAllWarns() throws Exception {
		List<AbnormalBaseWarn> dbList = abnormalBaseService.findAll();
		List<AbnormalBaseWarn> cacheList = getBaseWarnCache().getAllWarns();
		dbList.addAll(cacheList);
		// 按时间进行排序
		ListUtils.sort(dbList, getDescendingComparator());
		return dbList;
	}
	public AbnormalBaseService getAbnormalBaseService() {
		return abnormalBaseService;
	}
	public void setAbnormalBaseService(AbnormalBaseService abnormalBaseService) {
		this.abnormalBaseService = abnormalBaseService;
	}
	@Override
	public List<AbnormalBaseStatictisWarn> getStatisticWarns(List<AbnormalBaseWarn> matchWarns) throws Exception {
		Map<String, AbnormalBaseStatictisWarn> statisticWarns = new HashMap<String, AbnormalBaseStatictisWarn>();
		for (AbnormalBaseWarn abnormalBaseWarn : matchWarns) {
			AbnormalBaseStatictisWarn currentWarn = statisticWarns
					.get(abnormalBaseWarn.getNodeId());
			if (null == currentWarn) {
				currentWarn = new AbnormalBaseStatictisWarn(
						abnormalBaseWarn.getNodeId());
				statisticWarns.put(abnormalBaseWarn.getNodeId(), currentWarn);
			}
			currentWarn.getStatisticalWarns().add(abnormalBaseWarn);
		}
		Iterator<AbnormalBaseStatictisWarn> iterator = statisticWarns.values()
				.iterator();
		while (iterator.hasNext()) {
			// 单独对每一个统计的警告讯息进行排序
			AbnormalBaseStatictisWarn abnormalBaseStatictisWarn = iterator
					.next();
			abnormalBaseStatictisWarn.getStatisticalWarns().sort(
					getAscendingComparator());
		}
		return new ArrayList<AbnormalBaseStatictisWarn>(statisticWarns.values());
	}
}
