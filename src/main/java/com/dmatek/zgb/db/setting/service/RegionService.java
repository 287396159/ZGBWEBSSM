package com.dmatek.zgb.db.setting.service;

import java.util.List;
import com.dmatek.zgb.db.pojo.setting.Region;

public interface RegionService {
	boolean insertRegion(Region region) throws Exception;
	boolean deleteRegion(List<Integer> ids) throws Exception;
	boolean updateRegion(Region region) throws Exception;
	Region findOne(int id) throws Exception;
	List<Region> findImageName(String imgName) throws Exception;
	List<Region> findAll() throws Exception;
	List<Region> findPage(int page,int limit) throws Exception;
	List<Region> findPageKeyName(int page,int limit,String name) throws Exception;
	List<Region> findAllFromGroupId(int groupId) throws Exception;
}
