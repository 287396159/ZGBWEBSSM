package com.dmatek.zgb.setting.arround.node.read.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseArroundNodeReadUdpPack;

public class ReadArroundNodeWifiRssiUdpPack extends BaseArroundNodeReadUdpPack{
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 39;
	private static final int BYTE_SIZE = 1;
	private static final byte TYPE = 0x16;
	public static final String KEY_NAME = "ReadArroundNodeWifiRssiUdpPack";
	@SuppressWarnings("unchecked")
	@Override
	public String parse() throws Exception {
		byte[] backBytes = getBackBytes();
		if(null != backBytes && backBytes.length == BYTE_SIZE) {
			return backBytes[0] + "";
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
	protected void initBytes(byte[] bytes) {
		if(null != bytes) {
			for (int i = 5; i < 32 + 5; i++) {
				getBytes()[i] = 0x00;
			}
			System.arraycopy(bytes, 0, getBytes(), 5, bytes.length);
		}
	}
	@Override
	public int obtainDelayMs() {
		// TODO Auto-generated method stub
		return 6000;
	}
}
