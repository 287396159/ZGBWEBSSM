package com.dmatek.zgb.db.warn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.AbnormalReferWarn;

public interface AbnormalReferDao {
	boolean addAbnormalRefer(@Param("abnormalReferWarn") AbnormalReferWarn abnormalReferWarn) throws Exception;
	boolean deleteAbnormalRefer(@Param("uuid") String uuid) throws Exception;
	AbnormalReferWarn findOne(@Param("uuid") String uuid) throws Exception;
	List<AbnormalReferWarn> findAll() throws Exception;
	WarnTypeCardReportInfo findWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;
	List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;

	void clearAbnormalRefers(@Param("days") int days) throws Exception;
}
