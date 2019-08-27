package com.dmatek.zgb.setting.arround.refer.setting.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseArroundSettingUdpPack;

public class SetArroundReferSigThresholdUdp extends BaseArroundSettingUdpPack{
	private static final long serialVersionUID = 1L;
	public static final String KEY_NAME = "SetArroundReferSigThresholdUdp";
	private static int SIZE = 7;
	private static byte TYPE = 0x45;
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
		if(null != bytes && bytes.length >= 1) {
			getBytes()[4] = bytes[0];
		}
	}
	@Override
	public int obtainDelayMs() {
		// TODO Auto-generated method stub
		return 6000;
	}
}
