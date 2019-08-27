package com.dmatek.zgb.controller.setting.node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@RestController
@RequestMapping("/setting/")
public class DevicesParamController {
	@Autowired
	private BaseDeviceParamTool deviceParamTool;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private IPackTool iPackTool;
	/**
	 * 复位设备
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/reset/{id}/{keyName}")
	public Result setReset(@PathVariable String id,
			@PathVariable String keyName) throws Exception {
		if (StringUtils.isEmpty(id)) {
			return viewResultFactory.errorResult("設備ID不能為空");
		}
		if (id.length() != 4) {
			return viewResultFactory.errorResult("設備ID只能為4位");
		}
		if(!iPackTool.validate(keyName)) {
			return viewResultFactory.errorResult("鍵名不存在");
		}
		return deviceParamTool.readParam(id, keyName);
	}
	/**
	 * 讀取參數值-自身帶參數
	 * @param id
	 * @param paramKey
	 * @param paramVal
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/read/{id}/{paramKey}/{paramVal}")
	public Result readParam(@PathVariable String id,
			@PathVariable String paramKey,
			@PathVariable String paramVal) throws Exception {
		if (StringUtils.isEmpty(id)) {
			return viewResultFactory.errorResult("設備ID不能為空");
		}
		if (id.length() != 4) {
			return viewResultFactory.errorResult("設備ID只能為4位");
		}
		if(!iPackTool.validate(paramKey)) {
			return viewResultFactory.errorResult("參數名稱有誤");
		}
		return deviceParamTool.readParam(id, paramKey, paramVal);
	}
	/**
	 * 读取指定设备的参数
	 * @param id 
	 * @param paramType
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/read/{id}/{paramKey}")
	public Result readParam(@PathVariable String id,
			@PathVariable String paramKey) throws Exception {
		if (StringUtils.isEmpty(id)) {
			return viewResultFactory.errorResult("設備ID不能為空");
		}
		if (id.length() != 4) {
			return viewResultFactory.errorResult("設備ID只能為4位");
		}
		if(!iPackTool.validate(paramKey)) {
			return viewResultFactory.errorResult("參數名稱有誤");
		}
		return deviceParamTool.readParam(id, paramKey);
	}
	/**
	 * 设置设备参数
	 * @param id
	 * @param paramType
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/set/{id}/{paramKey}")
	public Result setParam(@PathVariable String id,
			@PathVariable String paramKey, 
			@RequestParam(name="paramVal") String paramVal) throws Exception {
		if(StringUtils.isEmpty(id)) {
			return viewResultFactory.errorResult("設備ID不能為空");
		}
		if (id.length() != 4) {
			return viewResultFactory.errorResult("設備ID只能為4位");
		}
		if(StringUtils.isEmpty(paramVal)) {
			return viewResultFactory.errorResult("參數值不能為空");
		}
		String errMsg = iPackTool.validateValue(paramKey, paramVal);
		if(!StringUtils.isEmpty(errMsg)) {
			return viewResultFactory.errorResult(errMsg);
		}
		return deviceParamTool.setParam(id, paramKey, paramVal);
	}
}
