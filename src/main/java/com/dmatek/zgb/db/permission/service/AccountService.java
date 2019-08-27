package com.dmatek.zgb.db.permission.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.db.pojo.permission.Account;

public interface AccountService {
	boolean addAccount(Account account) throws Exception;
	boolean deleteAccount(List<Integer> ids) throws Exception;
	boolean deleteAccountName(String name) throws Exception;
	boolean updateAccount(Account account) throws Exception;
	Account findOne(int id) throws Exception;
	Account findOneFromName(@Param("name") String name) throws Exception;
	List<Account> findAll() throws Exception;
}
