package com.dmatek.zgb.setting.arround.node.setting.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseArroundSettingUdpPack;

public class SetArroundNodeWifiStaticIpUdpPack extends BaseArroundSettingUdpPack{
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 11;
	private static final byte TYPE = 0x1A;
	public static final String KEY_NAME = "SetArroundNodeWifiStaticIpUdpPack";
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
		if(null != bytes && bytes.length == 4) {
			System.arraycopy(bytes, 0, getBytes(), 5, 4);
		}
	}

	@Override
	public int obtainDelayMs() {
		// TODO Auto-generated method stub
		return 6000;
	}

}
