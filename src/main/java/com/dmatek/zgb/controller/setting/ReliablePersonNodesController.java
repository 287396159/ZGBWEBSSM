package com.dmatek.zgb.controller.setting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dmatek.zgb.controller.validate.ReliablePersonNodesValidationTool;
import com.dmatek.zgb.db.person.service.PersonService;
import com.dmatek.zgb.db.pojo.person.Person;
import com.dmatek.zgb.db.pojo.setting.Node;
import com.dmatek.zgb.db.pojo.setting.ReliablePerson_Node;
import com.dmatek.zgb.db.setting.service.ReliablePerson_NodeService;
import com.dmatek.zgb.log.anno.Operation;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.result.factory.base.ViewResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@RestController
@RequestMapping("/reliablenodes")
@RequiresPermissions(value="reliableNodes:*")
@PermissionName(name="設置人員可靠近參考點權限")
public class ReliablePersonNodesController {
	@Autowired
	private ReliablePerson_NodeService reliablePerson_NodeService;
	@Autowired
	private PersonService personService;
	@Autowired
	private ReliablePersonNodesValidationTool reliablePersonNodesValidationTool;
	@Autowired
	private ViewResultFactory viewResultFactory;
	/**
	 * 添加人员靠近的参考点
	 * @param personNo
	 * @param ids
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/add/person/{createName}/{personNo}/")
	@Transactional(noRollbackFor=Exception.class)// 添加事务异常回滚
	@RequiresPermissions(value="reliableNodes:add")
	@PermissionName(name="添加人員可靠近參考點權限")
	@Operation(description="添加人員可靠近參考點資料")
	public Result addReliablePersonNodes(@PathVariable String personNo, @PathVariable String createName,
			@RequestBody String[] ids) throws Exception {
		// 验证是否有效
		Result result = reliablePersonNodesValidationTool.validate_Create(
				personNo, ids, createName);
		if(null != result) {
			return result;
		}
		// 删除原始的记录
		reliablePerson_NodeService.deleteReliablePerson_Node(personNo);
		if(null != ids && ids.length > 0) {
			List<ReliablePerson_Node> reliableNodes = new ArrayList<ReliablePerson_Node>();
			// 添加新的记录
			for (int i = 0; i < ids.length; i++) {
				ReliablePerson_Node reliableNode = new ReliablePerson_Node();
				reliableNode.setCreateName(createName);
				reliableNode.setNodeId(ids[i]);
				reliableNode.setPersonNo(personNo);
				reliableNodes.add(reliableNode);
			}
			reliablePerson_NodeService.addReliablePerson_Node(reliableNodes);
		}
		return viewResultFactory.successResult();
	}
	/**
	 * 添加公司可靠近參考點
	 * @param companyNo
	 * @param createName
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/add/company/{createName}/{companyNo}/")
	@Transactional(noRollbackFor=Exception.class)// 添加事务异常回滚
	@RequiresPermissions(value="reliableNodes:add")
	@PermissionName(name="添加人員可靠近參考點權限")
	@Operation(description="添加人員可靠近參考點資料")
	public Result addReliableCompanyNodes(@PathVariable String companyNo, @PathVariable String createName,
			@RequestBody String[] ids) throws Exception {
		Result result = reliablePersonNodesValidationTool.validate_Create(
				companyNo, ids, createName);
		if (null != result) {
			return result;
		}
		// 刪除指定公司所有人員可靠近參考點
		reliablePerson_NodeService.deleteReliableCompany_Node(companyNo);
		if (null != ids && ids.length > 0) {
			// 獲取當前公司的所有人員訊息
			List<ReliablePerson_Node> reliableNodes = new ArrayList<ReliablePerson_Node>();
			List<Person> persons = personService.findCompany(companyNo);
			for (Person person : persons) {
				for (String rd : ids) {
					ReliablePerson_Node personNode = new ReliablePerson_Node();
					personNode.setPersonNo(person.getNo());
					personNode.setNodeId(rd);
					personNode.setCreateName(createName);
					personNode.setCreateTime(new Date());
					reliableNodes.add(personNode);
				}
			}
			reliablePerson_NodeService.addReliablePerson_Node(reliableNodes);
		}
		return viewResultFactory.successResult();
	}
	
	@GetMapping("/all/person/{personNo}")
	@RequiresPermissions(value="reliableNodes:find")
	@PermissionName(name="查找人員可靠近參考點權限")
	public Result getPersonAllReliableNodes(@PathVariable String personNo) throws Exception{
		List<Node> reliableNodes = reliablePerson_NodeService
				.selectOne(personNo);
		return viewResultFactory.successResult(reliableNodes);
	}
	
	@GetMapping("/all/company/{companyNo}")
	@RequiresPermissions(value="reliableNodes:find")
	@PermissionName(name="查找人員可靠近參考點權限")
	public Result getCompanyAllReliableNodes(@PathVariable String companyNo) throws Exception{
		// 獲取公司的所有人員數量
		List<Person> allPersons = personService.findCompany(companyNo);
		if(null != allPersons) {// 获取公司的所有人员数量
			List<Node> reliableNodes = reliablePerson_NodeService.findReliableCompany_Node(companyNo, allPersons.size());
			return viewResultFactory.successResult(reliableNodes);
		}
		return viewResultFactory.errorResult("公司【companyNo :" + companyNo + "】信息為空...");
	} 
}
