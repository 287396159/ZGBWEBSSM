package com.dmatek.zgb.access.manager;
import com.dmatek.zgb.access.base.BaseAccessWork;
import com.dmatek.zgb.access.status.DoorControlStatus;
import com.dmatek.zgb.broadcast.imessage.DoorControlStatusGetter;
import com.dmatek.zgb.broadcast.interfaces.IBroadcastTransmitter;
import com.dmatek.zgb.controller.setting.params.base.SystemSettingParamTool;
import com.dmatek.zgb.db.pojo.setting.Node;
import com.dmatek.zgb.db.setting.service.NodeService;
import com.dmatek.zgb.monitor.device.detail.TagDetail;

public class AccessManager extends BaseAccessManager {
	public AccessManager(NodeService nodeService, 
			BaseAccessWork enterAccessWork, 
			BaseAccessWork leaveAccessWork,
			SystemSettingParamTool systemSettingParamTool,
			DoorControlStatusGetter doorControlStatusGetter,
			IBroadcastTransmitter broadCastTransmitter) {
		super(nodeService, enterAccessWork, leaveAccessWork, 
				systemSettingParamTool, 
			  doorControlStatusGetter, broadCastTransmitter);
	}
	protected boolean isAccess(TagDetail tagDetail) throws Exception {
		// 获取卡片详细记录中参考点类型
		Node node = getNodeService().findOne(tagDetail.getrId());
		if (null != node && node.getType() == getAccesstype()) {
			return true;
		}
		return false;
	}
	@Override
	public void saveAccessRecord(TagDetail tagDetail) throws Exception {
		if(isAuto()) {
			if(isAccess(tagDetail)) { // 经过出入统计参考点
				if(getCurrentDoorStatus() == DoorControlStatus.EnterStatus) { // 上班记录
					// 此时我们需要将这些上班记录添加到缓存里面
					getEnterAccessWork().execute(tagDetail);
				} else if(getCurrentDoorStatus() == DoorControlStatus.LeaveStatus) { // 下班记录
					// 我们需要将这些上班记录添加到缓存里面
					getLeaveAccessWork().execute(tagDetail);
				}
			}
		}
	}
}
