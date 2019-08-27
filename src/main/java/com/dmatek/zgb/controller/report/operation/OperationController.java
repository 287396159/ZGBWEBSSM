package com.dmatek.zgb.controller.report.operation;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dmatek.zgb.controller.tool.page.PageTool;
import com.dmatek.zgb.log.bean.OperationLog;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.report.operation.tool.LogTool;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@RestController
@RequestMapping("/report/operation")
public class OperationController {
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private LogTool logTool;
	@Autowired
	private PageTool pageTool;
	@GetMapping("/empty")
	public Result findEmptyLog(@RequestParam("page") int page,
			@RequestParam("limit") int limit) {
		return viewResultFactory.successResult(null, 0);
	}
	@GetMapping("/search")
	@PermissionName(name="人員操作記錄報表輸出權限設置")
	@RequiresPermissions(value="report:operation")
	public Result findAllLogs(@RequestParam("page") int page, @RequestParam("limit") int limit,
			@RequestParam(name="userName",required=false) String userName,
			@RequestParam(name="start",required=false) String start, 
			@RequestParam(name="end",required=false) String end) throws Exception {
		List<OperationLog> allLogs = logTool.searchLogs(userName, start, end);
		return viewResultFactory.successResult(pageTool.operate(allLogs, page, limit), allLogs.size());
	}
	
	@GetMapping("/print")
	@PermissionName(name="人員操作記錄報表輸出權限設置")
	@RequiresPermissions(value="report:operation")
	public Result printAllLogs(@RequestParam(name="userName",required=false) String userName,
			@RequestParam(name="start",required=false) String start, 
			@RequestParam(name="end",required=false) String end) throws Exception {
		List<OperationLog> allLogs = logTool.searchLogs(userName, start, end);
		return viewResultFactory.successResult(allLogs);
	}
}
