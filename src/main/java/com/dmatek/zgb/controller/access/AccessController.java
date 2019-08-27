package com.dmatek.zgb.controller.access;

import java.util.List;
import javax.annotation.Resource;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dmatek.zgb.access.AccessTool;
import com.dmatek.zgb.access.bean.FillAccessRecord;
import com.dmatek.zgb.access.bean.TagAccessRecord;
import com.dmatek.zgb.controller.tool.page.PageTool;
import com.dmatek.zgb.log.anno.Operation;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.show.vo.AccessView;

@RestController
@RequestMapping("/access/")
@PermissionName(name="進出統計權限設置")
@RequiresPermissions("access:*")
public class AccessController {
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Resource(name="accessTool")
	private AccessTool accessTool;
	// 列表分页工具
	@Autowired
	private PageTool pageTool;
	/**
	 * 获取当前的门控状态
	 * @return
	 */
	@GetMapping("/doorcontrol/status")
	public Result getDoorControlStatus() {
		return viewResultFactory.successResult(accessTool
				.getDoorControlStatus());
	}
	/**
	 * 設置當前的門控狀態
	 * @param status
	 * @return
	 * @throws InterruptedException 
	 */
	@PutMapping("/doorcontrol/status/{status}")
	@PermissionName(name="切換門控權限設置")
	@RequiresPermissions("access:doorcontrol")
	@Operation(description="設置當前的門控狀態")
	public Result setDoorControlStatus(@PathVariable int status) throws InterruptedException {
		accessTool.manualSetCurrentDoorStatus(status);
		return viewResultFactory.successResult(accessTool
				.getDoorControlStatus());
	}
	/**
	 * 上班選擇
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	@PermissionName(name="進出統計資料獲取權限")
	@RequiresPermissions(value="access:find")
	@GetMapping("/onduty/{groupId}")
	public Result getAccessOndutyFromGroup(@PathVariable int groupId,
			@RequestParam(name="accessTime", required=false) String sTime) throws Exception{
		List<TagAccessRecord> accessRecords = accessTool.getAccessOnDutyFromGroup(groupId, 
											  accessTool.getAccessTime(sTime));
		return viewResultFactory.successResult(accessRecords);
	}
	/**
	 * 下班選擇
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	@PermissionName(name="進出統計資料獲取權限")
	@RequiresPermissions(value="access:find")
	@GetMapping("/offduty/{groupId}")
	public Result getAccessOffdutyFromGroup( @PathVariable int groupId, 
			@RequestParam(name="accessTime",required=false) String sTime) throws Exception {
		List<TagAccessRecord> accessRecords = accessTool.getAccessOffDutyFromGroup(groupId, 
											  accessTool.getAccessTime(sTime));
		return viewResultFactory.successResult(accessRecords);
	}
	/**
	 * 在列表模式下面
	 * @param groupId
	 * @param page
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@PermissionName(name="進出統計資料獲取權限")
	@RequiresPermissions(value="access:find")
	@GetMapping("/{groupId}")
	public Result getAccessFromGroup(@PathVariable int groupId,
			@RequestParam("page") int page,
			@RequestParam("limit") int limit,
			@RequestParam(name="accessTime",required=false) String sTime) throws Exception{
		List<AccessView> totalRecords = accessTool.getAccessFromGroup(groupId,
										accessTool.getAccessTime(sTime));
		// 獲取分頁的組別訊息
		return viewResultFactory.successResult(pageTool.operate(totalRecords, page, limit), totalRecords.size());
	}
	/**
	 * 刪除進出記錄
	 * @param accessView
	 * @return
	 * @throws Exception 
	 */
	@PermissionName(name="刪除進出統計資料權限")
	@RequiresPermissions(value="access:delete")
	@PostMapping("/delete/{groupId}")
	@Operation(description="刪除進出統計資料")
	public Result deleteAccessRecord(@PathVariable int groupId, @RequestBody AccessView accessView) throws Exception{
		accessTool.deleteAccessInfor(accessView, groupId);
		return viewResultFactory.successResult();
	}
	/**
	 * 補填上下班訊息
	 * @param accessRecord
	 * @return
	 * @throws Exception 
	 */
	@PermissionName(name="獲取進出統計訊息權限")
	@RequiresPermissions(value="access:find")
	@PutMapping("/")
	public Result fillAccessInfor(@RequestBody FillAccessRecord accessRecord) throws Exception {
		if(accessTool.fillAccessInfor(accessRecord)) {
			return viewResultFactory.successResult();
		} else {
			return viewResultFactory.errorResult("補填上下班訊息失敗！");
		}
	}
	/**
	 * 根據關鍵字、時間、組別獲取上班訊息
	 * @param sTime
	 * @param groupId
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	@PermissionName(name="獲取進出統計訊息權限")
	@RequiresPermissions(value="access:find")
	@GetMapping("/key/onduty/{groupId}")
	public Result getAccessOnDutyFromKey(@PathVariable int groupId, 
			@RequestParam("accessTime") String sTime,
			@RequestParam("key") String key) throws Exception{
		if(Strings.isEmpty(key)){
			return viewResultFactory.errorResult("關鍵字不能為空...");
		}
		List<TagAccessRecord> accessRecords = accessTool.getAccessOnDutyFromGroup(groupId,
											  accessTool.getAccessTime(sTime));
		List<TagAccessRecord> filterRecords = accessTool.filterAccessRecords(accessRecords, key);
		return viewResultFactory.successResult(filterRecords);
	}
	/**
	 * 根據關鍵字、時間、組別獲取下班訊息
	 * @param groupId
	 * @param sTime
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@PermissionName(name="獲取進出統計訊息權限")
	@RequiresPermissions(value="access:find")
	@GetMapping("/key/offduty/{groupId}")
	public Result getAccessOffDutyFromKey(@PathVariable int groupId,
			@RequestParam("accessTime") String sTime,
			@RequestParam("key") String key) throws Exception{
		if(Strings.isEmpty(key)){
			return viewResultFactory.errorResult("關鍵字不能為空...");
		}
		List<TagAccessRecord> accessRecords = accessTool.getAccessOffDutyFromGroup(groupId,
											  accessTool.getAccessTime(sTime));
		List<TagAccessRecord> filterRecords = accessTool.filterAccessRecords(
				accessRecords, key);
		return viewResultFactory.successResult(filterRecords);
	}
	
	/**
	 * 在列表模式下面
	 * @param groupId
	 * @param page
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@PermissionName(name="獲取進出統計訊息權限")
	@RequiresPermissions(value="access:find")
	@GetMapping("/key/{groupId}")
	public Result getAccessFromKey(@PathVariable int groupId,@RequestParam("page") int page,@RequestParam("limit") int limit,@RequestParam(name="accessTime",required=false) String sTime, @RequestParam("key") String key) throws Exception {
		if(Strings.isEmpty(key)) {
			return viewResultFactory.errorResult("關鍵字不能為空...");
		}
		// 獲取所有的記錄儀
		List<AccessView> totalRecords = accessTool.getAccessFromGroup(groupId, 
										accessTool.getAccessTime(sTime));
		// 獲取含有關鍵字的所有記錄
		List<AccessView> filterTotalRecords = accessTool
				.filterAccessViewRecords(totalRecords, key);
		return viewResultFactory.successResult(
				pageTool.operate(filterTotalRecords, page, limit),
				filterTotalRecords.size());
	}
}
