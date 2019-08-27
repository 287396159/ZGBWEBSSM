package com.dmatek.zgb.result.factory.base;

import com.dmatek.zgb.setting.vo.Result;

public interface BaseResultFactory<T> {
	public T successResult();
	public T errorResult(String msg);
	public T errorResult(String msg, Object obj);
	public Result successLayResult(Object data);
	public T successResult(Object data);
	public T successResult(Object data,int count);
	public T successPagesResult(Object data,int page);
}
