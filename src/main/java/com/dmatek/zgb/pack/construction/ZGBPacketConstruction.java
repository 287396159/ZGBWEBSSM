package com.dmatek.zgb.pack.construction;

import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.utils.StringUtil;

public class ZGBPacketConstruction implements PacketConstruction<TagDetail> {
	private static final int SINGLE_LEN = 24;
	/**
	 * 构建ZGB数据包
	 * 0xFC(0) + 0x03(1) + Index(2) + type(3) + tagId(4-5) + battery(6) + 
	 * notMoveTime(7-8) + sleepTime(9-10) + referId(11-12) + rssi(13) + 
	 * year(14-15) + month(16) + day(17) + hour(18) + minute(19) + second(20) + 
	 * alarm(21) + cs(22) + 0xFB(23)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public byte[] reconstructed(TagDetail tagDetail) throws Exception {
		byte[] bytes = new byte[SINGLE_LEN];
		bytes[0] = (byte) 0xFC;
		bytes[1] = 0x03; 
		// 序列号
		bytes[2] = tagDetail.getIndex();
		// 是否发生人员求救
		bytes[3] = (byte) (tagDetail.isAlarm()?1:0);
		if(tagDetail.getId().length() != 4){
			return null;
		}
		// 卡片ID
		System.arraycopy(StringUtil.hexStringToByteArray(tagDetail.getId()), 0, bytes, 4, 2);
		// 电池电量
		bytes[6] = tagDetail.getBat();
		// 未移动时间
		bytes[7] = (byte)(tagDetail.getNotMoveTime() >> 8);
		bytes[8] = (byte)tagDetail.getNotMoveTime();
		// 休眠时间
		bytes[9] = (byte)(tagDetail.getSleepTime() >> 8);
		bytes[10]= (byte)tagDetail.getSleepTime();
		// 靠近的基站、靠近基站的信号强度
		System.arraycopy(StringUtil.hexStringToByteArray(tagDetail.getrId()), 0, bytes, 11, 2);
		bytes[13] = tagDetail.getRssi();
		// 上报的 年 + 月 + 日
		bytes[14] = (byte)(tagDetail.getReportTime().getYear() >> 8);
		bytes[15] = (byte)(tagDetail.getReportTime().getYear());
		bytes[16] = (byte) tagDetail.getReportTime().getMonth();
		bytes[17] = (byte) tagDetail.getReportTime().getDate();
		// 上报的时 + 分秒
		bytes[18] = (byte) tagDetail.getReportTime().getHours();
		bytes[19] = (byte) tagDetail.getReportTime().getMinutes(); 
		bytes[20] = (byte) tagDetail.getReportTime().getSeconds();
		//1 bit : 低电量
		//2 bit : 卡片未移动报警
		//3 bit : 区域管制报警
		//4 bit : 低电量报警
		//5 bit : 卡片异常报警
		bytes[21] = (byte)((tagDetail.isLowPower()?0x1:0x0) | ((tagDetail.isNotMove()?0x1:0x0)<<1) 
						| ((tagDetail.isAreaControl()?0x1:0x0) << 2) | ((tagDetail.isLowPower()?0x1:0x0) << 3) 
						| ((tagDetail.isAbnormalTag()?0x1:0x0) << 4)) ;
		
		bytes[bytes.length - 2] = 0;
		for (int i = 0; i < bytes.length - 2; i++) {
			bytes[bytes.length - 2] += bytes[i];
		}
		bytes[bytes.length - 1] = (byte) 0xFB;
		return bytes;
	}

}
