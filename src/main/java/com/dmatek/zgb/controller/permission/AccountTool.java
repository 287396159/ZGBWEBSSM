package com.dmatek.zgb.controller.permission;

import java.util.Collection;
import java.util.List;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.util.ListUtils;
import com.dmatek.zgb.broadcast.imessage.KickOutMessageGetter;
import com.dmatek.zgb.controller.setting.params.base.ShowSettingParamTool;
import com.dmatek.zgb.db.permission.service.AccountService;
import com.dmatek.zgb.db.pojo.permission.Account;
import com.dmatek.zgb.params.setting.cache.ParamsKey;
import com.dmatek.zgb.shiro.realm.User;
import com.dmatek.zgb.websocket.container.WebSocketContainer;
import com.dmatek.zgb.websocket.container.WebSocketContainerManager;

@Configuration
public class AccountTool {
	@Autowired
	private AccountService accountService;
	@Autowired
	private SessionDAO sessionDao;
	@Autowired
	private WebSocketContainerManager webSocketContainerManager;
	@Autowired
	private KickOutMessageGetter kickOutMessageGetter;
	@Autowired
	private ShowSettingParamTool showSettingTool;
	/**
	 * 根据SesionDao更新更新状态
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void updateAccountStatusSessionDao(List<Account> allAccounts) throws Exception {
		Collection<Session> sessions = sessionDao.getActiveSessions();
		for (Account account : allAccounts) {
			String userName = account.getName();
			for (Session session : sessions) {
				SimplePrincipalCollection simple = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
				if(null != simple) {
					User user = (User) simple.getPrimaryPrincipal();
					if(null != user 
					&& userName.equalsIgnoreCase(user.getName())) {
						// 說明當前用戶在線
						account.setStatus(1);
						account.setNumber(account.getNumber() + 1);
					}
				}
			}
		}
	}
	/**
	 * 根据webSocket更新账户当前状态
	 * @param allAccounts
	 * @throws Exception
	 */
	private void updateAccountStatusWebSocket(List<Account> allAccounts) throws Exception {
		for (Account account : allAccounts) {
			String userName = account.getName();
			List<javax.websocket.Session> allSessions = webSocketContainerManager.getAllUsers(WebSocketContainer.CONTAINERNAME, userName);
			if(null != allSessions && allSessions.size() > 0) {
				account.setStatus(1);
				account.setNumber(allSessions.size());
			} else {
				account.setStatus(0);
				account.setNumber(0);
			}
		}
	}
	public List<Account> getAllAccount() throws Exception {
		// 获取所有登陆的Session
		List<Account> allAccounts = accountService.findAll();
		// 需要知道我們是否應該顯示Debuger訊息
		boolean isShowDebug = (boolean) showSettingTool
				.getShowParamsValue(ParamsKey.isShowDebug);
		if(!isShowDebug && !ListUtils.isEmpty(allAccounts)) {
			// 我們需要從列表中將debuger賬戶剔除掉
			for (int i = 0; i < allAccounts.size(); i++) {
				if("Debuger".equalsIgnoreCase(allAccounts.get(i).getName())) {
					// 需要刪除Debuger賬戶訊息
					allAccounts.remove(i);
					break;
				}
			}
		}
		updateAccountStatusWebSocket(allAccounts);
		return allAccounts;
	}
	/**
	 * 強制用戶下線
	 * @param userName
	 * @throws Exception 
	 */
	public void kickOutUser(String userName) throws Exception {
		Collection<Session> sessions = sessionDao.getActiveSessions();
		for (Session session : sessions) {
			SimplePrincipalCollection simple = (SimplePrincipalCollection) session
					.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if(null != simple) {
				User user = (User) simple.getPrimaryPrincipal();
				if(null != user && user.getName().equalsIgnoreCase(userName)) {
					// 应该让当前的用户下线
					session.stop();
				}
			}
		}
		// 給出信息提醒
		kickOutMessageGetter.setMsg(KickOutMessageGetter.KICKOUT_TEXT);
		webSocketContainerManager.sendMessageToUser(WebSocketContainer.CONTAINERNAME, 
				userName, kickOutMessageGetter.getBroadCastMessage());
	}
	/**
	 * 判断用户是否在线
	 * @param userName
	 * @return
	 */
	public boolean isOnLine(String userName) {
		Collection<Session> sessions = sessionDao.getActiveSessions();
		for (Session session : sessions) {
			SimplePrincipalCollection simple = (SimplePrincipalCollection) session
					.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if(null != simple) {
				User user = (User) simple.getPrimaryPrincipal();
				if(null != user && user.getName().equalsIgnoreCase(userName)) {
					// 说明用户在线
					return true;
				}
			}
		}
		return false;
	}
}
