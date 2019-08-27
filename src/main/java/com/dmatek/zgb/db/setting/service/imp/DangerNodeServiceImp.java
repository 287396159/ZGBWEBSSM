package com.dmatek.zgb.db.setting.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.db.pojo.setting.DangerNode;
import com.dmatek.zgb.db.setting.dao.DangerNodeDao;
import com.dmatek.zgb.db.setting.service.DangerNodeService;
@Service("dangerNodeService")
public class DangerNodeServiceImp implements DangerNodeService {
	@Autowired
	private DangerNodeDao dangerNodeDao;
	@Override
	public boolean addDangerNode(DangerNode dangerNode) throws Exception {
		if(null != dangerNode){
			return dangerNodeDao.addDangerNode(dangerNode);
		}
		return false;
	}
	@Override
	public boolean deleteDangerNode(List<String> ids) throws Exception {
		if(null != ids && !ids.isEmpty()){
			return dangerNodeDao.deleteDangerNode(ids);
		}
		return false;
	}
	@Override
	public boolean updateDangerNode(DangerNode dangerNode) throws Exception {
		if(null != dangerNode){
			return dangerNodeDao.updateDangerNode(dangerNode);
		}
		return false;
	}
	@Override
	public DangerNode findOne(String id) throws Exception {
		if(!StringUtils.isEmpty(id)){
			return dangerNodeDao.findOne(id);
		}
		return null;
	}
	@Override
	public List<DangerNode> findAll() throws Exception {
		return dangerNodeDao.findAll();
	}
}
