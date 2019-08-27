package com.dmatek.zgb.controller.report.totalhours;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.ListUtils;
import org.apache.shiro.util.CollectionUtils;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.comparator.IComparatorTool;
import com.dmatek.zgb.db.access.service.AccessService;
import com.dmatek.zgb.db.access.work.service.AccessWorkService;
import com.dmatek.zgb.redis.cache.AccessRedisCache;
import com.dmatek.zgb.report.access.tool.AccessReportTool;
import com.dmatek.zgb.report.access.tool.IDailyAccessTools;
import com.dmatek.zgb.show.vo.AccessView;
import com.dmatek.zgb.show.vo.TotalHoursView;

public class TotalHoursTool extends AccessReportTool implements ITotalHoursTool<TotalHoursView>{
	private DecimalFormat df = null;
	public TotalHoursTool(AccessRedisCache iRedisCache,
			AccessService accessService, AccessWorkService accessWorkService,
			IDailyAccessTools<TagAccessRecord, AccessView> dailyAccessTools,
			IComparatorTool iComparatorTool) {
		super(iRedisCache, accessService, accessWorkService, dailyAccessTools, iComparatorTool);
		df = new DecimalFormat("0.00");
	}
	/**
	 * 查询满足条件的记录
	 * @param name
	 * @param companyNo
	 * @param jobTypeNo
	 * @param groupId
	 * @param start
	 * @param end
	 * @return
	 */
	@Override
	public List<TotalHoursView> printTotalHours(String name, String companyNo,
			String jobTypeNo, int groupId, String start, String end)
			throws Exception {
		return null;
	}
	@Override
	public List<TotalHoursView> searchTotalHours(String name, String companyNo,
			String jobTypeNo, int groupId, String start, String end)
			throws Exception {
		// 搜索所有经过出入基站的记录
		List<AccessView> allAccessRecords = searchAllAccessBaseRecords(name,
				companyNo, jobTypeNo, groupId, start, end);
		// 搜索所有没有进过上下班的记录
		List<AccessView> allNoAccessRecords = searchAllNoAccessBaseRecords(name, 
				companyNo, jobTypeNo, groupId, start, end);
		// 合并两个类
		@SuppressWarnings("unchecked")
		List<AccessView> allRecords = ListUtils.union(allAccessRecords,
				allNoAccessRecords);
		return gatherTotalHours(allRecords, getTimeSimpleDateFormat().parse(start),
				getTimeSimpleDateFormat().parse(end));
	}
	private List<TotalHoursView> gatherTotalHours(List<AccessView> everyHours, Date start, Date end) throws Exception {
		Map<String, TotalHoursView> totalMap = new HashMap<String, TotalHoursView>();
		TotalHoursView totalHoursView = null;
		for (AccessView accessView : everyHours) {
			if(!StringUtils.isEmpty(accessView.getEnterReferId()) && !StringUtils.isEmpty(accessView.getLeaveReferId())) {
				float tt = getDailyAccessTools().calcWorkHours(accessView);
				totalHoursView = totalMap.get(accessView.getTagId());
				if(null != totalHoursView) {
					totalHoursView.setTotalHours(totalHoursView.getTotalHours() + tt);
					totalHoursView.setDfTotalHours(totalHoursView
							.getTotalHours() > 0 ? getDf().format(
							totalHoursView.getTotalHours()) : "0.00");
				} else {// 说明之前没有
					totalHoursView = new TotalHoursView(accessView.getTagId(),
							accessView.getTagName(), accessView.getTagNo(), 
							accessView.getCompanyName(), accessView.getJobTypeName(), 
							accessView.getGroupName(), start, end, tt);
					totalHoursView.setDfTotalHours(tt>0?getDf().format(tt) : "0.00");
					totalMap.put(accessView.getTagId(), totalHoursView);
				}
			}
		}
		return CollectionUtils.asList(totalMap.values().toArray(
				new TotalHoursView[0]));
	}
	public DecimalFormat getDf() {
		return df;
	}
	public void setDf(DecimalFormat df) {
		this.df = df;
	}
}
