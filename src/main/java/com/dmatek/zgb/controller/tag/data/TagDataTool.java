package com.dmatek.zgb.controller.tag.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.dmatek.zgb.cache.tool.detail.CaptureTagCacheData;
import com.dmatek.zgb.db.pojo.setting.Group;
import com.dmatek.zgb.db.pojo.setting.Region;
import com.dmatek.zgb.db.setting.service.GroupService;
import com.dmatek.zgb.db.setting.service.JobTypeService;
import com.dmatek.zgb.db.setting.service.RegionService;
import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.show.vo.Group_Regions;
import com.dmatek.zgb.warm.scan.ScanTagWarmTool;

@Component
public class TagDataTool {
	@Autowired
	private CaptureTagCacheData captureTagCacheData;
	@Autowired
	private JobTypeService jobTypeService;
	@Autowired
	private ScanTagWarmTool scanTagWarmTool;
	@Autowired
	private GroupService groupService;
	@Autowired
	private RegionService regionService;
	/**
	 * 获取组别数量缓存
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, Group_Regions> getCacheGroupRegionsNumber() throws Exception {
		Map<Integer, Group_Regions> groupsTag = new HashMap<Integer, Group_Regions>();
		List<Group> groups = groupService.findAll();
		for (int i = 0; i < groups.size(); i++) {
			// 获取指定组别的所有区域
			List<Region> regions = regionService.findAllFromGroupId(groups.get(
					i).getId());
			// 设置当前区域的卡片数量
			int groupNumber = 0;
			for (int j = 0; j < regions.size(); j++) {
				 Map<String, TagDetail> tagsMap = 
						 getCacheTagDetailsFromRegion(regions.get(j).getId());
				 // 设置区域数量
				 regions.get(j).setNumber(tagsMap.size());
				 groupNumber += tagsMap.size();
			}
			Group_Regions group_Regions = new Group_Regions(groups.get(i),
					regions, groupNumber);
			groupsTag.put(groups.get(i).getId(), group_Regions);
		}
		return groupsTag;
	}
	/**
	 * 获取卡片的总数量
	 * @return
	 * @throws Exception
	 */
	public int getCacheTagsTotal() throws Exception {
		Map<String,TagDetail> allTags = getCacheAllTagDetails();
		return allTags.size();
	}
	/**
	 * 获取搜索的卡片讯息
	 * @param searchText
	 * @return
	 * @throws Exception
	 */
	public TagDetail getCacheTagDetailFromSearchText(String searchText) throws Exception{
		Map<Object,Object> cacheMap = captureTagCacheData.getCacheData();
		Iterator<Object> iterator = cacheMap.keySet().iterator();
		while(iterator.hasNext()){
			String key = (String) iterator.next();
			TagDetail tagDetail = (TagDetail) cacheMap.get(key);
			// 比较卡片ID是否匹配
			if(searchText.equalsIgnoreCase(tagDetail.getId())){// ID匹配上
				return tagDetail;
			} else if(searchText.equalsIgnoreCase(tagDetail.getTagName())){// 卡片名称匹配上
				return tagDetail;
			} else if(searchText.equalsIgnoreCase(tagDetail.getTagNo())){
				return tagDetail;
			}
		}
		return null;
	}
	/**
	 * 根据参考点ID获取卡片讯息
	 * @param referId
	 * @return
	 * @throws Exception
	 */
	public Map<String,TagDetail> getCacheTagDetailsFromRefer(String referId) throws Exception {
		Map<String,TagDetail> tagDetailMap = new HashMap<String, TagDetail>();
		Map<Object,Object> cacheMap = captureTagCacheData.getCacheData();
		Iterator<Object> iterator = cacheMap.keySet().iterator();
		while(iterator.hasNext()){
			String key = (String) iterator.next();
			TagDetail tagDetail = (TagDetail) cacheMap.get(key);
			if(tagDetail.getrId().equalsIgnoreCase(referId) && 
			  !tagDetail.isClear()){
				tagDetailMap.put(key, tagDetail);
			}
		}
		return tagDetailMap;
	}
	/**
	 * 根据区域ID获取卡片讯息
	 * @param regionId
	 * @return
	 * @throws Exception
	 */
	public Map<String,TagDetail> getCacheTagDetailsFromRegion(int regionId) throws Exception{
		Map<String,TagDetail> tagDetailMap = new HashMap<String, TagDetail>();
		Map<Object,Object> cacheMap = captureTagCacheData.getCacheData();
		Iterator<Object> iterator = cacheMap.keySet().iterator();
		while(iterator.hasNext()) {
			String key = (String) iterator.next();
			TagDetail tagDetail = (TagDetail) cacheMap.get(key);
			// 比较当前卡片是否断开连接
			if(tagDetail.getRegionId() == regionId && 
				!tagDetail.isClear()) {
				tagDetailMap.put(key, tagDetail);
			}
		}
		return tagDetailMap;
	}
	/**
	 * 获取缓存的所有卡片详细讯息
	 * @return
	 * @throws Exception
	 */
	public Map<String,TagDetail> getCacheAllTagDetails() throws Exception{
		Map<String,TagDetail> tagDetailMap = new HashMap<String, TagDetail>();
		Map<Object,Object> cacheMap = captureTagCacheData.getCacheData();
		Iterator<Object> iterator = cacheMap.keySet().iterator();
		while(iterator.hasNext()) {
			String key = (String) iterator.next();
			TagDetail tagDetail = (TagDetail) cacheMap.get(key);
			if(!tagDetail.isClear()) {
				tagDetailMap.put(key, tagDetail);
			}
		}
		return tagDetailMap;
	}
}
