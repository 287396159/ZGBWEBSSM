package com.dmatek.zgb.setting.arround.node.setting.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseArroundSettingUdpPack;

public class SetArroundNodeWifiPswUdpPack extends BaseArroundSettingUdpPack{
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 39;
	private static final byte TYPE = 0x0B;
	public static final String KEY_NAME = "SetArroundNodeWifiPswUdpPack";
	@Override
	public String keyName() {
		// TODO Auto-generated method stub
		return KEY_NAME;
	}

	@Override
	protected int size() {
		// TODO Auto-generated method stub
		return SIZE;
	}

	@Override
	protected byte type() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void initBytes(byte[] bytes) {
		// TODO Auto-generated method stub
		if(null != bytes && bytes.length <= 32) {
			// 需要我們先將參數位全部設置成0
			for (int i = 5; i < 37; i++) {
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
