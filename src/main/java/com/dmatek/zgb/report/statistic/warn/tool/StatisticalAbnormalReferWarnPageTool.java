package com.dmatek.zgb.report.statistic.warn.tool;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.dmatek.zgb.report.statistical.warn.bean.AbnormalReferStatictisWarn;
import com.dmatek.zgb.warn.bean.AbnormalReferWarn;
@Component
public class StatisticalAbnormalReferWarnPageTool extends
		BaseStatisticalBaseWarnPageTool<AbnormalReferWarn, AbnormalReferStatictisWarn> {
	@Override
	protected AbnormalReferStatictisWarn instance(String id) {
		return new AbnormalReferStatictisWarn(id);
	}
	@Override
	protected void after(Map<String, AbnormalReferStatictisWarn> statisticMap) {
	}
}
