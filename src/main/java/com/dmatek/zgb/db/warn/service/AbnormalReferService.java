package com.dmatek.zgb.db.warn.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.AbnormalReferWarn;

public interface AbnormalReferService {
	boolean addAbnormalRefer(AbnormalReferWarn abnormalReferWarn) throws Exception;
	boolean deleteAbnormalRefer(String uuid) throws Exception;
	AbnormalReferWarn findOne(String uuid) throws Exception;
	List<AbnormalReferWarn> findAll() throws Exception;
	WarnTypeCardReportInfo findWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;
	List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;

	void clearAbnormalRefers(int days) throws Exception;
}
