package com.dmatek.zgb.setting.groupTool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmatek.zgb.cache.tool.detail.BaseCaptureCacheData;
import com.dmatek.zgb.monitor.bean.TagPacket;
import com.dmatek.zgb.monitor.device.detail.TagDetail;

@Configuration
public class TagNumberToolConfig {
	@Bean
	public GroupTagNumberTool getGroupTagNumberTool(BaseCaptureCacheData<TagDetail, TagPacket> baseTagDetailCapture){
		return new GroupTagNumberTool(baseTagDetailCapture);
	}
}
