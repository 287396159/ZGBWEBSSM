package com.dmatek.zgb.db.person.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.db.pojo.person.Person;

public interface PersonDao {
	public boolean addPerson(@Param("person") Person person) throws Exception;
	public boolean deletePersons(@Param("serials") int[] serials) throws Exception;
	public boolean updatePerson(@Param("person") Person person) throws Exception;
	// 根据序列号查询
	public Person findOneFromSerialNumber(@Param("serialNumber") int serialNumber);
	public Person findOneFromNo(@Param("no") String no) throws Exception;
	public Person findOneFromName(@Param("name") String name) throws Exception;
	public Person findOneFromTagId(@Param("tagId") String tagId) throws Exception;
	List<Person> findImageName(@Param("imgName") String imgName) throws Exception;
	public List<Person> findCompany(@Param("companyNo") String companyNo) throws Exception;
	public List<Person> findAll() throws Exception;
	public List<Person> findPage(@Param("page") int page, @Param("limit") int limit) throws Exception;
	public List<Person> findPageKeyName(@Param("page") int page, @Param("limit") int limit,@Param("name") String name) throws Exception;
}
