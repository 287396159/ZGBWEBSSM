package com.dmatek.zgb.setting.directly.node.read.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlyNodeReadUdpPack;
import com.dmatek.zgb.utils.StringUtil;

public class ReadNodeTypeVersionUdpPack extends BaseDirectlyNodeReadUdpPack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 6;
	private static final int BYTE_SIZE = 7;
	private static final byte TYPE = 0x11;
	public static final String KEY_NAME = "ReadDeviceTypeVersionUdpPack";
	public static final int DELAY_MS = 5000;
	@Override
	public String parse() throws Exception {
		byte[] backBytes = getBackBytes();
		if (null != backBytes && backBytes.length == BYTE_SIZE) {
			int version = (backBytes[3] & 0xFF) << 24
					| (backBytes[4] & 0xFF) << 16 | (backBytes[5] & 0xFF) << 8
					| (backBytes[6] & 0xFF);
			String devName = "";
			switch (backBytes[2]) {
			case 0x01:
				devName = "ZB2530-01PA_02PA_WIFI_V1.0";
				break;
			case 0x02:
				devName = "ZB2530-LAN_V02.02";
				break;
			case 0x03:
				devName = "ZB2530_LAN_WIFI_04_V1.0-Lan";
				break;
			case 0x04:
				devName = "ZB2530_LAN_WIFI_04_V1.0-Wifi";
				break;
			}
			String id = String.format("%02X", backBytes[0]) + String.format("%02X", backBytes[1]);
			return "{\"ID\":\"" + id + "\",\"TYPE\":\"" + devName + "\",\"VERSION\": \""
					+ StringUtil.convertVersion(version) + "\",\"KIND\":\"NODE\"}";
		}
		return null;
	}

	@Override
	public int byteSize() {
		// TODO Auto-generated method stub
		return BYTE_SIZE;
	}

	@Override
	public String keyName() {
		// TODO Auto-generated method stub
		return KEY_NAME;
	}

	@Override
	protected int size() {
		// TODO Auto-generated method stub
		return SIZE;
	}

	@Override
	protected byte type() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	public int obtainDelayMs() {
		// TODO Auto-generated method stub
		return 4000;
	}
}
