package com.dmatek.zgb.access.retriever;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import com.dmatek.zgb.access.IAccessDetailCapture;
import com.dmatek.zgb.access.bean.FillAccessRecord;
import com.dmatek.zgb.access.bean.TagAccessRecord;

/**
 * 
 * @author Administrator
 * @data 2019年4月13日 下午3:35:52
 * @Description
 */
public class StorageAccessRetriever extends BaseAccessRetriever {
	private CacheAccessRetriever cacheAccessRetriever;
	private DataBaseAccessRetriever dataBaseAccessRetriever;
	public StorageAccessRetriever(IAccessDetailCapture iAccessDetailCapture, CacheAccessRetriever cacheAccessRetriever, DataBaseAccessRetriever dataBaseAccessRetriever) {
		super(iAccessDetailCapture);
		this.cacheAccessRetriever = cacheAccessRetriever;
		this.dataBaseAccessRetriever = dataBaseAccessRetriever;
	}
	@Override
	public Map<String, TagAccessRecord> searchLatestAccessRecord(int groupId,
			int accessType, Date accessTime) throws Exception {
		// 获取缓存出入记录
		Map<String, TagAccessRecord> cacheAccessRecords = getCacheAccessRetriever()
				.searchLatestAccessRecord(groupId, accessType, accessTime);
		// 获取数据库出入记录
		Map<String, TagAccessRecord> dataBaseAccessRecords = getDataBaseAccessRetriever()
				.searchLatestAccessRecord(groupId, accessType, accessTime);
		// 将两个记录合并，去掉重复的记录
		Iterator<TagAccessRecord> iterator = dataBaseAccessRecords.values().iterator();
		while(iterator.hasNext()) {
			TagAccessRecord accessRecord = iterator.next();
			if(isMatch(accessRecord, cacheAccessRecords)) {
				cacheAccessRecords.put(accessRecord.getTagId(), accessRecord);
			}
		}
		return cacheAccessRecords;
	}
	@Override
	public boolean removeAccessRecord(String tagId, int groupId,
			int accessType, Date accessTime) throws Exception {
		getCacheAccessRetriever().removeAccessRecord(tagId, groupId, accessType, accessTime);
		getDataBaseAccessRetriever().removeAccessRecord(tagId, groupId, accessType, accessTime);
		return false;
	}
	@Override
	public boolean addAccessRecord(FillAccessRecord accessRecord)
			throws Exception {
		// 判断当前记录 的 时间
		return false;
	}
	public CacheAccessRetriever getCacheAccessRetriever() {
		return cacheAccessRetriever;
	}
	public void setCacheAccessRetriever(CacheAccessRetriever cacheAccessRetriever) {
		this.cacheAccessRetriever = cacheAccessRetriever;
	}
	public DataBaseAccessRetriever getDataBaseAccessRetriever() {
		return dataBaseAccessRetriever;
	}
	public void setDataBaseAccessRetriever(
			DataBaseAccessRetriever dataBaseAccessRetriever) {
		this.dataBaseAccessRetriever = dataBaseAccessRetriever;
	}
}
