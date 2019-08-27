package com.dmatek.zgb.setting.groupTool;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.dmatek.zgb.cache.tool.detail.BaseCaptureCacheData;
import com.dmatek.zgb.db.pojo.setting.Group;
import com.dmatek.zgb.db.pojo.setting.GroupTagNumber;
import com.dmatek.zgb.monitor.bean.TagPacket;
import com.dmatek.zgb.monitor.device.detail.TagDetail;

public class GroupTagNumberTool extends TagNumberTool<GroupTagNumber,Group> {
	public GroupTagNumberTool(
			BaseCaptureCacheData<TagDetail, TagPacket> baseTagDetailCapture) {
		super(baseTagDetailCapture);
	}
	@Override
	public Collection<GroupTagNumber> getAllSettingPojo(List<Group> pojos)
			throws Exception {
		@SuppressWarnings("unchecked")
		Map<Integer, Integer> groupTagNumberMap = new HashedMap();
		Iterator<Object> iterators = getBaseTagDetailCapture().getCacheData()
					.values().iterator();
		while (iterators.hasNext()) {
			TagDetail tagDetail = (TagDetail) iterators.next();
			Integer groupTagNumber = groupTagNumberMap.get(tagDetail.getGroupId());
			if(null == groupTagNumber){
				groupTagNumberMap.put(tagDetail.getGroupId(), 1);
			} else {
				groupTagNumberMap.put(tagDetail.getGroupId(), groupTagNumber + 1);
			}
		}
		Map<Integer, GroupTagNumber> groupTagsMap = new LinkedHashMap<Integer, GroupTagNumber>();
		for (int i = 0; i < pojos.size(); i++) {
			Integer number = groupTagNumberMap.get(pojos.get(i).getId());
			if(null == number){
				groupTagsMap.put(pojos.get(i).getId(), 
						new GroupTagNumber(0, pojos.get(i)));
			} else {
				groupTagsMap.put(pojos.get(i).getId(), 
						new GroupTagNumber(number, pojos.get(i)));
			}
		}
		return groupTagsMap.values();
	}
}
