package com.dmatek.zgb.monitor.listener;

import java.io.IOException;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.dmatek.zgb.broadcast.imessage.MonitorStateGetter;
import com.dmatek.zgb.broadcast.interfaces.IBroadcastTransmitter;
import com.dmatek.zgb.controller.setting.params.base.NetWorkSettingParamTool;
import com.dmatek.zgb.controller.setting.params.base.SystemSettingParamTool;
import com.dmatek.zgb.monitor.BaseDataMonitor;
import com.dmatek.zgb.params.setting.cache.ParamsKey;
import com.dmatek.zgb.params.setting.cache.SettingParams;
import com.dmatek.zgb.redis.service.RedisService;
import com.dmatek.zgb.tool.Inetaddress.InetAddressTools;
import com.dmatek.zgb.websocket.container.WebSocketContainer;
/**
 * 监听启动器
 * @author zf
 * @data 2018年12月17日 上午11:09:26
 * @Description
 */
@Component
public class MonitorStarter extends BaseAllTaskManager {
	private Logger logger = Logger.getLogger(MonitorStarter.class);
	@Autowired
	private BaseDataMonitor baseDataMonitor;
	@Autowired
	private SettingParams settingParams;
	@Autowired
	private NetWorkSettingParamTool netWorkSettingParamTool;
	@Autowired
	private SystemSettingParamTool sysSettingParamTool;
	@Autowired
	private MonitorStateGetter monitorStateGetter;
	@Autowired
	private IBroadcastTransmitter iBroadCastTransmitter;
	@Resource
	private RedisService redisService;
	/**
	 * 开启监听
	 * @throws Exception 
	 */
	public void start() throws Exception {
		logger.warn("############-启动应用程式-############");
		// 加载设置的参数讯息
		try {
			settingParams.loadParams(SettingParams.PARAMS_PATH);
		} catch (IOException e) {
			logger.error("加载系统设置的参数信息出现异常...");
		}
		System.out.println(settingParams.toString());
		// 开启监听网络数据包, 出现问题时后面都不会执行
		if (null != baseDataMonitor) {
			// 获取ip和端口信息
			String sIp = (String) netWorkSettingParamTool.getNetWorkParamValue(ParamsKey.ip);
			int port = (int) netWorkSettingParamTool.getNetWorkParamValue(ParamsKey.port);
			int optimizeModel = (int) sysSettingParamTool.getSysParamValue(ParamsKey.optimizeMode);
			int optimizeVal = (int) sysSettingParamTool.getSysParamValue(ParamsKey.optimizeValue);
			baseDataMonitor.initParam(InetAddressTools.getLocalIp(sIp), port, optimizeModel, optimizeVal);
			baseDataMonitor.start();
		}
		// 启动所有的任务
		startAllTask();
		// 清空所有的警告缓存数据包
		clearWarnCache();
		// 发送开启监听的广播
		sendMonitorState();
	}
	/**
	 * 结束监听
	 */
	public void stop() {
		logger.warn("############-停止应用程式-############");
		// 停止所有的任务
		stopAllTask();
		// 关闭监听网络数据包
		if(null != baseDataMonitor) {
			baseDataMonitor.stop();
		}
		sendMonitorState();
	}
	/**
	 * 获取网络监听状态
	 * @return
	 */
	public boolean getState(){
		return baseDataMonitor.getState();
	}
	/**
	 * 发送监听状态的广播
	 */
	public void sendMonitorState() {
		try {
			iBroadCastTransmitter.sendBroadcast(WebSocketContainer.CONTAINERNAME,monitorStateGetter.getBroadCastMessage());
		} catch (Exception e) {
			logger.error("发送网络监听状态广播时出现异常...");
		}
	}
}
