package com.dmatek.zgb.setting.arround.node.setting.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseArroundSettingUdpPack;

public class SetArroundNodeServerPortUdpPack extends BaseArroundSettingUdpPack{
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 9;
	private static final byte TYPE = 0x07;
	public static final String KEY_NAME = "SetArroundNodeServerPortUdpPack";
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
			getBytes()[5] = bytes[0];
			getBytes()[6] = bytes[1];
		}
	}
	@Override
	public int obtainDelayMs() {
		// TODO Auto-generated method stub
		return 6000;
	}
}
