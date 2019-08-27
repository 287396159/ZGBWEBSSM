package com.dmatek.zgb.controller.node.data;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dmatek.zgb.monitor.device.detail.NodeDetail;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@RestController
@RequestMapping("/nodemsg")
public class NodeDataController {
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private NodeDataTool nodeDataTool;
	
	@GetMapping("/region/{regionId}")
	public Result getRegionNodes(@PathVariable int regionId) throws Exception{
		List<NodeDetail> nodeDetails = null;
		if(regionId >= 0){
			nodeDetails = nodeDataTool.getRegionNodes(regionId);
		} else {
			nodeDetails = nodeDataTool.getOtherNodeDetails();
		}
		return viewResultFactory.successResult(nodeDetails);
	}
}
