package com.dmatek.zgb.controller.setting;

import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.controller.setting.validation.BaseSettingValidation;
import com.dmatek.zgb.db.pojo.setting.Node;
import com.dmatek.zgb.db.setting.service.NodeService;
import com.dmatek.zgb.log.anno.Operation;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
/**
 * 基站/參考點控制器
 * @author zf
 * @data 2018年12月28日 下午3:09:51
 * @Description
 */
@RestController
@RequestMapping("/enode")
@PermissionName(name="設置基站/參考點權限")
@RequiresPermissions(value="node:*")
public class NodeController {
	private static final int SINGLE_MAXNUMBER = 100;
	@Autowired
	private NodeService nodeService;
	@Autowired
	private BaseSettingValidation<String, Node> baseSettingValidation;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	/**
	 * 添加基站訊息
	 * @param node
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/")
	@PermissionName(name="添加基站/參考點權限")
	@RequiresPermissions(value="node:add")
	@Operation(description="添加基站/參考點資料")
	public Result addNode(@RequestBody Node node) throws Exception{
		Result result = baseSettingValidation.saveValidate(node);
		if(null != result) {
			return result;
		}
		Node oldNode = nodeService.findOne(node.getId());
		if(null != oldNode){
			return viewResultFactory.errorResult("基站【ID: " + node.getId() + "】已經存在...");
		}
		if(nodeService.addNode(node)){
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("添加基站失敗...");
	}
	/**
	 * 刪除基站訊息
	 * @param nodeIds
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/{nodeIds}")
	@PermissionName(name="刪除基站/參考點權限")
	@RequiresPermissions(value="node:delete")
	@Operation(description="刪除基站/參考點資料")
	public Result deleteNode(@PathVariable String[] nodeIds) throws Exception{
		if(null == nodeIds || nodeIds.length <= 0){
			return viewResultFactory.errorResult("需要刪除的id不能為空...");
		}
		if(nodeIds.length <= SINGLE_MAXNUMBER){
			nodeService.deleteNode(Arrays.asList(nodeIds));
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("單次最多刪" + SINGLE_MAXNUMBER + "條記錄...");
	}
	/**
	 * 更新基站訊息
	 * @param nodeId
	 * @param node
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/{nodeId}")
	@PermissionName(name="更新基站/參考點權限")
	@RequiresPermissions(value="node:update")
	@Operation(description="更新基站/參考點資料")
	public Result updateNode(@PathVariable String nodeId, @RequestBody Node node) throws Exception{
		node.setId(nodeId);
		Result result = baseSettingValidation.updateValidate(node);
		if(null != result) {
			return result;
		}
		// 判斷更新的基站是否存在
		Node oldNode = nodeService.findOne(nodeId);
		if(null == oldNode){
			return viewResultFactory.errorResult("需要更新的基站【ID: " + nodeId + "】不存在...");
		}
		if(node.getWidth() <= 0 || node.getHeight() <= 0){
			node.setWidth(oldNode.getWidth());
			node.setHeight(oldNode.getHeight());
		}
		if(nodeService.updateNode(node)){
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("更新基站信息失敗...");
	}
	/**
	 * 查詢單個基站的訊息
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{nodeId}")
	public Result findOneNode(@PathVariable String nodeId) throws Exception{
		if(StringUtils.isEmpty(nodeId)){
			return viewResultFactory.errorResult("查詢單個基站的基站ID不能為空...");
		}
		Node node = nodeService.findOne(nodeId);
		if(null == node){
			return viewResultFactory.errorResult("對不起，沒有查詢到【id: " + nodeId + "】的任何記錄...");
		}
		return viewResultFactory.successResult(node);
	}
	/**
	 * 查詢分頁的基站訊息
	 * @param page
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/page/")
	@PermissionName(name="查詢基站/參考點權限")
	@RequiresPermissions(value="node:find")
	public Result findPageNodes(@RequestParam int page,@RequestParam int limit) throws Exception{
		List<Node> nodes = nodeService.findPage(page, limit);
		List<Node> allNodes = nodeService.findAll();
		if(null != nodes && null != allNodes) {
			return viewResultFactory.successResult(nodes, allNodes.size());
		}
		return viewResultFactory.errorResult("查詢分頁基站記錄出現異常...");
	}
	
	@GetMapping("/page/{regionId}")
	@PermissionName(name="查詢基站/參考點權限")
	@RequiresPermissions(value="node:find")
	public Result findPageNodesFromRegionId(@PathVariable int regionId,@RequestParam int page,@RequestParam int limit) throws Exception{
		List<Node> nodes = nodeService.findPageFromRegionId(regionId, page, limit);
		List<Node> allNodes = nodeService.findNodesFromRegionId(regionId);
		if(null != nodes && null != allNodes) {
			return viewResultFactory.successResult(nodes, allNodes.size());
		}
		return viewResultFactory.errorResult("查詢分頁基站記錄出現異常...");
	}
	
	@GetMapping("/page/key_name/{regionId}")
	@PermissionName(name="查詢基站/參考點權限")
	@RequiresPermissions(value="node:find")
	public Result findPageNodesKeyNameFromRegionId(@PathVariable int regionId,@RequestParam("page") int page,@RequestParam("limit") int limit,@RequestParam("name") String name) throws Exception{
		List<Node> allNodes = nodeService.findNodesFromRegionId(regionId);
		List<Node> nodes = nodeService.findPageKeyNameFromRegionId(regionId, page, limit, name);
		if(null != allNodes && null != nodes){
			return viewResultFactory.successResult(nodes, allNodes.size());
		}
		return viewResultFactory.errorResult("查詢分頁基站記錄出現異常...");
	}
	
	/**
	 * 查詢所有的基站訊息
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/all/")
	public Result findAllNodes() throws Exception {
		List<Node> allNodes = nodeService.findAll();
		if(null != allNodes){
			return viewResultFactory.successResult(allNodes);
		}
		return viewResultFactory.errorResult("查詢所有基站記錄出現異常...");
	}
	/**
	 * 查詢指定區域的基站訊息出現異常
	 * @param regionId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/region/{regionId}")
	public Result findNodesFromRegionId(@PathVariable int regionId) throws Exception{
		if(regionId <= 0){
			return viewResultFactory.errorResult("區域ID不能小於等於0...");
		}
		List<Node> nodes = nodeService.findNodesFromRegionId(regionId);
		if(null != nodes){
			return viewResultFactory.successResult(nodes);
		}
		return viewResultFactory.errorResult("查詢區域【ID: " + regionId + "】的基站出現異常...");
	}
	
	@GetMapping("/group/refer/{groupId}")
	public Result findRefersFromGroupId(@PathVariable int groupId) throws Exception{
		if(groupId <= 0){
			return viewResultFactory.errorResult("組別ID不能小於等於0...");
		}
		List<Node> nodes = nodeService.findRefersFromGroupId(groupId);
		if(null != nodes){
			return viewResultFactory.successResult(nodes);
		}
		return viewResultFactory.errorResult("查詢組別【ID: " + groupId + "】的基站出現異常...");
	}
}

