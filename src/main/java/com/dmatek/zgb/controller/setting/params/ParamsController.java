package com.dmatek.zgb.controller.setting.params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dmatek.zgb.controller.setting.params.base.BaseSettingParams;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@RestController
@RequestMapping("/params")
public class ParamsController extends BaseSettingParams{
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	/**
	 * 获取所有的参数键值
	 * @return
	 */
	@GetMapping("/")
	public Result getAllParams() {
		return viewResultFactory.successResult(getAllSettingParams());
	}
}
