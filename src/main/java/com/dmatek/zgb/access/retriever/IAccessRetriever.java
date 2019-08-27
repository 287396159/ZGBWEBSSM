package com.dmatek.zgb.access.retriever;

import java.util.Date;
import java.util.Map;
import com.dmatek.zgb.access.bean.FillAccessRecord;
import com.dmatek.zgb.access.bean.TagAccessRecord;

public interface IAccessRetriever {
	/**
	 * 查找最新出入记录
	 * @param groupId
	 * @param accessType
	 * @param accessTime
	 * @return
	 * @throws Exception
	 */
	public Map<String, TagAccessRecord> searchLatestAccessRecord(int groupId, int accessType,
			Date accessTime) throws Exception;
	/**
	 * 删除出入记录
	 * @param groupId
	 * @param accessType
	 * @param accessTime
	 * @return
	 * @throws Exception
	 */
	public boolean removeAccessRecord(String tagId, int groupId, int accessType, Date accessTime) throws Exception;
	/**
	 * 补填上下班记录
	 * @param accessRecord
	 * @return
	 * @throws Exception
	 */
	public boolean addAccessRecord(FillAccessRecord accessRecord) throws Exception;
}
