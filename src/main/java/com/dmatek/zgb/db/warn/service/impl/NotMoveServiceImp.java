package com.dmatek.zgb.db.warn.service.impl;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmatek.zgb.db.warn.dao.NotMoveDao;
import com.dmatek.zgb.db.warn.service.NotMoveService;
import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.NotMoveWarn;

@Service("notMoveService")
public class NotMoveServiceImp implements NotMoveService{
	@Autowired
	private NotMoveDao notMoveDao;
	@Override
	public boolean addNotMove(NotMoveWarn notMoveWarn) throws Exception {
		if(null != notMoveWarn){
			return notMoveDao.addNotMove(notMoveWarn);
		}
		return false;
	}
	@Override
	public boolean deleteNotMove(String uuid) throws Exception {
		if(!Strings.isEmpty(uuid)){
			return notMoveDao.deleteNotMove(uuid);
		}
		return false;
	}

	@Override
	public NotMoveWarn findOne(String uuid) throws Exception {
		if(!Strings.isEmpty(uuid)){
			return notMoveDao.findOne(uuid);
		}
		return null;
	}

	@Override
	public List<NotMoveWarn> findAll() throws Exception {
		return notMoveDao.findAll();
	}
	@Override
	public WarnTypeCardReportInfo findWarnCardReportInfo(int peroid)
			throws Exception {
		return notMoveDao.findWarnCardReportInfo(peroid);
	}
	@Override
	public List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(int peroid)
			throws Exception {
		return notMoveDao.findGroupsWarnCardReportInfo(peroid);
	}
	@Override
	public void clearNotMoves(int days) throws Exception {
		notMoveDao.clearNotMoves(days);
	}
}
