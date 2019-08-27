package com.dmatek.zgb.controller.report.totalhours;

import java.util.List;

public interface ITotalHoursTool <T> {
	/**
	 * 搜索所有工时记录
	 * @param name
	 * @param companyNo
	 * @param jobTypeNo
	 * @param groupId
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	List<T> searchTotalHours(String name, String companyNo, 
			String jobTypeNo, int groupId, String start, String end) throws Exception;
	/**
	 * 打印所有的工时记录
	 * @param name
	 * @param companyNo
	 * @param jobTypeNo
	 * @param groupId
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	List<T> printTotalHours(String name, String companyNo, 
			String jobTypeNo, int groupId, String start, String end) throws Exception;
}
