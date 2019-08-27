package com.dmatek.zgb.broadcast.imessage;

import com.dmatek.zgb.broadcast.vo.BroadCastVo;

public class DoorControlStatusGetter extends BaseMessageGetter {
	private static int BROADCAST_WARN_TYPE = 4;
	private String msg;
	private int doorStatus;
	public DoorControlStatusGetter(String msg, int doorStatus) {
		super();
		this.msg = msg;
		this.doorStatus = doorStatus;
	}
	@Override
	public BroadCastVo getBroadCastVo() throws Exception {
		return new BroadCastVo(BROADCAST_WARN_TYPE, getMsg(), getDoorStatus());
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public static int getBROADCAST_WARN_TYPE() {
		return BROADCAST_WARN_TYPE;
	}
	public static void setBROADCAST_WARN_TYPE(int bROADCAST_WARN_TYPE) {
		BROADCAST_WARN_TYPE = bROADCAST_WARN_TYPE;
	}
	public int getDoorStatus() {
		return doorStatus;
	}
	public void setDoorStatus(int doorStatus) {
		this.doorStatus = doorStatus;
	}
}
