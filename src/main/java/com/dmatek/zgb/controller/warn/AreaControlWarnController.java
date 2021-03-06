package com.dmatek.zgb.controller.warn;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dmatek.zgb.controller.tool.page.PageTool;
import com.dmatek.zgb.log.anno.Operation;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.warn.bean.AreaControlWarn;
import com.dmatek.zgb.warn.cache.base.BaseWarnCache;

@RestController
@RequestMapping("/warm/areacontrol")
@PermissionName(name="警告訊息權限設置")
@RequiresPermissions(value="warmMsg:*")
public class AreaControlWarnController {
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private BaseWarnCache<AreaControlWarn> areaControlWarnCache;
	@Autowired
	private PageTool pageTool;
	/**
	 * 獲取所有的人員求救信息
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/")
	@PermissionName(name="查看警告訊息權限")
	@RequiresPermissions(value="warmMsg:find")
	public Result getAreaControlWarnWarns(@RequestParam("page") int page, @RequestParam("limit") int limit) throws Exception {
		List<AreaControlWarn> totals = areaControlWarnCache.getAllWarns();
		List<AreaControlWarn> pages = pageTool.operate(totals, page, limit);
		return viewResultFactory.successResult(pages, totals.size());
	}
	/**
	 * 處理人員求救
	 * @param uuids
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/handle/{uuids}")
	@PermissionName(name="處理警告訊息權限")
	@RequiresPermissions(value="warmMsg:handle")
	@Operation(description="處理區域管制警告訊息")
	public Result handleAreaControlWarnWarns(@PathVariable String[] uuids) throws Exception {
		if(null != uuids && uuids.length > 0){
			areaControlWarnCache.handleWarns(uuids);
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("處理警告的UUID不能為空...");
	}
	/**
	 * 清除人員求救
	 * @param uuids
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/clear/{uuids}")
	@PermissionName(name="清除警告訊息權限")
	@RequiresPermissions(value="warmMsg:clear")
	@Operation(description="清除區域管制警告訊息")
	public Result clearAreaControlWarnWarns(@PathVariable String[] uuids) throws Exception{
		if(null != uuids && uuids.length > 0){
			areaControlWarnCache.clearWarns(uuids);
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("清除警告的UUID不能為空...");
	}
}
