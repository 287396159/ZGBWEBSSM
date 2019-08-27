package com.dmatek.zgb.monitor.bean;

public class TrackPacket extends TagPacket {
	private static final long serialVersionUID = 1L;
	private boolean isPersonnelHelp, isNotMove, isLowPower, isAreaControl, isAbnormalTag;
	public TrackPacket() {
		super();
	}
	public TrackPacket(TagPacket tagPacket, boolean isPersonnelHelp, boolean isNotMove,
			boolean isLowPower, boolean isAreaControl, boolean isAbnormalTag) {
		super(tagPacket.getId(),tagPacket.getSleepTime(),tagPacket.getTick(),tagPacket.isStatus(),
			  tagPacket.getReportTime(), tagPacket.getVersion(), tagPacket.getrId(), 
			  tagPacket.getIndex(), tagPacket.isAlarm(), tagPacket.getRssi(),
			  tagPacket.getBat(), tagPacket.getStaticSleep(), tagPacket.getNotMoveTime(),
			  tagPacket.getReferNum(), tagPacket.getCurrefsigs(), tagPacket.getJumpnum(),
			  tagPacket.getLastjumpId());
		this.isPersonnelHelp = isPersonnelHelp;
		this.isNotMove = isNotMove;
		this.isLowPower = isLowPower;
		this.isAreaControl = isAreaControl;
		this.isAbnormalTag = isAbnormalTag;
	}
	public boolean isPersonnelHelp() {
		return isPersonnelHelp;
	}
	public void setPersonnelHelp(boolean isPersonnelHelp) {
		this.isPersonnelHelp = isPersonnelHelp;
	}
	public boolean isNotMove() {
		return isNotMove;
	}
	public void setNotMove(boolean isNotMove) {
		this.isNotMove = isNotMove;
	}
	public boolean isLowPower() {
		return isLowPower;
	}
	public void setLowPower(boolean isLowPower) {
		this.isLowPower = isLowPower;
	}
	public boolean isAreaControl() {
		return isAreaControl;
	}
	public void setAreaControl(boolean isAreaControl) {
		this.isAreaControl = isAreaControl;
	}
	public boolean isAbnormalTag() {
		return isAbnormalTag;
	}
	public void setAbnormalTag(boolean isAbnormalTag) {
		this.isAbnormalTag = isAbnormalTag;
	}
}
