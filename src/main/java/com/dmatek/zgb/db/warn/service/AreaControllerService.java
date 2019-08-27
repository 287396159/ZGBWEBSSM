package com.dmatek.zgb.db.warn.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.AreaControlWarn;

public interface AreaControllerService {
	boolean addAreaController(AreaControlWarn areaControlWarn) throws Exception;
	boolean deleteAreaController(String uuid) throws Exception;
	AreaControlWarn findOne(String uuid) throws Exception;
	List<AreaControlWarn> findAll() throws Exception;
	WarnTypeCardReportInfo findWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;
	List<GroupCardReportWarnInfo> findGroupsWarnCardReportInfo(@Param("peroid") int peroid) throws Exception;
	/*--清除区域管制--*/
	void clearAreaControllers(int days) throws Exception;
}
