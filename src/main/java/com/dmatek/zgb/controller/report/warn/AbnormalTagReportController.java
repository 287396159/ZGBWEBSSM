package com.dmatek.zgb.controller.report.warn;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.report.statistic.warn.tool.BaseStatisticalTagWarnPageTool;
import com.dmatek.zgb.report.statistical.warn.bean.AbnormalTagStatictisWarn;
import com.dmatek.zgb.report.warn.tool.AbnormalTagReportWarnTool;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.show.warn.vo.TagWarnSearchInfo;
import com.dmatek.zgb.show.warn.vo.WarnSearchFactory;
import com.dmatek.zgb.warn.bean.AbnormalTagWarn;

@RestController
@RequestMapping("/report/abnormaltag/")
public class AbnormalTagReportController {
	@Autowired
	private AbnormalTagReportWarnTool abnormalTagReportWarnTool;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private WarnSearchFactory warnSearchFactory;
	@Autowired
	private BaseStatisticalTagWarnPageTool<AbnormalTagWarn, AbnormalTagStatictisWarn> pageTool;
	
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
	public Result searchWarns(@RequestParam(name="name",required=false) String name,
			@RequestParam(name="company",required=false) String company, 
			@RequestParam(name="jobType",required=false) String jobType, 
			@RequestParam(name="group",required=false) String group,
			@RequestParam(name="start",required=false) String sStart,
			@RequestParam(name="end",required=false) String sEnd,
			@RequestParam("page") int page, @RequestParam("limit") int limit) throws Exception {
		TagWarnSearchInfo tagWarnSearchInfo = warnSearchFactory
				.getTagWarnSearchInfo(name, company, jobType, group, sStart, sEnd);
		List<AbnormalTagWarn> totalWarns = abnormalTagReportWarnTool
				.getAllMatchWarns(tagWarnSearchInfo);
		List<AbnormalTagStatictisWarn> abnormalStatisticalWarn = abnormalTagReportWarnTool
				.getStatisticWarns(totalWarns);
		return viewResultFactory.successResult(
				pageTool.operate(abnormalStatisticalWarn, page, limit),
				pageTool.total(abnormalStatisticalWarn));
	}
	
	@GetMapping("/total")
	@PermissionName(name="警報訊息報表輸出權限設置")
	@RequiresPermissions(value="report:warn")
	public Result totalWarns(@RequestParam(name="name",required=false) String name,
			@RequestParam(name="company",required=false) String company, 
			@RequestParam(name="jobType",required=false) String jobType, 
			@RequestParam(name="group",required=false) String group,
			@RequestParam(name="start",required=false) String sStart,
			@RequestParam(name="end",required=false) String sEnd) throws Exception {
		TagWarnSearchInfo tagWarnSearchInfo = warnSearchFactory
				.getTagWarnSearchInfo(name, company, jobType, group, sStart, sEnd);
		List<AbnormalTagWarn> totalWarns = abnormalTagReportWarnTool
				.getAllMatchWarns(tagWarnSearchInfo);
		List<AbnormalTagStatictisWarn> abnormalStatisticalWarn = abnormalTagReportWarnTool
				.getStatisticWarns(totalWarns);
		return viewResultFactory.successResult(pageTool.total(abnormalStatisticalWarn));
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
	public Result printWarns(@RequestParam(name="name",required=false) String name,
			@RequestParam(name="company",required=false) String company, 
			@RequestParam(name="jobType",required=false) String jobType, 
			@RequestParam(name="group",required=false) String group,
			@RequestParam(name="start",required=false) String sStart,
			@RequestParam(name="end",required=false) String sEnd) throws Exception {
		TagWarnSearchInfo tagWarnSearchInfo = warnSearchFactory
				.getTagWarnSearchInfo(name, company, jobType, group, sStart, sEnd);
		List<AbnormalTagWarn> totalWarns = abnormalTagReportWarnTool.getAllMatchWarns(tagWarnSearchInfo);
		return viewResultFactory.successResult(abnormalTagReportWarnTool.getStatisticWarns(totalWarns));
	}
}
