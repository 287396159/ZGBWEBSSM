package com.dmatek.zgb.setting.directly.node.read.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlyNodeReadUdpPack;

public class ReadIPUdpPack extends BaseDirectlyNodeReadUdpPack{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 6;
	private static final int BYTE_SIZE = 4;
	private static final byte TYPE = 0x02;
	public static final String KEY_NAME = "ReadIPUdpPack";
	public static final int DELAY_MS = 4000;
	@Override
	protected int size() {
		return SIZE;
	}
	@Override
	protected byte type() {
		return TYPE;
	}
	@Override
	public String keyName() {
		return KEY_NAME;
	}
	@Override
	public String parse() {
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
	public int obtainDelayMs() {
		// TODO Auto-generated method stub
		return 1000;
	}
}
