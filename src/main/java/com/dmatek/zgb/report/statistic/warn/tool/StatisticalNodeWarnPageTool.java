package com.dmatek.zgb.report.statistic.warn.tool;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.dmatek.zgb.report.statistical.warn.bean.AbnormalBaseStatictisWarn;
import com.dmatek.zgb.warn.bean.AbnormalBaseWarn;

@Component
public class StatisticalNodeWarnPageTool extends
		BaseStatisticalBaseWarnPageTool<AbnormalBaseWarn, AbnormalBaseStatictisWarn> {
	@Override
	protected void after(Map<String, AbnormalBaseStatictisWarn> statisticMap) {
	}
	@Override
	protected AbnormalBaseStatictisWarn instance(String id) {
		return new AbnormalBaseStatictisWarn(id);
	}
}
