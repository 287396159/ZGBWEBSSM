package com.dmatek.zgb.pack.construction;
/**
 * 构建数据包的抽象类
 * @author Administrator
 * @data 2019年1月18日 下午2:10:58
 * @Description
 */
public interface PacketConstruction<T> {
	public byte[] reconstructed(T t) throws Exception;
}
