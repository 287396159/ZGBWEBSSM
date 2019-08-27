package com.dmatek.zgb.report.warn.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.thymeleaf.util.ListUtils;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.controller.setting.params.base.ShowSettingParamTool;
import com.dmatek.zgb.db.warn.service.PersonHelpService;
import com.dmatek.zgb.report.statistical.warn.bean.PersonHelpStatisticalWarn;
import com.dmatek.zgb.warn.bean.PersonnelHelpWarn;
import com.dmatek.zgb.warn.cache.base.BaseTagWarnCache;

public class PersonHelpReportWarnTool extends BaseTagWarnReportTool<PersonnelHelpWarn, PersonHelpStatisticalWarn> {
	// 获取获取缓存记录
	private PersonHelpService personHelpService;
	public PersonHelpReportWarnTool(ShowSettingParamTool showSettingParamTool,
			BaseWarmComparator descendingComparator, BaseWarmComparator ascendingComparator,
			BaseTagWarnCache<PersonnelHelpWarn> personHelpCache,
			PersonHelpService personHelpService) {
		super(showSettingParamTool, descendingComparator, ascendingComparator,  personHelpCache);
		this.personHelpService = personHelpService;
	}
	/**
	 * 获取所有的讯息
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<PersonnelHelpWarn> getAllWarns() throws Exception {
		List<PersonnelHelpWarn> dbList = personHelpService.findAll();
		List<PersonnelHelpWarn> cacheList = getTagWarnCache().getAllWarns();
		dbList.addAll(cacheList);
		// 按时间进行排序
		ListUtils.sort(dbList, getDescendingComparator());
		return dbList;
	}
	@Override
	public List<PersonHelpStatisticalWarn> getStatisticWarns(List<PersonnelHelpWarn> matchWarns) throws Exception {
		Map<String, PersonHelpStatisticalWarn> statisticWarns = new HashMap<String, PersonHelpStatisticalWarn>();
		for (PersonnelHelpWarn personHelpWarn : matchWarns) {
			PersonHelpStatisticalWarn currentWarn = statisticWarns.get(personHelpWarn.getTagId());
			if(null == currentWarn) {
				currentWarn = new PersonHelpStatisticalWarn(personHelpWarn.getTagId());
				statisticWarns.put(personHelpWarn.getTagId(), currentWarn);
			}
			currentWarn.getStatisticalWarns().add(personHelpWarn);
		}
		Iterator<PersonHelpStatisticalWarn> iterator = statisticWarns.values().iterator();
		while (iterator.hasNext()) {
			// 单独对每一个统计的警告讯息进行排序
			PersonHelpStatisticalWarn personHelpStatisticalWarn = iterator
					.next();
			personHelpStatisticalWarn.getStatisticalWarns().sort(
					getAscendingComparator());
		}
		return new ArrayList<PersonHelpStatisticalWarn>(statisticWarns.values());
	}
}
