package com.dmatek.zgb.db.access.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.access.bean.TagAccessRecord;

public interface AccessService {
	boolean addAccessRecord(TagAccessRecord accessRecord) throws Exception;
	boolean deleteAccessRecord(String uuid) throws Exception;
	List<TagAccessRecord> findAll() throws Exception;
	TagAccessRecord findOne(String tagId, int groupId, int accessType, Date accessTime) throws Exception;
	// 获取同一天的所有记录
	List<TagAccessRecord> findAllRecordOneDay(String tagId,int accessType, Date accessTime) throws Exception;
	List<TagAccessRecord> findGroupTime(@Param("groupId") int groupId,@Param("accessTime") Date accessTime) throws Exception;
	List<TagAccessRecord> findGroupTypeTime(@Param("groupId") int groupId,@Param("accessType") int accessType,@Param("accessTime") Date accessTime) throws Exception;
	// 条件查询
	List<TagAccessRecord> searchTagAccessRecords(String name,
			String companyNo, String jobTypeNo, int groupId, Date start,
			Date end) throws Exception;

	void clearRecords(int days) throws Exception;
}
