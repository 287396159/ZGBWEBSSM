package com.dmatek.zgb.setting.directly.refer.read.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlyReferReadUdpPack;

public class ReadReferReceiveSignUdpPack extends BaseDirectlyReferReadUdpPack{
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 6;
	private static final int BYTE_SIZE = 1;
	private static final byte TYPE = 0x21;
	public static final String KEY_NAME = "ReadReferReceiveSign";
	@Override
	public String parse() throws Exception {
		byte[] backBytes = getBackBytes();
		if (null != backBytes && backBytes.length == 1) {
			int sign = backBytes[0] & 0xFF;
			return sign + "";
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
		return 2000;
	}
}
