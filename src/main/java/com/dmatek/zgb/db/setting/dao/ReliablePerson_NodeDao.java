package com.dmatek.zgb.db.setting.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.dmatek.zgb.db.pojo.setting.Node;
import com.dmatek.zgb.db.pojo.setting.ReliablePerson_Node;

public interface ReliablePerson_NodeDao {
	boolean addReliablePerson_Node(@Param("personNodes") List<ReliablePerson_Node> personNodes) throws Exception;
	boolean deleteReliablePerson_Node(@Param("personNo") String personNo) throws Exception;
	boolean deleteReliableCompany_Node(@Param("companyNo") String companyNo) throws Exception;
	List<ReliablePerson_Node> selectAll() throws Exception;
	List<ReliablePerson_Node> selectPage(@Param("page") int page, @Param("limit") int limit) throws Exception;
	List<ReliablePerson_Node> selectPageKeyName(@Param("page") int page,@Param("limit") int limit,@Param("name") String name) throws Exception;
	List<Node> selectOne(@Param("personNo") String personNo) throws Exception;

	List<Node> findReliableCompany_Node(@Param("companyNo") String companyNo,@Param("psNumber") int psNumber) throws Exception;
}