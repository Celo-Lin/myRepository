package com.juan.adx.api.converter.yueke;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.util.Base64Utils;

class YueKePriceEncrypt {
	
	public static String encrypt(String src, String key) {
		byte[] encryptedBytes = aesEncrypt(src, key);
        String encryptedString = Base64Utils.encodeToString(encryptedBytes);
        return encryptedString;
	}

    public static byte[] aesEncrypt(String src, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] content = src.getBytes();
            return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String aesDecrypt(String encryptedHex, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] encryptedBytes = Base64Utils.decodeFromString(encryptedHex);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String src = "1000";
        String key = "123456789abcdefg";
        String encryptedString = encrypt(src, key);
        System.out.println("Encrypted string: " + encryptedString);
        System.out.println("Decrypt string: " + aesDecrypt(encryptedString, key));
    }
}
