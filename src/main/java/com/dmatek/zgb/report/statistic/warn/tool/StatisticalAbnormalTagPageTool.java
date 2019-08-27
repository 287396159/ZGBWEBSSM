package com.dmatek.zgb.report.statistic.warn.tool;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.dmatek.zgb.report.statistical.warn.bean.AbnormalTagStatictisWarn;
import com.dmatek.zgb.warn.bean.AbnormalTagWarn;

@Component
public class StatisticalAbnormalTagPageTool extends
		BaseStatisticalTagWarnPageTool<AbnormalTagWarn, AbnormalTagStatictisWarn> {
	@Override
	protected AbnormalTagStatictisWarn instance(String id) {
		return new AbnormalTagStatictisWarn(id);
	}
	@Override
	protected void after(Map<String, AbnormalTagStatictisWarn> statisticMap) {
	}
}
