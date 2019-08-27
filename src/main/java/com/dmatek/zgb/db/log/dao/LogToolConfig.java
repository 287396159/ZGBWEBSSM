package com.dmatek.zgb.db.log.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.dmatek.zgb.db.log.service.OperationLogService;
import com.dmatek.zgb.report.operation.tool.LogTool;

@Configuration
public class LogToolConfig {
	@Bean
	public LogTool getLogTool(OperationLogService logService) {
		return new LogTool(logService);
	}
}
