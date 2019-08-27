package com.dmatek.zgb.db.setting.service;

import java.util.List;
import com.dmatek.zgb.db.pojo.setting.Group;
public interface GroupService {
	boolean addGroup(Group group) throws Exception;
	boolean deleteGroup(List<Integer> ids) throws Exception;
	boolean updateGroup(Group group) throws Exception;
	Group findOne(int id) throws Exception;
	Group findName(String name) throws Exception;
	List<Group> findAll() throws Exception;
	List<Group> findPage(int page, int limit) throws Exception;
	List<Group> findPageKeyName(int page, int limit, String name) throws Exception;
}
