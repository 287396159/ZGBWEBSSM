package com.dmatek.zgb.rawextract;

import java.io.File;
import java.util.Date;
import com.dmatek.zgb.monitor.bean.TrackPacket;
import com.dmatek.zgb.pack.parsing.IByteFileParse;

public class ZGBMultiExtractHistoryData extends RootZGBExtractHistoryData{
	public ZGBMultiExtractHistoryData(IByteFileParse<TrackPacket> iDataParse) {
		super(iDataParse);
	}
	@Override
	protected File[] getOriginalRecords(String tagId, Date start, Date end)
			throws Exception {
		File rootDir = new File(getHistoryPath());
		if(null != rootDir) {
			File[] dtDirs = getDuringFile( rootDir , start, end);
			if(null != dtDirs) {
				return getAllHours(dtDirs, start, end);
			}
		}
		return null;
	}

}
