package com.dmatek.zgb.db.access.work.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.access.bean.TagAccessRecord;

public interface AccessWorkDao {
	boolean insertOne(@Param("accessRecord") TagAccessRecord tagAccessRecord) throws Exception;
	boolean deleteOne(@Param("uuid") String uuid) throws Exception;
	List<TagAccessRecord> findAll() throws Exception;
	TagAccessRecord findCurrentDayOne(@Param("tagId") String tagId,
			@Param("current") Date current,@Param("type") int type) throws Exception;
	/*搜寻所有出入记录*/
	List<TagAccessRecord> searchAccessRecords( @Param("name") String name, 
			@Param("companyNo") String companyNo, @Param("jobTypeNo") String jobTypeNo, 
			@Param("groupId") int groupId, @Param("start") Date start, 
			@Param("end") Date end) throws Exception;

	void clearRecords(int days) throws Exception;
}
