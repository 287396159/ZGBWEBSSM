package com.dmatek.zgb.setting.directly.node.read.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlyNodeReadUdpPack;


public class ReadPortUdpPack extends BaseDirectlyNodeReadUdpPack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 6;
	private static final int BYTE_SIZE = 2;
	private static final byte TYPE = 0x04;
	public static final String KEY_NAME = "ReadPortUdpPack";
	public static final int DELAY_MS = 4000;
	@Override
	public String parse() throws Exception {
		byte[] backBytes = getBackBytes();
		if(null != backBytes && backBytes.length == BYTE_SIZE) {
			int port = ((backBytes[0] & 0xFF) << 8) | (backBytes[1] & 0xFF);
			return port + "";
		}
		return null;
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
	public int byteSize() {
		return BYTE_SIZE;
	}
	@Override
	public int obtainDelayMs() {
		// TODO Auto-generated method stub
		return 4000;
	}
}
