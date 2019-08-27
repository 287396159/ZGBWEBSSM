package com.dmatek.zgb.warn.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.db.warn.service.LowPowerService;
import com.dmatek.zgb.redis.service.RedisService;
import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnType;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.LowPowerWarn;
import com.dmatek.zgb.warn.cache.base.BaseTagWarnCache;

public class LowPowerWarnCache extends BaseTagWarnCache<LowPowerWarn> {
	private LowPowerService lowPowerService;
	public LowPowerWarnCache(RedisService redisService,
			BaseWarmComparator warmDefaultComparator,
			LowPowerService lowPowerService) {
		super(redisService, warmDefaultComparator);
		this.lowPowerService = lowPowerService;
	}

	@Override
	public String getWarnName() {
		return "lowPowerWarnCache";
	}

	@Override
	public void addWarnToDb(LowPowerWarn warn) throws Exception {
		if(null != lowPowerService) {
			if(null == lowPowerService.findOne(warn.getUuid())) {
				lowPowerService.addLowPower(warn);	
			}
		}
		
	}

	@Override
	protected WarnTypeCardReportInfo getDataBaseWarnTypeCardReportInfor(
			int peroid) throws Exception {
		WarnTypeCardReportInfo warnTypeCardInfor = 
				lowPowerService.findWarnCardReportInfo(peroid);
		List<GroupCardReportWarnInfo> groupsCardReportWarnInfo = 
				lowPowerService.findGroupsWarnCardReportInfo(peroid);
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
			warnTypeCardInfor.setWarnType(WarnType.LowPowerWarn);
		}
		return warnTypeCardInfor;
	}
}
