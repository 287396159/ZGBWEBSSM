package com.dmatek.zgb.db.warn.service.impl;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmatek.zgb.db.warn.dao.LowPowerDao;
import com.dmatek.zgb.db.warn.service.LowPowerService;
import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.LowPowerWarn;
@Service("lowPowerService")
public class LowPowerServiceImp implements LowPowerService{
	@Autowired
	private LowPowerDao lowPowerDao;
	@Override
	public boolean addLowPower(LowPowerWarn lowPowerWarn) throws Exception {
		if(null != lowPowerWarn){
			return lowPowerDao.addLowPower(lowPowerWarn);
		}
		return false;
	}

	@Override
	public boolean deleteLowPower(String uuid) throws Exception {
		if(!Strings.isEmpty(uuid)){
			return lowPowerDao.deleteLowPower(uuid);
		}
		return false;
	}

	@Override
	public LowPowerWarn findOne(String uuid) throws Exception {
		if(!Strings.isEmpty(uuid)){
			return lowPowerDao.findOne(uuid);
		}
		return null;
	}

	@Override
	public List<LowPowerWarn> findAll() throws Exception {
		return lowPowerDao.findAll();
	}

	@Override
	public WarnTypeCardReportInfo findWarnCardReportInfo(int peroid)
			throws Exception {
		// TODO Auto-generated method stub
		return lowPowerDao.findWarnCardReportInfo(peroid);
	}

	@Override
	public List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(int peroid)
			throws Exception {
		// TODO Auto-generated method stub
		return lowPowerDao.findGroupsWarnCardReportInfo(peroid);
	}

	@Override
	public void clearLowPowers(int days) throws Exception {
		lowPowerDao.clearLowPowers(days);
	}
}
