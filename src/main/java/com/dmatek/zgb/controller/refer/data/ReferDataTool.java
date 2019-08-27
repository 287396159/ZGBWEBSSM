package com.dmatek.zgb.controller.refer.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmatek.zgb.cache.tool.detail.CaptureNodeCacheData;
import com.dmatek.zgb.cache.tool.detail.CaptureReferCacheData;
import com.dmatek.zgb.cache.tool.detail.CaptureTagCacheData;
import com.dmatek.zgb.controller.tag.data.TagDataTool;
import com.dmatek.zgb.db.pojo.setting.Node;
import com.dmatek.zgb.db.setting.service.NodeService;
import com.dmatek.zgb.monitor.bean.ReferPack;
import com.dmatek.zgb.monitor.device.detail.NodeDetail;
import com.dmatek.zgb.monitor.device.detail.ReferDetail;
import com.dmatek.zgb.monitor.device.detail.TagDetail;

@Component
public class ReferDataTool {
	@Autowired
	private NodeService nodeService;
	@Autowired
	private TagDataTool tagDataTool;
	@Autowired
	private CaptureTagCacheData captureTagCacheData;
	@Autowired
	private CaptureReferCacheData captureReferCacheData;
	@Autowired
	private CaptureNodeCacheData captureNodeCacheData;
	/**
	 * 獲取所有的基站訊息
	 * @return
	 * @throws Exception
	 */
	public List<NodeDetail> getAllNodeDetails() throws Exception{
		List<NodeDetail> allNodeDetails = new ArrayList<NodeDetail>();
		Iterator<Object> iterators = captureNodeCacheData.getCacheData().values().iterator();
		while (iterators.hasNext()) {
			NodeDetail nodePack = (NodeDetail) iterators.next();
			allNodeDetails.add(nodePack);
		}
		return allNodeDetails;
	}
	/**
	 * 獲取所有的參考點訊息
	 * @return
	 * @throws Exception
	 */
	public List<ReferDetail> getAllReferDetails() throws Exception {
		List<ReferDetail> allReferDetails = new ArrayList<ReferDetail>();
		Iterator<Object> iterators = captureReferCacheData.getCacheData().values().iterator();
		while (iterators.hasNext()) {
			ReferDetail referPack = (ReferDetail) iterators.next();
			allReferDetails.add(referPack);
		}
		return allReferDetails;
	}
	/**
	 * 獲取所有其他的參考點詳細訊息
	 * @return
	 * @throws Exception
	 */
	public List<ReferDetail> getOtherReferDetails() throws Exception {
		List<ReferDetail> allReferDetails = new ArrayList<ReferDetail>();
		Iterator<Object> iterators = captureReferCacheData.getCacheData().values().iterator();
		while (iterators.hasNext()) {
			ReferDetail referPack = (ReferDetail) iterators.next();
			Node node = nodeService.findOne(referPack.getId());
			if(node == null){// 說明當前參考點并沒有設置
				allReferDetails.add(referPack);
			}
		}
		return allReferDetails;
	}
	/**
	 * 获取参考点详细讯息
	 * @param regionId
	 * @return
	 * @throws Exception
	 */
	public List<ReferDetail> getRegionRefers(int regionId) throws Exception {
		List<ReferDetail> refersList = new ArrayList<ReferDetail>();
		Map<Object, Object> refersCacheMap = captureReferCacheData.getCacheData();
		Iterator<Object> iterator = refersCacheMap.keySet().iterator();
		while(iterator.hasNext()){
			String key = (String) iterator.next();
			ReferPack referPack = (ReferPack) refersCacheMap.get(key);
			ReferDetail referDetail = captureReferCacheData.getDetailCacheData(referPack);
			if(referDetail.getRegionId() == regionId){
				refersList.add(referDetail);
			}
		}
		return refersList;
	}
	
	/**
	 * 获取参考点周围卡片的数量
	 * @param regionId
	 * @return
	 * @throws Exception
	 */
	public Map<String, ReferNumber> getReferNumber(int regionId) throws Exception{
		Map<String, ReferNumber> referMap = new HashMap<String, ReferNumber>();
		List<Node> nodesList = nodeService.findNodesFromRegionId(regionId);
		for (Node node : nodesList) {
			// 0: 正常參考點，2： 進出統計的參考點
			if(node.getType() == 0 || node.getType() == 2){// 参考点
				referMap.put(node.getId(), new ReferNumber(node.getId(), 0));
			}
		}
		// 获取redis中的区域卡片讯息 
		Map<Object,Object> cacheMap = captureTagCacheData.getCacheData();
		Iterator<Object> iterator = cacheMap.keySet().iterator();
		while(iterator.hasNext()) {
			String key = (String) iterator.next();
			TagDetail tagPack = (TagDetail) cacheMap.get(key);
			if(!tagPack.isClear()) {
				ReferNumber referNumber = referMap.get(tagPack.getrId());
				// status=> false: 断开状态; true: 连接状态;
				if(null != referNumber) {
					referNumber.setTotal(referNumber.getTotal() + 1);
				}
			}
		}
		return referMap;
	}
}
