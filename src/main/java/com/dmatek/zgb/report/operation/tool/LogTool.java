package com.dmatek.zgb.report.operation.tool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.util.Strings;

import com.dmatek.zgb.db.log.service.OperationLogService;
import com.dmatek.zgb.log.bean.OperationLog;

public class LogTool {
	private OperationLogService logService;
	private SimpleDateFormat simpleDateFormat;
	public LogTool(OperationLogService logService) {
		super();
		this.logService = logService;
		this.simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	public List<OperationLog> searchLogs(String userName, String sStart,String sEnd) throws Exception {
		List<OperationLog> searchLogs = new ArrayList<OperationLog>();
		List<OperationLog> allLogs = logService.findAllLogs();
		Date start = Strings.isEmpty(sStart)?null:getSimpleDateFormat().parse(sStart);
		Date end = Strings.isEmpty(sEnd)?null:getSimpleDateFormat().parse(sEnd);
		for (OperationLog operationLog : allLogs) {
			boolean isUserName = Strings.isEmpty(userName) || userName.equalsIgnoreCase(operationLog.getUserName());
			boolean isInterval = ((null == start || null == end)) 
					|| (start.compareTo(operationLog.getLogTime()) <= 0 && end.compareTo(operationLog.getLogTime()) >= 0);
			if(isUserName && isInterval){
				searchLogs.add(operationLog);
			}
		}
		return searchLogs;
	}
	public OperationLogService getLogService() {
		return logService;
	}
	public void setLogService(OperationLogService logService) {
		this.logService = logService;
	}
	public SimpleDateFormat getSimpleDateFormat() {
		return simpleDateFormat;
	}
	public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
		this.simpleDateFormat = simpleDateFormat;
	}
}
