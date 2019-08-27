package com.dmatek.zgb.controller.permission;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmatek.zgb.db.pojo.permission.Account;
import com.dmatek.zgb.utils.Encryptor;

@Aspect
@Component
public class AccountAspect {
	private static final String[] METHOD_NAMES = {"save","add","update"};
	@Autowired
	private HashedCredentialsMatcher hashedMatcher;
	@Around(value="execution(* com.dmatek.zgb.db.permission.service.imp.AccountServiceImp.*(..))")
	public Object updateArround(ProceedingJoinPoint  joinPoint){
		Account account = null;
		Object obj = null;
		Object[] args = joinPoint.getArgs();
		String methodName = joinPoint.getSignature().getName();
		for (String name : METHOD_NAMES) {
			if(methodName.indexOf(name) >= 0 && args.length > 0) {
				/*说明此时是一个更新的操作，我们应该获取相关参数*/
				if (args[0] instanceof Account) {
					// 我们需要对Account进行更新
					account = (Account) args[0];
					// 对密码进行加密运算
					String enPsw = Encryptor.Encrypt(hashedMatcher,
							account.getName(), account.getPsw());
					account.setPsw(enPsw);
				}
			}
		}
		try {
			obj = joinPoint.proceed(args);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return obj;
	}
}
