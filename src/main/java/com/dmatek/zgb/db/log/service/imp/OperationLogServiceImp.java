package com.dmatek.zgb.db.log.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmatek.zgb.db.log.dao.OperationLogDao;
import com.dmatek.zgb.db.log.service.OperationLogService;
import com.dmatek.zgb.log.bean.OperationLog;

@Service("logService")
public class OperationLogServiceImp implements OperationLogService {
	@Autowired
	private OperationLogDao operationLogDao;
	@Override
	public boolean addOperationLog(OperationLog log) throws Exception {
		if(null != log) {
			return operationLogDao.addOperationLog(log);
		}
		return false;
	}
	@Override
	public List<OperationLog> findAllLogs() throws Exception {
		return operationLogDao.findAllLogs();
	}
	@Override
	public void clearLogs(int days) throws Exception {
		operationLogDao.clearLogs(days);
	}
}
