package com.dmatek.zgb.db.warn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.LowPowerWarn;

public interface LowPowerDao {
	boolean addLowPower(@Param("lowPowerWarn") LowPowerWarn lowPowerWarn) throws Exception;
	boolean deleteLowPower(@Param("uuid") String uuid) throws Exception;
	LowPowerWarn findOne(@Param("uuid") String uuid) throws Exception;
	List<LowPowerWarn> findAll() throws Exception;
	WarnTypeCardReportInfo findWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;
	List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;

	void clearLowPowers(@Param("days") int days) throws Exception;
}
