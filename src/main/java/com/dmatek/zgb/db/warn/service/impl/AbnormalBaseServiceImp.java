package com.dmatek.zgb.db.warn.service.impl;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmatek.zgb.db.warn.dao.AbnormalBaseDao;
import com.dmatek.zgb.db.warn.service.AbnormalBaseService;
import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.AbnormalBaseWarn;
@Service("abnormalBaseService")
public class AbnormalBaseServiceImp implements AbnormalBaseService {
	@Autowired
	private AbnormalBaseDao abnormalBaseDao;
	@Override
	public boolean addAbnormalBase(AbnormalBaseWarn abnormalBaseWarn)
			throws Exception {
		if(null != abnormalBaseWarn){
			return abnormalBaseDao.addAbnormalBase(abnormalBaseWarn);
		}
		return false;
	}

	@Override
	public boolean deleteAbnormalBase(String uuid) throws Exception {
		if(!Strings.isEmpty(uuid)){
			return abnormalBaseDao.deleteAbnormalBase(uuid);
		}
		return false;
	}


	@Override
	public AbnormalBaseWarn findOne(String uuid) throws Exception {
		if(!Strings.isEmpty(uuid)){
			return abnormalBaseDao.findOne(uuid);
		}
		return null;
	}

	@Override
	public List<AbnormalBaseWarn> findAll() throws Exception {
		return abnormalBaseDao.findAll();
	}

	@Override
	public WarnTypeCardReportInfo findWarnCardReportInfo(int peroid)
			throws Exception {
		return abnormalBaseDao.findWarnCardReportInfo(peroid);
	}

	@Override
	public List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(int peroid)
			throws Exception {
		return abnormalBaseDao.findGroupsWarnCardReportInfo(peroid);
	}

	@Override
	public void clearAbnormalBases(int days) throws Exception {
		abnormalBaseDao.clearAbnormalBases(days);
	}
}
