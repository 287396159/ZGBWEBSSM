package com.dmatek.zgb.setting.directly.node.set.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlyNodeSettingUdpPack;

public class SetNodeServerIpUdpPack extends BaseDirectlyNodeSettingUdpPack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String KEY_NAME = "SetNodeServerIp";
	private static final int SIZE = 10;
	private static final byte TYPE = 0x01;
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
		if(null != bytes && bytes.length == 4) {
			System.arraycopy(bytes, 0, getBytes(), 4, 4);
		}
	}

	@Override
	public int obtainDelayMs() {
		// TODO Auto-generated method stub
		return 4000;
	}
}
