package com.dmatek.zgb.setting.pack.abstract_;

public abstract class BaseArroundUdpPack extends BaseUdpPack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final byte HEAD = (byte) 0xF2;
	private static final byte END = (byte) 0xF1;
	@Override
	protected byte head() {
		return HEAD;
	}
	@Override
	protected byte end() {
		return END;
	}
}
