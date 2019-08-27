package com.dmatek.zgb.shiro.configuration;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

import com.dmatek.zgb.broadcast.imessage.KickOutMessageGetter;
import com.dmatek.zgb.controller.setting.params.base.SystemSettingParamTool;
import com.dmatek.zgb.filter.MyFormAuthenticationFilter;
import com.dmatek.zgb.login.access.filter.KickoutSessionControlFilter;
import com.dmatek.zgb.shiro.realm.MyShiroRealm;
import com.dmatek.zgb.websocket.container.WebSocketContainerManager;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;

@Configuration
public class ShiroConfiguration {
	private static final String LOGINURL = "/login";
	private static final String SUCCESSURL = "/index";
	private static final String UNAUTHORIZEDURL = "/unauth";
	@Autowired
	private SystemSettingParamTool systemSettingTool;
	@Bean("shiroDialect")
	public ShiroDialect getShiroDialect() {
		return new ShiroDialect();
	}
	@Bean
	public EhCacheManager getEhCacheManager() {
		EhCacheManager ehCacheManager = new EhCacheManager();
		ehCacheManager.setCacheManagerConfigFile("classpath:shiro-ehcache.xml");
		return ehCacheManager;
	}
	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator 
								= new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
	    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
	    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
	    return authorizationAttributeSourceAdvisor;
	}
	@Bean
	public FilterRegistrationBean delegatingFilterProxy() {
	    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
	    DelegatingFilterProxy proxy = new DelegatingFilterProxy();
	    proxy.setTargetFilterLifecycle(true);
	    proxy.setTargetBeanName("shiroFilter");
	    filterRegistrationBean.setFilter(proxy);
	    return filterRegistrationBean;
	}
	@Bean
	public HashedCredentialsMatcher getHashedCredentialsMatcher() {
		HashedCredentialsMatcher myHashedCredentialsMatcher = new HashedCredentialsMatcher("MD5");
		myHashedCredentialsMatcher.setHashIterations(2);
		return myHashedCredentialsMatcher;
	}
	@Bean
	public SecurityManager getSecurityManager(AuthorizingRealm authorizingRealm,
		DefaultWebSessionManager sessionManager, EhCacheManager ehCacheManager) {
		DefaultWebSecurityManager secManager = new DefaultWebSecurityManager();
		secManager.setRealm(authorizingRealm);
		secManager.setSessionManager(sessionManager);
		secManager.setCacheManager(ehCacheManager);
		return secManager;
	}
	@Bean
	public KickoutSessionControlFilter getKickoutSessionControlFilter(
		DefaultWebSessionManager sessionManager, 
		EhCacheManager ehCacheManager,
		WebSocketContainerManager webSocketContainerManager,
		KickOutMessageGetter kickOutMessageGetter) {
		KickoutSessionControlFilter kickoutFilter = new KickoutSessionControlFilter();
		kickoutFilter.setKickOutMessageGetter(kickOutMessageGetter);
		kickoutFilter.setWebSocketContainerManager(webSocketContainerManager);
		kickoutFilter.setSessionManager(sessionManager);
		kickoutFilter.setKickoutAfter(false);
		kickoutFilter.setMaxSession(10);
		kickoutFilter.setKickoutUrl(LOGINURL);
		kickoutFilter.setCacheManager(ehCacheManager);
		return kickoutFilter;
	}
	@Bean
	public AuthorizingRealm getAuthorizingRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(getHashedCredentialsMatcher());
		return myShiroRealm;
	}
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(
			SecurityManager securityManager, 
			KickoutSessionControlFilter kickoutFilter) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl(LOGINURL);
		Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
		filters.put("kickout", kickoutFilter);
		filters.put("authc", new MyFormAuthenticationFilter());
		shiroFilterFactoryBean.setFilters(filters);
		shiroFilterFactoryBean.setSuccessUrl(SUCCESSURL);
		shiroFilterFactoryBean.setUnauthorizedUrl(UNAUTHORIZEDURL);
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put("/js/lib/**", "anon");
		filterChainDefinitionMap.put("/js/module/**", "anon");
		filterChainDefinitionMap.put("/css/lib/**", "anon");
		filterChainDefinitionMap.put("/css/module/**", "anon");
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/**", "kickout,authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}
}
