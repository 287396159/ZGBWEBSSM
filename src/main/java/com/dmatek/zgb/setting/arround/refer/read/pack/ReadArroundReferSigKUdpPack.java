package com.dmatek.zgb.setting.arround.refer.read.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseArroundReadUdpPack;

public class ReadArroundReferSigKUdpPack extends BaseArroundReadUdpPack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 6;
	private static final int BYTE_SIZE = 1;
	private static final byte TYPE = 0x48;
	public static final String KEY_NAME = "ReadArroundReferSigKUdpPack";
	@SuppressWarnings("unchecked")
	@Override
	public String parse() throws Exception {
		byte[] backBytes = getBackBytes();
		if(null != backBytes && backBytes.length == BYTE_SIZE) {
			int sigK = backBytes[0] & 0xFF;
			return String.format("%1.2f", (float)sigK/100);
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
