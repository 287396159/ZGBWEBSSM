package com.dmatek.zgb.controller.validate;

import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

public abstract class BaseValidateTool {
	private BaseResultFactory<Result> viewResultFactory;
	public BaseValidateTool(BaseResultFactory<Result> viewResultFactory) {
		super();
		this.viewResultFactory = viewResultFactory;
	}
	public BaseResultFactory<Result> getViewResultFactory() {
		return viewResultFactory;
	}
	public void setViewResultFactory(BaseResultFactory<Result> viewResultFactory) {
		this.viewResultFactory = viewResultFactory;
	}
}
