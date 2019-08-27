package com.dmatek.zgb.db.warn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.NotMoveWarn;

public interface NotMoveDao {
	boolean addNotMove(@Param("notMoveWarn") NotMoveWarn notMoveWarn) throws Exception;
	boolean deleteNotMove(@Param("uuid") String uuid) throws Exception;
	NotMoveWarn findOne(@Param("uuid") String uuid) throws Exception;
	List<NotMoveWarn> findAll() throws Exception;
	WarnTypeCardReportInfo findWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;
	List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;
	
	void clearNotMoves(@Param("days") int days) throws Exception;
}
