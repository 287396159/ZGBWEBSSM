package com.dmatek.zgb.db.setting.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.db.pojo.setting.Company;

public interface CompanyDao {
	boolean addCompany(@Param("company") Company company) throws Exception;
	boolean deleteCompany(@Param("ids")List<String> nos) throws Exception;
	boolean updateCompany(@Param("company")Company company) throws Exception;
	Company findOne(@Param("no")String no) throws Exception;
	List<Company> findAll() throws Exception;
	List<Company> findPage(@Param("page") int page,@Param("limit") int limit) throws Exception;
	List<Company> findPageKeyName(@Param("page") int page,@Param("limit") int limit,@Param("name") String name) throws Exception;
	Company findName(@Param("name") String name) throws Exception;
}
