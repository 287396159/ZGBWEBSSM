package com.dmatek.zgb.access.retriever;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.thymeleaf.util.StringUtils;
import com.dmatek.zgb.access.IAccessDetailCapture;
import com.dmatek.zgb.access.bean.FillAccessRecord;
import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.db.access.service.AccessService;

public class DataBaseAccessRetriever extends BaseAccessRetriever {
	private AccessService accessService;
	public DataBaseAccessRetriever(IAccessDetailCapture iAccessDetailCapture, 
			AccessService accessService) {
		super(iAccessDetailCapture);
		this.accessService = accessService;
	}
	@Override
	public Map<String, TagAccessRecord> searchLatestAccessRecord(int groupId,
			int accessType, Date accessTime) throws Exception {
		Map<String, TagAccessRecord> accessMap = new HashMap<String, TagAccessRecord>();
		List<TagAccessRecord> dbAccessRecords = getAccessService()
				.findGroupTypeTime(groupId, accessType, accessTime);
		for (TagAccessRecord tagAccessRecord : dbAccessRecords) {
			// 判断两个时间哪一个在后面，在后面的需要重新添加
			if (isMatch(tagAccessRecord, accessMap)) {
				accessMap.put(tagAccessRecord.getTagId(), tagAccessRecord);
			}
		}
		return accessMap;
	}
	@Override
	public boolean removeAccessRecord(String tagId, int groupId,
			int accessType, Date accessTime) throws Exception {
		TagAccessRecord tagAccessRecord = null;
		if(!StringUtils.isEmpty(tagId) && groupId > 0 && null != accessTime) {
			tagAccessRecord = getAccessService().findOne(tagId, groupId, accessType, accessTime);
		}
		if(null != tagAccessRecord) {
			return getAccessService().deleteAccessRecord(tagAccessRecord.getUuid());
		}
		return false;
	}
	@Override
	public boolean addAccessRecord(FillAccessRecord accessRecord)
			throws Exception {
		TagAccessRecord tagAccessRecord = getAccessService().findOne(
				accessRecord.getTagId(), accessRecord.getGroupId(),
				accessRecord.getAccessType(), accessRecord.getAccessTime());
		if(null != tagAccessRecord) {
			getAccessService().deleteAccessRecord(tagAccessRecord.getUuid());
		}
		TagAccessRecord record = getiAccessDetailCapture().obtainAccessRecord(accessRecord);
		return getAccessService().addAccessRecord(record);
	}
	public AccessService getAccessService() {
		return accessService;
	}
	public void setAccessService(AccessService accessService) {
		this.accessService = accessService;
	}
}
