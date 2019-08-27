package com.dmatek.zgb.rawstorage;

public interface IRawDataStorage<T> {
	public void saveOrigin(T t) throws Exception;
}
