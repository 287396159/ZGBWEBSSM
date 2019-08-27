package com.dmatek.zgb.rawextract;

import com.dmatek.zgb.pack.parsing.IByteFileParse;

public abstract class BaseExtractHistoryData<T> implements IRawDataExtract<T>{
	private IByteFileParse<?> iDataParse;
	private String historyPath;
	public BaseExtractHistoryData(IByteFileParse<?> iDataParse){
		this.iDataParse = iDataParse;
	}
	public IByteFileParse<?> getiDataParse() {
		return iDataParse;
	}
	public void setiDataParse(IByteFileParse<?> iDataParse) {
		this.iDataParse = iDataParse;
	}
	public String getHistoryPath() {
		return historyPath;
	}
	public void setHistoryPath(String historyPath) {
		this.historyPath = historyPath;
	}
}
