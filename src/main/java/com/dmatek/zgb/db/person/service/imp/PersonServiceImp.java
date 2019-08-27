package com.dmatek.zgb.db.person.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dmatek.zgb.db.person.dao.PersonDao;
import com.dmatek.zgb.db.person.service.PersonService;
import com.dmatek.zgb.db.pojo.person.Person;
@Service("personService")
public class PersonServiceImp implements PersonService{
	@Autowired
	private PersonDao personDao;
	@Override
	public boolean addPerson(Person person) throws Exception {
		return personDao.addPerson(person);
	}
	@Override
	public boolean deletePersons(int[] ids) throws Exception {
		return personDao.deletePersons(ids);
	}
	@Override
	public boolean updatePerson(Person person) throws Exception {
		return personDao.updatePerson(person);
	}
	@Override
	public Person findOneFromNo(String no) throws Exception {
		return personDao.findOneFromNo(no);
	}
	@Override
	public Person findOneFromName(String name) throws Exception {
		return personDao.findOneFromName(name);
	}
	@Override
	public Person findOneFromTagId(String tagId) throws Exception {
		return personDao.findOneFromTagId(tagId);
	}
	@Override
	public List<Person> findAll() throws Exception {
		return personDao.findAll();
	}
	@Override
	public List<Person> findPage(int page, int limit) throws Exception {
		return personDao.findPage((page - 1) * limit, limit);
	}
	@Override
	public List<Person> findPageKeyName(int page, int limit, String name)
			throws Exception {
		return personDao.findPageKeyName((page - 1) * limit, limit, name);
	}
	@Override
	public List<Person> findImageName(String imgName) throws Exception {
		if(!StringUtils.isEmpty(imgName)){
			return personDao.findImageName(imgName);
		}
		return null;
	}
	@Override
	public List<Person> findCompany(String companyNo) throws Exception {
		if(!StringUtils.isEmpty(companyNo)){
			return personDao.findCompany(companyNo);
		}
		return null;
	}
	@Override
	public Person findOneFromSerialNumber(int serialNumber) throws Exception {
		if (serialNumber >= 0) {
			return personDao.findOneFromSerialNumber(serialNumber);
		}
		return null;
	}
}
