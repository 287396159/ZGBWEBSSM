package com.dmatek.zgb.db.access.work.service.imp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.db.access.work.dao.AccessWorkDao;
import com.dmatek.zgb.db.access.work.service.AccessWorkService;

@Service("accessWorkService")
public class AccessWorkServiceImp implements AccessWorkService {
	@Autowired
	private AccessWorkDao accessWorkDao;
	@Override
	public boolean insertOne(TagAccessRecord tagAccessRecord) throws Exception {
		if(null != tagAccessRecord){
			return accessWorkDao.insertOne(tagAccessRecord);
		}
		return false;
	}
	@Override
	public boolean deleteOne(String uuid) throws Exception {
		if(!StringUtils.isEmpty(uuid)){
			return accessWorkDao.deleteOne(uuid);
		}
		return false;
	}
	@Override
	public List<TagAccessRecord> findAll() throws Exception {
		return accessWorkDao.findAll();
	}
	@Override
	public TagAccessRecord findCurrentDayOne(String tagId, Date current,
			int type) throws Exception {
		if(!StringUtils.isEmpty(tagId) && null != current) {
			return accessWorkDao.findCurrentDayOne(tagId, current, type);
		}
		return null;
	}
	@Override
	public List<TagAccessRecord> searchAccessRecords(String name,
			String companyNo, String jobTypeNo, int groupId, Date start,
			Date end) throws Exception {
		return accessWorkDao.searchAccessRecords(name, companyNo, jobTypeNo, groupId, start, end);
	}
	@Override
	public void clearRecords(int days) throws Exception {
		if(days > 0) {
			accessWorkDao.clearRecords(days);
		}
	}
}
