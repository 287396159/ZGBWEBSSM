package com.dmatek.zgb.setting.pack.abstract_;

import com.dmatek.zgb.setting.vo.SearchDevicePo;
/**
 * 抽象的搜索周圍設備的  Udp 數據包
 * @author Administrator
 * @data 2019年4月25日 上午10:46:30
 * @Description
 */
public abstract class BaseSearchArroundUdpPack extends BaseArroundUdpPack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 設備返回的數據包
	private byte[] backBytes = null;
	
	public abstract <T extends SearchDevicePo> T[] parse() throws Exception;
	public abstract void replaceVal(byte[] bytes, int pos) throws Exception;
	
	@Override
	public void init(byte[] ids, byte[] pbytes) {
		// 进行cs校验
		setCsVal();
		// 每次构建相关数据包将标志位置为false
		setMark(false);
	}
	@Override
	protected void initBytes(byte[] bytes) {
	}
	public byte[] getBackBytes() {
		return backBytes;
	}
	public void setBackBytes(byte[] backBytes) {
		this.backBytes = backBytes;
	}
}
