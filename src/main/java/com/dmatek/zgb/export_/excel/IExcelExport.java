package com.dmatek.zgb.export_.excel;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dmatek.zgb.setting.vo.Result;

public interface IExcelExport<T> {
	Result exportExcel() throws Exception;
	void uploadExcel(HttpServletResponse response, HttpServletRequest request) throws Exception;
}
