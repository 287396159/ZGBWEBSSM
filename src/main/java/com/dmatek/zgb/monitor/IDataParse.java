package com.dmatek.zgb.monitor;

import java.net.InetAddress;

public interface IDataParse {
	public int packParse(byte[] cache,int pos,InetAddress inetAddress, int port) throws Exception;
}
