package com.dmatek.zgb.controller.report.access;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.ListUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dmatek.zgb.comparator.AccessViewComparator;
import com.dmatek.zgb.controller.tool.page.PageTool;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.report.access.tool.AccessReportTool;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.show.vo.AccessView;

@RestController
@RequestMapping("/report/access/")
@PermissionName(name="報表輸出權限設置")
@RequiresPermissions(value="report:*")
public class AccessReportController {
	// 获取进出统计报表输出工具类
	@Resource(name="accessReportTool")
	private AccessReportTool accessReportTool;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private PageTool pageTool;
	@GetMapping("/empty")
	public Result getEmptyAccessRecords(@RequestParam("page") int page,
			@RequestParam("limit") int limit){
		return viewResultFactory.successResult(null, 0);
	}
	/**
	 * 打印进出记录讯息
	 * @throws Exception 
	 */
	@GetMapping("/print/in-outBase/")
	@PermissionName(name="上下班記錄報表輸出權限設置")
	@RequiresPermissions(value="report:access")
	public Result printAccessBaseRecords(
			@RequestParam(name="name",required=false) String name,
			@RequestParam(name="company",required=false) String companyNo, 
			@RequestParam(name="jobType",required=false) String jobTypeNo, 
			@RequestParam(name="group",required=false) int groupId,
			@RequestParam(name="start",required=false) String start,
			@RequestParam(name="end",required=false) String end) throws Exception {
		List<AccessView> allAccessRecords = accessReportTool
				.searchAllAccessBaseRecords(name, companyNo, jobTypeNo,groupId, start, end);
		List<AccessView> filterAccessRecords = accessReportTool.getDailyAccessTools().filter(allAccessRecords);
		accessReportTool.getDailyAccessTools().statisticalWorkHours(filterAccessRecords);
		return viewResultFactory.successResult(filterAccessRecords);
	}
	/**
	 * 查询所有没有经过出入基站的记录
	 * @param name
	 * @param companyNo
	 * @param jobTypeNo
	 * @param groupId
	 * @param start
	 * @param end
	 * @param page
	 * @param limit
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/print/noBase/")
	@PermissionName(name="上下班記錄報表輸出權限設置")
	@RequiresPermissions(value="report:access")
	public Result printNoAccessBaseRecords(
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "company", required = false) String companyNo,
			@RequestParam(name = "jobType", required = false) String jobTypeNo,
			@RequestParam(name = "group", required = false) int groupId,
			@RequestParam(name = "start", required = false) String start,
			@RequestParam(name = "end", required = false) String end) throws Exception {
		List<AccessView> noAccessBaseRecordViews = accessReportTool
				.searchAllNoAccessBaseRecords(name, companyNo, jobTypeNo, groupId, start, end);
		// 统计工时
		accessReportTool.getDailyAccessTools().statisticalWorkHours(noAccessBaseRecordViews);
		return viewResultFactory.successResult(noAccessBaseRecordViews);
	}
	/**
	 * 打印所有没有经过出入基站的记录
	 * @param name
	 * @param companyNo
	 * @param jobTypeNo
	 * @param groupId
	 * @param start
	 * @param end
	 * @param page
	 * @param limit
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/print/all/")
	@PermissionName(name="上下班記錄報表輸出權限設置")
	@RequiresPermissions(value="report:access")
	public Result printAllAccessBaseRecords(
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "company", required = false) String companyNo,
			@RequestParam(name = "jobType", required = false) String jobTypeNo,
			@RequestParam(name = "group", required = false) int groupId,
			@RequestParam(name = "start", required = false) String start,
			@RequestParam(name = "end", required = false) String end) throws Exception {
		// 獲取所有經過出入基站的訊息
		List<AccessView> allIn_OutAccessRecords = accessReportTool
				.searchAllAccessBaseRecords(name, companyNo, jobTypeNo,groupId, start, end);
	    List<AccessView> filterAccessRecords = accessReportTool.getDailyAccessTools()
	    		.filter(allIn_OutAccessRecords);
		// 獲取所有 沒有 經過出入基站的 訊息
		List<AccessView> noAccessBaseRecordViews = accessReportTool
				.searchAllNoAccessBaseRecords(name, companyNo, jobTypeNo, groupId, start, end);
		// 合并两次的数据
		@SuppressWarnings("unchecked")
		List<AccessView> allAccessRecords = ListUtils.union(filterAccessRecords, noAccessBaseRecordViews);
		// 对资料进行排序
		Collections.sort(allAccessRecords, accessReportTool
				.getiComparatorTool().obtain(AccessViewComparator.class));
		// 统计工时
		accessReportTool.getDailyAccessTools().statisticalWorkHours(allAccessRecords);
		return viewResultFactory.successResult(allAccessRecords);
	}
	/**
	 * 所有經過出入基站的進出記錄統計查詢
	 * @param name
	 * @param companyNo
	 * @param jobTypeNo
	 * @param groupId
	 * @param start
	 * @param end
	 * @param page
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/search/in-outBase/")
	@PermissionName(name="上下班記錄報表輸出權限設置")
	@RequiresPermissions(value="report:access")
	public Result searchAccessBaseRecords(@RequestParam(name="name",required=false) String name,
			@RequestParam(name="company",required=false) String companyNo, 
			@RequestParam(name="jobType",required=false) String jobTypeNo, 
			@RequestParam(name="group",required=false) int groupId,
			@RequestParam(name="start",required=false) String start,
			@RequestParam(name="end",required=false) String end,
			@RequestParam(name="page",required=false) int page, 
			@RequestParam(name="limit",required=false) int limit) throws Exception {
		List<AccessView> allAccessRecords = accessReportTool
				.searchAllAccessBaseRecords(name, companyNo, jobTypeNo,groupId, start, end);
		List<AccessView> filterAccessRecords = accessReportTool.getDailyAccessTools()
				.filter(allAccessRecords);
		List<AccessView> pageAccessRecords = pageTool.operate(
				filterAccessRecords, page, limit);
		// 统计工时
		accessReportTool.getDailyAccessTools().statisticalWorkHours(pageAccessRecords);
		return viewResultFactory.successResult(pageAccessRecords, filterAccessRecords.size());
	}
	/**
	 * 查询所有没有经过出入基站的记录
	 * @param name
	 * @param companyNo
	 * @param jobTypeNo
	 * @param groupId
	 * @param start
	 * @param end
	 * @param page
	 * @param limit
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/search/noBase/")
	@PermissionName(name="上下班記錄報表輸出權限設置")
	@RequiresPermissions(value="report:access")
	public Result searchNoAccessBaseRecords(
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "company", required = false) String companyNo,
			@RequestParam(name = "jobType", required = false) String jobTypeNo,
			@RequestParam(name = "group", required = false) int groupId,
			@RequestParam(name = "start", required = false) String start,
			@RequestParam(name = "end", required = false) String end,
			@RequestParam(name = "page", required = false) int page,
			@RequestParam(name = "limit", required = false) int limit) throws Exception {
		List<AccessView> noAccessBaseRecordViews = accessReportTool
				.searchAllNoAccessBaseRecords(name, companyNo, jobTypeNo, groupId, start, end);
		List<AccessView> pageAccessRecords = pageTool.operate(
				noAccessBaseRecordViews, page, limit);
		// 统计工时
		accessReportTool.getDailyAccessTools().statisticalWorkHours(pageAccessRecords);
		return viewResultFactory.successResult(pageAccessRecords, noAccessBaseRecordViews.size());
	}
	
	/**
	 * 查询所有上下班訊息
	 * @param name
	 * @param companyNo
	 * @param jobTypeNo
	 * @param groupId
	 * @param start
	 * @param end
	 * @param page
	 * @param limit
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/search/all/")
	@PermissionName(name="上下班記錄報表輸出權限設置")
	@RequiresPermissions(value="report:access")
	public Result searchAllAccessBaseRecords(
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "company", required = false) String companyNo,
			@RequestParam(name = "jobType", required = false) String jobTypeNo,
			@RequestParam(name = "group", required = false) int groupId,
			@RequestParam(name = "start", required = false) String start,
			@RequestParam(name = "end", required = false) String end,
			@RequestParam(name = "page", required = false) int page,
			@RequestParam(name = "limit", required = false) int limit) throws Exception {
		// 獲取所有經過出入基站的訊息
		List<AccessView> allIn_OutAccessRecords = accessReportTool
				.searchAllAccessBaseRecords(name, companyNo, jobTypeNo, groupId, start, end);
		List<AccessView> filterAccessRecords = 
		accessReportTool.getDailyAccessTools().filter(allIn_OutAccessRecords);
		// 獲取所有 沒有 經過出入基站的 訊息
		List<AccessView> noAccessBaseRecordViews = accessReportTool
				.searchAllNoAccessBaseRecords(name, companyNo, jobTypeNo, groupId, start, end);
		// 合并两次的数据
		@SuppressWarnings("unchecked")
		List<AccessView> allAccessRecords = ListUtils.union(filterAccessRecords, noAccessBaseRecordViews);
		// 对资料进行排序
		Collections.sort(allAccessRecords, accessReportTool
				.getiComparatorTool().obtain(AccessViewComparator.class));
		List<AccessView> pageAccessRecords = pageTool.operate(
				allAccessRecords, page, limit);
		// 统计工时
		accessReportTool.getDailyAccessTools().statisticalWorkHours(pageAccessRecords);
		return viewResultFactory.successResult(pageAccessRecords, allAccessRecords.size());
	}
}
