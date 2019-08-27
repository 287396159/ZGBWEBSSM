package com.dmatek.zgb.rawextract;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmatek.zgb.controller.track.TrackValidationTool;
import com.dmatek.zgb.pack.parsing.ZGBByteFileParse;
import com.dmatek.zgb.pack.parsing.ZGBDataPacketParse;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@Configuration
public class RawDataConfig {
	private final static String zgbPath = "C:\\ZGBTemp\\HISTORY";
	private final static String zgbTimePath = "C:\\ZGBTemp\\TIMEHISTORY";
	@Bean
	public TrackValidationTool getTrackValidationTool(BaseResultFactory<Result> viewResultFactory){
		return new TrackValidationTool(viewResultFactory);
	}
	@Bean
	public ZGBDataPacketParse getIDataPacketParse() {
		return new ZGBDataPacketParse();
	}
	@Bean
	public ZGBByteFileParse getIByteFileParse(ZGBDataPacketParse zgbDataPacketParse) {
		return new ZGBByteFileParse(zgbDataPacketParse);
	}
	@Bean
	public ZGBExtractHistoryData getZGBExtractHistoryData(ZGBByteFileParse zgbByteFileParse) {
		ZGBExtractHistoryData zgbExtractHistoryData = new ZGBExtractHistoryData(zgbByteFileParse);
		zgbExtractHistoryData.setHistoryPath(zgbPath);
		return zgbExtractHistoryData;
	}
	@Bean
	public ZGBMultiExtractHistoryData getZGBMultiExtractHistoryData(ZGBByteFileParse zgbByteFileParse){
		ZGBMultiExtractHistoryData zgbMultiExtractHistoryData = new ZGBMultiExtractHistoryData(zgbByteFileParse);
		zgbMultiExtractHistoryData.setHistoryPath(zgbTimePath);
		return zgbMultiExtractHistoryData;
	}
}
