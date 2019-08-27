package com.dmatek.zgb.setting.directly.node.set.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlyNodeSettingUdpPack;

public class SetWifiNameUdpPack extends BaseDirectlyNodeSettingUdpPack{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String KEY_NAME = "SetWifiNameUdpPack";
	private static int SIZE = 38;
	private static byte TYPE = 0x05;
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
		if(null != bytes && bytes.length <= 32) {
			// 需要我們先將參數位全部設置成0
			for (int i = 4; i < 36; i++) {
				getBytes()[i] = 0x00;
			}
			System.arraycopy(bytes, 0, getBytes(), 4, bytes.length);
		}
	}
	@Override
	public int obtainDelayMs() {
		// TODO Auto-generated method stub
		return 4000;
	}
}
