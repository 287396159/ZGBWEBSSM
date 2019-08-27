package com.dmatek.zgb.controller.permission;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.SecurityUtils;
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
import com.dmatek.zgb.controller.tool.page.PageTool;
import com.dmatek.zgb.db.permission.service.AccountService;
import com.dmatek.zgb.db.permission.service.RoleService;
import com.dmatek.zgb.db.pojo.permission.Account;
import com.dmatek.zgb.db.pojo.permission.Role;
import com.dmatek.zgb.log.anno.Operation;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.shiro.realm.User;

@RestController
@RequestMapping("/account")
@PermissionName(name="賬戶設置權限")
@RequiresPermissions(value="account:*")
public class AccountController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private BaseSettingValidation<Integer, Account> baseSettingValidation;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private PageTool pageTool;
	@Autowired
	private AccountTool accountTool;
	// 管理員角色
	private final static String ADMIN_NAME = "Admin", DEBUGER_NAME = "Debuger";
	/**
	 * 添加賬號訊息
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@PermissionName(name="添加賬戶權限")
	@RequiresPermissions(value="account:add")
	@Operation(description="添加賬戶資料")
	@PutMapping("/")
	public Result saveAccount(@RequestBody Account account) throws Exception {
		Result result = baseSettingValidation.saveValidate(account);
		if(null != result) {
			return result;
		}
		Account currentAccount = accountService.findOneFromName(account.getName());
		if(null != currentAccount) {
			return viewResultFactory.errorResult("添加的賬戶訊息已經存在!");
		}
		if(accountService.addAccount(account)) {
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("添加賬戶失敗！");
	}
	/**
	 * 刪除賬號訊息
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@PermissionName(name="刪除賬戶權限")
	@RequiresPermissions(value="account:delete")
	@Operation(description="刪除賬戶資料")
	@DeleteMapping("/{name}")
	public Result deleteAccountName(@PathVariable String name) throws Exception {
		if(Strings.isEmpty(name)) {
			return viewResultFactory.errorResult("刪除賬戶訊息不能為空!");
		}
		if(ADMIN_NAME.equalsIgnoreCase(name) 
		|| DEBUGER_NAME.equalsIgnoreCase(name)) {
			return viewResultFactory.errorResult("用戶【name: " + name + "】不能刪除！");
		}
		if(accountService.deleteAccountName(name)) {
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("刪除賬戶失敗!");
	}
	/**
	 * 更新賬號
	 * @param accountId
	 * @param account
	 * @return
	 * @throws Exception
	 */
	@PermissionName(name="更新賬戶權限")
	@RequiresPermissions(value="account:update")
	@Operation(description="更新賬戶資料")
	@PostMapping("/")
	public Result updateAccount(@RequestBody Account account) throws Exception {
		Result result = baseSettingValidation.updateValidate(account);
		if(null != result) {
			return result;
		}
		Account currentAccount = accountService.findOneFromName(account.getName());
		if(null == currentAccount) {
			return viewResultFactory.errorResult("賬戶名稱不能修改!");
		}
		Role role = roleService.findOne(account.getRoleId());
		if(null == role) {
			return viewResultFactory.errorResult("賬戶的角色【id: " + account.getRoleId() + "】不存在!");
		}
		// 查看当前是否是Admin账户，Admin账户必须是super角色，它的角色不允许修改
		if("admin".equalsIgnoreCase(account.getName()) && !role.getName().equalsIgnoreCase("super")) {
			return viewResultFactory.errorResult("賬戶【name: admin】角色必須是超級管理員! ");
		}
		if("debuger".equalsIgnoreCase(account.getName()) 
			&& !role.getName().equalsIgnoreCase("super")) {
			return viewResultFactory.errorResult("賬戶【name: debuger】角色必須是超級管理員! ");
		}
		
		if(accountService.updateAccount(account)) {
			return viewResultFactory.successResult(account);
		}
		return viewResultFactory.errorResult("添加賬戶失敗!");
	}
	/**
	 * 查找所有的賬戶訊息
	 * @return
	 * @throws Exception
	 */
	@PermissionName(name="查找賬戶權限")
	@RequiresPermissions(value="account:find")
	@GetMapping("/")
	public Result findAllAccount(@RequestParam("page") int page,
			@RequestParam("limit") int limit) throws Exception {
		List<Account> accounts = accountTool.getAllAccount();
		List<Account> pages = pageTool.operate(accounts, page, limit);
		if(null != pages && null != accounts) {
			return viewResultFactory.successResult(pages, accounts.size());
		}
		return viewResultFactory.errorResult("查找所有的賬戶訊息時出現異常！");
	}
	/**
	 * 踢出用户
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	@PermissionName(name="踢出用户權限")
	@RequiresPermissions(value="account:kickOut")
	@GetMapping("/kickOut/{userName}")
	public Result kickOutUser(@PathVariable String userName) throws Exception {
		if(StringUtils.isEmpty(userName)) {
			return viewResultFactory.errorResult("踢出的用戶名稱不能為空! ");
		}
		// 判斷是否是用戶自身,如果是用戶自身是不允許進行強制下線的
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		if(null != user && user.getName().equalsIgnoreCase(userName)) {
			return viewResultFactory.errorResult("不用強制當前用戶下線! ");
		}
		// 判断当前用户是否在线
		if(accountTool.isOnLine(userName)) {// 用户在线
			accountTool.kickOutUser(userName);
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("當前用戶【userName : " + userName + "】不在線!");
	}
}
