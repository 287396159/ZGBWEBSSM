package com.dmatek.zgb.monitor.bean;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
/**
 * 卡片上报上来的基站讯息
 * @author zf
 * @data 2018年12月13日 上午9:52:42
 * @Description
 */
public class TagPacket extends BaseDevicePack implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rId; // 参考点ID
    private byte index;// 设备上报的序列号
    private boolean isAlarm;// 是否产生人员求救
    private byte rssi,bat;// 分别是信号强度、电池电量
    private int staticSleep, notMoveTime;// 静止时休眠时间、未移动时间
    private int referNum;// 本次数据包上报的参考点数量
    // 本次上报上来的参考点信号强度集合
    private HashMap<String, ReferSig> curReferSigs = new HashMap<String, ReferSig>();
    private int jumpnum;//连续跳点次数
	private String lastjumpId;//上一次跳点的ID
	public TagPacket() {
		super();
	}
	public TagPacket(String id, int sleepTime, long tick, boolean status,
			Date reportTime, int version, String rId, byte index,
			boolean isAlarm, byte rssi, byte bat, int staticSleep,
			int notMoveTime, int referNum,
			HashMap<String, ReferSig> curReferSigs, int jumpnum,
			String lastjumpId) {
		super(id, sleepTime, tick, status, reportTime, version);
		this.rId = rId;
		this.index = index;
		this.isAlarm = isAlarm;
		this.rssi = rssi;
		this.bat = bat;
		this.staticSleep = staticSleep;
		this.notMoveTime = notMoveTime;
		this.referNum = referNum;
		this.curReferSigs = curReferSigs;
		this.jumpnum = jumpnum;
		this.lastjumpId = lastjumpId;
	}
	public String getrId() {
		return rId;
	}
	public void setrId(String rId) {
		this.rId = rId;
	}
	public boolean isAlarm() {
		return isAlarm;
	}
	public void setAlarm(boolean isAlarm) {
		this.isAlarm = isAlarm;
	}
	public byte getRssi() {
		return rssi;
	}
	public void setRssi(byte rssi) {
		this.rssi = rssi;
	}
	public byte getBat() {
		return bat;
	}
	public void setBat(byte bat) {
		this.bat = bat;
	}
	public int getStaticSleep() {
		return staticSleep;
	}
	public void setStaticSleep(int staticSleep) {
		this.staticSleep = staticSleep;
	}
	public int getNotMoveTime() {
		return notMoveTime;
	}
	public void setNotMoveTime(int notMoveTime) {
		this.notMoveTime = notMoveTime;
	}
	public int getReferNum() {
		return referNum;
	}
	public void setReferNum(int referNum) {
		this.referNum = referNum;
	}
	public HashMap<String, ReferSig> getCurrefsigs() {
		return curReferSigs;
	}
	public void setCurrefsigs(HashMap<String, ReferSig> currefsigs) {
		this.curReferSigs = currefsigs;
	}
	public int getJumpnum() {
		return jumpnum;
	}
	public void setJumpnum(int jumpnum) {
		this.jumpnum = jumpnum;
	}
	public String getLastjumpId() {
		return lastjumpId;
	}
	public void setLastjumpId(String lastjumpId) {
		this.lastjumpId = lastjumpId;
	}
	public byte getIndex() {
		return index;
	}
	public void setIndex(byte index) {
		this.index = index;
	}
	@Override
	public String toString() {
		return "TagPacket [rId=" + rId + ", index=" + index + ", isAlarm="
				+ isAlarm + ", rssi=" + rssi + ", bat=" + bat + ", getId()="
				+ getId() + ", getSleepTime()=" + getSleepTime() + "]";
	}
}
