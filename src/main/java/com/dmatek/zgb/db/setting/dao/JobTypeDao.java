package com.dmatek.zgb.db.setting.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.db.pojo.setting.JobType;
import com.dmatek.zgb.db.pojo.setting.TimelyJobType;

public interface JobTypeDao {
	boolean addJobType(@Param("jobType") JobType jobType) throws Exception;
	boolean deleteJobType(@Param("ids") List<String> nos) throws Exception;
	boolean updateJobType(@Param("jobType") JobType jobType) throws Exception;
	JobType findOne(@Param("no") String no) throws Exception;
	List<JobType> findPageKeyName(@Param("page")int page,@Param("limit")int limit,@Param("keyName")String keyName) throws Exception;
	List<JobType> findPage(@Param("page") int page,@Param("limit") int limit) throws Exception;
	List<JobType> findAll() throws Exception;
	List<TimelyJobType> findTimelyJobTypes() throws Exception;
	
	JobType findName(@Param("name") String name) throws Exception;
}
