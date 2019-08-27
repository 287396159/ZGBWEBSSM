package com.dmatek.zgb.task.scan;

import com.dmatek.zgb.broadcast.imessage.IMessageGetter;
import com.dmatek.zgb.broadcast.interfaces.IBroadcastTransmitter;
import com.dmatek.zgb.websocket.container.WarnWebSocketContainer;
/**
 * 定时发送广播消息
 * @author zf
 * @data 2018年12月21日 下午4:13:15
 * @Description
 */
public class TimingBroadCastTask extends BaseScanTask {
	private IBroadcastTransmitter broadCastTransmitter;
	private IMessageGetter[] iMessageGetters;
	public TimingBroadCastTask(int peroid, IBroadcastTransmitter broadCastTransmitter,
		IMessageGetter...iMessageGetters) {
		super(peroid);
		this.broadCastTransmitter = broadCastTransmitter;
		this.iMessageGetters = iMessageGetters;
	}
	@Override
	public void scanTask() throws Exception {
		for (IMessageGetter iMessageGetter : iMessageGetters) {
			broadCastTransmitter.sendBroadcast(WarnWebSocketContainer.CONTAINERNAME, iMessageGetter.getBroadCastMessage());
		}
	}
}
