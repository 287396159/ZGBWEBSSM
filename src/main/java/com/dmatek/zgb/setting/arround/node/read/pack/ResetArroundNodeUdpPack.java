package com.dmatek.zgb.setting.arround.node.read.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseArroundNodeReadUdpPack;

public class ResetArroundNodeUdpPack extends BaseArroundNodeReadUdpPack{
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 7;
	private static final int BYTE_SIZE = 0;
	private static final byte TYPE = 0x0D;
	public static final String KEY_NAME = "ResetArroundNodeUdpPack";
	@SuppressWarnings("unchecked")
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
		return 6000;
	}
}
