package com.dmatek.zgb.controller.report.access;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.comparator.AscInOutRecordComparator;
import com.dmatek.zgb.controller.tool.page.PageTool;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.report.access.tool.AccessReportTool;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@RestController
@RequestMapping("/in-out/record/")
public class InOutRecordController {
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Resource(name="accessReportTool")
	private AccessReportTool accessReportTool;
	@Autowired
	private AscInOutRecordComparator accessComparator;
	@Autowired
	private PageTool pageTool;
	/**
	 * 获取所有的空记录
	 * @return
	 */
	@GetMapping("/empty")
	public Result getEmptyRecords(@RequestParam(name="page", required=false) int page,
			@RequestParam(name="limit", required=false) int limit) {
		return viewResultFactory.successResult(null, 0);
	}
	/**
	 * 查询记录
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
	@PermissionName(name="進出記錄報表輸出權限設置")
	@RequiresPermissions("report:in-out")
	public Result searchRecords(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "company", required = false) String companyNo,
			@RequestParam(name = "jobType", required = false) String jobTypeNo,
			@RequestParam(name = "group", required = false) int groupId,
			@RequestParam(name = "start", required = false) String start,
			@RequestParam(name = "end", required = false) String end,
			@RequestParam(name = "page", required = false) int page,
			@RequestParam(name = "limit", required = false) int limit) throws Exception {
		// 获取进出的所有的记录
		Date pstart = accessReportTool.getTimeSimpleDateFormat().parse(start);
		Date pend = accessReportTool.getTimeSimpleDateFormat().parse(end);	
		List<TagAccessRecord> tagAccessRecords = accessReportTool
				.searchAllAccessRecords(name, companyNo, jobTypeNo, groupId, pstart, pend);
		// 对所有的记录排序
		Collections.sort(tagAccessRecords, accessComparator);
		// 对所有的记录进行分页
		List<TagAccessRecord> pageRecords = pageTool.operate(tagAccessRecords,
				page, limit);
		return viewResultFactory.successResult(pageRecords, tagAccessRecords.size());
	}
	
	/**
	 * 查询记录
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
	@GetMapping("/print/")
	@PermissionName(name="進出記錄報表輸出權限設置")
	@RequiresPermissions("report:in-out")
	public Result printRecords(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "company", required = false) String companyNo,
			@RequestParam(name = "jobType", required = false) String jobTypeNo,
			@RequestParam(name = "group", required = false) int groupId,
			@RequestParam(name = "start", required = false) String start,
			@RequestParam(name = "end", required = false) String end) throws Exception {
		Date pstart = accessReportTool.getTimeSimpleDateFormat().parse(start);
		Date pend = accessReportTool.getTimeSimpleDateFormat().parse(end);
		// 获取进出的所有的记录
		List<TagAccessRecord> tagAccessRecords = accessReportTool
				.searchAllAccessRecords(name, companyNo, jobTypeNo, groupId, pstart, pend);
		// 对所有的记录排序
		Collections.sort(tagAccessRecords, accessComparator);
		return viewResultFactory.successResult(tagAccessRecords);
	}
}
