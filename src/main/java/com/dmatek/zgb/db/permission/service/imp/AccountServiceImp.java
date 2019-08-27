package com.dmatek.zgb.db.permission.service.imp;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.db.permission.dao.AccountDao;
import com.dmatek.zgb.db.permission.service.AccountService;
import com.dmatek.zgb.db.pojo.permission.Account;

@Service("accountService")
public class AccountServiceImp implements AccountService {
	@Autowired
	private AccountDao accountDao;
	@Override
	public boolean addAccount(Account account) throws Exception {
		if(null != account){
			return accountDao.addAccount(account);
		}
		return false;
	}

	@Override
	public boolean deleteAccount(List<Integer> ids) throws Exception {
		if(null != ids && !ids.isEmpty()){
			return accountDao.deleteAccount(ids);
		}
		return false;
	}

	@Override
	public boolean updateAccount(Account account) throws Exception {
		if(null != account){
			return accountDao.updateAccount(account);
		}
		return false;
	}

	@Override
	public Account findOne(int id) throws Exception {
		return accountDao.findOne(id);
	}

	@Override
	public List<Account> findAll() throws Exception {
		return accountDao.findAll();
	}

	@Override
	public Account findOneFromName(String name) throws Exception {
		if(!StringUtils.isEmpty(name)){
			return accountDao.findOneFromName(name);
		}
		return null;
	}

	@Override
	public boolean deleteAccountName(String name) throws Exception {
		if(!Strings.isEmpty(name)){
			return accountDao.deleteAccountName(name);
		}
		return false;
	}
}
