package com.juan.adx.common.alg.bcrypt;

import java.security.NoSuchAlgorithmException;

public class TestBCrypt{
	
	public static void main(String[] args) throws NoSuchAlgorithmException 
	{
		String  originalPassword = "password";
		String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(13));
		System.out.println(generatedSecuredPasswordHash);
		
		boolean matched = BCrypt.checkpw(originalPassword, generatedSecuredPasswordHash);
		System.out.println(matched);
	}

}
