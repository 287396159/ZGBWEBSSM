package com.dmatek.zgb.setting.pack.abstract_;

import java.io.Serializable;

/**
 * udp封包的抽象類
 * @author zhangfu
 * @data 2019年4月19日 下午5:39:09
 * @Description
 */
public abstract class BaseUdpPack implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final int id_pos = 2;
	private byte[] bytes;
	// 设备是否返回数据包的标志
	private boolean mark;
	public BaseUdpPack() {
		bytes = new byte[size()];
		bytes[0] = head();// 设置数据包头
		bytes[bytes.length - 1] = end();// 设置数据包尾
		bytes[1] = type();// 设置数据包类型位
	}
	public void init(byte[] ids, byte[] pbytes) {
		// 替换设备ID
		if(id_pos > 1 && id_pos < size() - 2 && ids.length >= 2) {
			if(ids.length == 2) {
				System.arraycopy(ids, 0, getBytes(), id_pos, 2);
			} else {// 說明加上了信道
				System.arraycopy(ids, 0, getBytes(), id_pos, 3);
			}
		}
		// 替换参数值
		initBytes(pbytes);
		// 进行cs校验
		setCsVal();
		// 每次构建相关数据包将标志位置为false
		setMark(false);
	}
	/**
	 * 设置校验值
	 */
	protected void setCsVal() {
		// 設置校驗數據包
		short cs = 0;
		for (int i = 0; i < getBytes().length - 2; i++) {
			cs += getBytes()[i];
		}
		cs &= 0xFF;
		getBytes()[getBytes().length - 2] = (byte) cs;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public boolean isMark() {
		return mark;
	}
	public void setMark(boolean mark) {
		this.mark = mark;
	}
	public static int getIdPos() {
		return id_pos;
	}
	public abstract String keyName();// 鍵值
	protected abstract byte head();// 包头
	protected abstract byte end();// 包尾
	protected abstract int size();// 初始化包长度
	protected abstract byte type();// 类型字段
	protected abstract void initBytes(byte[] bytes);// 初始化包
	public abstract int obtainDelayMs();
}
