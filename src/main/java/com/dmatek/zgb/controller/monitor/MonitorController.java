package com.dmatek.zgb.controller.monitor;

import java.net.BindException;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dmatek.zgb.log.anno.Operation;
import com.dmatek.zgb.monitor.listener.MonitorStarter;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.websocket.container.WebSocketContainerManager;

@RestController
@RequestMapping("/monitor")
@PermissionName(name="網絡監控權限設置")
@RequiresPermissions(value="monitor:*")
public class MonitorController {
	@Autowired
	private MonitorStarter monitorStarter;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private WebSocketContainerManager webSocketContainerManager;
	/**
	 * 開始啟動網絡監聽
	 * @throws Exception 
	 * @throws UnknownHostException 
	 * @throws SocketException 
	 */
	@GetMapping("/start")
	@PermissionName(name="監聽權限設置")
	@RequiresPermissions(value="monitor:listen")
	@Operation(description="開啟網絡監聽")
	public Result StartListener() throws Exception {
		try {
			monitorStarter.start();
		} catch(BindException e){
			return viewResultFactory.errorResult("啟動網絡監聽的端口可能被占用...");
		} catch (UnknownHostException | SocketException e) {
			return viewResultFactory.errorResult("啟動網絡監聽時出現異常: " + e.toString());
		}
		// 需要我們推送開啟網絡監聽的消息
		monitorStarter.sendMonitorState();
		return viewResultFactory.successResult();
	}
	/**
	 * 關閉網絡監聽
	 */
	@GetMapping("/close")
	@PermissionName(name="監聽權限設置")
	@RequiresPermissions(value="monitor:listen")
	@Operation(description="關閉網絡監聽")
	public Result CloseListener(){
		monitorStarter.stop();
		// 需要我們推送關閉網絡監聽的消息
		monitorStarter.sendMonitorState();
		return viewResultFactory.successResult();
	}
	/**
	 * 獲取當前網絡監聽狀態
	 * @return
	 */
	@GetMapping("/state")
	public Result getMonitorState() throws Exception{
		return viewResultFactory.successResult(monitorStarter.getState());
	}
}
