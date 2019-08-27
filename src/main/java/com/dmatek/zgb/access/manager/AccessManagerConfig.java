package com.dmatek.zgb.access.manager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmatek.zgb.access.offduty.LeaveAccessRecord;
import com.dmatek.zgb.access.onduty.EntryAccessRecord;
import com.dmatek.zgb.broadcast.imessage.DoorControlStatusGetter;
import com.dmatek.zgb.broadcast.interfaces.IBroadcastTransmitter;
import com.dmatek.zgb.controller.setting.params.base.SystemSettingParamTool;
import com.dmatek.zgb.db.setting.service.NodeService;
import com.dmatek.zgb.redis.cache.AccessRedisCache;

@Configuration
public class AccessManagerConfig {
	@Bean("accessLock")
	public Object getScanAccessLock() {
		return new Object();
	}
	@Bean("enteryAccessRecord")
	public EntryAccessRecord getBaseEntryAccessRecord(AccessRedisCache iRedisCache) {
		return new EntryAccessRecord(iRedisCache);
	}
	@Bean("leaveAccessRecord")
	public LeaveAccessRecord getBaseLeaveAccessRecord(AccessRedisCache iRedisCache) {
		return new LeaveAccessRecord(iRedisCache);
	}
	@Bean("accessManager")
	public AccessManager getAccessManager(NodeService nodeService,
			EntryAccessRecord enterAccessWork, 
			LeaveAccessRecord leaveAccessWork,
			SystemSettingParamTool systemSettingParamTool, 
			DoorControlStatusGetter doorControlStatusGetter,
			IBroadcastTransmitter broadCastTransmitter) {
		return new AccessManager(nodeService, enterAccessWork,
				leaveAccessWork, systemSettingParamTool,
				doorControlStatusGetter, broadCastTransmitter);
	}
}
