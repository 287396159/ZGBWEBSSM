package com.dmatek.zgb.controller.view;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dmatek.zgb.params.setting.cache.ParamsKey;
import com.dmatek.zgb.params.setting.cache.SettingParams;
import com.dmatek.zgb.shiro.realm.User;


@Controller
public class ViewController {
	@Autowired
	private SettingParams settingParams;
	private final String shiroLogin = "shiroLoginFailure";
	private final Logger logger = Logger.getLogger(ViewController.class);
	/**
	 * 登陸界面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String Login(HttpServletRequest request){
		String exceptionName = (String) request.getAttribute(shiroLogin);
		logger.warn("exceptionName : " + exceptionName);
		if (null != exceptionName) {// 說明存在錯誤訊息
			if (UnknownAccountException.class.getName().equals(exceptionName)) {
				request.setAttribute("userErr", "賬號不存在");
			} else if (IncorrectCredentialsException.class.getName().equals(
					exceptionName)) {
				request.setAttribute("pswErr", "用戶名或密碼錯誤");
			} else {
				request.setAttribute("userErr", "未知錯誤");
			}
		}
		return "login";
	}
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String defaultLogin(HttpServletRequest request){
		return "login";
	}
	/**
	 * 主界面
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(){
		return "index";
	}
	/**
	 * 主頁 功能導航欄 視圖解析器
	 * @return
	 */
	@GetMapping("/toolNav")
	public String toolNav(){
		return "nav/mainNav";
	}
	/**
	 * 設置界面
	 * @param req
	 * @return
	 */
	@GetMapping("/setting")
	public ModelAndView setting(HttpServletRequest req){
		// 獲取所有的參數
		Map<String,Object> allParams = settingParams.getAllParams();
		ModelAndView modelView = new ModelAndView("setting/ccSetMgr");
		modelView.addObject("allParams", allParams);
		return modelView;
	}
	/**
	 * 包商管理設置界面
	 * @return
	 */
	@GetMapping("/contractor")
	public ModelAndView contractor(HttpServletRequest req){
		ModelAndView modelView = new ModelAndView("setting/contractorDataMgr");
		return modelView;
	}
	/**
	 * 動態監控
	 * @param req
	 * @return
	 */
	@GetMapping("/monitor")
	public ModelAndView monitor(HttpServletRequest req){
		ModelAndView modelView = new ModelAndView("monitor/monitoring");
		Object param = settingParams.get(ParamsKey.isBsVisible.getVal());
		Object regionShowNumber = settingParams.get(ParamsKey.isShowMonitorRegionNumber.getVal());
		modelView.addObject(ParamsKey.isBsVisible.getVal(), param);
		modelView.addObject(ParamsKey.isShowMonitorRegionNumber.getVal(), regionShowNumber);
		return modelView;
	}
	/**
	 * 報警訊息
	 * @param req
	 * @return
	 */
	@GetMapping("/warm")
	public ModelAndView warm(HttpServletRequest req){
		ModelAndView modelView = new ModelAndView("warm/alarm");
		return modelView;
	}
	/**
	 * 基站訊息
	 * @param req
	 * @return
	 */
	@GetMapping("/base")
	public ModelAndView base(HttpServletRequest req){
		ModelAndView modelView = new ModelAndView("base/baseStation");
		Object param = settingParams.get(ParamsKey.isNodeManagerShow.getVal());
		Object isRefrshTreeTime = settingParams.get(ParamsKey.isRefreshTreeTime.getVal());
		Object refreshTime = settingParams.get(ParamsKey.refreshTreeTime.getVal());
		modelView.addObject(ParamsKey.isRefreshTreeTime.getVal(), isRefrshTreeTime);
		modelView.addObject(ParamsKey.refreshTreeTime.getVal(), refreshTime);
		modelView.addObject(ParamsKey.isNodeManagerShow.getVal(), param);
		User user = (User)SecurityUtils.getSubject().getPrincipal();
		String userName = (null != user ? user.getName():"");
		modelView.addObject("isDebug","Debuger".equals(userName)?true:false);
		return modelView;
	}
	/**
	 *  進出記錄管理
	 * @param req
	 * @return
	 */
	@GetMapping("/access")
	public ModelAndView access(HttpServletRequest req){
		ModelAndView modelView = new ModelAndView("access/recordOfAccess");
		return modelView;
	}
	/**
	 * 輸出報表
	 * @param req
	 * @return
	 */
	@GetMapping("/report")
	public ModelAndView report(HttpServletRequest req){
		ModelAndView modelView = new ModelAndView("report/exportReport");
		return modelView;
	}
	/**
	 * 查看歷史軌跡訊息
	 * @param req
	 * @return
	 */
	@GetMapping("/history")
	public ModelAndView history(HttpServletRequest req) {
		ModelAndView modelView = new ModelAndView("history/historyTrack");
		Map<String,Object> allParams = settingParams.getAllParams();
		modelView.addObject("allParams", allParams);
		return modelView;
	}
	/**
	 * 獲取無權限的頁面
	 * @return
	 */
	@RequestMapping(value="/unauth",method=RequestMethod.GET)
	public String unauth(){
		return "unauth";
	}
	/**
	 * 基站視圖
	 * @return
	 */
	@RequestMapping(value="/nodeview",method=RequestMethod.GET)
	public String node(){
		return "/tab/node";
	}
	/**
	 * 基站視圖
	 * @return
	 */
	@RequestMapping(value="/accountview",method=RequestMethod.GET)
	public String account(){
		return "/account/userAccountMgr";
	}
}
