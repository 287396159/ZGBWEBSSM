package com.dmatek.zgb.setting.pack.abstract_;
/**
 * 直接设置设备参数的抽象类
 * @author Administrator
 * @data 2019年4月19日 下午5:59:20
 * @Description
 */
public abstract class BaseDirectlyUdpPack extends BaseUdpPack{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final byte HEAD = (byte) 0xF1;
	private static final byte END = (byte) 0xF2;
	@Override
	protected final byte head() {
		return HEAD;
	}
	@Override
	protected final byte end() {
		return END;
	}
}
