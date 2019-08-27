package com.dmatek.zgb.setting.arround.node.setting.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseArroundSettingUdpPack;

public class SetArroundNodeWifiIpModelUdpPack extends BaseArroundSettingUdpPack{
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 8;
	private static final byte TYPE = 0x18;
	public static final String KEY_NAME = "SetArroundNodeWifiIpModelUdpPack";
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
		if(null != bytes && bytes.length == 1) {
			getBytes()[5] = bytes[0];
		}
	}

	@Override
	public int obtainDelayMs() {
		// TODO Auto-generated method stub
		return 6000;
	}

}
