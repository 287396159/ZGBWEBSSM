package com.dmatek.zgb.report.statistic.warn.tool;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.dmatek.zgb.report.statistical.warn.bean.AreaControlStatictisWarn;
import com.dmatek.zgb.warn.bean.AreaControlWarn;

@Component
public class StatisticalAreaControlPageTool extends
		BaseStatisticalTagWarnPageTool<AreaControlWarn, AreaControlStatictisWarn> {
	@Override
	protected AreaControlStatictisWarn instance(String id) {
		return new AreaControlStatictisWarn(id);
	}
	@Override
	protected void after(Map<String, AreaControlStatictisWarn> statisticMap) {
		
	}
}
