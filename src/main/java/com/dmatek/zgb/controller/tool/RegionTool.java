package com.dmatek.zgb.controller.tool;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.dmatek.zgb.db.pojo.setting.Region;
import com.dmatek.zgb.db.setting.service.RegionService;
import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.redis.cache.TagPackRedisCache;

@Configuration
public class RegionTool {
	@Autowired
	private TagPackRedisCache tagPackRedisCache;
	@Autowired
	private RegionService regionService;
	public List<Region> getGroupAllRegionsNumber(int groupId) throws Exception {
		List<Region> regions =	regionService.findAllFromGroupId(groupId);
		Map<String, TagDetail> tagsMap = tagPackRedisCache.getCacheMap();
		Iterator<TagDetail> iterators = tagsMap.values().iterator();
		while (iterators.hasNext()) {
			TagDetail tagDetail = iterators.next();
			if(!tagDetail.isClear() && 
			tagDetail.getGroupId() == groupId) {
				for (Region region : regions) {
					if(tagDetail.getRegionId() == region.getId()) {
						// 當前區域數量  + 1
						region.setNumber(region.getNumber() + 1);
					}
				}
			}
		}
		return regions;
	}
}
