package com.dmatek.zgb.result.factory.base;

import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.tool.check.bean.CheckedResult;

public class CheckedResultFactory implements BaseResultFactory<CheckedResult>{
	@Override
	public CheckedResult successResult() {
		return new CheckedResult(true,null);
	}
	@Override
	public CheckedResult errorResult(String msg) {
		return new CheckedResult(false, msg);
	}
	@Override
	public CheckedResult successResult(Object data) {
		return new CheckedResult(true,null,data);
	}
	@Override
	public CheckedResult successResult(Object data, int count) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Result successLayResult(Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CheckedResult successPagesResult(Object data, int page) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CheckedResult errorResult(String msg, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
