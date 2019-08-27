package com.dmatek.zgb.db.warn.service.impl;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmatek.zgb.db.warn.dao.PersonnelHelpDao;
import com.dmatek.zgb.db.warn.service.PersonHelpService;
import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.PersonnelHelpWarn;

@Service("personnelHelpWarnService")
public class PersonnelHelpServiceImp implements
		PersonHelpService {
	@Autowired
	private PersonnelHelpDao personHelpDao;
	@Override
	public boolean addPersonHelp(PersonnelHelpWarn personHelpWarn)
			throws Exception {
		if(null != personHelpWarn){
			return personHelpDao.addPersonHelp(personHelpWarn);
		}
		return false;
	}

	@Override
	public boolean deletePersonHelp(String uuid) throws Exception {
		if(!Strings.isEmpty(uuid)){
			return personHelpDao.deletePersonHelp(uuid);
		}
		return false;
	}
	@Override
	public PersonnelHelpWarn findOne(String uuid) throws Exception {
		if(!Strings.isEmpty(uuid)){
			return personHelpDao.findOne(uuid);
		}
		return null;
	}

	@Override
	public List<PersonnelHelpWarn> findAll() throws Exception {
		return personHelpDao.findAll();
	}

	@Override
	public WarnTypeCardReportInfo findWarnCardReportInfo(int peroid)
			throws Exception {
		return personHelpDao.findWarnCardReportInfo(peroid);
	}

	@Override
	public List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(
			int peroid) throws Exception {
		return personHelpDao.findGroupsWarnCardReportInfo(peroid);
	}

	@Override
	public void clearPersonHelps(int day) throws Exception {
		// TODO Auto-generated method stub
		personHelpDao.clearPersonHelps(day);
	}
}
