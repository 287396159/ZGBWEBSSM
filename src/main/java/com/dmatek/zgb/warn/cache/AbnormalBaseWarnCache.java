package com.dmatek.zgb.warn.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.db.warn.service.AbnormalBaseService;
import com.dmatek.zgb.redis.service.RedisService;
import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnType;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.AbnormalBaseWarn;
import com.dmatek.zgb.warn.cache.base.BaseNodeWarnCache;

public class AbnormalBaseWarnCache extends BaseNodeWarnCache<AbnormalBaseWarn> {
	private AbnormalBaseService abnormalBaseService;
	public AbnormalBaseWarnCache(RedisService redisService,
			BaseWarmComparator warmDefaultComparator,
			AbnormalBaseService abnormalBaseService) {
		super(redisService, warmDefaultComparator);
		this.abnormalBaseService = abnormalBaseService;
	}
	@Override
	public String getWarnName() {
		return "abnormalBaseWarnCache";
	}
	@Override
	public void addWarnToDb(AbnormalBaseWarn warn) throws Exception {
		if(null != abnormalBaseService){
			if(null == abnormalBaseService.findOne(warn.getUuid())) {
				abnormalBaseService.addAbnormalBase(warn);
			}
		}
	}
	@Override
	protected WarnTypeCardReportInfo getDataBaseWarnTypeCardReportInfor(
			int peroid) throws Exception {
		WarnTypeCardReportInfo warnTypeCardInfor = 
				abnormalBaseService.findWarnCardReportInfo(peroid);
		List<GroupCardReportWarnInfo> groupsCardReportWarnInfo = 
				abnormalBaseService.findGroupsWarnCardReportInfo(peroid);
		for (GroupCardReportWarnInfo groupCardReportWarnInfo : groupsCardReportWarnInfo) {
			if(null != warnTypeCardInfor) {
				Map<Integer, GroupCardReportWarnInfo> map = warnTypeCardInfor.getGroupCardMap();
				if(null == map) {
					map = new HashMap<Integer, GroupCardReportWarnInfo>();
					warnTypeCardInfor.setGroupCardMap(map);
				}
				map.put(groupCardReportWarnInfo.getGroupId(), groupCardReportWarnInfo);
			}
		}
		if(null != warnTypeCardInfor) {
			warnTypeCardInfor.setWarnType(WarnType.AbnormalReferWarn);
		}
		return warnTypeCardInfor;
	}
}
