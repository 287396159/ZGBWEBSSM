package com.dmatek.zgb.db.warn.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.AbnormalTagWarn;

public interface AbnormalTagService {
	boolean addAbnormalTag(AbnormalTagWarn abnormalTagWarn) throws Exception;
	boolean deleteAbnormalTag(String uuid) throws Exception;
	AbnormalTagWarn findOne(String uuid) throws Exception;
	List<AbnormalTagWarn> findAll() throws Exception;
	WarnTypeCardReportInfo findWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;
	List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;

	void clearAbnormalTags(int days) throws Exception;
}
