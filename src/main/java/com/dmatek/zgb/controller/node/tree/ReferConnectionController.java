package com.dmatek.zgb.controller.node.tree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dmatek.zgb.controller.refer.data.ReferDataTool;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@RestController
@RequestMapping("/connection")
public class ReferConnectionController {
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private ReferDataTool referDataTool;
	/**
	 * 獲取所有的基站訊息
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/nodes")
	public Result getAllNodes() throws Exception {
		return viewResultFactory.successResult(referDataTool.getAllNodeDetails());
	}
	/**
	 * 獲取所有當前上報的基站訊息
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/refers")
	public Result getAllRefers() throws Exception {
		return viewResultFactory.successResult(referDataTool.getAllReferDetails());
	}
}
