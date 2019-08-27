package com.dmatek.zgb.rawstorage;

import com.dmatek.zgb.pack.construction.PacketConstruction;


public abstract class BaseStorageHistoryData<T> implements IRawDataStorage<T> {
	private PacketConstruction<T> packConstruction;
	private String historyPath;
	public BaseStorageHistoryData(PacketConstruction<T> packConstruction, String historyPath){
		this.packConstruction = packConstruction;
		this.historyPath = historyPath;
	}
	public abstract String getRoot(T t) throws Exception;
	public String getHistoryPath() {
		return historyPath;
	}
	public void setHistoryPath(String historyPath) {
		this.historyPath = historyPath;
	}
	public PacketConstruction<T> getPackConstruction() {
		return packConstruction;
	}
	public void setPackConstruction(PacketConstruction<T> packConstruction) {
		this.packConstruction = packConstruction;
	}
}
