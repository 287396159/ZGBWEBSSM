package com.dmatek.zgb.db.setting.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.db.pojo.setting.Group;

public interface GroupDao {
	boolean addGroup(@Param("group") Group group) throws Exception;
	boolean deleteGroup(@Param("ids") List<Integer> ids) throws Exception;
	boolean updateGroup(@Param("group")Group group) throws Exception;
	Group findOne(@Param("id")int id) throws Exception;
	List<Group> findAll() throws Exception;
	Group findName(@Param("name") String name) throws Exception;
	List<Group> findPage(@Param("page") int page,@Param("limit") int limit) throws Exception;
	List<Group> findPageKeyName(@Param("page") int page,@Param("limit") int limit,@Param("name") String name) throws Exception;
}
