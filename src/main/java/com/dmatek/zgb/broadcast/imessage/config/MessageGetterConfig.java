package com.dmatek.zgb.broadcast.imessage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmatek.zgb.access.status.DoorControlStatus;
import com.dmatek.zgb.broadcast.imessage.DoorControlStatusGetter;
import com.dmatek.zgb.broadcast.imessage.KickOutMessageGetter;
import com.dmatek.zgb.broadcast.imessage.MonitorStateGetter;
import com.dmatek.zgb.broadcast.imessage.WarnMessageGetter;
import com.dmatek.zgb.broadcast.interfaces.IBroadcastTransmitter;
import com.dmatek.zgb.broadcast.interfaces.imp.BroadCastTransmitter;
import com.dmatek.zgb.monitor.listener.MonitorStarter;
import com.dmatek.zgb.warn.bean.AbnormalBaseWarn;
import com.dmatek.zgb.warn.bean.AbnormalReferWarn;
import com.dmatek.zgb.warn.bean.AbnormalTagWarn;
import com.dmatek.zgb.warn.bean.AreaControlWarn;
import com.dmatek.zgb.warn.bean.LowPowerWarn;
import com.dmatek.zgb.warn.bean.NotMoveWarn;
import com.dmatek.zgb.warn.bean.PersonnelHelpWarn;
import com.dmatek.zgb.warn.cache.base.BaseWarnCache;
import com.dmatek.zgb.websocket.container.WebSocketContainerManager;

@Configuration
public class MessageGetterConfig {
	@Autowired
	private BaseWarnCache<PersonnelHelpWarn> personnelHelpWarns;
	@Autowired
	private BaseWarnCache<AreaControlWarn> areaControlWarns;
	@Autowired
	private BaseWarnCache<NotMoveWarn> notMoveWarns;
	@Autowired
	private BaseWarnCache<LowPowerWarn> lowPowerWarns;
	@Autowired
	private BaseWarnCache<AbnormalTagWarn> abnormalTagWarns;
	@Autowired
	private BaseWarnCache<AbnormalBaseWarn> abnormalBaseWarns;
	@Autowired
	private BaseWarnCache<AbnormalReferWarn> abnormalReferWarns;
	@Autowired
	private WebSocketContainerManager webSocketContainerManager;
	@Autowired
	private MonitorStarter monitorStarter;
	@Bean
	public WarnMessageGetter getWarnMessageGetter(){
		return new WarnMessageGetter(personnelHelpWarns, areaControlWarns,
				notMoveWarns, lowPowerWarns, abnormalTagWarns,
				abnormalBaseWarns, abnormalReferWarns);
	}
	@Bean
	public MonitorStateGetter getMonitorStateGetter(){
		return new MonitorStateGetter(monitorStarter);
	}
	@Bean
	public IBroadcastTransmitter getBroadcastTransmitter(){
		return new BroadCastTransmitter(webSocketContainerManager);
	}
	@Bean
	public KickOutMessageGetter getKickOutMessageGetter() {
		return new KickOutMessageGetter();
	}
	@Bean
	public DoorControlStatusGetter getDoorControlStatusGetter() {
		return new DoorControlStatusGetter("門控狀態改變", DoorControlStatus.EnterStatus.getStatus());
	}
}
