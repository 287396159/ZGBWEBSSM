package com.dmatek.zgb.controller.setting.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.db.permission.service.AccountService;
import com.dmatek.zgb.db.pojo.setting.Group;
import com.dmatek.zgb.db.pojo.setting.Region;
import com.dmatek.zgb.db.setting.service.GroupService;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
@Component
public class RegionValidation extends BaseSettingValidation<Integer, Region> {
	@Autowired
	private AccountService accountService;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private GroupService groupService;
	@Override
	public Result validateValue(Region region) throws Exception {
		if(null == region){
			viewResultFactory.errorResult("區域訊息不能為空...");
		}
		if(StringUtils.isEmpty(region.getName())){
			return viewResultFactory.errorResult("區域名稱不能為空！");
		}
		if(region.getGroupId() <= 0){
			return viewResultFactory.errorResult("區域所在組別ID不能小於或等於0！");
		}
		Group group = groupService.findOne(region.getGroupId());
		if(null == group){
			return viewResultFactory.errorResult("區域所屬的組別不存在...");
		}
		if(StringUtils.isEmpty(region.getImage())){
			return viewResultFactory.errorResult("區域地圖路徑不能為空...");
		}
		return checkDataLength(region);
	}

	@Override
	public AccountService getAccountService() {
		return accountService;
	}

	private Result checkDataLength(Region region) throws Exception {
		if(region.getName().length() > 50) {
			return viewResultFactory.errorResult("區域名稱不能超過50個字符! ");
		}
		if(region.getImage().length() > 50){
			return viewResultFactory.errorResult("區域地圖長度不能超過50個字符！");
		}
		return null;
	}
}
