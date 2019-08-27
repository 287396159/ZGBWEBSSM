package com.dmatek.zgb.db.access.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.db.access.dao.AccessDao;
import com.dmatek.zgb.db.access.service.AccessService;

@Service("accessService")
public class AccessServiceImp implements AccessService{
	@Autowired
	private AccessDao accessDao;
	@Override
	public boolean addAccessRecord(TagAccessRecord accessRecord)
			throws Exception {
		if(null != accessRecord) {
			return accessDao.addAccessRecord(accessRecord);
		}
		return false;
	}
	@Override
	public boolean deleteAccessRecord(String uuid) throws Exception {
		if(!Strings.isEmpty(uuid)) {
			return accessDao.deleteAccessRecord(uuid);
		}
		return false;
	}
	@Override
	public List<TagAccessRecord> findAllRecordOneDay(String tagId,
			int accessType, Date accessTime) throws Exception {
		if(!Strings.isEmpty(tagId) && null != accessTime) {
			return accessDao.findAllRecordOneDay(tagId, accessType, accessTime);
		}
		return null;
	}
	@Override
	public TagAccessRecord findOne(String tagId, int groupId, int accessType, Date accessTime)
			throws Exception {
		if(!Strings.isEmpty(tagId) && null != accessTime) {
			return accessDao.findOne(tagId, groupId, accessType, accessTime);
		}
		return null;
	}
	@Override
	public List<TagAccessRecord> findGroupTypeTime(int groupId, int accessType,
			Date accessTime) throws Exception {
		if(null != accessTime) {
			return accessDao.findGroupTypeTime(groupId, accessType, accessTime);
		}
		return null;
	}
	@Override
	public List<TagAccessRecord> findGroupTime(int groupId, Date accessTime)
			throws Exception {
		if(null != accessTime) {
			return accessDao.findGroupTime(groupId, accessTime);
		}
		return null;
	}
	/**
	 * 查询所有的记录讯息
	 */
	@Override
	public List<TagAccessRecord> findAll() throws Exception {
		return accessDao.findAll();
	}
	@Override
	public List<TagAccessRecord> searchTagAccessRecords(String name,
			String companyNo, String jobTypeNo, int groupId, Date start,
			Date end) throws Exception {
		return accessDao.searchTagAccessRecords(name, companyNo, jobTypeNo, groupId, start, end);
	}
	@Override
	public void clearRecords(int days) throws Exception {
		if(days > 0) {
			accessDao.clearRecords(days);
		}
	}
}
