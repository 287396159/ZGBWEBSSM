package com.dmatek.zgb.controller.track.map;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmatek.zgb.monitor.bean.TrackPacket;
import com.dmatek.zgb.rawextract.ZGBExtractHistoryData;
import com.dmatek.zgb.rawextract.ZGBMultiExtractHistoryData;

@Component
public class GraphictraceTool {
	public final static int SINGLETAG_TYPE = 1;
	public final static int ALLTAG_TYPE = 0;
	@Autowired
	private ZGBExtractHistoryData zgbExtractHistoryData;
	@Autowired
	private ZGBMultiExtractHistoryData zgbMultiExtractHistoryData;
	public List<TrackPacket> getAllSingleTrack(String tagId, Date start, Date end) throws Exception {
		return zgbExtractHistoryData.ExtractOrigin(tagId, start, end);
	}
	public List<TrackPacket> getAllMultiTrack(Date start, Date end) throws Exception {
		return zgbMultiExtractHistoryData.ExtractOrigin("", start, end);
	}
}
