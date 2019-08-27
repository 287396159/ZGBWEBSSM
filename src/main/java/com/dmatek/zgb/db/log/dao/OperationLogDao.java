package com.dmatek.zgb.db.log.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.log.bean.OperationLog;

public interface OperationLogDao {
	boolean addOperationLog(@Param("log") OperationLog log) throws Exception;
	List<OperationLog> findAllLogs() throws Exception;
	void clearLogs(@Param("days") int days) throws Exception;
}
