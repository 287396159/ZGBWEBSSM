package com.dmatek.zgb.setting.directly.node.read.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlyNodeReadUdpPack;

public class ReadNodeGateWayUdpPack extends BaseDirectlyNodeReadUdpPack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 6;
	private static final int BYTE_SIZE = 4;
	private static final byte TYPE = 0x10;
	public static final String KEY_NAME = "ReadNodeGateWayUdpPack";
	public static final int DELAY_MS = 50;
	@Override
	public String parse() throws Exception {
		byte[] backBytes = getBackBytes();
		if (null != backBytes && backBytes.length == BYTE_SIZE) {
			return (int)(backBytes[0] & 0xFF) + "." + (int)(backBytes[1] & 0xFF) + "." + 
				   (int)(backBytes[2] & 0xFF) + "." + (int)(backBytes[3] & 0xFF);
		}
		return null;
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
		return 1000;
	}

}
