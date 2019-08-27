package com.dmatek.zgb.setting.directly.node.read.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlyNodeReadUdpPack;

public class ReadNodeWifiIpModeUdpPack extends BaseDirectlyNodeReadUdpPack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 6;
	private static final int BYTE_SIZE = 1;
	private static final byte TYPE = 0x17;
	public static final String KEY_NAME = "ReadNodeWifiIpModeUdpPack";
	public static final int DELAY_MS = 4000;
	@Override
	public String parse() throws Exception {
		byte[] backBytes = getBackBytes();
		if(null != backBytes && backBytes.length == BYTE_SIZE) {
			return backBytes[0] == 1?"靜態":"DHCP";
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
