package com.dmatek.zgb.db.setting.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.dmatek.zgb.db.pojo.setting.Group;
import com.dmatek.zgb.db.setting.dao.GroupDao;
import com.dmatek.zgb.db.setting.service.GroupService;
@Service("groupService")
public class GroupServiceImp implements GroupService {
	@Autowired
	private GroupDao groupDao;
	@Override
	public boolean addGroup(Group group) throws Exception {
		if(null != group) {
			return groupDao.addGroup(group);
		}
		return false;
	}
	@Override
	public boolean deleteGroup(List<Integer> ids) throws Exception {
		if(null != ids && !ids.isEmpty()){
			return  groupDao.deleteGroup(ids);
		}
		return false;
	}
	@Override
	public boolean updateGroup(Group group) throws Exception {
		if(null != group){
			return groupDao.updateGroup(group);
		}
		return false;
	}
	@Override
	public Group findOne(int id) throws Exception {
		return groupDao.findOne(id);
	}
	@Override
	public List<Group> findAll() throws Exception {
		return groupDao.findAll();
	}
	@Override
	public List<Group> findPage(int page, int limit) throws Exception {
		return groupDao.findPage((page - 1) * limit, limit);
	}
	@Override
	public List<Group> findPageKeyName(int page, int limit, String name)
			throws Exception {
		return groupDao.findPageKeyName((page - 1) * limit, limit, name);
	}
	@Override
	public Group findName(String name) throws Exception {
		if(!StringUtils.isEmpty(name)) {
			return groupDao.findName(name);
		}
		return null;
	}
}
