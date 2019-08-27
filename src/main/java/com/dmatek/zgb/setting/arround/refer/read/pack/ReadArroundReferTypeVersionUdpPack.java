package com.dmatek.zgb.setting.arround.refer.read.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseArroundReadUdpPack;
import com.dmatek.zgb.setting.vo.SearchDevicePo;
import com.dmatek.zgb.utils.StringUtil;

public class ReadArroundReferTypeVersionUdpPack extends BaseArroundReadUdpPack {
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 6;
	private static final int BYTE_SIZE = 7;
	private static final byte TYPE = 0x42;
	public static final String KEY_NAME = "ReadArroundReferTypeVersionUdpPack";
	
	@SuppressWarnings("unchecked")
	@Override
	public SearchDevicePo parse() throws Exception {
		byte[] backBytes = getBackBytes();
		if (null != backBytes && backBytes.length == BYTE_SIZE) {
			int version = (backBytes[3] & 0xFF) << 24 | (backBytes[4] & 0xFF) << 16 | 
						  (backBytes[5] & 0xFF) << 8  | (backBytes[6] & 0xFF);
			String devName = "";
			switch (backBytes[2]) {
			case 0x01:
				devName = "ZB2530-01PA_02PA_WIFI_V01.00";
				break;
			case 0x02:
				devName = "ZB2530-03_V1.0";
				break;
			case 0x03:
				devName = "ZB2530-LAN_WIFI_04_V01.01";
				break;
			case 0x04:
				devName = "ZB2530-04-V1.1";
				break;
			}
			String id = String.format("%02X", backBytes[0]) + String.format("%02X", backBytes[1]);
			return new SearchDevicePo(id, devName, StringUtil.convertVersion(version));
		}
		return null;
	}
	@Override
	public int byteSize() {
		return BYTE_SIZE;
	}
	@Override
	public String keyName() {
		return KEY_NAME;
	}
	@Override
	protected int size() {
		return SIZE;
	}
	@Override
	protected byte type() {
		return TYPE;
	}
	@Override
	public int obtainDelayMs() {
		// TODO Auto-generated method stub
		return 6000;
	}
}
