package com.dmatek.zgb.shiro.configuration;

import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionManagerConfig {
	@Bean
	public SimpleCookie getSimpleCookie() {
		return new SimpleCookie("shiro.sesssion");
	}
	@Bean
	public SessionDAO getsessionDAO() {
		return new MemorySessionDAO();
	}
	@Bean
	public DefaultWebSessionManager getSessionManager(SessionDAO sessionDao, 
			SimpleCookie simpleCookie) {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionDAO(sessionDao);
		sessionManager.setSessionIdCookie(simpleCookie);
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setGlobalSessionTimeout(-1);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		return sessionManager;
	}
}
