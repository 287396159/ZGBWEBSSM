package com.dmatek.zgb.access.onduty.workaccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.lang.time.DateUtils;
import com.dmatek.zgb.access.base.IWorkAccess;
import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.db.access.work.service.AccessWorkService;
import com.dmatek.zgb.monitor.device.detail.TagDetail;

public abstract class BaseWorkAccess implements IWorkAccess<TagDetail> {
	private static final byte ONDUTY_TYPE = 1;
	private static final byte OFFDUTY_TYPE = 2;
	private SimpleDateFormat simpleDateFormat;
	private String sTime, eTime;
	private AccessWorkService accessWorkService;
	public BaseWorkAccess(AccessWorkService accessWorkService) {
		super();
		this.accessWorkService = accessWorkService;
		this.simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
	}
	@SuppressWarnings("deprecation")
	public Date getDate(String sTime, Date reportDate) throws ParseException {
		Date conversion = getSimpleDateFormat().parse(sTime);
		conversion.setYear(reportDate.getYear());
		conversion.setMonth(reportDate.getMonth());
		conversion.setDate(reportDate.getDate());
		return conversion;
	}
	public TagAccessRecord createTagAccessRecord(TagDetail record) throws Exception {
		String uuid = UUID.randomUUID().toString();
		TagAccessRecord tagAccessRecord = new TagAccessRecord(uuid,
				record.getrId(), record.getId(), record.getTagNo(),
				record.getTagName(), record.getReferName(),
				record.getCompanyNo(), record.getCompanyName(),
				record.getJobTypeNo(), record.getJobTypeName(),
				record.getGroupId(), record.getRegionId(),
				record.getGroupName(), record.getRegionName(), getType(),
				DateUtils.round(record.getReportTime(), Calendar.SECOND), record.getImage(), record.getResTime(),
				record.isLowPower(), getType()==OFFDUTY_TYPE?"外出":"進入");
		
		return tagAccessRecord;
	}
	protected abstract boolean isAccessRecord(TagDetail record) throws Exception;
	protected abstract byte getType() throws Exception;
	public String getsTime() {
		return sTime;
	}
	public void setsTime(String sTime) {
		this.sTime = sTime;
	}
	public String geteTime() {
		return eTime;
	}
	public void seteTime(String eTime) {
		this.eTime = eTime;
	}
	public AccessWorkService getAccessWorkService() {
		return accessWorkService;
	}
	public void setAccessWorkService(AccessWorkService accessWorkService) {
		this.accessWorkService = accessWorkService;
	}
	public SimpleDateFormat getSimpleDateFormat() {
		return simpleDateFormat;
	}
	public static byte getOndutyType() {
		return ONDUTY_TYPE;
	}
	public static byte getOffdutyType() {
		return OFFDUTY_TYPE;
	}
	public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
		this.simpleDateFormat = simpleDateFormat;
	}
	@Override
	public void setParams(String... args) {
		if(args.length >= 2) {
			setsTime(args[0]);
			seteTime(args[1]);
		}
	}
}
