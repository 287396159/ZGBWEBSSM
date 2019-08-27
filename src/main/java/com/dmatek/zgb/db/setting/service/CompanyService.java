package com.dmatek.zgb.db.setting.service;

import java.util.List;

import com.dmatek.zgb.db.pojo.setting.Company;

public interface CompanyService {
	boolean addCompany(Company company) throws Exception;
	boolean deleteCompany(List<String> nos) throws Exception;
	boolean updateCompany(Company company) throws Exception;
	Company findOne(String no) throws Exception;
	List<Company> findAll() throws Exception;
	List<Company> findPage(int page, int limit) throws Exception;
	List<Company> findPageKeyName(int page, int limit, String name) throws Exception;
	Company findName(String name) throws Exception;
}
