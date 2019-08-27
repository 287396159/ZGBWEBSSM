package com.dmatek.zgb.broadcast.imessage;

import com.dmatek.zgb.broadcast.vo.BroadCastVo;

public class KickOutMessageGetter extends BaseMessageGetter {
	private static int BROADCAST_WARN_TYPE = 3;
	public static String KICKOUT_TEXT = "你已被其他用戶強制下線...";
	public static String LANDINGELSEWHERE_TEXT = "當前賬戶已在其他地方登錄...";
	private String msg;
	public  KickOutMessageGetter() {
		this.msg = KICKOUT_TEXT;
	}
	public  KickOutMessageGetter(String msg) {
		this.msg = msg;
	}
	@Override
	public BroadCastVo getBroadCastVo() throws Exception {
		return new BroadCastVo(BROADCAST_WARN_TYPE, getMsg(), null);
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
