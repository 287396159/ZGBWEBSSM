package com.dmatek.zgb.controller.tag.data;

import java.util.Map;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dmatek.zgb.monitor.callback.ZGBMonitorCallBack;
import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.show.vo.Group_Regions;

@RestController
@RequestMapping("/tagmsg")
public class TagDataController {
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private ZGBMonitorCallBack zgbMonitorCallBack;
	@Autowired
	private TagDataTool tagDataTool;
	/**
	 * 獲取指定區域的所有卡片顯示訊息
	 * @param regionId
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/{regionId}")
	public Result getRegionTags(@PathVariable int regionId) throws Exception {
		Map<String, TagDetail> tagDetailHashMap = null;
		tagDetailHashMap = tagDataTool.getCacheTagDetailsFromRegion(regionId);
		return viewResultFactory.successLayResult(tagDetailHashMap.values());
	}
	/**
	 * 獲取卡片當前的總數量
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/total/")
	public Result getTotalTags() throws Exception{
		int total = tagDataTool.getCacheTagsTotal();
		return viewResultFactory.successResult(total);
	}
	/**
	 * 獲取組別人數
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/all/")
	public Result getGroupsTags() throws Exception{
		Map<Integer,Group_Regions> g_regionsNumber = tagDataTool.getCacheGroupRegionsNumber();
		return viewResultFactory.successResult(g_regionsNumber.values());
	}
	/**
	 * 獲取參考點周圍的卡片訊息
	 * @param referId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/refer/{referId}")
	public Result getReferTags(@PathVariable String referId) throws Exception {
		Map<String, TagDetail> tagDetailHashMap = null;
		tagDetailHashMap = tagDataTool.getCacheTagDetailsFromRefer(referId);
		return viewResultFactory.successResult(tagDetailHashMap.values());
	}
	/**
	 * 獲取搜索的卡片訊息
	 * @param searchText
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/search/{searchText}")
	public Result getSearchTags(@PathVariable String searchText) throws Exception{
		if(Strings.isEmpty(searchText)){
			return viewResultFactory.errorResult("搜索卡片信息不能為空...");
		}
		TagDetail  searchTag = tagDataTool.getCacheTagDetailFromSearchText(searchText);
		if(null != searchTag){
			return viewResultFactory.successResult(searchTag);
		}
		return viewResultFactory.errorResult("卡片不存在...");
	}
}
