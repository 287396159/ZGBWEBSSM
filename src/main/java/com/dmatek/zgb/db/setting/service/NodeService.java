package com.dmatek.zgb.db.setting.service;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.dmatek.zgb.db.pojo.setting.Node;

public interface NodeService {
	boolean addNode(Node node) throws Exception;
	boolean deleteNode(List<String> ids) throws Exception;
	boolean updateNode(Node node) throws Exception;
	Node findOne(String id) throws Exception;
	List<Node> findAll() throws Exception;
	List<Node> findNodesFromRegionId(@Param("regionId") int regionId) throws Exception;
	List<Node> findNodesFromGroupId(@Param("groupId") int groupId) throws Exception;
	List<Node> findRefersFromGroupId(@Param("groupId") int groupId) throws Exception;
	List<Node> findPage(int page,int limit) throws Exception;
	List<Node> findPageFromRegionId(int regionId,int page,int limit) throws Exception;
	List<Node> findPageKeyNameFromRegionId(int regionId,int page,int limit,String name) throws Exception;
}
