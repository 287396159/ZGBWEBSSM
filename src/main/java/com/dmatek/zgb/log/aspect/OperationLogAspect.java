package com.dmatek.zgb.log.aspect;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.dmatek.zgb.db.log.service.OperationLogService;
import com.dmatek.zgb.log.anno.Operation;
import com.dmatek.zgb.log.bean.OperationLog;
import com.dmatek.zgb.shiro.realm.User;
import com.dmatek.zgb.tool.Inetaddress.InetAddressTools;

@Aspect
@Component
public class OperationLogAspect {
	private Logger logger = Logger.getLogger(OperationLogAspect.class);
	@Autowired
	private OperationLogService logService; 
	/**
	 * 添加操作注解切点
	 */
    @Pointcut("@annotation(com.dmatek.zgb.log.anno.Operation)") 
    public void controllerAspect(){
    }
    
    /**
     * 环绕控制切点
     * @param joinPoint
     * @return
     */
    @Around(value="controllerAspect()")
    public Object arroundController(ProceedingJoinPoint joinPoint) {
    	Object[] args = joinPoint.getArgs();
    	Object obj = null;
    	try {
    		obj = joinPoint.proceed(args);
		} catch (Throwable e) {
			e.printStackTrace();
		}
    	addSysLog(joinPoint);
		return obj;
    }
    /**
     * 添加日志功能
     * @param joinPoint
     */
    private void addSysLog(ProceedingJoinPoint joinPoint) {
    	// 执行Controller没有出现任何问题
    	OperationLog log = new OperationLog();
    	User user = (User) SecurityUtils.getSubject().getPrincipal();
    	// 设置用户名称
    	log.setUserName(user.getName());
    	// 设置用户描述
    	MethodSignature method = (MethodSignature)joinPoint.getSignature();
    	Operation operation = method.getMethod().getAnnotation(Operation.class);
    	if(null != operation){
    		log.setDes(operation.description());
    	}
    	// 记录日志时间
    	log.setLogTime(new Date());
    	// 获取IP
    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    	log.setIp(InetAddressTools.getRequestIp(request));
    	Object[] args = joinPoint.getArgs();
    	if(null != args && args.length > 0) {
    		StringBuffer sArgs = new StringBuffer();
    		for (int i = 0; i < args.length; i++) {
    			if (args[i] instanceof String[]) {
    				String[] keys = (String[])args[i];
    				sArgs.append("[參數：");
    				for (int j = 0; j < keys.length; j++) {
    					if(j == keys.length - 1){
    						sArgs.append(keys[j]);	
    					} else {
    						sArgs.append(keys[j]+" , ");
    					}
					}
    				sArgs.append("] , ");
				} else if(args[i] instanceof String){
					sArgs.append("[參數 : " + args[i] + "]");
				} else if(args[i] instanceof Integer[]){
					Integer[] keys = (Integer[])args[i];
    				sArgs.append("[參數：");
    				for (int j = 0; j < keys.length; j++) {
    					if(j == keys.length - 1){
    						sArgs.append(keys[j]);	
    					} else {
    						sArgs.append(keys[j]+" , ");
    					}
					}
    				sArgs.append("] , ");
				} else if(args[i] instanceof Integer) {
					sArgs.append("[參數 : " + args[i] + "] , ");
				} else if(args[i] instanceof HttpServletRequest) {
					// 不保存任何参数
				} else {
					sArgs.append(args[i].toString() + " , ");
				}
			}
    		// 设置参数
    		log.setArgs(sArgs.toString());
    	}
    	if(null != logService) {
    		try {
				logService.addOperationLog(log);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("添加操作记录出现异常：" + e.toString());
			}
    	}
    }
}
