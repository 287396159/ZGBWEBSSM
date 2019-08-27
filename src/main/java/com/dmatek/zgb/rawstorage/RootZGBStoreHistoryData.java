package com.dmatek.zgb.rawstorage;

import java.io.FileOutputStream;

import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.pack.construction.PacketConstruction;

public abstract class RootZGBStoreHistoryData extends BaseZGBStoreHistoryData {
	public RootZGBStoreHistoryData(PacketConstruction<TagDetail> packConstruction,String historyPath) {
		super(packConstruction, historyPath);
	}
	public final void saveRawData(byte[] bytes,String path) throws Exception {
		FileOutputStream out = null;
		synchronized (RootZGBStoreHistoryData.class) { // 防止写数据时进行读取操作
			try {
				out = new FileOutputStream(path, true);
				out.write(bytes);
			} catch (Exception e) {

			} finally {
				if (null != out) {
					out.close();
				}
			}
		}
	}
	@Override
	public void saveOrigin(TagDetail tagPack) throws Exception {
		byte[] bytes = getPackConstruction().reconstructed(tagPack);
		String orgPath = OriginalFormat(tagPack.getReportTime(), getRoot(tagPack));
		saveRawData(bytes, orgPath);
	}
	@Override
	public String getRoot(TagDetail t) throws Exception {
		return t.getId();
	}
}
