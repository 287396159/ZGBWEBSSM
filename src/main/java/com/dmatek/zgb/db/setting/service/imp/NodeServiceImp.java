package com.dmatek.zgb.db.setting.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.db.pojo.setting.Node;
import com.dmatek.zgb.db.setting.dao.NodeDao;
import com.dmatek.zgb.db.setting.service.NodeService;
@Service("nodeService")
public class NodeServiceImp implements NodeService {
	@Autowired
	private NodeDao nodeDao;
	@Override
	public boolean addNode(Node node) throws Exception {
		if(null != node){
			return nodeDao.addNode(node);
		}
		return false;
	}

	@Override
	public boolean deleteNode(List<String> ids) throws Exception {
		if(null != ids && !ids.isEmpty()){
			return nodeDao.deleteNode(ids);
		}
		return false;
	}

	@Override
	public boolean updateNode(Node node) throws Exception {
		if(null != node){
			return nodeDao.updateNode(node);
		}
		return false;
	}

	@Override
	public Node findOne(String id) throws Exception {
		if(!StringUtils.isEmpty(id)){
			return nodeDao.findOne(id);
		}
		return null;
	}

	@Override
	public List<Node> findAll() throws Exception {
		return nodeDao.findAll();
	}

	@Override
	public List<Node> findPage(int page, int limit) throws Exception {
		return nodeDao.findPage((page - 1) * limit, limit);
	}
	@Override
	public List<Node> findNodesFromRegionId(int regionId) throws Exception {
		return nodeDao.findNodesFromRegionId(regionId);
	}

	@Override
	public List<Node> findPageFromRegionId(int regionId, int page, int limit)
			throws Exception {
		return nodeDao.findPageFromRegionId(regionId, (page - 1) * limit, limit);
	}

	@Override
	public List<Node> findPageKeyNameFromRegionId(int regionId, int page,
			int limit, String name) throws Exception {
		return nodeDao.findPageKeyNameFromRegionId(regionId, (page - 1) * limit, limit, name);
	}

	@Override
	public List<Node> findNodesFromGroupId(int groupId) throws Exception {
		return nodeDao.findNodesFromGroupId(groupId);
	}

	@Override
	public List<Node> findRefersFromGroupId(int groupId) throws Exception {
		return nodeDao.findRefersFromGroupId(groupId);
	}
}
