package com.dmatek.zgb.db.warn.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.AbnormalBaseWarn;

public interface AbnormalBaseService {
	boolean addAbnormalBase(AbnormalBaseWarn abnormalBaseWarn) throws Exception;
	boolean deleteAbnormalBase(String uuid) throws Exception;
	AbnormalBaseWarn findOne(String uuid) throws Exception;
	List<AbnormalBaseWarn> findAll() throws Exception;
	WarnTypeCardReportInfo findWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;
	List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;

	void clearAbnormalBases(int days)throws Exception;
}
