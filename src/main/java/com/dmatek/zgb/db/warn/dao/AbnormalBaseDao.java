package com.dmatek.zgb.db.warn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.AbnormalBaseWarn;

public interface AbnormalBaseDao {
	boolean addAbnormalBase(@Param("abnormalBaseWarn") AbnormalBaseWarn abnormalBaseWarn) throws Exception;
	boolean deleteAbnormalBase(@Param("uuid") String uuid) throws Exception;
	AbnormalBaseWarn findOne(@Param("uuid") String uuid) throws Exception;
	List<AbnormalBaseWarn> findAll() throws Exception;
	WarnTypeCardReportInfo findWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;
	List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;

	void clearAbnormalBases(@Param("days") int days) throws Exception;
}
