package com.dmatek.zgb.access;

import java.util.Timer;
import java.util.TimerTask;

import com.dmatek.zgb.access.manager.AccessManager;
import com.dmatek.zgb.access.manager.scan.ScanAccessManagerTask;
import com.dmatek.zgb.access.status.DoorControlStatus;
import com.dmatek.zgb.comparator.DescAccessComparator;
import com.dmatek.zgb.controller.setting.params.base.SystemSettingParamTool;
import com.dmatek.zgb.db.access.service.AccessService;
import com.dmatek.zgb.params.setting.cache.ParamsKey;
import com.dmatek.zgb.redis.cache.AccessRedisCache;

public class AccessTool extends BaseAccessTool {
	private ScanAccessManagerTask scanAccessManagerTask;
	private AccessManager accessManager;
	private SystemSettingParamTool sysSettingTool;
	public AccessTool(AccessRedisCache iRedisCache,
			IAccessDetailCapture iAccessDetailCapture,
			DescAccessComparator baseAccessComparator,
			AccessService accessService,
			AccessManager accessManager, 
			ScanAccessManagerTask scanAccessManagerTask,
			SystemSettingParamTool sysSettingTool) {
		super(iRedisCache, iAccessDetailCapture, baseAccessComparator, accessService);
		this.scanAccessManagerTask = scanAccessManagerTask;
		this.accessManager = accessManager;
		this.sysSettingTool = sysSettingTool;
	}
	public void manualSetCurrentDoorStatus(int status) throws InterruptedException {
		// 1.首先我們要阻塞掃描状态时间的线程, 让这个线程处在阻塞状态
		getScanAccessManagerTask().Pause();
		// 2.改变当前的门控状态
		getAccessManager().setCurrentDoorStatus(
				DoorControlStatus.getDoorControlStatus(status));
		// 3.开启一个定时线程
		int delay = (int) getSysSettingTool().getSysParamValue(
				ParamsKey.manualSetCurrentDoorStatusDelay);
		if(delay > 0) {
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					getScanAccessManagerTask().Resume();
				}
			}, delay * 1000);
		}
	}
	/**
	 * 獲取門控狀態
	 * @return
	 */
	public int getDoorControlStatus() {
		return getAccessManager().getCurrentDoorStatus().getStatus();
	}
	public ScanAccessManagerTask getScanAccessManagerTask() {
		return scanAccessManagerTask;
	}
	public void setScanAccessManagerTask(ScanAccessManagerTask scanAccessManagerTask) {
		this.scanAccessManagerTask = scanAccessManagerTask;
	}
	public AccessManager getAccessManager() {
		return accessManager;
	}
	public void setAccessManager(AccessManager accessManager) {
		this.accessManager = accessManager;
	}
	public SystemSettingParamTool getSysSettingTool() {
		return sysSettingTool;
	}
	public void setSysSettingTool(SystemSettingParamTool sysSettingTool) {
		this.sysSettingTool = sysSettingTool;
	}
}
