package com.dmatek.zgb.utils;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

public class Encryptor {
	public static String Encrypt(HashedCredentialsMatcher credentailMatcher,
								String salt,String psw){
		String hashAligoName = credentailMatcher.getHashAlgorithmName();
		int hashIterations = credentailMatcher.getHashIterations();
		SimpleHash simplehash = null;
		if("MD5".equalsIgnoreCase(hashAligoName)){
			simplehash = new Md5Hash(psw, salt, hashIterations);
		}
		return simplehash.toString();
	}
}
