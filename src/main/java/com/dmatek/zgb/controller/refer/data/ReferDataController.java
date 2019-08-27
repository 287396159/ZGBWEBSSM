package com.dmatek.zgb.controller.refer.data;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dmatek.zgb.monitor.device.detail.ReferDetail;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@RestController
@RequestMapping("/refer/data/")
public class ReferDataController {
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private ReferDataTool referDataTool;
	@GetMapping("/number/{regionId}")
	public Result getReferNumber(@PathVariable int regionId) throws Exception{
		Map<String, ReferNumber> referMap = null;
		referMap = referDataTool.getReferNumber(regionId);
		return viewResultFactory.successResult(referMap.values());
	}
	@GetMapping("/region/{regionId}")
	public Result getRegionRefers(@PathVariable int regionId) throws Exception{
		List<ReferDetail> refers = null;
		if(regionId >= 0){
			refers = referDataTool.getRegionRefers(regionId);
		} else {
			refers = referDataTool.getOtherReferDetails();
		}
		return viewResultFactory.successResult(refers);
	}
	
}
