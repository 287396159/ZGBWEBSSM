package com.dmatek.zgb.controller.report.warn;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.report.statistic.warn.tool.StatisticalAbnormalReferWarnPageTool;
import com.dmatek.zgb.report.statistical.warn.bean.AbnormalReferStatictisWarn;
import com.dmatek.zgb.report.warn.tool.AbnormalReferReportWarnTool;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.show.warn.vo.NodeWarnSearchInfo;
import com.dmatek.zgb.show.warn.vo.WarnSearchFactory;
import com.dmatek.zgb.warn.bean.AbnormalReferWarn;

@RestController
@RequestMapping("/report/abnormalrefer/")
public class AbnormalReferReportController {
	@Autowired
	private AbnormalReferReportWarnTool abnormalReferReportWarnTool;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private WarnSearchFactory warnSearchFactory;
	@Autowired
	private StatisticalAbnormalReferWarnPageTool pageTool;
	
	@GetMapping("/empty")
	public Result getEmptyWarn(@RequestParam("page") int page,@RequestParam("limit") int limit) {
		return viewResultFactory.successResult(null, 0);
	}
	/**
	 * 搜索警告讯息
	 * @param name
	 * @param sCompany
	 * @param sJobType
	 * @param group
	 * @param sStart
	 * @param sEnd
	 * @param page
	 * @param limit
	 * @return
	 * @throws Exception
	 * private String company, jobType;
	   private int group;
	   private String name;
	   private Date start, end;
	 */
	@GetMapping("/search")
	@PermissionName(name="警報訊息報表輸出權限設置")
	@RequiresPermissions(value="report:warn")
	public Result searchWarns(@RequestParam(name="name", required=false) String name,
			@RequestParam(name="group", required=false) String group,
			@RequestParam(name="start", required=false) String sStart,
			@RequestParam(name="end", required=false) String sEnd,
			@RequestParam("page") int page, 
			@RequestParam("limit") int limit) throws Exception {
		NodeWarnSearchInfo nodeWarnSearchInfo = warnSearchFactory
				.getNodeWarnSearchInfo(name, group, sStart, sEnd);
		List<AbnormalReferWarn> totalWarns = abnormalReferReportWarnTool
				.getAllMatchWarns(nodeWarnSearchInfo);
		List<AbnormalReferStatictisWarn> statisticWarns = abnormalReferReportWarnTool
				.getStatisticWarns(totalWarns);
		return viewResultFactory.successResult(
				pageTool.operate(statisticWarns, page, limit),
				pageTool.total(statisticWarns));
	}
	
	@GetMapping("/total")
	@PermissionName(name="警報訊息報表輸出權限設置")
	@RequiresPermissions(value="report:warn")
	public Result totalWarns(@RequestParam(name="name", required=false) String name,
			@RequestParam(name="group", required=false) String group,
			@RequestParam(name="start", required=false) String sStart,
			@RequestParam(name="end", required=false) String sEnd) throws Exception {
		NodeWarnSearchInfo nodeWarnSearchInfo = warnSearchFactory
				.getNodeWarnSearchInfo(name, group, sStart, sEnd);
		List<AbnormalReferWarn> totalWarns = abnormalReferReportWarnTool
				.getAllMatchWarns(nodeWarnSearchInfo);
		List<AbnormalReferStatictisWarn> statisticWarns = abnormalReferReportWarnTool
				.getStatisticWarns(totalWarns);
		return viewResultFactory.successResult(pageTool.total(statisticWarns));
	}
	/**
	 * 搜索警告讯息
	 * @param name
	 * @param sCompany
	 * @param sJobType
	 * @param group
	 * @param sStart
	 * @param sEnd
	 * @param page
	 * @param limit
	 * @return
	 * @throws Exception
	 * private String company, jobType;
	   private int group;
	   private String name;
	   private Date start, end;
	 */
	@GetMapping("/print")
	@PermissionName(name="警報訊息報表輸出權限設置")
	@RequiresPermissions(value="report:warn")
	public Result printWarns(@RequestParam(name="name", required=false) String name,
			@RequestParam(name="group", required=false) String group,
			@RequestParam(name="start", required=false) String sStart,
			@RequestParam(name="end", required=false) String sEnd) throws Exception {
		NodeWarnSearchInfo nodeWarnSearchInfo = warnSearchFactory
				.getNodeWarnSearchInfo(name, group, sStart, sEnd);
		List<AbnormalReferWarn> totalWarns = abnormalReferReportWarnTool.getAllMatchWarns(nodeWarnSearchInfo);
		return viewResultFactory.successResult(abnormalReferReportWarnTool.getStatisticWarns(totalWarns));
	}
}
