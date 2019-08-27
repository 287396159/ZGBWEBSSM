package com.dmatek.zgb.broadcast.imessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.dmatek.zgb.broadcast.vo.BroadCastVo;
import com.dmatek.zgb.monitor.listener.MonitorStarter;

public class MonitorStateGetter extends BaseMessageGetter {
	private static int BROADCAST_WARN_TYPE = 2;
	@Autowired
	private MonitorStarter monitorStarter;
	public MonitorStateGetter(MonitorStarter monitorStarter){
		super();
		this.monitorStarter = monitorStarter;
	}
	@Override
	public BroadCastVo getBroadCastVo() throws Exception {
		BroadCastVo broadCastVo = new BroadCastVo();
		broadCastVo.setType(BROADCAST_WARN_TYPE);
		broadCastVo.setMsg("網絡監聽狀態...");
		broadCastVo.setData(monitorStarter.getState());
		return broadCastVo;
	}
}
