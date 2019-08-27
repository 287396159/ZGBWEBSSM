package com.dmatek.zgb.rawstorage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmatek.zgb.pack.construction.ZGBPacketConstruction;
import com.dmatek.zgb.rawstorage.time.ZGBRawTimeDataStorage;

@Configuration
public class ZGBStoreHistoryDataConfig {
	public static final String HISTORY_DIR = "C:/ZGBTEMP/HISTORY";
	public static final String TIME_HISTORY_DIR = "C:/ZGBTEMP/TIMEHISTORY";
	/**
	 * ZGB数据包构建对象  
	 * @return 
	 */
	@Bean
	public ZGBPacketConstruction getZGBPacketConstruction(){
		return new ZGBPacketConstruction();
	}
	/**
	 * ZGB按时间存储
	 * @param zgbPacketConstruction
	 * @return
	 */
	@Bean
	public ZGBRawTimeDataStorage getZGBRawTimeDataStorage(ZGBPacketConstruction zgbPacketConstruction) {
		return new ZGBRawTimeDataStorage(zgbPacketConstruction, TIME_HISTORY_DIR);
	}
	/** 
	 * 储存ZGB历史数据对象 
	 * @param zgbPacketConstruction
	 * @return 
	 */
	@Bean
	public ZGBStoreHistoryData getZGBStoreHistoryData(ZGBPacketConstruction zgbPacketConstruction){
		return new ZGBStoreHistoryData(zgbPacketConstruction, HISTORY_DIR);
	}
}
