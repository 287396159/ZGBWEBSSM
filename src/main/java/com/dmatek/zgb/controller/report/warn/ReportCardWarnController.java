package com.dmatek.zgb.controller.report.warn;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.report.warn.tool.ReportWarnTool;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
/**
 * 输出警告讯息的报表
 * @author Administrator
 * @data 2019年1月25日 上午10:25:55
 * @Description
 */
@RestController
@RequestMapping("/report/warn/")
public class ReportCardWarnController {
	@Autowired
	private ReportWarnTool reportWarnTool;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@GetMapping("/empty/")
	public Result getEmptyWarn() {
		return viewResultFactory.successResult(null, 0);
	}
	/**
	 * 输出警告讯息卡片式
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/personhelp/")
	@PermissionName(name="警報訊息報表輸出權限設置")
	@RequiresPermissions(value="report:warn")
	public Result reportPersonHelpWarnInfor() throws Exception{
		WarnTypeCardReportInfo warnTypeCardReportInfo = reportWarnTool.getPersonCardReportInfor();
		return viewResultFactory.successResult(warnTypeCardReportInfo);
	}
	/**
	 * 区域管制卡片式
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/areacontroller/")
	@PermissionName(name="警報訊息報表輸出權限設置")
	@RequiresPermissions(value="report:warn")
	public Result reportAreaControllerWarnInfor() throws Exception{
		WarnTypeCardReportInfo warnTypeCardReportInfo = reportWarnTool.getAreaControllerCardReportInfor();
		return viewResultFactory.successResult(warnTypeCardReportInfo);
	}
	/**
	 * 低电量讯息
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/lowpower/")
	@PermissionName(name="警報訊息報表輸出權限設置")
	@RequiresPermissions(value="report:warn")
	public Result reportLowPowerWarnInfor() throws Exception{
		WarnTypeCardReportInfo warnTypeCardReportInfo = reportWarnTool.getLowerPowerCardReportInfor();
		return viewResultFactory.successResult(warnTypeCardReportInfo);
	}
	/**
	 * 卡片未移动
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/notmove/")
	@PermissionName(name="警報訊息報表輸出權限設置")
	@RequiresPermissions(value="report:warn")
	public Result reportNotMoveWarnInfor() throws Exception{
		WarnTypeCardReportInfo warnTypeCardReportInfo = reportWarnTool.getNotMoveCardReportInfor();
		return viewResultFactory.successResult(warnTypeCardReportInfo);
	}
	/**
	 * 卡片异常
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/abnormaltag/")
	@PermissionName(name="警報訊息報表輸出權限設置")
	@RequiresPermissions(value="report:warn")
	public Result reportAbnormalTagWarnInfor() throws Exception{
		WarnTypeCardReportInfo warnTypeCardReportInfo = reportWarnTool.getAbnormalTagCardReportInfor();
		return viewResultFactory.successResult(warnTypeCardReportInfo);
	}
	/**
	 * 参考点异常
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/abnormalrefer/")
	@PermissionName(name="警報訊息報表輸出權限設置")
	@RequiresPermissions(value="report:warn")
	public Result reportAbnormalReferWarnInfor() throws Exception{
		WarnTypeCardReportInfo warnTypeCardReportInfo = reportWarnTool.getAbnormalReferCardReportInfor();
		return viewResultFactory.successResult(warnTypeCardReportInfo);
	}
	/**
	 * 数据节点异常
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/abnormalbase/")
	@PermissionName(name="警報訊息報表輸出權限設置")
	@RequiresPermissions(value="report:warn")
	public Result reportAbnormalBaseWarnInfor() throws Exception{
		WarnTypeCardReportInfo warnTypeCardReportInfo = reportWarnTool.getAbnormalBaseCardReportInfor();
		return viewResultFactory.successResult(warnTypeCardReportInfo);
	}
}
