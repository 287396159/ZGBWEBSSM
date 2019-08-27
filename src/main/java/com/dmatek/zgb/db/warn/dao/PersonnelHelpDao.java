package com.dmatek.zgb.db.warn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.PersonnelHelpWarn;

/**
 * 
 * @author Administrator
 * @data 2019年1月19日 上午11:45:12
 * @Description
 */
public interface PersonnelHelpDao {
	boolean addPersonHelp(@Param("personHelpWarn") PersonnelHelpWarn personHelpWarn) throws Exception;
	boolean deletePersonHelp(@Param("uuid") String uuid) throws Exception;
	PersonnelHelpWarn findOne(@Param("uuid") String uuid) throws Exception;
	List<PersonnelHelpWarn> findAll() throws Exception;
	
	WarnTypeCardReportInfo findWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;
	List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;

	void clearPersonHelps(@Param("days") int days) throws Exception;
}
