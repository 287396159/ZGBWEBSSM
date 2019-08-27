package com.dmatek.zgb.report.warn.tool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmatek.zgb.comparator.AscendingWarmComparator;
import com.dmatek.zgb.comparator.DescendingWarmComparator;
import com.dmatek.zgb.controller.setting.params.base.ShowSettingParamTool;
import com.dmatek.zgb.db.warn.service.AbnormalBaseService;
import com.dmatek.zgb.db.warn.service.AbnormalReferService;
import com.dmatek.zgb.db.warn.service.AbnormalTagService;
import com.dmatek.zgb.db.warn.service.AreaControllerService;
import com.dmatek.zgb.db.warn.service.LowPowerService;
import com.dmatek.zgb.db.warn.service.NotMoveService;
import com.dmatek.zgb.db.warn.service.PersonHelpService;
import com.dmatek.zgb.warn.cache.AbnormalBaseWarnCache;
import com.dmatek.zgb.warn.cache.AbnormalReferWarnCache;
import com.dmatek.zgb.warn.cache.AbnormalTagWarnCache;
import com.dmatek.zgb.warn.cache.AreaControlWarnCache;
import com.dmatek.zgb.warn.cache.LowPowerWarnCache;
import com.dmatek.zgb.warn.cache.NotMoveWarnCache;
import com.dmatek.zgb.warn.cache.PersonnelHelpWarnCache;

@Configuration
public class ReportToolConfig {
	@Bean
	public ReportWarnTool getReportWarnTool(ShowSettingParamTool showSettingParamTool,
			PersonnelHelpWarnCache personHelpCache,
			AreaControlWarnCache areaControllerCache,
			LowPowerWarnCache lowPowerWarnCache,
			NotMoveWarnCache notMoveWarnCache,
			AbnormalTagWarnCache abnormalTagWarnCache,
			AbnormalReferWarnCache abnormalReferWarnCache,
			AbnormalBaseWarnCache abnormalBaseWarnCache,
			DescendingWarmComparator descendingWarmComparator,
			AscendingWarmComparator ascendingWarmComparator) {
		return new ReportWarnTool(showSettingParamTool, personHelpCache, 
				areaControllerCache,lowPowerWarnCache,
				notMoveWarnCache, abnormalTagWarnCache,
				abnormalReferWarnCache, abnormalBaseWarnCache, 
				descendingWarmComparator, ascendingWarmComparator);
	}
	@Bean
	public PersonHelpReportWarnTool getPersonHelpReportWarnTool(ShowSettingParamTool showSettingParamTool, 
			DescendingWarmComparator descendingWarmComparator,
			AscendingWarmComparator ascendingWarmComparator,
			PersonnelHelpWarnCache personHelpCache,
			PersonHelpService personHelpService) {
		return new PersonHelpReportWarnTool(showSettingParamTool, 
				descendingWarmComparator,ascendingWarmComparator,
				personHelpCache, personHelpService);
	}
	@Bean
	public RegionControlReportWarnTool getRegionControlReportWarnTool(ShowSettingParamTool showSettingParamTool, 
			DescendingWarmComparator descendingWarmComparator,
			AscendingWarmComparator ascendingWarmComparator,
			AreaControlWarnCache areaControlWarnCache,
			AreaControllerService areaControlService) {
		return new RegionControlReportWarnTool(showSettingParamTool, 
				descendingWarmComparator, ascendingWarmComparator,
				areaControlWarnCache, areaControlService);
	}
	@Bean
	public NotMoveReportWarnTool getNotMoveReportWarnTool(ShowSettingParamTool showSettingParamTool, 
			DescendingWarmComparator descendingWarmComparator,
			AscendingWarmComparator ascendingWarmComparator,
			NotMoveWarnCache notMoveWarnCache,
			NotMoveService notMoveService) {
		return new NotMoveReportWarnTool(showSettingParamTool, 
				descendingWarmComparator, ascendingWarmComparator,
				notMoveWarnCache, notMoveService);
	}
	@Bean
	public LowPowerReportWarnTool getLowPowerReportWarnTool(ShowSettingParamTool showSettingParamTool,
			DescendingWarmComparator descendingWarmComparator,
			AscendingWarmComparator ascendingWarmComparator,
			LowPowerWarnCache lowPowerWarnCache,
			LowPowerService lowPowerService) {
		return new LowPowerReportWarnTool(showSettingParamTool, 
				descendingWarmComparator, ascendingWarmComparator,
				lowPowerWarnCache, lowPowerService);
	}
	@Bean
	public AbnormalTagReportWarnTool getAbnormalTagReportWarnTool(ShowSettingParamTool showSettingParamTool,
			DescendingWarmComparator descendingWarmComparator,
			AscendingWarmComparator ascendingWarmComparator,
			AbnormalTagWarnCache abnormalTagWarnCache,
			AbnormalTagService abnormalTagService) {
		return new AbnormalTagReportWarnTool(showSettingParamTool, descendingWarmComparator, 
				ascendingWarmComparator, abnormalTagWarnCache, abnormalTagService);
	}
	@Bean
	public AbnormalNodeReportWarnTool getAbnormalNodeReportWarnTool(ShowSettingParamTool showSettingParamTool,
			DescendingWarmComparator descendingWarmComparator,
			AscendingWarmComparator ascendingWarmComparator,
			AbnormalBaseWarnCache abnormalBaseWarnCache,
			AbnormalBaseService abnormalBaseService) {
		return new AbnormalNodeReportWarnTool(showSettingParamTool, 
				descendingWarmComparator, ascendingWarmComparator,
				abnormalBaseWarnCache, abnormalBaseService);
	}
	@Bean
	public AbnormalReferReportWarnTool getAbnormalReferReportWarnTool(ShowSettingParamTool showSettingParamTool,
			DescendingWarmComparator descendingWarmComparator,
			AscendingWarmComparator ascendingWarmComparator,
			AbnormalReferWarnCache abnormalReferWarnCache,
			AbnormalReferService abnormalReferService){
		return new AbnormalReferReportWarnTool(showSettingParamTool, 
				descendingWarmComparator, ascendingWarmComparator,
				abnormalReferWarnCache, abnormalReferService);
	}
}
