package com.dmatek.zgb.warn.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.db.warn.service.NotMoveService;
import com.dmatek.zgb.redis.service.RedisService;
import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnType;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.NotMoveWarn;
import com.dmatek.zgb.warn.cache.base.BaseTagWarnCache;

public class NotMoveWarnCache extends BaseTagWarnCache<NotMoveWarn> {
	private NotMoveService notMoveService;
	public NotMoveWarnCache(RedisService redisService,
			BaseWarmComparator warmDefaultComparator,
			NotMoveService notMoveService) {
		super(redisService, warmDefaultComparator);
		this.notMoveService = notMoveService;
	}
	@Override
	public String getWarnName() {
		return "notMoveWarnCache";
	}
	@Override
	public void addWarnToDb(NotMoveWarn warn) throws Exception {
		if(null != notMoveService) {
			if(null == notMoveService.findOne(warn.getUuid())) {
				notMoveService.addNotMove(warn);
			}
		}
	}
	@Override
	protected WarnTypeCardReportInfo getDataBaseWarnTypeCardReportInfor(
			int peroid) throws Exception {
		WarnTypeCardReportInfo warnTypeCardInfor = 
				notMoveService.findWarnCardReportInfo(peroid);
		List<GroupCardReportWarnInfo> groupsCardReportWarnInfo = 
				notMoveService.findGroupsWarnCardReportInfo(peroid);
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
			warnTypeCardInfor.setWarnType(WarnType.NotMoveWarn);
		}
		return warnTypeCardInfor;
	}
}
