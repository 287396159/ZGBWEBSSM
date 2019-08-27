package com.dmatek.zgb.db.warn.service.impl;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmatek.zgb.db.warn.dao.AbnormalReferDao;
import com.dmatek.zgb.db.warn.service.AbnormalReferService;
import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.AbnormalReferWarn;

@Service("abnormalReferService")
public class AbnormalReferServiceImp implements AbnormalReferService {
	@Autowired
	private AbnormalReferDao abnormalReferDao;
	@Override
	public boolean addAbnormalRefer(AbnormalReferWarn abnormalReferWarn)
			throws Exception {
		if(null != abnormalReferWarn){
			return abnormalReferDao.addAbnormalRefer(abnormalReferWarn);
		}
		return false;
	}
	@Override
	public boolean deleteAbnormalRefer(String uuid) throws Exception {
		if(!Strings.isEmpty(uuid)){
			return abnormalReferDao.deleteAbnormalRefer(uuid);
		}
		return false;
	}
	@Override
	public AbnormalReferWarn findOne(String uuid) throws Exception {
		if(!Strings.isEmpty(uuid)){
			return abnormalReferDao.findOne(uuid);
		}
		return null;
	}
	@Override
	public List<AbnormalReferWarn> findAll() throws Exception {
		return abnormalReferDao.findAll();
	}
	@Override
	public WarnTypeCardReportInfo findWarnCardReportInfo(int peroid)
			throws Exception {
		return abnormalReferDao.findWarnCardReportInfo(peroid);
	}
	@Override
	public List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(int peroid)
			throws Exception {
		return abnormalReferDao.findGroupsWarnCardReportInfo(peroid);
	}
	@Override
	public void clearAbnormalRefers(int days) throws Exception {
		abnormalReferDao.clearAbnormalRefers(days);
	}
}
