package com.dmatek.zgb.db.setting.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.db.pojo.setting.SafeNode;

public interface SafeNodeDao {
	boolean addSafeNode(@Param("safeNode") SafeNode safeNode) throws Exception;
	boolean deleteSafeNode(@Param("ids") List<String> id) throws Exception;
	boolean updateSafeNode(@Param("safeNode") SafeNode safeNode) throws Exception;
	SafeNode findOne(@Param("id") String id) throws Exception;
	List<SafeNode> findAll() throws Exception;
}
