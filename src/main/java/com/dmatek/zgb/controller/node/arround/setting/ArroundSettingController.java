package com.dmatek.zgb.controller.node.arround.setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.controller.setting.node.IPackTool;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@RestController
@RequestMapping("/arround")
public class ArroundSettingController {
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private BaseArroundSettingTool arroundSettingTool;
	@Autowired
	private IPackTool iPackTool;
	/**
	 * 讀取參考點相關參數
	 * @param id
	 * @param keyName
	 * @param routerId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/refer/read/{id}/{keyName}/{routerId}")
	public Result readReferParam(@PathVariable String id,
			@PathVariable String keyName,
			@PathVariable String routerId) throws Exception {
		if (StringUtils.isEmpty(id)) {
			return viewResultFactory.errorResult("設備ID不能為空");
		}
		if(StringUtils.isEmpty(routerId)) {
			return viewResultFactory.errorResult("路由設備ID不能為空");
		}
		if (StringUtils.isEmpty(keyName)) {
			return viewResultFactory.errorResult("鍵名不能為空");
		}
		if (!iPackTool.validate(keyName)) {
			return viewResultFactory.errorResult("鍵名無效");
		}
		return arroundSettingTool.readReferParam(id, keyName, routerId);
	}
	/**
	 * 設置參考點相關參數
	 * @param id
	 * @param keyName
	 * @param paramValue
	 * @param routerId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/refer/set/{id}/{keyName}/{routerId}")
 	public Result setReferParam(@PathVariable String id, @PathVariable String keyName,
			@RequestParam(name="paramValue") String paramValue,@PathVariable String routerId) throws Exception  {
		if (StringUtils.isEmpty(id)) {
			return viewResultFactory.errorResult("設備ID不能為空");
		}
		if(StringUtils.isEmpty(routerId)) {
			return viewResultFactory.errorResult("路由設備ID不能為空");
		}
		if (StringUtils.isEmpty(keyName)) {
			return viewResultFactory.errorResult("鍵名不能為空");
		}
		if (StringUtils.isEmpty(paramValue)){
			return viewResultFactory.errorResult("參數值不能為空");
		}
		String errorMsg = iPackTool.validateValue(keyName, paramValue);
		if(!StringUtils.isEmpty(errorMsg)) {
			return viewResultFactory.errorResult(errorMsg);
		}
		if (!iPackTool.validate(keyName)) {
			return viewResultFactory.errorResult("鍵名無效");
		}
		return arroundSettingTool.setReferParam(id, keyName, paramValue, routerId);
	}
	/**
	 * 搜索周圍設備
	 * @param id
	 * @param keyName
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/search/{id}/{keyName}")
	public Result searchArround(@PathVariable String id,
			@PathVariable String keyName) throws Exception {
		if (StringUtils.isEmpty(id)) {
			return viewResultFactory.errorResult("基站ID不能為空");
		}
		if (StringUtils.isEmpty(keyName)) {
			return viewResultFactory.errorResult("鍵名不能為空");
		}
		if (!iPackTool.validate(keyName)) {
			return viewResultFactory.errorResult("鍵名無效");
		}
		return arroundSettingTool.searchArroundDevices(id, keyName);
	}
	/**
	 * 讀取基站相關參數
	 * @param id
	 * @param keyName
	 * @param routerId
	 * @param channel
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/node/read/{id}/{keyName}/{routerId}/{channel}")
	public Result readNodeParam(@PathVariable String id,
			@PathVariable String keyName, @PathVariable String routerId,
			@PathVariable int channel) throws Exception {
		if(StringUtils.isEmpty(id)) {
			return viewResultFactory.errorResult("設備ID不能為空");
		}
		if(StringUtils.isEmpty(routerId)) {
			return viewResultFactory.errorResult("路由設備ID不能為空");
		}
		if(StringUtils.isEmpty(keyName)) {
			return viewResultFactory.errorResult("鍵名不能為空");
		}
		if(!iPackTool.validate(keyName)) {
			return viewResultFactory.errorResult("鍵名無效");
		}
		// 判斷信道是否有效
		if(channel <= 11 || channel > 26) {//範圍 11 < channel <= 26
			return viewResultFactory.errorResult("信道範圍為12~26(包含)");
		}
		return arroundSettingTool.readNodeParam(id, keyName, routerId, channel);
	}
	/**
	 * 讀取基站相關參數，自身帶參數
	 * @param id
	 * @param keyName
	 * @param keyParam
	 * @param routerId
	 * @param channel
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/node/read/{id}/{keyName}/{keyParam}/{routerId}/{channel}")
	public Result readNodeParam(@PathVariable String id,
			@PathVariable String keyName,
			@PathVariable String keyParam,
			@PathVariable String routerId,
			@PathVariable int channel) throws Exception {
		if(StringUtils.isEmpty(id)) {
			return viewResultFactory.errorResult("設備ID不能為空");
		}
		if(StringUtils.isEmpty(routerId)) {
			return viewResultFactory.errorResult("路由設備ID不能為空");
		}
		if(StringUtils.isEmpty(keyName)) {
			return viewResultFactory.errorResult("鍵名不能為空");
		}
		if(!iPackTool.validate(keyName)) {
			return viewResultFactory.errorResult("鍵名無效");
		}
		String errMsg = iPackTool.validateValue(keyName, keyParam);
		if(!StringUtils.isEmpty(errMsg)) {
			return viewResultFactory.errorResult(errMsg);
		}
		// 判斷信道是否有效
		if(channel <= 11 || channel > 26) {//範圍 11 < channel <= 26
			return viewResultFactory.errorResult("信道範圍為12~26(包含)");
		}
		return arroundSettingTool.readNodeParam(id, keyName, keyParam,
				routerId, channel);
	}
	
	@GetMapping("/node/read/param/{id}/{keyName}/{timeOut}/{routerId}/{channel}")
	public Result readNodeParam(@PathVariable String id,
			@PathVariable String keyName,
			@PathVariable int timeOut,
			@RequestParam("keyParam") String keyParam,
			@PathVariable String routerId,
			@PathVariable int channel) throws Exception {
		if(StringUtils.isEmpty(id)) {
			return viewResultFactory.errorResult("設備ID不能為空");
		}
		if(StringUtils.isEmpty(routerId)) {
			return viewResultFactory.errorResult("路由設備ID不能為空");
		}
		if(StringUtils.isEmpty(keyName)) {
			return viewResultFactory.errorResult("鍵名不能為空");
		}
		if(!iPackTool.validate(keyName)) {
			return viewResultFactory.errorResult("鍵名無效");
		}
		if(timeOut < 0 || timeOut > 65535) {
			return viewResultFactory.errorResult("超時時間範圍0~65535");
		}
		String errMsg = iPackTool.validateValue(keyName, keyParam);
		if(!StringUtils.isEmpty(errMsg)) {
			return viewResultFactory.errorResult(errMsg);
		}
		// 判斷信道是否有效
		if(channel <= 11 || channel > 26) {//範圍 11 < channel <= 26
			return viewResultFactory.errorResult("信道範圍為12~26(包含)");
		}
		return arroundSettingTool.readNodeParam(id, keyName, timeOut,
				keyParam, routerId, channel);
	}
	/**
	 * 設置基站相關參數
	 * @param id
	 * @param keyName
	 * @param keyParam
	 * @param routerId
	 * @param channel
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/node/set/{id}/{keyName}/{keyParam}/{routerId}/{channel}")
	public Result setNodeParam(@PathVariable String id,
			@PathVariable String keyName, @PathVariable String keyParam,
			@PathVariable String routerId, @PathVariable int channel) throws Exception {
		if(StringUtils.isEmpty(id)) {
			return viewResultFactory.errorResult("設備ID不能為空");
		}
		if(StringUtils.isEmpty(routerId)) {
			return viewResultFactory.errorResult("路由設備ID不能為空");
		}
		if (StringUtils.isEmpty(keyName)) {
			return viewResultFactory.errorResult("鍵名不能為空");
		}
		if (StringUtils.isEmpty(keyParam)){
			return viewResultFactory.errorResult("參數值不能為空");
		}
		String errorMsg = iPackTool.validateValue(keyName, keyParam);
		if(!StringUtils.isEmpty(errorMsg)) {
			return viewResultFactory.errorResult(errorMsg);
		}
		if (!iPackTool.validate(keyName)) {
			return viewResultFactory.errorResult("鍵名無效");
		}
		return arroundSettingTool.setNodeParam(id, keyName, keyParam, routerId, channel);
	}
}
