package com.juan.adx.api.converter.oppo;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class OppoPriceEncrypt {
    
    /**
    * 加密
    * *
    @param content 需要加密内容
    * @param encryptionKey 秘钥
    * @return
    * @throws Exception
    */
    public static String encrypt(String content, String encryptionKey) {
    try {
	    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
	    SecretKey secretKey = new SecretKeySpec(Base64.decodeBase64(encryptionKey), "AES");
	    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	    return Base64.encodeBase64String(cipher.doFinal(content.getBytes()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } 
	    return content;
    }
    public static void main(String[] args) {
    	// 示例如下
    	String price = "1222.01";
    	String key = "5AE8274196C85364D44938E47D80ACA6";

    	String encodeStr = OppoPriceEncrypt.encrypt(price, key); 
    	System.out.println(encodeStr);
	}

}


