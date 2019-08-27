package com.dmatek.zgb.report.statistic.warn.tool;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.dmatek.zgb.report.statistical.warn.bean.LowBatteryStatisticalWarn;
import com.dmatek.zgb.warn.bean.LowPowerWarn;

@Component
public class StatisticalLowerPowerPageTool extends
		BaseStatisticalTagWarnPageTool<LowPowerWarn, LowBatteryStatisticalWarn> {
	@Override
	protected LowBatteryStatisticalWarn instance(String id) {
		return new LowBatteryStatisticalWarn(id);
	}
	@Override
	protected void after(Map<String, LowBatteryStatisticalWarn> statisticMap) {
		
	}
}
