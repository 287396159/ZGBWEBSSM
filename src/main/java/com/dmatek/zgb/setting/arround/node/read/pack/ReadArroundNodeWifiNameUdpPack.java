package com.dmatek.zgb.setting.arround.node.read.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseArroundNodeReadUdpPack;

public class ReadArroundNodeWifiNameUdpPack extends BaseArroundNodeReadUdpPack{
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 7;
	private static final int BYTE_SIZE = 32;
	private static final byte TYPE = 0x0A;
	public static final String KEY_NAME = "ReadArroundNodeWifiNameUdpPack";
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public String parse() throws Exception {
		byte[] backBytes = getBackBytes();
		if(null != backBytes && backBytes.length == BYTE_SIZE) {
			int size_ = 0;
			for (int i = backBytes.length - 1; i >= 0; i--) {
				if(backBytes[i] == 0) {
					size_ = i;
				}
			}
			byte[] iwifiNames = new byte[size_];
			System.arraycopy(backBytes, 0, iwifiNames, 0, size_);
			return new String(iwifiNames, 0);
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
