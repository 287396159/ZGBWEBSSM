package com.dmatek.zgb.controller.track.map;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import com.dmatek.zgb.controller.validate.BaseValidateTool;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@Component
public class GraphictraceValidationTool extends BaseValidateTool {
	private SimpleDateFormat simpleDateFormat;
	public GraphictraceValidationTool(
			BaseResultFactory<Result> viewResultFactory) {
		super(viewResultFactory);
		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	public Result validateSearchCondition(int playType, String tagId, String iCurrent) {
		if (playType != GraphictraceTool.ALLTAG_TYPE
		 && playType != GraphictraceTool.SINGLETAG_TYPE) {
			return getViewResultFactory().errorResult("只能選擇單張卡片和所有卡片...");
		}
		if (playType == GraphictraceTool.SINGLETAG_TYPE
		&& StringUtils.isEmpty(tagId)) {
			return getViewResultFactory().errorResult("選擇單張卡片时卡片ID不能为空...");
		}
		if(StringUtils.isEmpty(iCurrent)){
			return getViewResultFactory().errorResult("時間不能為空...");
		}
		try {
			format(iCurrent);
		} catch (ParseException e) {
			return getViewResultFactory().errorResult("時間格式有誤,格式必須為 yyyy-MM-dd HH:mm:ss");
		}
		return null;
	}
	public Date format(String cur) throws ParseException {
		return getSimpleDateFormat().parse(cur);
	}
	public SimpleDateFormat getSimpleDateFormat() {
		return simpleDateFormat;
	}
	public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
		this.simpleDateFormat = simpleDateFormat;
	}
}
