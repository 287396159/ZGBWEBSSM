package com.dmatek.zgb.setting.directly.node.read.pack;

import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlyNodeReadUdpPack;

/**
 * 直接基站读取
 * @author zhangfu
 * @data 2019年4月22日 上午10:07:15
 * @Description
 */
public class ResetNodePack extends BaseDirectlyNodeReadUdpPack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 6;
	private static final byte TYPE = 0x00;
	public static final String KEY_NAME = "ResetNodePack";
	public static final int DELAY_MS = 4000;
	@Override
	protected int size() {
		return SIZE;
	}
	@Override
	protected byte type() {
		return TYPE;
	}
	@Override
	public String keyName() {
		return KEY_NAME;
	}
	@Override
	public String parse() throws Exception {
		return null;
	}
	@Override
	public int byteSize() {
		return 0;
	}
	@Override
	public int obtainDelayMs() {
		// TODO Auto-generated method stub
		return 4000;
	}
}
