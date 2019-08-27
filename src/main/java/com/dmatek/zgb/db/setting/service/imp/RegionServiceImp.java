package com.dmatek.zgb.db.setting.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dmatek.zgb.db.pojo.setting.Region;
import com.dmatek.zgb.db.setting.dao.RegionDao;
import com.dmatek.zgb.db.setting.service.RegionService;
@Service("regionService")
public class RegionServiceImp implements RegionService {
	@Autowired
	private RegionDao regionDao;
	@Override
	public boolean insertRegion(Region region) throws Exception {
		if(null != region){
			return regionDao.insertRegion(region);
		}
		return false;
	}
	@Override
	public boolean deleteRegion(List<Integer> ids) throws Exception {
		if(null != ids && !ids.isEmpty()){
			return regionDao.deleteRegion(ids);
		}
		return false;
	}
	@Override
	public boolean updateRegion(Region region) throws Exception {
		if(null != region){
			return regionDao.updateRegion(region);
		}
		return false;
	}
	@Override
	public Region findOne(int id) throws Exception {
		return regionDao.findOne(id);
	}
	@Override
	public List<Region> findAll() throws Exception {
		return regionDao.findAll();
	}
	@Override
	public List<Region> findPage(int page, int limit) throws Exception {
		return regionDao.findPage( (page - 1) * limit, limit);
	}
	@Override
	public List<Region> findAllFromGroupId(int groupId) throws Exception {
		return regionDao.findAllFromGroupId(groupId);
	}
	@Override
	public List<Region> findPageKeyName(int page, int limit,String name) throws Exception {
		return regionDao.findPageKeyName((page - 1) * limit, limit, name);
	}
	@Override
	public List<Region> findImageName(String imgName) throws Exception {
		if(!StringUtils.isEmpty(imgName)) {
			return regionDao.findImageName(imgName);
		}
		return null;
	}
}
