package com.dmatek.zgb.warn.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.db.warn.service.AbnormalReferService;
import com.dmatek.zgb.redis.service.RedisService;
import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnType;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.AbnormalReferWarn;
import com.dmatek.zgb.warn.cache.base.BaseNodeWarnCache;

public class AbnormalReferWarnCache extends BaseNodeWarnCache<AbnormalReferWarn> {
	private AbnormalReferService abnormalReferService;
	public AbnormalReferWarnCache(RedisService redisService,
			BaseWarmComparator warmDefaultComparator,
			AbnormalReferService abnormalReferService) {
		super(redisService, warmDefaultComparator);
		this.abnormalReferService = abnormalReferService;
	}

	@Override
	public String getWarnName() {
		return "abnormalReferWarnCache";
	}

	@Override
	public void addWarnToDb(AbnormalReferWarn warn) throws Exception {
		if(null != abnormalReferService) {
			if(null == abnormalReferService.findOne(warn.getUuid())) {
				abnormalReferService.addAbnormalRefer(warn);
			}
		}
	}

	@Override
	protected WarnTypeCardReportInfo getDataBaseWarnTypeCardReportInfor(
			int peroid) throws Exception {
		WarnTypeCardReportInfo warnTypeCardInfor = 
				abnormalReferService.findWarnCardReportInfo(peroid);
		List<GroupCardReportWarnInfo> groupsCardReportWarnInfo = 
				abnormalReferService.findGroupsWarnCardReportInfo(peroid);
		for (GroupCardReportWarnInfo groupCardReportWarnInfo : groupsCardReportWarnInfo) {
			if(null != warnTypeCardInfor){
				Map<Integer, GroupCardReportWarnInfo> map = warnTypeCardInfor.getGroupCardMap();
				if(null == map){
					map = new HashMap<Integer, GroupCardReportWarnInfo>();
					warnTypeCardInfor.setGroupCardMap(map);
				}
				map.put(groupCardReportWarnInfo.getGroupId(), groupCardReportWarnInfo);
			}
		}
		if(null != warnTypeCardInfor){
			warnTypeCardInfor.setWarnType(WarnType.AbnormalReferWarn);
		}
		return warnTypeCardInfor;
	}
}
