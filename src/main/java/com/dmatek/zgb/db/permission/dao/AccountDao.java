package com.dmatek.zgb.db.permission.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dmatek.zgb.db.pojo.permission.Account;

public interface AccountDao {
	boolean addAccount(@Param("account") Account account) throws Exception;
	boolean deleteAccount(@Param("ids") List<Integer> ids) throws Exception;
	boolean deleteAccountName(@Param("name") String name) throws Exception;
	boolean updateAccount(@Param("account") Account account) throws Exception;
	Account findOne(int id) throws Exception;
	Account findOneFromName(@Param("name") String name) throws Exception;
	List<Account> findAll() throws Exception;
}
