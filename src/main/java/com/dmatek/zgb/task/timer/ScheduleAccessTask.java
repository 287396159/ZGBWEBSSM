package com.dmatek.zgb.task.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.dmatek.zgb.access.onduty.workaccess.OffDutyWorkAccess;
import com.dmatek.zgb.controller.setting.params.base.SystemSettingParamTool;
import com.dmatek.zgb.params.setting.cache.ParamsKey;
/**
 * 统计上下班时间点的任务
 * @author zhangfu
 * @data 2019年3月25日 下午4:15:56
 * @Description
 */
public class ScheduleAccessTask extends TimerTask {
	private final static Logger logger = Logger.getLogger(ScheduleAccessTask.class);
	private OffDutyWorkAccess offDutyWorkAccess;
	private SimpleDateFormat simpleDateFormat;
	private SystemSettingParamTool systemSettingParamTool;
	public ScheduleAccessTask(OffDutyWorkAccess offDutyWorkAccess,
			SystemSettingParamTool systemSettingParamTool) {
		super();
		this.offDutyWorkAccess = offDutyWorkAccess;
		this.systemSettingParamTool = systemSettingParamTool;
		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	@Override
	public void run() {
		try {
			if(null != offDutyWorkAccess) {
				boolean isAutoStatisticAccess = (boolean) getSystemSettingParamTool().getSysParamValue(ParamsKey.isAutoStatisticAccess);
				if(isAutoStatisticAccess) {
					String sStart = (String) getSystemSettingParamTool()
							.getSettingParams().get(ParamsKey.accessOffdutyStartTime.getVal());
					String sEnd = (String) getSystemSettingParamTool()
							.getSettingParams().get(ParamsKey.accessOffdutyEndTime.getVal());
					// 统计时间
					if(!StringUtils.isEmpty(sStart) && !StringUtils.isEmpty(sEnd)) {
						offDutyWorkAccess.setParams(sStart, sEnd);
						getLogger().warn("【" + getSimpleDateFormat().format(new Date()) + "】 ：掃描所有的卡片緩存資料保存所有卡片當天最後一筆資料...");
						//--统计时设置统计时间，防止设置成11:59:59到后面变成00:00:00存储到库里面异常--/
						offDutyWorkAccess.setScheduleDate(new Date());
						offDutyWorkAccess.scanAllTagDetails();
					}
				}
			}
		} catch (Exception e) {
			getLogger().error("【" + getSimpleDateFormat().format(new Date()) + "】: 掃描所有的卡片緩存資料出現異常: " + e.getMessage());
		}
	}
	public OffDutyWorkAccess getOffDutyWorkAccess() {
		return offDutyWorkAccess;
	}
	public void setOffDutyWorkAccess(OffDutyWorkAccess offDutyWorkAccess) {
		this.offDutyWorkAccess = offDutyWorkAccess;
	}
	public SimpleDateFormat getSimpleDateFormat() {
		return simpleDateFormat;
	}
	public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
		this.simpleDateFormat = simpleDateFormat;
	}
	public SystemSettingParamTool getSystemSettingParamTool() {
		return systemSettingParamTool;
	}
	public void setSystemSettingParamTool(
			SystemSettingParamTool systemSettingParamTool) {
		this.systemSettingParamTool = systemSettingParamTool;
	}
	public static Logger getLogger() {
		return logger;
	}
}
