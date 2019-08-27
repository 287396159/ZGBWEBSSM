package com.dmatek.zgb.access.onduty.workaccess;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.db.access.work.service.AccessWorkService;
import com.dmatek.zgb.monitor.device.detail.TagDetail;

public class OnDutyWorkAccess extends BaseWorkAccess {
	public OnDutyWorkAccess(AccessWorkService accessWorkService) {
		super(accessWorkService);
	}
	@Override
	protected boolean isAccessRecord(TagDetail record) throws Exception {
		Date current, start, end;
		current = record.getReportTime();
		if (DateUtils.isSameDay(current, new Date()) && 
			record.getGroupId() > 0 && record.getRegionId() > 0) {
			start = getDate(getsTime(), current);
			end = getDate(geteTime(), current);
			// 判断卡片上报时间是否处在开始时间和结束时间之间
			if (current.compareTo(start) >= 0 && current.compareTo(end) <= 0) {
				TagAccessRecord tagAccessRecord = getAccessWorkService()
						.findCurrentDayOne(record.getId(), current, getType());
				if (null == tagAccessRecord) {// 说明当天的记录已经存在
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public void execute(TagDetail record) throws Exception {
		if (isAccessRecord(record)) {// 是否可以进行录入记录
			TagAccessRecord tagAccessRecord = createTagAccessRecord(record);
			if (null != tagAccessRecord) {
				getAccessWorkService().insertOne(tagAccessRecord);
			}
		}
	}
	@Override
	protected byte getType() throws Exception {
		return BaseWorkAccess.getOndutyType();
	}
}
