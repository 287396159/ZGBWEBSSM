package com.dmatek.zgb.setting.directly.node.read.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlyNodeReadUdpPack;

public class ReadWifiPswUdpPack extends BaseDirectlyNodeReadUdpPack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 6;
	private static final int BYTE_SIZE = 32;
	private static final byte TYPE = 0x08;
	public static final String KEY_NAME = "ReadWifiPswUdpPack";
	
	public static final int DELAY_MS = 4000;
	@SuppressWarnings("deprecation")
	@Override
	public String parse() throws Exception {
		byte[] backBytes = getBackBytes();
		if(null != backBytes && backBytes.length == BYTE_SIZE) {
			int size_ = 0;
			for (int i = backBytes.length - 1; i >= 0; i--) {
				if(backBytes[i] == 0) {
					size_ = i;
				}
			}
			byte[] iwifiPsws = new byte[size_];
			System.arraycopy(backBytes, 0, iwifiPsws, 0, size_);
			return new String(iwifiPsws, 0);
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
		return 4000;
	}
}
