package com.dmatek.zgb.druid.component;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import com.alibaba.druid.support.http.WebStatFilter;
/**
 * 配置Druid过滤器参数
 * @author zf
 * @data 2018年12月7日 上午9:28:11
 * @Description
 */
@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*", initParams = {// 配置本过滤器放行的请求后缀
@WebInitParam(name = "exclusions", value = "*.js,*.jpg,*.png,*.gif,*.ico,*.css,/druid/*") })
public class DruidStatFilter extends WebStatFilter {

	public DruidStatFilter() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
