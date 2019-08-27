package com.dmatek.zgb.db.log.service;

import java.util.List;

import com.dmatek.zgb.log.bean.OperationLog;

public interface OperationLogService {
	boolean addOperationLog(OperationLog log) throws Exception;
	List<OperationLog> findAllLogs() throws Exception;
	void clearLogs(int days) throws Exception;
}
