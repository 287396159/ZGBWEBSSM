package com.dmatek.zgb.controller.track;

import com.dmatek.zgb.controller.validate.BaseValidateTool;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.show.vo.SearchTrack;
/**
 * 軌跡校驗工具
 * @author zf
 * @data 2019年2月12日 下午4:21:35
 * @Description
 */
public class TrackValidationTool extends BaseValidateTool{
	public TrackValidationTool(BaseResultFactory<Result> viewResultFactory) {
		super(viewResultFactory);
	}
	public Result validateSearchCondition(SearchTrack searchTrack) {
		if(null == searchTrack.getTagInfo() || "".equals(searchTrack.getTagInfo())) {
			return getViewResultFactory().errorResult("卡片ID或名稱不能為空！");
		}
		if(null == searchTrack.getStart() || null == searchTrack.getEnd()) {
			return getViewResultFactory().errorResult("卡片開始時間、結束時間不能為空!");
		}
		if(searchTrack.getStart().compareTo(searchTrack.getEnd()) >= 0) {
			return getViewResultFactory().errorResult("查詢軌跡數據的開始時間必須在結束時間之前！");
		}
		return null;
	}
}
