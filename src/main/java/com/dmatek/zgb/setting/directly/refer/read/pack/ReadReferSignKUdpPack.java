package com.dmatek.zgb.setting.directly.refer.read.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlyReferReadUdpPack;

public class ReadReferSignKUdpPack extends BaseDirectlyReferReadUdpPack{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 6;
	private static final int BYTE_SIZE = 1;
	private static final byte TYPE = 0x23;
	public static final String KEY_NAME = "ReadReferSignKUdpPack";
	@Override
	public String parse() throws Exception {
		byte[] bytes = getBackBytes();
		if (null != bytes && bytes.length == 1) {
			int signK = bytes[0] & 0xFF;
			return String.format("%1.2f", (float)signK/100);
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
