package com.dmatek.zgb.warn.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.db.warn.service.AbnormalTagService;
import com.dmatek.zgb.redis.service.RedisService;
import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnType;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.AbnormalTagWarn;
import com.dmatek.zgb.warn.cache.base.BaseTagWarnCache;

public class AbnormalTagWarnCache extends BaseTagWarnCache<AbnormalTagWarn> {
	private AbnormalTagService abnormalTagService;
	public AbnormalTagWarnCache(RedisService redisService,
			BaseWarmComparator warmDefaultComparator,
			AbnormalTagService abnormalTagService) {
		super(redisService, warmDefaultComparator);
		this.abnormalTagService = abnormalTagService;
	}
	@Override
	public String getWarnName() {
		return "abnormalTagWarnCache";
	}
	@Override
	public void addWarnToDb(AbnormalTagWarn warn) throws Exception {
		if(null != abnormalTagService){
			if(null == abnormalTagService.findOne(warn.getUuid())) {
				abnormalTagService.addAbnormalTag(warn);
			}
		}
	}
	@Override
	protected WarnTypeCardReportInfo getDataBaseWarnTypeCardReportInfor(
			int peroid) throws Exception {
		WarnTypeCardReportInfo warnTypeCardInfor = 
				abnormalTagService.findWarnCardReportInfo(peroid);
		List<GroupCardReportWarnInfo> groupsCardReportWarnInfo = 
				abnormalTagService.findGroupsWarnCardReportInfo(peroid);
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
			warnTypeCardInfor.setWarnType(WarnType.AbnormalTagWarn);
		}
		return warnTypeCardInfor;
	}
}
