package com.dmatek.zgb.report.access.tool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.comparator.IComparatorTool;
import com.dmatek.zgb.db.access.service.AccessService;
import com.dmatek.zgb.db.access.work.service.AccessWorkService;
import com.dmatek.zgb.redis.cache.AccessRedisCache;
import com.dmatek.zgb.show.vo.AccessView;

@Configuration
public class AccessReportToolConfig {
	@Bean
	public IDailyAccessTools<TagAccessRecord, AccessView> getIDailyAccessTools() {
		return new DailyAccessTools();
	}
	@Bean(name="accessReportTool")
	public AccessReportTool getAccessReportTool(AccessRedisCache iRedisCache,
			AccessService accessService, 
			AccessWorkService accessWorkService,
			IDailyAccessTools<TagAccessRecord, AccessView> dailyAccessTools,
			IComparatorTool iComparatorTool){
		return new AccessReportTool(iRedisCache, accessService, 
					accessWorkService, dailyAccessTools,
					iComparatorTool);
	}
}
