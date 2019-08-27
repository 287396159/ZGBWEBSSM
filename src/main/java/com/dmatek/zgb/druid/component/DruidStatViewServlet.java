package com.dmatek.zgb.druid.component;
import javax.servlet.Servlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import com.alibaba.druid.support.http.StatViewServlet;
/**
 * 配置进入Druid内置的页面Servlet的参数
 * @author zf
 * @data 2018年12月7日 上午9:25:27
 * @Description
 */
@WebServlet(urlPatterns = { "/druid/*" }, initParams = {
		/*@WebInitParam(name = "allow", value = "127.0.0.1"),*/
		@WebInitParam(name = "loginUsername", value = "root"),
		@WebInitParam(name = "loginPassword", value = "123"),
		@WebInitParam(name = "resetEnable", value = "true") // 允许HTML页面上的“Reset
})
public class DruidStatViewServlet extends StatViewServlet implements Servlet {
	private static final long serialVersionUID = 1L;
}
