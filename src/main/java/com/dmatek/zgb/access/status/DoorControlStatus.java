package com.dmatek.zgb.access.status;
/**
 * 门控状态
 * @author zf
 * @data 2019年3月21日 上午8:55:26
 * @Description
 */
public enum DoorControlStatus {
	EnterStatus(0),// 进入状态
	LeaveStatus(1),// 离开状态; 
	RestStatus(2);// 不记录上下班状态
	private int status;// 状态对应的值
	private DoorControlStatus(int status){
		this.status = status;
	}
	public static DoorControlStatus getDoorControlStatus(int status){
		switch (status) {
		case 0:return EnterStatus;
		case 1:return LeaveStatus;
		default:
			return RestStatus;
		}
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
