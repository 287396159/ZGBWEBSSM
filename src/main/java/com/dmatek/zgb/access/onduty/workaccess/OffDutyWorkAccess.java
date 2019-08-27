package com.dmatek.zgb.access.onduty.workaccess;

import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.time.DateUtils;

import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.db.access.work.service.AccessWorkService;
import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.redis.cache.IRedisMapCache;

public class OffDutyWorkAccess extends BaseWorkAccess {
	private IRedisMapCache<String, TagDetail> tagDetailsRedisCache;
	private Date scheduleDate;
	public OffDutyWorkAccess(AccessWorkService accessWorkService,
			IRedisMapCache<String, TagDetail> tagDetailsRedisCache) {
		super(accessWorkService);
		this.tagDetailsRedisCache = tagDetailsRedisCache;
	}
	@Override
	protected boolean isAccessRecord(TagDetail record) throws Exception {
		Date current, start, end;
		current = record.getReportTime();
		if (DateUtils.isSameDay(current, getScheduleDate()) && 
			record.getGroupId() > 0 && record.getRegionId() > 0) {
			start = getDate(getsTime(), current);
			end = getDate(geteTime(), current);
			// 判断卡片上报时间是否处在开始时间和结束时间之间
			if (current.compareTo(start) >= 0 && current.compareTo(end) <= 0) {
				return true;
			}
		}
		return false;
	}
	@Override
	public void execute(TagDetail record) throws Exception {
		if (isAccessRecord(record)) {// 是否可以进行录入记录
			TagAccessRecord tagAccessRecord = createTagAccessRecord(record);
			if (null != tagAccessRecord) {
				TagAccessRecord dbRecord = getAccessWorkService()
						.findCurrentDayOne(record.getId(),record.getReportTime(), getType());
				if(null != dbRecord) {
					getAccessWorkService().deleteOne(dbRecord.getUuid());
				}
				getAccessWorkService().insertOne(tagAccessRecord);
			}
		}
	}
	public void scanAllTagDetails() throws Exception {
		Iterator<TagDetail> iterator = getTagDetailsRedisCache()
				.getCacheMap().values().iterator();
		while (iterator.hasNext()) {
			TagDetail tagDetail = (TagDetail) iterator.next();
			execute(tagDetail);
		}
	}
	public IRedisMapCache<String, TagDetail> getTagDetailsRedisCache() {
		return tagDetailsRedisCache;
	}
	public void setTagDetailsRedisCache(IRedisMapCache<String, TagDetail> tagDetailsRedisCache) {
		this.tagDetailsRedisCache = tagDetailsRedisCache;
	}
	public Date getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	@Override
	protected byte getType() throws Exception {
		return BaseWorkAccess.getOffdutyType();
	}
}
