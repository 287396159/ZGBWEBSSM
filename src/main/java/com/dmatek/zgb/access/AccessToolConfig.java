package com.dmatek.zgb.access;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.dmatek.zgb.access.base.IWorkAccess;
import com.dmatek.zgb.access.manager.AccessManager;
import com.dmatek.zgb.access.manager.scan.ScanAccessManagerTask;
import com.dmatek.zgb.access.onduty.workaccess.OffDutyWorkAccess;
import com.dmatek.zgb.access.onduty.workaccess.OnDutyWorkAccess;
import com.dmatek.zgb.comparator.DescAccessComparator;
import com.dmatek.zgb.controller.setting.params.base.SystemSettingParamTool;
import com.dmatek.zgb.db.access.service.AccessService;
import com.dmatek.zgb.db.access.work.service.AccessWorkService;
import com.dmatek.zgb.db.person.service.PersonService;
import com.dmatek.zgb.db.setting.service.CompanyService;
import com.dmatek.zgb.db.setting.service.GroupService;
import com.dmatek.zgb.db.setting.service.JobTypeService;
import com.dmatek.zgb.db.setting.service.NodeService;
import com.dmatek.zgb.db.setting.service.RegionService;
import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.redis.cache.AccessRedisCache;
import com.dmatek.zgb.redis.cache.IRedisMapCache;

/**
 *
 * @author zhangfu
 * @data 2019年1月22日 上午9:41:26
 * @Description 进出统计的配置文件
 */
@Configuration
public class AccessToolConfig {
	@Bean("onDutyWorkAccess")
	public IWorkAccess<TagDetail> getOnDutyWorkAccess(AccessWorkService accessWorkService) {
		return new OnDutyWorkAccess(accessWorkService);
	}
	@Bean("offDutyWorkAccess")
	public OffDutyWorkAccess getOffDutyWorkAccess(AccessWorkService accessWorkService, 
			IRedisMapCache<String, TagDetail> tagDetailsRedisCache) {
		return new OffDutyWorkAccess(accessWorkService, tagDetailsRedisCache);
	}
	@Bean("accessTool")
	public AccessTool getAccessManager(AccessRedisCache iRedisCache,
			IAccessDetailCapture iAccessDetailCapture,
			DescAccessComparator baseAccessComparator, 
			AccessService accessService,
			AccessManager accessManager,
			ScanAccessManagerTask scanAccessManagerTask,
			SystemSettingParamTool sysSettingTool) {
		return new AccessTool(iRedisCache, iAccessDetailCapture,
							  baseAccessComparator, accessService,
							  accessManager, scanAccessManagerTask,
							  sysSettingTool);
	}
	@Bean
	public TagDetailCapture getTagDetailCapture(PersonService personService, CompanyService companyService, 
			JobTypeService jobTypeService, NodeService nodeService, GroupService groupService,RegionService regionService){
		return new TagDetailCapture(personService, companyService,
				jobTypeService, nodeService, groupService, regionService);
	}	
	@Bean
	public IAccessDetailCapture getAccessDetailCapture(TagDetailCapture tagDetailCapture) {
		return new AccessDetailCapture(tagDetailCapture);
	}
}
