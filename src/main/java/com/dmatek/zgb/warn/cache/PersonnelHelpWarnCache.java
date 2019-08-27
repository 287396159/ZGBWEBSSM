package com.dmatek.zgb.warn.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.db.warn.service.PersonHelpService;
import com.dmatek.zgb.redis.service.RedisService;
import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnType;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.PersonnelHelpWarn;
import com.dmatek.zgb.warn.cache.base.BaseTagWarnCache;

public class PersonnelHelpWarnCache extends BaseTagWarnCache<PersonnelHelpWarn>{
	private PersonHelpService personHelpService;
	public PersonnelHelpWarnCache(RedisService redisService,
			BaseWarmComparator warmDefaultComparator,
			PersonHelpService personHelpService) {
		super(redisService, warmDefaultComparator);
		this.personHelpService = personHelpService;
	}
	@Override
	public String getWarnName() {
		return "personnelHelpWarnCache";
	}

	@Override
	public void addWarnToDb(PersonnelHelpWarn warn) throws Exception {
		if(null != personHelpService) {
			if(null == personHelpService.findOne(warn.getUuid())) {
				personHelpService.addPersonHelp(warn);
			}
		}
	}
	/**
	 * 获取数据库中人员求救的统计讯息
	 * @param peroid
	 * @return
	 * @throws Exception
	 */
	@Override
	protected WarnTypeCardReportInfo getDataBaseWarnTypeCardReportInfor(int peroid) throws Exception {
		WarnTypeCardReportInfo warnTypeCardInfor = 
				personHelpService.findWarnCardReportInfo(peroid);
		List<GroupCardReportWarnInfo> groupsCardReportWarnInfo = 
				personHelpService.findGroupsWarnCardReportInfo(peroid);
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
			warnTypeCardInfor.setWarnType(WarnType.PersonnelHelpWarn);
		}
		return warnTypeCardInfor;
	}
}
