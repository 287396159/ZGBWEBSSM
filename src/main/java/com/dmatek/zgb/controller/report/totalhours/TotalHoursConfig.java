package com.dmatek.zgb.controller.report.totalhours;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.comparator.IComparatorTool;
import com.dmatek.zgb.db.access.service.AccessService;
import com.dmatek.zgb.db.access.work.service.AccessWorkService;
import com.dmatek.zgb.redis.cache.AccessRedisCache;
import com.dmatek.zgb.report.access.tool.IDailyAccessTools;
import com.dmatek.zgb.show.vo.AccessView;

@Configuration
public class TotalHoursConfig {
	@Bean
	public TotalHoursTool getTotalHoursTool(AccessRedisCache iRedisCache,
			AccessService accessService, AccessWorkService accessWorkService,
			IDailyAccessTools<TagAccessRecord, AccessView> dailyAccessTools,
			IComparatorTool iComparatorTool) {
		return new TotalHoursTool(iRedisCache, accessService, 
				accessWorkService, dailyAccessTools, iComparatorTool);
	}
}
