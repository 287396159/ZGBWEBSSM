package com.dmatek.zgb.setting.arround.node.read.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseArroundNodeReadUdpPack;

public class ReadArroundNodeAtCommandUdpPack extends BaseArroundNodeReadUdpPack{
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 99;
	private static final int BYTE_SIZE = 90;
	private static final byte TYPE = 0x1C;
	public static final String KEY_NAME = "ReadArroundNodeAtCommandUdpPack";
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public String parse() throws Exception {
		byte[] backBytes = getBackBytes();
		if(null != backBytes && backBytes.length == BYTE_SIZE) {
			int size_ = backBytes.length;
			for (int i = backBytes.length - 1; i >= 0; i--) {
				if(backBytes[i] == 0) {
					size_ = i;
				}
			}
			byte[] replyCommondr = new byte[size_];
			System.arraycopy(backBytes, 0, replyCommondr, 0, size_);
			return new String(replyCommondr, 0);
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
	protected void initBytes(byte[] bytes) {
		if(null != bytes) {
			for (int i = 5; i < 92 + 5; i++) {
				getBytes()[i] = 0x00;
			}
			System.arraycopy(bytes, 0, getBytes(), 5, bytes.length);
		}
	}
	@Override
	public int obtainDelayMs() {
		return 6000;
	}
}
