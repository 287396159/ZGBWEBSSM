package com.dmatek.zgb.db.warn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.AbnormalTagWarn;

public interface AbnormalTagDao {
	boolean addAbnormalTag(@Param("abnormalTagWarn") AbnormalTagWarn abnormalTagWarn) throws Exception;
	boolean deleteAbnormalTag(@Param("uuid") String uuid) throws Exception;
	AbnormalTagWarn findOne(@Param("uuid") String uuid) throws Exception;
	List<AbnormalTagWarn> findAll() throws Exception;
	WarnTypeCardReportInfo findWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;
	List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;

	void clearAbnormalTags(@Param("days") int days) throws Exception;
}
