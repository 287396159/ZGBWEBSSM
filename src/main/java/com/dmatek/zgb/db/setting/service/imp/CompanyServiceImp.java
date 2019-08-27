package com.dmatek.zgb.db.setting.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.db.pojo.setting.Company;
import com.dmatek.zgb.db.setting.dao.CompanyDao;
import com.dmatek.zgb.db.setting.service.CompanyService;
@Service("companyService")
public class CompanyServiceImp implements CompanyService {
	@Autowired
	private CompanyDao companyDao;
	@Override
	public boolean addCompany(Company company) throws Exception {
		if(null != company){
			return companyDao.addCompany(company);
		}
		return false;
	}
	@Override
	public boolean deleteCompany(List<String> nos) throws Exception {
		if(null != nos && !nos.isEmpty()){
			return companyDao.deleteCompany(nos);
		}
		return false;
	}
	@Override
	public boolean updateCompany(Company company) throws Exception {
		if(null != company){
			return companyDao.updateCompany(company);
		}
		return false;
	}
	@Override
	public Company findOne(String no) throws Exception {
		if(!StringUtils.isEmpty(no)){
			return companyDao.findOne(no);
		}
		return null;
	}
	@Override
	public List<Company> findAll() throws Exception {
		return companyDao.findAll();
	}
	@Override
	public List<Company> findPage(int page, int limit) throws Exception {
		return companyDao.findPage((page - 1) * limit, limit);
	}
	@Override
	public List<Company> findPageKeyName(int page, int limit, String name)
			throws Exception {
		return companyDao.findPageKeyName((page - 1) * limit, limit, name);
	}
	@Override
	public Company findName(String name) throws Exception {
		if(!StringUtils.isEmpty(name)) {
			return companyDao.findName(name);
		}
		return null;
	}
}
