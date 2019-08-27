package com.dmatek.zgb.monitor.listener;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.dmatek.zgb.access.manager.AccessManager;
import com.dmatek.zgb.access.manager.scan.ScanAccessManagerTask;
import com.dmatek.zgb.access.onduty.workaccess.OffDutyWorkAccess;
import com.dmatek.zgb.broadcast.imessage.WarnMessageGetter;
import com.dmatek.zgb.broadcast.interfaces.IBroadcastTransmitter;
import com.dmatek.zgb.cache.tool.detail.CaptureTagCacheData;
import com.dmatek.zgb.controller.setting.params.base.SystemSettingParamTool;
import com.dmatek.zgb.db.access.service.AccessService;
import com.dmatek.zgb.db.setting.service.NodeService;
import com.dmatek.zgb.monitor.callback.ZGBMonitorCallBack;
import com.dmatek.zgb.params.setting.cache.ParamsKey;
import com.dmatek.zgb.redis.cache.AccessRedisCache;
import com.dmatek.zgb.redis.service.RedisService;
import com.dmatek.zgb.redis.util.RedisCacheName;
import com.dmatek.zgb.task.scan.BaseScanTask;
import com.dmatek.zgb.task.scan.CacheCleanUpTask;
import com.dmatek.zgb.task.scan.ClearAbnormalTagPackTask;
import com.dmatek.zgb.task.scan.ClearAccessCacheTask;
import com.dmatek.zgb.task.scan.ClearHandleWarnTask;
import com.dmatek.zgb.task.scan.ScanDevicesTask;
import com.dmatek.zgb.task.scan.TimingBroadCastTask;
import com.dmatek.zgb.task.timer.BaseTimePointTimer;
import com.dmatek.zgb.task.timer.ScanNodesTimer;
import com.dmatek.zgb.task.timer.ScheduleAccessTask;
import com.dmatek.zgb.task.timer.ScheduleAccessTimer;

public abstract class BaseAllTaskManager extends BaseAllWarmCacheManagement{
	private Logger logger = Logger.getLogger(BaseAllTaskManager.class);
	private static final String DEFAULT_STATISTIC_TIME = "21:00:00";
	private static final int CLEARHANDLEWARNTASK_PEROID = 60000;
	private static final int CACHECLEARUP_PEROID = 60000;
	private static final int BROADCAST_PEROID = 2000;
	private static final int CLEARACCESSCACHE_PEROID = 60000;
	private static final int CLEARABNORMALTAGPACKTASK_PEROID = 300000;
	private int checkedTagTime, checkedTagdisK1, checkedTagdisK2,
			checkedDevdisK1, checkedDevdisK2, autoClearHandleWarnTime;
	private boolean autoClearHandleWarn = true;
	@Autowired
	private SystemSettingParamTool systemSettingParamTool;
	@Autowired
	private ZGBMonitorCallBack zgbMonitorCallBack;
	@Autowired
	private IBroadcastTransmitter broadCastTransmitter;
	@Resource
	private RedisService redisService;
	@Autowired
	private AccessRedisCache accessRedisCache;
	@Autowired
	private AccessService accessService;
	@Autowired
	private NodeService nodeService;
	@Autowired
	private CaptureTagCacheData captureTagCacheData;
	// 信息获取器
	@Autowired
	private WarnMessageGetter warnMessageGetter;
	@Autowired
	private AccessManager accessManager;
	private BaseScanTask cacheCleanUpTask, devicesConnectionTask,
			clearHandleWarnTask, timingBroadCastTask, clearAccessCacheTask,
			clearAbnormalTagPackTask;
	@Autowired
	private ScanAccessManagerTask scanAccessManagerTask;
	private BaseTimePointTimer baseTimePointTimer;
	private ScheduleAccessTask scheduleAccessTask;
	@Resource(name="offDutyWorkAccess")
	private OffDutyWorkAccess offDutyWorkAccess;
	// 注入扫描设备的延时器
	@SuppressWarnings("unused")
	private ScanNodesTimer scanNodesTimer;
	/**
	 * 启动所有的扫描任务
	 * @throws Exception 
	 */
	protected void startAllTask() throws Exception {
		// 开启缓存清理的数据监听，每隔cacheClearUpPeroid时间自动清除已经被清理的警告讯息
		if (null == cacheCleanUpTask) {
			cacheCleanUpTask = new CacheCleanUpTask(CACHECLEARUP_PEROID,
					getPersonnelHelpWarnCache(), getNotMoveWarnCache(),
					getLowPowerWarnCache(), getAreaControlWarnCache(),
					getAbnormalTagWarnCache(), getAbnormalReferWarnCache(),
					getAbnormalBaseWarnCache());
		}
		cacheCleanUpTask.Start();
		if (null == clearAbnormalTagPackTask) {
			clearAbnormalTagPackTask = new ClearAbnormalTagPackTask(CLEARABNORMALTAGPACKTASK_PEROID,
					captureTagCacheData);
		}
		clearAbnormalTagPackTask.Start();
		// 获取设备断开检测的扫描时间间隔
		if(null == devicesConnectionTask) {
			// 扫描设备任务
			checkedTagTime = (int) systemSettingParamTool
					.getSysParamValue(ParamsKey.checkdistime);
			checkedTagdisK1 = (int) systemSettingParamTool
					.getSysParamValue(ParamsKey.checkedtagdisK1);
			checkedTagdisK2 = (int) systemSettingParamTool
					.getSysParamValue(ParamsKey.checkedtagdisK2);
			checkedDevdisK1 = (int) systemSettingParamTool
					.getSysParamValue(ParamsKey.checkednodedisK1);
			checkedDevdisK2 = (int) systemSettingParamTool
					.getSysParamValue(ParamsKey.checkednodedisK2);
			devicesConnectionTask = new ScanDevicesTask(checkedTagTime * 1000,
					checkedTagdisK1, checkedTagdisK2, checkedDevdisK1,
					checkedDevdisK2, redisService,
					RedisCacheName.TAGS_CACHE_NAME,
					RedisCacheName.NODES_CACHE_NAME,
					RedisCacheName.REFERS_CACHE_NAME, this,
					zgbMonitorCallBack.getBaseTagDetailCapture(),
					zgbMonitorCallBack.getBaseNodeDetailCapture(),
					zgbMonitorCallBack.getBaseReferDetailCapture());
		}
		devicesConnectionTask.Start();
		// 自动将已经处理的警告清理掉
		autoClearHandleWarn = (boolean) systemSettingParamTool
				.getSysParamValue(ParamsKey.autoClearHandleWarn);
		if(autoClearHandleWarn) {
			if (null == clearHandleWarnTask) {
				// 清理处理警告任务
				autoClearHandleWarnTime = (int) systemSettingParamTool
						.getSysParamValue(ParamsKey.autoClearHandleWarnTime);
				clearHandleWarnTask = new ClearHandleWarnTask(
						CLEARHANDLEWARNTASK_PEROID, autoClearHandleWarnTime,
						getPersonnelHelpWarnCache(), getNotMoveWarnCache(),
						getLowPowerWarnCache(), getAreaControlWarnCache(),
						getAbnormalTagWarnCache(), getAbnormalReferWarnCache(),
						getAbnormalBaseWarnCache());
			}
			clearHandleWarnTask.Start();
		}
		// 清理出入缓存记录
		if (null == clearAccessCacheTask) {
			clearAccessCacheTask = new ClearAccessCacheTask(
					CLEARACCESSCACHE_PEROID, accessRedisCache, accessService);
		}
		clearAccessCacheTask.Start();
		// 定时广播
		if (null == timingBroadCastTask) {
			timingBroadCastTask = new TimingBroadCastTask(BROADCAST_PEROID,
					broadCastTransmitter, warnMessageGetter);
		}
		timingBroadCastTask.Start();
		if (null != scanAccessManagerTask && !scanAccessManagerTask.isAlive()) {
			scanAccessManagerTask.Start();
		}
		// 启动一个线程用于每天统计上下班记录
		if (null == baseTimePointTimer) {
			scheduleAccessTask = new ScheduleAccessTask(offDutyWorkAccess, systemSettingParamTool);
			String sStatisticTime = (String) systemSettingParamTool
					.getSettingParams().get(ParamsKey.accessStatisticTime.getVal());
			if (StringUtils.isEmpty(sStatisticTime)) {
				sStatisticTime = DEFAULT_STATISTIC_TIME;
			}
			baseTimePointTimer = new ScheduleAccessTimer(sStatisticTime, scheduleAccessTask);
			baseTimePointTimer.start();
		}
	}
	/**
	 * 关闭所有的扫描任务
	 */
	protected void stopAllTask(){
		logger.warn("-----关闭所有的扫描任务-----");
		// 结束缓存清理的数据监听
		if (null != cacheCleanUpTask) {
			cacheCleanUpTask.Stop();
			cacheCleanUpTask = null;
		}
		if (null != devicesConnectionTask) {
			devicesConnectionTask.Stop();
			devicesConnectionTask = null;
		}
		if (null != clearHandleWarnTask) {
			clearHandleWarnTask.Stop();
			clearHandleWarnTask = null;
		}
		if (null != timingBroadCastTask) {
			timingBroadCastTask.Stop();
			timingBroadCastTask = null;
		}
		if (null != clearAccessCacheTask) {
			clearAccessCacheTask.Stop();
			clearAccessCacheTask = null;
		}
		if (null != clearAbnormalTagPackTask) {
			clearAbnormalTagPackTask.Stop();
			clearAbnormalTagPackTask = null;
		}
	}
}
