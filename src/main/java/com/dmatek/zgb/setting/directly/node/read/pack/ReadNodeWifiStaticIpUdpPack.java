package com.dmatek.zgb.setting.directly.node.read.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlyNodeReadUdpPack;

public class ReadNodeWifiStaticIpUdpPack extends BaseDirectlyNodeReadUdpPack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 6;
	private static final int BYTE_SIZE = 4;
	private static final byte TYPE = 0x19;
	public static final String KEY_NAME = "ReadNodeWifiStaticIpUdpPack";
	public static final int DELAY_MS = 4000;
	@Override
	public String parse() throws Exception {
		byte[] backBytes = getBackBytes();
		if (null != backBytes && backBytes.length == BYTE_SIZE) {
			return (int)(backBytes[0] & 0xFF) + "." + (int)(backBytes[1] & 0xFF) + "." + 
				   (int)(backBytes[2] & 0xFF) + "." + (int)(backBytes[3] & 0xFF);
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
		return 2000;
	}
}
