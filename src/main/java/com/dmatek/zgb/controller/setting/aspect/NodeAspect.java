package com.dmatek.zgb.controller.setting.aspect;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.dmatek.zgb.db.pojo.setting.Node;

@Component
@Aspect
public class NodeAspect {
	private static final String REFER_TYPE_NAME = "參考點";
	private static final String NODE_TYPE_NAME = "數據節點";
	@Around(value = "execution(* com.dmatek.zgb.db.setting.service.imp.NodeServiceImp.find*(..))")
	public Object findNodesAdvice(ProceedingJoinPoint joinPoint){
		Object[] args = joinPoint.getArgs();
		Object obj = null;
		try {
			obj = joinPoint.proceed(args);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if(obj instanceof List) {
			@SuppressWarnings("unchecked")
			List<Object> nodes = (List<Object>) obj;
			for (Object object : nodes) {
				if (object instanceof Node) {
					Node node = (Node) object;
					node.setTypeName(node.getType()==0?REFER_TYPE_NAME:NODE_TYPE_NAME);
				}
			}
		}
		return obj;
	}
}
