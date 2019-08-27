package com.dmatek.zgb.setting.groupTool;

import java.util.Collection;
import java.util.List;

import com.dmatek.zgb.cache.tool.detail.BaseCaptureCacheData;
import com.dmatek.zgb.db.pojo.setting.BaseSettingPojo;
import com.dmatek.zgb.monitor.bean.TagPacket;
import com.dmatek.zgb.monitor.device.detail.TagDetail;

public abstract class TagNumberTool<T extends V,V extends BaseSettingPojo> {
	private BaseCaptureCacheData<TagDetail, TagPacket> baseTagDetailCapture;
	
	public TagNumberTool(BaseCaptureCacheData<TagDetail, TagPacket> baseTagDetailCapture){
		this.baseTagDetailCapture = baseTagDetailCapture;
	}
	public abstract Collection<T> getAllSettingPojo(List<V> pojos)throws Exception;
	public BaseCaptureCacheData<TagDetail, TagPacket> getBaseTagDetailCapture() {
		return baseTagDetailCapture;
	}
	public void setBaseTagDetailCapture(
			BaseCaptureCacheData<TagDetail, TagPacket> baseTagDetailCapture) {
		this.baseTagDetailCapture = baseTagDetailCapture;
	}
}
