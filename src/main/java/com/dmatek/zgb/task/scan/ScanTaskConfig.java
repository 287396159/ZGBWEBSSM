package com.dmatek.zgb.task.scan;

import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.dmatek.zgb.access.manager.AccessManager;
import com.dmatek.zgb.access.manager.scan.ScanAccessManagerTask;
import com.dmatek.zgb.controller.setting.params.base.SystemSettingParamTool;

@Configuration
public class ScanTaskConfig {
	private static final int SCANACCESSMANAGER_PEROID = 30 * 1000;
	@Resource(name="accessLock")
	private Object lock;
	@Bean
	public ScanAccessManagerTask getScanAccessManagerTask(
			AccessManager accessManager,
			SystemSettingParamTool systemSettingParamTool) {
		return new ScanAccessManagerTask(SCANACCESSMANAGER_PEROID,
				accessManager, systemSettingParamTool, lock);
	}
}
