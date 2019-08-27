package com.dmatek.zgb.db.access.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.access.bean.TagAccessRecord;

public interface AccessDao {
	boolean addAccessRecord(@Param("accessRecord") TagAccessRecord accessRecord) throws Exception;
	boolean deleteAccessRecord(@Param("uuid") String uuid) throws Exception;
	List<TagAccessRecord> findAll() throws Exception;
	TagAccessRecord findOne(@Param("tagId") String tagId,@Param("groupId") int groupId, @Param("accessType") int accessType,@Param("accessTime") Date accessTime) throws Exception;
	List<TagAccessRecord> findAllRecordOneDay(@Param("tagId") String tagId,@Param("accessType") int accessType,@Param("accessTime") Date accessTime) throws Exception;
	List<TagAccessRecord> findGroupTypeTime(@Param("groupId") int groupId,@Param("accessType") int accessType,@Param("accessTime") Date accessTime) throws Exception;
	List<TagAccessRecord> findGroupTime(@Param("groupId") int groupId,@Param("accessTime") Date accessTime) throws Exception;
	// 条件查询
	List<TagAccessRecord> searchTagAccessRecords(@Param("name") String name,
			@Param("companyNo") String companyNo,@Param("jobTypeNo") String jobTypeNo, 
			@Param("groupId") int groupId,@Param("start") Date start,
			@Param("end") Date end) throws Exception;

	void clearRecords(int days) throws Exception;
}
