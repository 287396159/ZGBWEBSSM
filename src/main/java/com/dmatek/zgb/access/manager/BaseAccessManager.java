package com.dmatek.zgb.access.manager;
import org.apache.log4j.Logger;
import com.dmatek.zgb.access.base.BaseAccessWork;
import com.dmatek.zgb.access.status.DoorControlStatus;
import com.dmatek.zgb.broadcast.imessage.DoorControlStatusGetter;
import com.dmatek.zgb.broadcast.interfaces.IBroadcastTransmitter;
import com.dmatek.zgb.controller.setting.params.base.SystemSettingParamTool;
import com.dmatek.zgb.db.setting.service.NodeService;
import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.params.setting.cache.ParamsKey;
import com.dmatek.zgb.websocket.container.WarnWebSocketContainer;
/**
 * 进出管理器
 * @author zf
 * @data 2019年3月21日 上午9:17:07
 * @Description
 */
public abstract class BaseAccessManager implements IAccessManager<TagDetail> {
	private static final Logger logger = Logger.getLogger(BaseAccessManager.class);
	// 出入基站类型
	private static final int ACCESSTYPE = 2;
	//  当前的门控状态
	private DoorControlStatus currentDoorStatus;
	//  获取参考点讯息
	private NodeService nodeService;
	private BaseAccessWork enterAccessWork, leaveAccessWork;
	private DoorControlStatusGetter doorControlStatusGetter;
	private IBroadcastTransmitter broadCastTransmitter;
	//  获取系统参数工具
	private SystemSettingParamTool systemSettingParamTool;
	public BaseAccessManager(NodeService nodeService, BaseAccessWork enterAccessWork,
			BaseAccessWork leaveAccessWork, 
			SystemSettingParamTool systemSettingParamTool,
			DoorControlStatusGetter doorControlStatusGetter,
			IBroadcastTransmitter broadCastTransmitter) {
		super();
		this.nodeService = nodeService;
		this.enterAccessWork = enterAccessWork;
		this.leaveAccessWork = leaveAccessWork;
		this.systemSettingParamTool = systemSettingParamTool;
		this.currentDoorStatus = DoorControlStatus.EnterStatus;
		this.doorControlStatusGetter = doorControlStatusGetter;
		this.broadCastTransmitter = broadCastTransmitter;
	}
	/**
	 * 是否经过进出参考点
	 * @param tagDetail
	 * @return
	 * @throws Exception
	 */
	protected abstract boolean isAccess(TagDetail tagDetail) throws Exception;
	/**
	 * 是否记录进出统计记录
	 * @return
	 */
	protected boolean isAuto() {
		return (boolean) getSystemSettingParamTool().getSysParamValue(
			   ParamsKey.isAutoStatisticAccess);
	}
	// 获取门控状态
	public DoorControlStatus getCurrentDoorStatus() {
		return currentDoorStatus;
	}
	// 设置门控状态
	public void setCurrentDoorStatus(DoorControlStatus currentDoorStatus) {
		// 判断两次是否相同
		if(currentDoorStatus.getStatus() != getCurrentDoorStatus().getStatus()) {
			this.currentDoorStatus = currentDoorStatus;
			// 需要我们推送广播消息到前端
			if(null != broadCastTransmitter){
				try {
					getDoorControlStatusGetter().setDoorStatus(currentDoorStatus.getStatus());
					getBroadCastTransmitter().sendBroadcast(WarnWebSocketContainer.CONTAINERNAME, 
							getDoorControlStatusGetter().getBroadCastMessage());
				} catch (Exception e) {
					getLogger().error("推送門控狀態出現異常【Exception : " + e.getMessage() + "】");
				}
			}
		}
	}
	public NodeService getNodeService() {
		return nodeService;
	}
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}
	public SystemSettingParamTool getSystemSettingParamTool() {
		return systemSettingParamTool;
	}
	public void setSystemSettingParamTool(
			SystemSettingParamTool systemSettingParamTool) {
		this.systemSettingParamTool = systemSettingParamTool;
	}
	public static int getAccesstype() {
		return ACCESSTYPE;
	}
	public BaseAccessWork getEnterAccessWork() {
		return enterAccessWork;
	}
	public void setEnterAccessWork(BaseAccessWork enterAccessWork) {
		this.enterAccessWork = enterAccessWork;
	}
	public BaseAccessWork getLeaveAccessWork() {
		return leaveAccessWork;
	}
	public void setLeaveAccessWork(BaseAccessWork leaveAccessWork) {
		this.leaveAccessWork = leaveAccessWork;
	}
	public DoorControlStatusGetter getDoorControlStatusGetter() {
		return doorControlStatusGetter;
	}
	public void setDoorControlStatusGetter(
			DoorControlStatusGetter doorControlStatusGetter) {
		this.doorControlStatusGetter = doorControlStatusGetter;
	}
	public IBroadcastTransmitter getBroadCastTransmitter() {
		return broadCastTransmitter;
	}
	public void setBroadCastTransmitter(IBroadcastTransmitter broadCastTransmitter) {
		this.broadCastTransmitter = broadCastTransmitter;
	}
	public static Logger getLogger() {
		return logger;
	}
}
