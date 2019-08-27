package com.dmatek.zgb.setting.directly.refer.set.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlyReferSettingUdpPack;

public class SetReferSignKUdpPack extends BaseDirectlyReferSettingUdpPack{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String KEY_NAME = "SetReferSignKUdpPack";
	private static final int SIZE = 7;
	private static final byte TYPE = 0x22;
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
		if(null != bytes && bytes.length == 1) {
			getBytes()[4] = bytes[0];
		}
	}
	@Override
	public int obtainDelayMs() {
		// TODO Auto-generated method stub
		return 2000;
	}
}
