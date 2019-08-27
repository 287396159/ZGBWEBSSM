package com.dmatek.zgb.controller.setting.node;

import com.dmatek.zgb.redis.service.RedisService;
import com.dmatek.zgb.setting.pack.abstract_.BaseArroundReadUdpPack;
import com.dmatek.zgb.setting.pack.abstract_.BaseDirectlyReadUdpPack;
import com.dmatek.zgb.setting.pack.abstract_.BaseSearchArroundUdpPack;
import com.dmatek.zgb.setting.pack.abstract_.BaseUdpPack;
/**
 * UdpPack 通讯的中间件
 * @author zhangfu
 * @data 2019年4月26日 上午9:51:22
 * @Description
 */
public class UdpCommunicationContainer {
	private RedisService redisService;
	private IPackTool iPackTool;
	private static final int DELAY_MS = 4000;// 等待延时时间
	private static final long EXPIRE_TIME = 10 * 60;// 键值失效时间10分钟
	public UdpCommunicationContainer(RedisService redisService, 
			IPackTool iPackTool) {
		super();
		this.redisService = redisService;
		this.iPackTool = iPackTool;
	}
	/**
	 * 获取指定的UdpPack的实例对象
	 * @param keyName
	 * @return
	 * @throws Exception
	 */
	public synchronized BaseUdpPack instance(String keyName) throws Exception {
		BaseUdpPack udpPack = null;
		if(redisService.hasKey(keyName)) {// 判断是否存在指定的键值
			udpPack = findBaseUdpPack(keyName);
		} else {
			udpPack = getiPackTool().createUdpPack(keyName);
			saveRedisUdpPack(keyName, udpPack);
		}
		return udpPack;
	}
	/**
	 * 设置指定键值的实例对象到缓存中，并设置失效时间
	 * @param keyName
	 * @param udpPack
	 * @param expireTime
	 * @throws Exception
	 */
	public synchronized void saveRedisUdpPack(String keyName,
			BaseUdpPack udpPack) throws Exception {
		redisService.set(keyName, udpPack);
		// 设置键值的失效时间
		redisService.expire(keyName, EXPIRE_TIME);
	}
	/**
	 * 获取Redis缓存中的UDP数据包
	 * @param keyName
	 * @return
	 * @throws Exception
	 */
	private synchronized BaseUdpPack findBaseUdpPack(String keyName) throws Exception {
		return (BaseUdpPack) redisService.get(keyName);
	}
	/**
	 * 设置UDP数据包状态
	 * @throws Exception 
	 */
	public void setUdpPackState(String keyName) throws Exception {
		BaseUdpPack udpPack = findBaseUdpPack(keyName);
		if(null != udpPack) {
			udpPack.setMark(true);
			saveRedisUdpPack(keyName, udpPack);
		}
	}
	/**
	 * 设置UdpPack的标志和值
	 * @param keyName
	 * @param bytes
	 * @param pos
	 * @throws Exception
	 */
	public void setUdpPackVal(String keyName, byte[] bytes, int pos) throws Exception {
		BaseUdpPack udpPack = findBaseUdpPack(keyName);
		if(udpPack instanceof BaseDirectlyReadUdpPack){
			udpPack.setMark(true);
			((BaseDirectlyReadUdpPack)udpPack).replaceVal(bytes, pos);
			saveRedisUdpPack(keyName, udpPack);
		} else if(udpPack instanceof BaseSearchArroundUdpPack) {
			udpPack.setMark(true);
			((BaseSearchArroundUdpPack)udpPack).replaceVal(bytes, pos);
			saveRedisUdpPack(keyName, udpPack);
		} else if(udpPack instanceof BaseArroundReadUdpPack) {
			udpPack.setMark(true);
			((BaseArroundReadUdpPack)udpPack).replaceVal(bytes, pos);
			saveRedisUdpPack(keyName, udpPack);
		}
	}
	/**
	 * 根据键名获取延时的 时间，单位ms
	 * @param keyName
	 * @return
	 * @throws Exception 
	 */
	private int obtainDelayMs(String keyName) throws Exception {
		BaseUdpPack udpPack = instance(keyName);
		return (null != udpPack && udpPack.obtainDelayMs() > 0)?udpPack.obtainDelayMs():DELAY_MS;
	}
	/**
	 * 延时等待UdpPack数据包回复标志置为真，等待的时间根据各自数据包定义的延时时间确定
	 * @param keyName
	 * @return
	 * @throws Exception
	 */
	public BaseUdpPack delayUdpBackPack(String keyName) throws Exception {
		long tick = System.currentTimeMillis();
		while (System.currentTimeMillis() - tick <= obtainDelayMs(keyName)) {
			// 每次休眠1ms唤醒后再去查看设备
			Thread.sleep(1);
			BaseUdpPack baseUdpPack = findBaseUdpPack(keyName);
			if (null == baseUdpPack) {
				break;
			}
			if (baseUdpPack.isMark()) {
				return baseUdpPack;
			}
		}
		return null;
	}
	/**
	 * 延时等待UdpPack数据包回复标志置为真，传入超时等待时间
	 * @param keyName
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public BaseUdpPack delayUdpBackPack(String keyName, int timeOut) throws Exception {
		long tick = System.currentTimeMillis();
		while (System.currentTimeMillis() - tick <= timeOut + 3000) {
			Thread.sleep(1);
			BaseUdpPack baseUdpPack = findBaseUdpPack(keyName);
			if (null == baseUdpPack) {
				break;
			}
			if (baseUdpPack.isMark()) {
				return baseUdpPack;
			}
		}
		return null;
	}
	
	public RedisService getRedisService() {
		return redisService;
	}
	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}
	public IPackTool getiPackTool() {
		return iPackTool;
	}
	public void setiPackTool(IPackTool iPackTool) {
		this.iPackTool = iPackTool;
	}
}
