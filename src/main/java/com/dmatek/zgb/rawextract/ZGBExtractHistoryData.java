package com.dmatek.zgb.rawextract;

import java.io.File;
import java.util.Date;
import com.dmatek.zgb.monitor.bean.TrackPacket;
import com.dmatek.zgb.pack.parsing.IByteFileParse;

public class ZGBExtractHistoryData extends RootZGBExtractHistoryData {
	public ZGBExtractHistoryData(IByteFileParse<TrackPacket> iDataParse) {
		super(iDataParse);
	}
	@Override
	protected File[] getOriginalRecords(String tagId, Date start, Date end)
			throws Exception {
		File rootDir = getTagFile(getHistoryPath(), tagId);
		if(null != rootDir) {
			File[] dtDirs = getDuringFile( rootDir , start, end);
			if(null != dtDirs) {
				return getAllHours(dtDirs, start, end);
			}
		}
		return null;
	}
}
