package com.dmatek.zgb.rawstorage.time;

import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.pack.construction.PacketConstruction;
import com.dmatek.zgb.rawstorage.RootZGBStoreHistoryData;

public class ZGBRawTimeDataStorage extends RootZGBStoreHistoryData {
	public ZGBRawTimeDataStorage(
			PacketConstruction<TagDetail> packConstruction, String historyPath) {
		super(packConstruction, historyPath);
	}
	@Override
	public String getRoot(TagDetail t) throws Exception {
		return "";
	}
}
