package com.dmatek.zgb.access.manager.scan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dmatek.zgb.access.manager.AccessManager;
import com.dmatek.zgb.access.status.DoorControlStatus;
import com.dmatek.zgb.controller.setting.params.base.SystemSettingParamTool;
import com.dmatek.zgb.params.setting.cache.ParamsKey;
import com.dmatek.zgb.task.scan.BaseScanTask;

public class ScanAccessManagerTask extends BaseScanTask {
	private AccessManager accessManager;
	private SystemSettingParamTool systemSettingParamTool;
	private SimpleDateFormat simpleDateFormat;
	private Object lock;
	private boolean control;
	public ScanAccessManagerTask(int peroid,AccessManager accessManager, 
			SystemSettingParamTool systemSettingParamTool, 
			Object lock) {
		super(peroid);
		this.accessManager = accessManager;
		this.systemSettingParamTool = systemSettingParamTool;
		this.simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.lock = lock;
	}
	@SuppressWarnings("deprecation")
	private Date getAccessDate(ParamsKey paramsKey, Date current) throws ParseException {
		Date accessTime = (Date) getSystemSettingParamTool().getSysParamValue(paramsKey);
		if(null != accessTime) {
			accessTime.setYear(current.getYear());
			accessTime.setMonth(current.getMonth());
			accessTime.setDate(current.getDate());
			return accessTime;
		}
		return null;
	}
	public void Pause() {
		getLogger().warn("【" + getSimpleDateFormat().format(new Date()) + "】門控狀態掃描管理器暫停...");
		setControl(true);
	}
	public void Resume() {
		synchronized (getLock()) {
			getLock().notify();
		}
		getLogger().warn("【" + getSimpleDateFormat().format(new Date()) + "】門控狀態掃描管理器繼續運行...");
		setControl(false);
	}
	@Override
	public void run() {
		setMark(true);
		while(isMark()) {
			synchronized (getLock()) {
				if(isControl()) {
					// 暂停当前的线程
					try {
						getLock().wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				scanTask();
			} catch (Exception e) {
				getLogger().error("掃描任務線程【" + this.getClass().getName() + "】出現異常: " + e.toString());
			}
			try {
				Thread.sleep(getPeroid());
			} catch (InterruptedException e) {
				
			}
		}
	}
	@Override
	public void scanTask() throws Exception {
		Date current = new Date();
		Date onDutyStartTime = getAccessDate(ParamsKey.accessOndutyStartTime,
				current);
		Date onDutyEndTime = getAccessDate(ParamsKey.accessOndutyEndTime,
				current);
		Date offDutyStartTime = getAccessDate(ParamsKey.accessOffdutyStartTime,
				current);
		Date offDutyEndTime = getAccessDate(ParamsKey.accessOffdutyEndTime,
				current);
		// 所有的時間都设置成当天
		if (null != onDutyStartTime && null != onDutyEndTime
				&& onDutyStartTime.compareTo(current) <= 0
				&& onDutyEndTime.compareTo(current) > 0) {
			// 此时应该处在上班检测状态
			if (getAccessManager().getCurrentDoorStatus() != DoorControlStatus.EnterStatus) {
				// 如果当前没有处在进入的状态，我们要修改里面的状态
				getLogger().warn(
						getSimpleDateFormat().format(new Date())
								+ " : 改变门控状态为进入状态");
				getAccessManager().setCurrentDoorStatus(
						DoorControlStatus.EnterStatus);
			}
		} else if (null != offDutyStartTime && null != offDutyEndTime
				&& offDutyStartTime.compareTo(current) <= 0
				&& offDutyEndTime.compareTo(current) > 0) {
			// 此时应该处在下班检测状态
			if (getAccessManager().getCurrentDoorStatus() != DoorControlStatus.LeaveStatus) {
				// 如果当前没有处在离开的状态，我们要修改里面的状态
				getLogger().warn(
						getSimpleDateFormat().format(new Date())
								+ " : 改变门控状态为离开状态");
				getAccessManager().setCurrentDoorStatus(
						DoorControlStatus.LeaveStatus);
			}
		} else {
			// 此时应该处在不检测上下班状态
			if (getAccessManager().getCurrentDoorStatus() != DoorControlStatus.RestStatus) {
				// 如果当前没有处在不检测上下班状态，我们要修改里面的状态
				getLogger().warn(
						getSimpleDateFormat().format(new Date())
								+ " : 改变门控状态为休息状态");
				getAccessManager().setCurrentDoorStatus(
						DoorControlStatus.RestStatus);
			}
		}
	}
	public AccessManager getAccessManager() {
		return accessManager;
	}
	public void setAccessManager(AccessManager accessManager) {
		this.accessManager = accessManager;
	}
	public SystemSettingParamTool getSystemSettingParamTool() {
		return systemSettingParamTool;
	}
	public void setSystemSettingParamTool(
			SystemSettingParamTool systemSettingParamTool) {
		this.systemSettingParamTool = systemSettingParamTool;
	}
	public SimpleDateFormat getSimpleDateFormat() {
		return simpleDateFormat;
	}
	public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
		this.simpleDateFormat = simpleDateFormat;
	}
	public Object getLock() {
		return lock;
	}
	public void setLock(Object lock) {
		this.lock = lock;
	}
	public boolean isControl() {
		return control;
	}
	public void setControl(boolean control) {
		this.control = control;
	}
}
