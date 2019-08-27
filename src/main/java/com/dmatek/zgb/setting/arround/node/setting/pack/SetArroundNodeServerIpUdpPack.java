package com.dmatek.zgb.setting.arround.node.setting.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseArroundSettingUdpPack;

public class SetArroundNodeServerIpUdpPack extends BaseArroundSettingUdpPack{
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 11;
	private static final byte TYPE = 0x05;
	public static final String KEY_NAME = "SetArroundNodeServerIpUdpPack";
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
	// 初始化參數
	@Override
	protected void initBytes(byte[] bytes) {
		if(null != bytes) {
			System.arraycopy(bytes, 0, getBytes(), 5, 4);
		}
	}
	@Override
	public int obtainDelayMs() {
		// TODO Auto-generated method stub
		return 6000;
	}
}
