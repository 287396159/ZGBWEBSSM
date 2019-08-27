package com.dmatek.zgb.db.warn.service.impl;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmatek.zgb.db.warn.dao.AbnormalTagDao;
import com.dmatek.zgb.db.warn.service.AbnormalTagService;
import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.AbnormalTagWarn;
@Service("abnormalTagService")
public class AbnormalTagServiceImp implements AbnormalTagService {
	@Autowired
	private AbnormalTagDao abnormalTagDao;
	@Override
	public boolean addAbnormalTag(AbnormalTagWarn abnormalTagWarn)
			throws Exception {
		if(null != abnormalTagWarn){
			return abnormalTagDao.addAbnormalTag(abnormalTagWarn);
		}
		return false;
	}
	@Override
	public boolean deleteAbnormalTag(String uuid) throws Exception {
		if(!Strings.isEmpty(uuid)){
			return abnormalTagDao.deleteAbnormalTag(uuid);
		}
		return false;
	}

	@Override
	public AbnormalTagWarn findOne(String uuid) throws Exception {
		if(!Strings.isEmpty(uuid)){
			return abnormalTagDao.findOne(uuid);
		}
		return null;
	}
	@Override
	public List<AbnormalTagWarn> findAll() throws Exception {
		return abnormalTagDao.findAll();
	}
	@Override
	public WarnTypeCardReportInfo findWarnCardReportInfo(int peroid)
			throws Exception {
		return abnormalTagDao.findWarnCardReportInfo(peroid);
	}
	@Override
	public List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(int peroid)
			throws Exception {
		return abnormalTagDao.findGroupsWarnCardReportInfo(peroid);
	}
	@Override
	public void clearAbnormalTags(int days) throws Exception {
		abnormalTagDao.clearAbnormalTags(days);
	}
}
