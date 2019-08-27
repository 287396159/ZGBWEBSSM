package com.dmatek.zgb.db.setting.service;

import java.util.List;

import com.dmatek.zgb.db.pojo.setting.JobType;
import com.dmatek.zgb.db.pojo.setting.TimelyJobType;

public interface JobTypeService {
	boolean addJobType(JobType jobType) throws Exception;
	boolean deleteJobType(List<String> nos) throws Exception;
	boolean updateJobType(JobType jobType) throws Exception;
	JobType findOne(String no) throws Exception;
	List<JobType> findPage(int page, int limit) throws Exception;
	List<JobType> findPageKeyName(int page, int limit, String keyName) throws Exception;
	List<JobType> findAll() throws Exception;
	List<TimelyJobType> findTimelyJobTypes() throws Exception;
	
	JobType findName(String name) throws Exception;
	
}
