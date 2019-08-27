package com.dmatek.zgb.db.warn.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.NotMoveWarn;

public interface NotMoveService {
	boolean addNotMove(NotMoveWarn notMoveWarn) throws Exception;
	boolean deleteNotMove(String uuid) throws Exception;
	NotMoveWarn findOne(String uuid) throws Exception;
	List<NotMoveWarn> findAll() throws Exception;
	WarnTypeCardReportInfo findWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;
	List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;
	/*--清除未移动警报--*/
	void clearNotMoves(int days) throws Exception;
}
