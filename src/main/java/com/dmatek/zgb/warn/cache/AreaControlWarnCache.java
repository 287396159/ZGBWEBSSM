package com.dmatek.zgb.warn.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.db.warn.service.AreaControllerService;
import com.dmatek.zgb.redis.service.RedisService;
import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnType;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.AreaControlWarn;
import com.dmatek.zgb.warn.cache.base.BaseTagWarnCache;

public class AreaControlWarnCache extends BaseTagWarnCache<AreaControlWarn> {
	private AreaControllerService areaControllerService;
	public AreaControlWarnCache(RedisService redisService,
			BaseWarmComparator warmDefaultComparator,
			AreaControllerService areaControllerService) {
		super(redisService, warmDefaultComparator);
		this.areaControllerService = areaControllerService;
	}

	@Override
	public String getWarnName() {
		return "areaControlWarnCache";
	}

	@Override
	public void addWarnToDb(AreaControlWarn warn) throws Exception {
		if(null != areaControllerService){
			if(null == areaControllerService.findOne(warn.getUuid())) {
				areaControllerService.addAreaController(warn);
			}
		}
	
	}

	@Override
	protected WarnTypeCardReportInfo getDataBaseWarnTypeCardReportInfor(
			int peroid) throws Exception {
		WarnTypeCardReportInfo warnTypeCardInfor = 
				areaControllerService.findWarnCardReportInfo(peroid);
		List<GroupCardReportWarnInfo> groupsCardReportWarnInfo = 
				areaControllerService.findGroupsWarnCardReportInfo(peroid);
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
			warnTypeCardInfor.setWarnType(WarnType.AreaControlWarn);
		}
		return warnTypeCardInfor;
	}
}
