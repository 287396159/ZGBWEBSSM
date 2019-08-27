package com.dmatek.zgb.db.setting.service.imp;

import java.util.List;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;
import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.db.pojo.setting.Node;
import com.dmatek.zgb.db.pojo.setting.ReliablePerson_Node;
import com.dmatek.zgb.db.setting.dao.ReliablePerson_NodeDao;
import com.dmatek.zgb.db.setting.service.ReliablePerson_NodeService;

@Service("reliableTag_NodeService")
public class ReliablePerson_NodeServiceImp implements ReliablePerson_NodeService {
	@Autowired
	private ReliablePerson_NodeDao reliablePerson_NodeDao;
	@Override
	public boolean addReliablePerson_Node(List<ReliablePerson_Node> personNodes)
			throws Exception {
		if(!ListUtils.isEmpty(personNodes)){
			return reliablePerson_NodeDao.addReliablePerson_Node(personNodes);
		}
		return false;
	}
	@Override
	public boolean deleteReliablePerson_Node(String tagId) throws Exception {
		if(!Strings.isEmpty(tagId)){
			return reliablePerson_NodeDao.deleteReliablePerson_Node(tagId);
		}
		return false;
	}
	@Override
	public List<ReliablePerson_Node> selectAll() throws Exception {
		return reliablePerson_NodeDao.selectAll();
	}
	@Override
	public List<ReliablePerson_Node> selectPage(int page, int limit)
			throws Exception {
		return reliablePerson_NodeDao.selectPage(page, limit);
	}
	@Override
	public List<ReliablePerson_Node> selectPageKeyName(int page, int limit,
			String name) throws Exception {
		return reliablePerson_NodeDao.selectPageKeyName(page, limit, name);
	}
	@Override
	public List<Node> selectOne(String tagId) throws Exception {
		if(!StringUtils.isEmpty(tagId)) {
			return reliablePerson_NodeDao.selectOne(tagId);
		}
		return null;
	}
	@Override
	public boolean deleteReliableCompany_Node(String companyNo)
			throws Exception {
		if(!StringUtils.isEmpty(companyNo)) {
			return reliablePerson_NodeDao.deleteReliableCompany_Node(companyNo);
		}
		return false;
	}
	@Override
	public List<Node> findReliableCompany_Node(String companyNo, int psNumber)
			throws Exception {
		if(!StringUtils.isEmpty(companyNo)) {
			return reliablePerson_NodeDao.findReliableCompany_Node(companyNo, psNumber);
		}
		return null;
	}
}
