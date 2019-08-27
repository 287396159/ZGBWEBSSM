package com.dmatek.zgb.setting.directly.node.set.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlyNodeSettingUdpPack;


public class SetNodeServerPortUdpPack extends BaseDirectlyNodeSettingUdpPack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String KEY_NAME = "SetNodeServerPort";
	private static final int SIZE = 8;
	private static final byte TYPE = 0x03;
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
		if(null != bytes && bytes.length == 2) {
			System.arraycopy(bytes, 0, getBytes(), 4, 2);
		}
	}
	@Override
	public int obtainDelayMs() {
		// TODO Auto-generated method stub
		return 4000;
	}
}
