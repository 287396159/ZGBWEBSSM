package com.dmatek.zgb.controller.report.totalhours;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dmatek.zgb.controller.tool.page.PageTool;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.show.vo.TotalHoursView;

@RestController
@RequestMapping("/report/totalHours")
@PermissionName(name="報表輸出權限設置")
@RequiresPermissions(value="report:*")
public class TotalHoursController {
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private PageTool pageTool;
	@Autowired
	private ITotalHoursTool<TotalHoursView> iToolHoursTool;
	/**
	 * 查询空工时讯息
	 * @return
	 */
	@GetMapping("/empty")
	public Result findEmptyTotalHours() {
		return viewResultFactory.successResult(null, 0);
	}
	/**
	 * 根据条件查找
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
	@GetMapping("/search/")
	@PermissionName(name="工時統計報表輸出權限設置")
	@RequiresPermissions(value="report:totalHours")
	public Result searchTotalHours(@RequestParam(name="name", required = false) String name,
			@RequestParam(name="companyNo", required = false) String companyNo, 
			@RequestParam(name="jobTypeNo", required = false) String jobTypeNo,
			@RequestParam(name="groupId", required = false) Integer groupId,
			@RequestParam(name="start", required = false) String start,
			@RequestParam(name="end", required = false) String end, 
			@RequestParam(name="page") int page,
			@RequestParam(name="limit") int limit) throws Exception {
		List<TotalHoursView> totals = iToolHoursTool.searchTotalHours(name,
				companyNo, jobTypeNo, (groupId!=null?groupId:-1), start, end);
		List<TotalHoursView> pages = pageTool.operate(totals, page, limit);
		return viewResultFactory.successResult(pages, totals.size());
	}
	/**
	 * 根据条件打印讯息
	 * @param name
	 * @param companyNo
	 * @param jobTypeNo
	 * @param groupId
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/print/")
	public Result printTotalHours(@RequestParam(name="name", required=false) String name,
			@RequestParam(name="companyNo", required=false) String companyNo, 
			@RequestParam(name="jobTypeNo", required=false) String jobTypeNo,
			@RequestParam(name="groupId", required=false) Integer groupId,
			@RequestParam(name="start", required=false) String start,
			@RequestParam(name="end", required=false) String end) throws Exception {
		List<TotalHoursView> totals = iToolHoursTool.searchTotalHours(name,
				companyNo, jobTypeNo, (groupId!=null?groupId:-1), start, end);
		return viewResultFactory.successResult(totals);
	}
}
