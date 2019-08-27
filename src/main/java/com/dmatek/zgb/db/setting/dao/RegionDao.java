package com.dmatek.zgb.db.setting.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.db.pojo.setting.Region;

public interface RegionDao {
	boolean insertRegion(@Param("region") Region region) throws Exception;
	boolean deleteRegion(@Param("ids") List<Integer> ids) throws Exception;
	boolean updateRegion(@Param("region") Region region) throws Exception;
	List<Region> findImageName(@Param("imgName") String imgName) throws Exception;
	Region findOne(@Param("id") int id) throws Exception;
	List<Region> findAll() throws Exception;
	List<Region> findPage(@Param("page") int page,@Param("limit") int limit) throws Exception;
	List<Region> findPageKeyName(@Param("page") int page,@Param("limit") int limit,@Param("name") String name) throws Exception;
	List<Region> findAllFromGroupId(@Param("groupId") int groupId) throws Exception;
}
