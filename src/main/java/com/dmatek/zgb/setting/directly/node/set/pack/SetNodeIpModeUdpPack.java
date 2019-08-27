package com.dmatek.zgb.setting.directly.node.set.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlyNodeSettingUdpPack;

public class SetNodeIpModeUdpPack extends BaseDirectlyNodeSettingUdpPack{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String KEY_NAME = "SetNodeIpModeUdpPack";
	private static int SIZE = 7;
	private static byte TYPE = 0x09;
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
		return 1000;
	}
}
