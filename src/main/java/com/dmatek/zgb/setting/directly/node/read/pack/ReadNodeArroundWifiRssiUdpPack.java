package com.dmatek.zgb.setting.directly.node.read.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlyNodeReadUdpPack;

public class ReadNodeArroundWifiRssiUdpPack extends BaseDirectlyNodeReadUdpPack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 38;
	private static final int BYTE_SIZE = 1;
	private static final byte TYPE = 0x15;
	public static final String KEY_NAME = "ReadNodeArroundWifiRssiUdpPack";
	
	public static final int DELAY_MS = 5000;
	@Override
	public String parse() throws Exception {
		byte[] backBytes = getBackBytes();
		if(null != backBytes && backBytes.length == BYTE_SIZE) {
			return backBytes[0] + "";
		}
		return null;
	}
	@Override
	protected void initBytes(byte[] bytes) {
		if(null != bytes) {
			for (int i = 4; i < 32 + 4; i++) {
				getBytes()[i] = 0x00;
			}
			System.arraycopy(bytes, 0, getBytes(), 4, bytes.length);
		}
	}
	@Override
	public int byteSize() {
		// TODO Auto-generated method stub
		return BYTE_SIZE;
	}

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
	public int obtainDelayMs() {
		// TODO Auto-generated method stub
		return 4000;
	}
}
