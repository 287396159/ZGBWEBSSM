package com.dmatek.zgb.rawstorage;

import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.pack.construction.PacketConstruction;

public class ZGBStoreHistoryData extends RootZGBStoreHistoryData {
	public ZGBStoreHistoryData(PacketConstruction<TagDetail> packConstruction,
			String historyPath) {
		super(packConstruction, historyPath);
	}
}
