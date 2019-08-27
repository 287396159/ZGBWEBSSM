package com.dmatek.zgb.setting.pack.abstract_;


/**
 * 直接读取节点的UDP抽象数据包
 * @author zhangfu
 * @data 2019年4月18日 下午4:01:20
 * @Description
 */
public abstract class BaseDirectlyReadUdpPack extends BaseDirectlyUdpPack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 防止后面读参数的数据包对整个进行重写
	 */
	byte[] backBytes = null;
	public abstract String parse() throws Exception;
	public abstract int byteSize();
	@Override
	protected void initBytes(byte[] bytes) {
	}
	public final void replaceVal(byte[] bytes, int pos) {
		if (null != bytes && pos > 0 && byteSize() > 0
		    && pos + byteSize() <= bytes.length) {
			backBytes = new byte[byteSize()];
			System.arraycopy(bytes, pos, backBytes, 0, byteSize());
		}
	}
	public byte[] getBackBytes() {
		return backBytes;
	}
}
