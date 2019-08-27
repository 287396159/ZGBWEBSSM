package com.dmatek.zgb.db.access.work.service;

import java.util.Date;
import java.util.List;

import com.dmatek.zgb.access.bean.TagAccessRecord;

public interface AccessWorkService {
	boolean insertOne(TagAccessRecord tagAccessRecord) throws Exception;
	boolean deleteOne(String uuid) throws Exception;
	List<TagAccessRecord> findAll() throws Exception;
	TagAccessRecord findCurrentDayOne(String tagId, Date current, int type) throws Exception;
	List<TagAccessRecord> searchAccessRecords(String name, String companyNo, String jobTypeNo, int groupId, Date start, Date end) throws Exception;

	void clearRecords(int days) throws Exception;
}
