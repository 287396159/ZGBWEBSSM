package com.dmatek.zgb.db.setting.service;

import java.util.List;

import com.dmatek.zgb.db.pojo.setting.SafeNode;

public interface SafeNodeService {
	boolean addSafeNode(SafeNode safeNode) throws Exception;
	boolean deleteSafeNode(List<String> ids) throws Exception;
	boolean updateSafeNode(SafeNode safeNode) throws Exception;
	SafeNode findOne(String id) throws Exception;
	List<SafeNode> findAll() throws Exception;
}
