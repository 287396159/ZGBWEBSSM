package com.dmatek.zgb.db.setting.service;

import java.util.List;

import com.dmatek.zgb.db.pojo.setting.DangerNode;

public interface DangerNodeService {
	boolean addDangerNode(DangerNode dangerNode) throws Exception;
	boolean deleteDangerNode(List<String> ids) throws Exception;
	boolean updateDangerNode(DangerNode dangerNode) throws Exception;
	DangerNode findOne(String id) throws Exception;
	List<DangerNode> findAll() throws Exception;
}
