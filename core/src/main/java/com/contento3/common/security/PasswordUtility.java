package com.contento3.common.security;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;

public class PasswordUtility {

	public static PasswordInfo createSaltedPassword(final String plainPassword){
		final RandomNumberGenerator saltGenerator = new SecureRandomNumberGenerator();
		final String salt = saltGenerator.nextBytes().toBase64();
		final ByteSource originalPassword = ByteSource.Util.bytes(plainPassword);
		
		final Hash hash = new Sha256Hash(originalPassword, salt, 1);
		final String finalHash = hash.toString();
		
		PasswordInfo info = new PasswordInfo();
		info.setPassword(finalHash);
		info.setSalt(salt);
		return info;
	}

	public static String getEncryptedPassword(final String salt,final String plainPassword){
		final ByteSource originalPassword = ByteSource.Util.bytes(plainPassword);
		
		final Hash hash = new Sha256Hash(originalPassword, salt, 1);
		final String finalHash = hash.toString();
		return finalHash;
	}

	
}
