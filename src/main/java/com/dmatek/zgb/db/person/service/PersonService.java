package com.dmatek.zgb.db.person.service;

import java.util.List;
import com.dmatek.zgb.db.pojo.person.Person;

public interface PersonService {
	public boolean addPerson(Person person) throws Exception;
	public boolean deletePersons(int[] ids) throws Exception;
	public boolean updatePerson(Person person) throws Exception;
	List<Person> findImageName(String imgName) throws Exception;
	public Person findOneFromSerialNumber(int serialNumber) throws Exception;
	public Person findOneFromNo(String no) throws Exception;
	public Person findOneFromName(String name) throws Exception;
	public Person findOneFromTagId(String tagId) throws Exception;
	public List<Person> findCompany(String companyNo) throws Exception;
	public List<Person> findAll() throws Exception;
	public List<Person> findPage(int page, int limit) throws Exception;
	public List<Person> findPageKeyName(int page, int limit, String name) throws Exception;
}
