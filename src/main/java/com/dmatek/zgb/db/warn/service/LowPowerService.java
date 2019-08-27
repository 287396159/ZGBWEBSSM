package com.dmatek.zgb.db.warn.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.LowPowerWarn;

public interface LowPowerService {
	boolean addLowPower(LowPowerWarn lowPowerWarn) throws Exception;
	boolean deleteLowPower(String uuid) throws Exception;
	LowPowerWarn findOne(String uuid) throws Exception;
	List<LowPowerWarn> findAll() throws Exception;
	WarnTypeCardReportInfo findWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;
	List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;
	
	void clearLowPowers(int days) throws Exception;
}
