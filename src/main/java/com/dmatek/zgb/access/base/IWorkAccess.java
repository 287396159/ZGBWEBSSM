package com.dmatek.zgb.access.base;

public interface IWorkAccess<T> {
	void setParams(String... args);
	void execute(T t) throws Exception;
}
