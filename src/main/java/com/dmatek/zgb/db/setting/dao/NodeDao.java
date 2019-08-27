package com.dmatek.zgb.db.setting.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.db.pojo.setting.Node;

public interface NodeDao {
	boolean addNode(@Param("node") Node node) throws Exception;
	boolean deleteNode(@Param("ids") List<String> ids) throws Exception;
	boolean updateNode(@Param("node") Node node) throws Exception;
	Node findOne(@Param("id") String id) throws Exception;
	List<Node> findAll() throws Exception;
	List<Node> findNodesFromRegionId(@Param("regionId") int regionId) throws Exception;
	List<Node> findNodesFromGroupId(@Param("groupId") int groupId) throws Exception;
	List<Node> findRefersFromGroupId(@Param("groupId") int groupId) throws Exception;
	List<Node> findPage(@Param("page") int page,@Param("limit") int limit) throws Exception;
	List<Node> findPageFromRegionId(@Param("regionId") int regionId,@Param("page") int page,@Param("limit") int limit) throws Exception;
	List<Node> findPageKeyNameFromRegionId(@Param("regionId") int regionId,@Param("page") int page,@Param("limit") int limit,@Param("name") String name) throws Exception;
}
