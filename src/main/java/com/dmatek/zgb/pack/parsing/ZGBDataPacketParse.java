package com.dmatek.zgb.pack.parsing;
import java.util.Date;
import com.dmatek.zgb.monitor.bean.TrackPacket;

public class ZGBDataPacketParse implements IDataPacketParse<TrackPacket>{
	/**
	 * 构建ZGB数据包
	 * 0xFC(0) + 0x03(1) + Index(2) + type(3) + tagId(4-5) + battery(6) + 
	 * notMoveTime(7-8) + sleepTime(9-10) + referId(11-12) + rssi(13) + 
	 * year(14-15) + month(16) + day(17) + hour(18) + minute(19) + second(20) + 
	 * alarm(21) + cs(22) + 0xFB(23)
	 */
	@Override
	public TrackPacket ParseData(byte[] bytes) throws Exception {
		if(null == bytes || bytes.length < singleLen){
			return null;
		}
		if((byte)0xFC != bytes[0] ||  (byte)0xFB != bytes[singleLen - 1]){
			return null;
		}
		//检验
		short cs = 0;
		for (int i = 0; i < singleLen - 2; i++) {
			cs += bytes[i];
		}
		cs &= 0xFF;
		if(((byte)cs) != bytes[singleLen - 2]){
			return null;
		}
		TrackPacket tgMsg = new TrackPacket();
		/**
		 * 构建ZGB数据包
		 * 0xFC(0) + 0x03(1) + Index(2) + type(3) + tagId(4-5) + battery(6) + 
		 * notMoveTime(7-8) + sleepTime(9-10) + referId(11-12) + rssi(13) + 
		 * year(14-15) + month(16) + day(17) + hour(18) + minute(19) + second(20) + 
		 * alarm(21) + cs(22) + 0xFB(23)
		 */
		tgMsg.setIndex(bytes[2]);
		tgMsg.setAlarm(bytes[3]==1?true:false);
		tgMsg.setId(String.format("%02X", bytes[4]) + String.format("%02X", bytes[5]));
		tgMsg.setBat(bytes[6]);
		tgMsg.setNotMoveTime(((bytes[7] & 0xFF) << 8 | (bytes[8] & 0xFF)));
		tgMsg.setSleepTime(((bytes[9] & 0xFF) << 8 | (bytes[10] & 0xFF)));
		tgMsg.setrId(String.format("%02X", bytes[11]) + String.format("%02X", bytes[12]));
		tgMsg.setRssi(bytes[13]);
		@SuppressWarnings("deprecation")
		Date report = new Date((bytes[14] & 0xFF) << 8 | (bytes[15] & 0xFF), 
								bytes[16], bytes[17], bytes[18], 
								bytes[19], bytes[20]);
		tgMsg.setReportTime(report);
		//设置警告讯息
		//1 bit : 低电量
		//2 bit : 卡片未移动报警
		//3 bit : 区域管制报警
		//4 bit : 低电量报警
		//5 bit : 卡片异常报警
		tgMsg.setLowPower((bytes[21] & 0x1)==0x1?true:false);
		tgMsg.setNotMove((bytes[21] & 0x2)==0x2?true:false);
		tgMsg.setAreaControl((bytes[21] & 0x4)==0x4?true:false);
		tgMsg.setLowPower((bytes[21] & 0x8)==0x8?true:false);
		tgMsg.setAbnormalTag((bytes[21] & 0x10)==0x10?true:false);
		return tgMsg;
	}
}
