package com.dmatek.zgb.controller.node.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.dmatek.zgb.cache.tool.detail.CaptureNodeCacheData;
import com.dmatek.zgb.cache.tool.detail.CaptureReferCacheData;
import com.dmatek.zgb.db.pojo.setting.Node;
import com.dmatek.zgb.db.setting.service.NodeService;
import com.dmatek.zgb.monitor.device.detail.NodeDetail;

@Component
public class NodeDataTool {
	@Autowired
	private NodeService nodeService;
	@Autowired
	private CaptureNodeCacheData captureNodeCacheData;
	@Autowired
	private CaptureReferCacheData captureReferCacheData;
	
	public List<NodeDetail> getRegionNodes(int regionId) throws Exception {
		List<NodeDetail> nodesList = new ArrayList<NodeDetail>();
		Map<Object, Object> nodesCacheMap = captureNodeCacheData.getCacheData();
		Iterator<Object> iterator = nodesCacheMap.keySet().iterator();
		while(iterator.hasNext()){
			String key = (String) iterator.next();
			NodeDetail nodeDetail = (NodeDetail) nodesCacheMap.get(key);
			if(nodeDetail.getRegionId() == regionId){
				nodesList.add(nodeDetail);
			}
		}
		return nodesList;
	}
	
	public List<NodeDetail> getAllNodeDetails() throws Exception{
		List<NodeDetail> nodesList = new ArrayList<NodeDetail>();
		Map<Object, Object> nodesCacheMap = captureNodeCacheData.getCacheData();
		Iterator<Object> iterator = nodesCacheMap.keySet().iterator();
		while(iterator.hasNext()){
			String key = (String) iterator.next();
			NodeDetail nodeDetail = (NodeDetail) nodesCacheMap.get(key);
			nodesList.add(nodeDetail);
		}
		return nodesList;
	}
	
	public List<NodeDetail> getOtherNodeDetails() throws Exception{
		List<NodeDetail> nodesList = new ArrayList<NodeDetail>();
		Map<Object, Object> nodesCacheMap = captureNodeCacheData.getCacheData();
		Iterator<Object> iterator = nodesCacheMap.keySet().iterator();
		while(iterator.hasNext()){
			String key = (String) iterator.next();
			NodeDetail nodeDetail = (NodeDetail) nodesCacheMap.get(key);
			Node node = nodeService.findOne(nodeDetail.getId());
			if(null == node){
				nodesList.add(nodeDetail);
			}
		}
		return nodesList;
	}
}
