package com.dmatek.zgb.setting.pack.abstract_;
/**
 * 周围节点读取Udp数据包
 * @author zhangfu
 * @data 2019年4月25日 上午9:27:15
 * @Description
 */
public abstract class BaseArroundReadUdpPack extends BaseArroundUdpPack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 防止后面读参数的数据包对整个进行重写
	 */
	byte[] backBytes = null;
	public abstract <T> T parse() throws Exception;
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
