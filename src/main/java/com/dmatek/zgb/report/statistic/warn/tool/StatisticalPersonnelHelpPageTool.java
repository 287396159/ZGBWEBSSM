package com.dmatek.zgb.report.statistic.warn.tool;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.dmatek.zgb.report.statistical.warn.bean.PersonHelpStatisticalWarn;
import com.dmatek.zgb.warn.bean.PersonnelHelpWarn;

@Component
public class StatisticalPersonnelHelpPageTool extends
		BaseStatisticalTagWarnPageTool<PersonnelHelpWarn, PersonHelpStatisticalWarn> {
	@Override
	protected PersonHelpStatisticalWarn instance(String id) {
		return new PersonHelpStatisticalWarn(id);
	}
	@Override
	protected void after(Map<String, PersonHelpStatisticalWarn> statisticMap) {
		
	}
}
