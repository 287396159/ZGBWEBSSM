package com.dmatek.zgb.db.setting.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.db.pojo.setting.SafeNode;
import com.dmatek.zgb.db.setting.dao.SafeNodeDao;
import com.dmatek.zgb.db.setting.service.SafeNodeService;
@Service("safeNodeService")
public class SafeNodeServiceImp implements SafeNodeService {
	@Autowired
	private SafeNodeDao safeNodeDao;
	@Override
	public boolean addSafeNode(SafeNode safeNode) throws Exception {
		if(null != safeNode){
			return safeNodeDao.addSafeNode(safeNode);
		}
		return false;
	}
	@Override
	public boolean deleteSafeNode(List<String> ids) throws Exception {
		if(null != ids && !ids.isEmpty()){
			return safeNodeDao.deleteSafeNode(ids);
		}
		return false;
	}
	@Override
	public boolean updateSafeNode(SafeNode safeNode) throws Exception {
		if(null != safeNode){
			return safeNodeDao.updateSafeNode(safeNode);
		}
		return false;
	}
	@Override
	public SafeNode findOne(String id) throws Exception {
		if(!StringUtils.isEmpty(id)){
			return safeNodeDao.findOne(id);
		}
		return null;
	}
	@Override
	public List<SafeNode> findAll() throws Exception {
		return safeNodeDao.findAll();
	}
}
