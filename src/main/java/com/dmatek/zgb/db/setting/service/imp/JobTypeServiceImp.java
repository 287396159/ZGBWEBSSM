package com.dmatek.zgb.db.setting.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.db.pojo.setting.JobType;
import com.dmatek.zgb.db.pojo.setting.TimelyJobType;
import com.dmatek.zgb.db.setting.dao.JobTypeDao;
import com.dmatek.zgb.db.setting.service.JobTypeService;
@Service("jobTypeService")
public class JobTypeServiceImp implements JobTypeService {
	@Autowired
	private JobTypeDao jobTypeDao;
	@Override
	public boolean addJobType(JobType jobType) throws Exception {
		if(null != jobType){
			return jobTypeDao.addJobType(jobType);
		}
		return false;
	}
	@Override
	public boolean deleteJobType(List<String> nos) throws Exception {
		if(null != nos && !nos.isEmpty()){
			return jobTypeDao.deleteJobType(nos);
		}
		return false;
	}
	@Override
	public boolean updateJobType(JobType jobType) throws Exception {
		if(null != jobType){
			return jobTypeDao.updateJobType(jobType);
		}
		return false;
	}
	@Override
	public JobType findOne(String no) throws Exception {
		if(!StringUtils.isEmpty(no)){
			return jobTypeDao.findOne(no);
		}
		return null;
	}
	@Override
	public List<JobType> findAll() throws Exception {
		return jobTypeDao.findAll();
	}
	@Override
	public List<JobType> findPage(int page, int limit) throws Exception {
		return jobTypeDao.findPage((page - 1) * limit, limit);
	}
	@Override
	public List<JobType> findPageKeyName(int page, int limit, String keyName)
			throws Exception {
		return jobTypeDao.findPageKeyName((page - 1) * limit, limit, keyName);
	}
	@Override
	public List<TimelyJobType> findTimelyJobTypes() throws Exception {
		return jobTypeDao.findTimelyJobTypes();
	}
	@Override
	public JobType findName(String name) throws Exception {
		if(!StringUtils.isEmpty(name)) {
			return jobTypeDao.findName(name);
		}
		return null;
	}
}
