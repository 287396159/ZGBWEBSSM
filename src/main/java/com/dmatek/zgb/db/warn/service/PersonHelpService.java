package com.dmatek.zgb.db.warn.service;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.PersonnelHelpWarn;

public interface PersonHelpService {
	boolean addPersonHelp(PersonnelHelpWarn personHelpWarn) throws Exception;
	boolean deletePersonHelp(String uuid) throws Exception;
	PersonnelHelpWarn findOne(String uuid) throws Exception;
	List<PersonnelHelpWarn> findAll() throws Exception;
	WarnTypeCardReportInfo findWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;
	List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;

	void clearPersonHelps(int days) throws Exception;
}
