package com.dmatek.zgb.monitor;

import java.io.IOException;
import java.net.SocketException;

import org.apache.log4j.Logger;

import com.dmatek.zgb.controller.setting.node.UdpCommunicationContainer;
import com.dmatek.zgb.monitor.callback.MonitorCallBack;
/**
 * 进行ZGB的数据监控
 * @author zf
 * @data 2018年12月13日 上午9:06:01
 * @Description
 */
public class ZGBDataMonitor extends BaseParseDataMonitor {
	private final Logger logger = Logger.getLogger(ZGBDataMonitor.class);
	private static final int TEMP_CACHE_SIZE = 2014;
	private byte[] tempCache = null;
	private int tempPos;
	public ZGBDataMonitor(MonitorCallBack zgbMonitorCallBack, 
			UdpCommunicationContainer udpCommunicationContainer) {
		super(zgbMonitorCallBack, udpCommunicationContainer);
		tempCache = new byte[TEMP_CACHE_SIZE];
	}
	@Override
	public void run() {
		tempPos = 0;
		while (isMark()) {
			try {
				getSocket().receive(getPackes());
				if (getPackes().getLength() > 0 && 
					tempPos < getDatapackBuffsize() && 
					tempPos >= 0) {
					// 将当前接收数据包的缓存数据放入到自定义的缓存中
					System.arraycopy(getPackes().getData(), 0, tempCache,
							tempPos, getPackes().getLength());
					tempPos += getPackes().getLength();
				} else {
					tempPos = 0;
				}
			} catch (SocketException e) {

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (null != getPackes() && null != getPackes().getAddress()) {
					tempPos = packParse(tempCache, tempPos, getPackes()
							.getAddress(), getPackes().getPort());
					
				}
			} catch (Exception e) {
				tempPos = 0;
				logger.error("解析ZGB的数据包时出现异常，异常讯息：" + e.toString());
			}
		}
	}
}
