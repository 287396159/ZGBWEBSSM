package com.dmatek.zgb.cache.tool.detail;

import org.springframework.beans.factory.annotation.Autowired;

import com.dmatek.zgb.db.pojo.setting.Group;
import com.dmatek.zgb.db.pojo.setting.Node;
import com.dmatek.zgb.db.pojo.setting.Region;
import com.dmatek.zgb.db.setting.service.GroupService;
import com.dmatek.zgb.db.setting.service.NodeService;
import com.dmatek.zgb.db.setting.service.RegionService;
import com.dmatek.zgb.monitor.bean.ReferPack;
import com.dmatek.zgb.monitor.device.detail.ReferDetail;
import com.dmatek.zgb.redis.service.RedisService;

public class CaptureReferCacheData extends BaseCaptureCacheData<ReferDetail, ReferPack> {
	@Autowired
	private NodeService nodeService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private GroupService groupService;
	public CaptureReferCacheData(RedisService redisService, String cacheName) {
		super(redisService, cacheName);
	}
	@Override
	public ReferDetail getDetailCacheData(ReferPack obj) throws Exception {
		Region region = null;
		Node node = nodeService.findOne(obj.getId());
		if (null != node) {
			region = regionService.findOne(node.getRegionId());
		}
		Group group = null;
		if (null != region) {
			group = groupService.findOne(region.getGroupId());
		}
		ReferDetail referDetail = new ReferDetail(
				null != region ? region.getGroupId() : -1,
				null != region ? region.getId() : -1,
				null != group ? group.getName() : "",
				null != region ? region.getName() : "",
				null != node ? node.getName() : "", obj);
		return referDetail;
	}
}
