package com.dmatek.zgb.db.warn.service.impl;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmatek.zgb.db.warn.dao.AreaControllerDao;
import com.dmatek.zgb.db.warn.service.AreaControllerService;
import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.AreaControlWarn;
@Service("areaControllerService")
public class AreaControllerServiceImp implements AreaControllerService {
	@Autowired
	private AreaControllerDao areaControllerDao;
	@Override
	public boolean addAreaController(AreaControlWarn areaControlWarn)
			throws Exception {
		if(null != areaControlWarn){
			return areaControllerDao.addAreaController(areaControlWarn);
		}
		return false;
	}
	@Override
	public boolean deleteAreaController(String uuid) throws Exception {
		if(!Strings.isEmpty(uuid)){
			return areaControllerDao.deleteAreaController(uuid);
		}
		return false;
	}
	@Override
	public AreaControlWarn findOne(String uuid) throws Exception {
		if(!Strings.isEmpty(uuid)){
			return areaControllerDao.findOne(uuid);
		}
		return null;
	}
	@Override
	public List<AreaControlWarn> findAll() throws Exception {
		return areaControllerDao.findAll();
	}
	@Override
	public WarnTypeCardReportInfo findWarnCardReportInfo(int peroid)
			throws Exception {
		return areaControllerDao.findWarnCardReportInfo(peroid);
	}
	@Override
	public List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(int peroid)
			throws Exception {
		return areaControllerDao.findGroupsWarnCardReportInfo(peroid);
	}
	@Override
	public void clearAreaControllers(int days) throws Exception {
		areaControllerDao.clearAreaControllers(days);
	}
}
