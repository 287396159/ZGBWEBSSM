package com.dmatek.zgb.access.manager;

public interface IAccessManager<T> {
	 void saveAccessRecord(T tagDetail) throws Exception;
}
