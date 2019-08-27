package com.dmatek.zgb.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.dmatek.zgb.exception.bean.ExceptionMessage;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.utils.AjaxUtils;

@RestControllerAdvice
public class BusinessExceptionHandler {
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@ExceptionHandler(Exception.class)
	public Object handleException(Exception e,HttpServletRequest req) {
		// 判断是否是ajax请求
		if(AjaxUtils.IsAjax(req)){// 是ajax的请求
			ExceptionMessage exceptionMessage = null;
			String errorMsg = "";
			if (e instanceof UnauthorizedException) {
				errorMsg = "沒有指定權限";
				exceptionMessage = new ExceptionMessage(501);
			} else {
				errorMsg = "未知異常";
				exceptionMessage = new ExceptionMessage(500);
			}
			exceptionMessage.setMsg(errorMsg);
			return viewResultFactory.errorResult(errorMsg, exceptionMessage);
		} else {// 不是ajax请求
			ModelAndView modelAndView = new ModelAndView();
			if (e instanceof UnauthorizedException) {
				modelAndView.setViewName("error/unauthor");
			} else {
				modelAndView.setViewName("error/500");
			}
			modelAndView.addObject("name", e.getClass().getName());
			modelAndView.addObject("msg", e.getMessage());
			modelAndView.addObject("url", req.getRequestURL());
			modelAndView.addObject("stackTrace", e.getStackTrace());
			modelAndView.setViewName("error");
			return modelAndView;
		}
	}
}
