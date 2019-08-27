package com.dmatek.zgb.db.setting.service;

import java.util.List;
import com.dmatek.zgb.db.pojo.setting.Node;
import com.dmatek.zgb.db.pojo.setting.ReliablePerson_Node;

public interface ReliablePerson_NodeService {
	boolean addReliablePerson_Node(List<ReliablePerson_Node> personNodes) throws Exception;
	boolean deleteReliablePerson_Node(String personNo) throws Exception;
	boolean deleteReliableCompany_Node(String companyNo) throws Exception;
	List<ReliablePerson_Node> selectAll() throws Exception;
	List<ReliablePerson_Node> selectPage(int page,int limit) throws Exception;
	List<ReliablePerson_Node> selectPageKeyName(int page,int limit,String name) throws Exception;
	List<Node> selectOne(String name) throws Exception;
	List<Node> findReliableCompany_Node(String companyNo, int psNumber) throws Exception;
}
