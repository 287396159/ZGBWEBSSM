package com.dmatek.zgb.pack.parsing;

public interface IDataPacketParse<T> {
	final int singleLen = 24;
	public T ParseData(byte[] bytes) throws Exception;
}
