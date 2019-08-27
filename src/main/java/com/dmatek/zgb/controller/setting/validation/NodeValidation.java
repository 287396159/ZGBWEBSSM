package com.dmatek.zgb.controller.setting.validation;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dmatek.zgb.db.permission.service.AccountService;
import com.dmatek.zgb.db.pojo.setting.Node;
import com.dmatek.zgb.db.pojo.setting.Region;
import com.dmatek.zgb.db.setting.service.RegionService;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
@Component
public class NodeValidation extends BaseSettingValidation<String, Node> {
	@Autowired
	private AccountService accountService;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private RegionService regionService;
	
	@Override
	public Result validateValue(Node value) throws Exception {
		if(null == value){
			return viewResultFactory.errorResult("基站不能為空...");
		}
		if(StringUtils.isEmpty(value.getId())){
			return viewResultFactory.errorResult("基站的ID不能為空...");
		}
		if(value.getId().length() != 4){
			return viewResultFactory.errorResult("基站的ID只能是4位...");
		}
		// 判斷當前的ID是否在0-F之間
		if(!Pattern.matches("([0-9A-F]{4})", value.getId())){
			return viewResultFactory.errorResult("基站ID只能包含0-9A-F之間的字符，例如AB01");
		}
		if(value.getType() < 0 || value.getType() > 2){
			return viewResultFactory.errorResult("基站的類型值【0: 參考點, 1: 數據節點, 2: 進出判斷設備】,超出範圍無效...");
		}
		Region region = regionService.findOne(value.getRegionId());
		if(null == region){
			return viewResultFactory.errorResult("基站所處的區域不存在...");
		}
		return checkDataLength(value);
	}
	@Override
	public AccountService getAccountService() {
		return accountService;
	}
	
	private Result checkDataLength(Node node) throws Exception {
		if(node.getId().length() != 4){
			return viewResultFactory.errorResult("基站/參考點ID不能超過4個字符！");
		}
		if(node.getName().length() > 20){
			return viewResultFactory.errorResult("基站/參考點名稱長度不能超過20個字符！");
		}
		return null;
	}
}
