package com.dmatek.zgb.result.factory.base;

import com.dmatek.zgb.setting.vo.PagesResult;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.setting.vo.ResultCode;

public class ViewResultFactory implements BaseResultFactory<Result>{
	@Override
	public Result successResult(){
		return new Result(ResultCode.SUCCESS_CODE,null,null);
	}
	@Override
	public Result successResult(Object data) {
		return new Result(ResultCode.SUCCESS_CODE, "", data);
	}
	@Override
	public Result successPagesResult(Object data, int pages) {
		return new PagesResult(ResultCode.SUCCESS_CODE, "", data, pages);
	}
	@Override
	public Result successLayResult(Object data) {
		return new Result(ResultCode.DATA_SUCCESS_CODE, "", data);
	}
	@Override
	public Result successResult(Object data,int count) {
		return new Result(ResultCode.DATA_SUCCESS_CODE, "", data, count);
	}
	@Override
	public Result errorResult(String msg) {
		return new Result(ResultCode.ERROR_CODE, msg, null);
	}
	@Override
	public Result errorResult(String msg, Object obj) {
		return new Result(ResultCode.ERROR_CODE, msg, obj);
	}
	public Result errorResult(int code, String msg) {
		return new Result(code, msg, null);
	}
}
