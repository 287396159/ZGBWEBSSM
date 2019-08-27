package com.dmatek.zgb.setting.directly.refer.read.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlyReferReadUdpPack;

public class ResetReferUdpPack extends BaseDirectlyReferReadUdpPack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 6;
	private static final int BYTE_SIZE = 1;
	private static final byte TYPE = 0x27;
	public static final String KEY_NAME = "ResetReferUdpPack";
	@Override
	public String parse() throws Exception {
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
