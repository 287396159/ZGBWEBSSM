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

import com.dmatek.zgb.controller.setting.validation.BaseSettingValidation;
import com.dmatek.zgb.db.pojo.setting.Group;
import com.dmatek.zgb.db.setting.service.GroupService;
import com.dmatek.zgb.log.anno.Operation;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.groupTool.GroupTagNumberTool;
import com.dmatek.zgb.setting.vo.Result;

@RequestMapping("/group")
@RestController
@PermissionName(name="設置組別資料權限")
@RequiresPermissions(value="group:*")
public class GroupController {
	private static final int SINGLE_MAXNUMBER = 100;
	@Autowired
	private GroupService groupService;
	@Autowired
	private BaseSettingValidation<Integer, Group> baseSettingValidation;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private GroupTagNumberTool groupTagNumberTool;
	/**
	 * 添加組群訊息
	 * @param group
	 * @return
	 */
	@PutMapping("/")
	@PermissionName(name="添加組別資料權限")
	@RequiresPermissions(value="group:add")
	@Operation(description="添加組別訊息")
	public Result saveGroup(@RequestBody Group group) throws Exception{
		Result result = baseSettingValidation.saveValidate(group);
		if(null != result) {
			return result;
		}
		Group oldGroup = groupService.findOne(group.getId());
		if(null != oldGroup){
			return viewResultFactory.errorResult("需要添加的組別【ID: " + group.getId() + "】已經存在...");
		}
		groupService.addGroup(group);
		return viewResultFactory.successResult();
	}
	/**
	 * 刪除組群訊息
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/{ids}")
	@PermissionName(name="刪除組別資料權限")
	@RequiresPermissions(value="group:delete")
	@Operation(description="刪除組別訊息")
	public Result deleteGroups(@PathVariable Integer[] ids) throws Exception {
		if(null == ids || ids.length <= 0) {
			return viewResultFactory.errorResult("需要刪除的ID不能為空!");
		}
		if(ids.length <= SINGLE_MAXNUMBER) {
			// 單次最多只能刪除SINGLE_MAXNUMBER條記錄
			groupService.deleteGroup(Arrays.asList(ids));
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("單次最多刪" + SINGLE_MAXNUMBER + "條記錄!");
	}
	/**
	 * 更新組群訊息
	 * @param groupId
	 * @param group
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/{groupId}")
	@PermissionName(name="更新組別資料權限")
	@RequiresPermissions(value="group:update")
	@Operation(description="更新組別訊息")
	public Result updateGroup(@PathVariable int groupId,@RequestBody Group group) throws Exception{
		group.setId(groupId);
		Result result = baseSettingValidation.updateValidate(group);
		if(null != result){
			return result;
		}
		Group oldGroup = groupService.findOne(groupId);
		if(null == oldGroup){
			return viewResultFactory.errorResult("需要更新的組別【ID: " + groupId + "】不能存在...");
		}
		groupService.updateGroup(group);
		return viewResultFactory.successResult();
	}
	/**
	 * 查詢指定ID的組別記錄
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{groupId}")
	public Result findOneGroup(@PathVariable int groupId) throws Exception {
		if(groupId <= 0){
			return viewResultFactory.errorResult("ID不能小於0！");
		}
		Group group = groupService.findOne(groupId);
		if(null != group){
			viewResultFactory.errorResult("對不起，沒有查詢到【id: " + groupId + "】的任何記錄");
		}
		return viewResultFactory.successResult(group);
	}
	/**
	 * 查詢分頁組別記錄訊息
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/page/")
	@PermissionName(name="查詢組別資料權限")
	@RequiresPermissions(value="group:find")
	public Result findPageGroup(@RequestParam("page") int page, @RequestParam("limit") int limit) throws Exception {
		List<Group> groups = groupService.findPage(page, limit);
		List<Group> allGroups = groupService.findAll();
		if(null != groups && null != allGroups) {
			return viewResultFactory.successResult(groups, allGroups.size());
		}
		return viewResultFactory.errorResult("查詢分頁組別記錄出現異常...");
	}
	/**
	 * 查詢keyName分頁組別記錄訊息
	 * @param page
	 * @param limit
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/page/key_name/")
	@PermissionName(name="查詢組別資料權限")
	@RequiresPermissions(value="group:find")
	public Result findPageKeyNameGroup(@RequestParam("page")int page, @RequestParam("limit")int limit, @RequestParam("name")String name) throws Exception{
		List<Group> allGroups = groupService.findAll();
		List<Group> keyGroups = groupService.findPageKeyName(page, limit, name);
		if(null != allGroups && null != keyGroups){
			return viewResultFactory.successResult(keyGroups, allGroups.size());
		}
		return viewResultFactory.errorResult("查詢分頁組別記錄出現異常...");
	}
	/**
	 * 獲取所有的組別訊息
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/all/")
	public Result findAllGroup() throws Exception{
		List<Group> groups = groupService.findAll();
		if(null != groups) {
			// 獲取所有組別中卡片的數量
			return viewResultFactory.successResult(groups);
		}
		return viewResultFactory.errorResult("查詢所有組別記錄出現異常");
	}
}
