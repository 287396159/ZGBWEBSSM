package com.dmatek.zgb.db.setting.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.db.pojo.setting.DangerNode;

public interface DangerNodeDao {
	boolean addDangerNode(@Param("dangerNode") DangerNode dangerNode) throws Exception;
	boolean deleteDangerNode(@Param("ids") List<String> ids) throws Exception;
	boolean updateDangerNode(@Param("dangerNode") DangerNode dangerNode) throws Exception;
	DangerNode findOne(@Param("id") String id) throws Exception;
	List<DangerNode> findAll() throws Exception;
}