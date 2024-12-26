package com.juan.adx.common.alg.des;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.juan.adx.common.alg.base64.Base64Util;


public class DESUtil {
    // 算法名称
    public static final String KEY_ALGORITHM = "DES";
    private static final Map<String, DESUtil> cache = new ConcurrentHashMap<String, DESUtil>();
    // 算法名称/加密模式/填充方式
    // DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
    public String lumiDesAlgorithm = "DES/ECB/PKCS5Padding";

    /**
     * 生成密钥key对象
     *
     * @param keyStr 密钥字符串
     * @return 密钥对象
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws Exception
     */
    private SecretKey keyGenerator(String keyStr) throws Exception {
        byte input[] = keyStr.getBytes("utf-8");
        DESKeySpec desKey = new DESKeySpec(input);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(desKey);
        return securekey;
    }

    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密后的数据
     */
    public String encrypt(String data, String key) throws Exception {
        Key deskey = keyGenerator(key);
        Cipher cipher = Cipher.getInstance(lumiDesAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] results = cipher.doFinal(data.getBytes());
        return Base64Util.encode(results);
    }

    /**
     * 解密数据
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return 解密后的数据
     */
    public String decrypt(String data, String key) throws Exception {
        Key deskey = keyGenerator(key);
        Cipher cipher = Cipher.getInstance(lumiDesAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        return new String(cipher.doFinal(Base64Util.decode(data)));
    }

    private DESUtil(String mode, String padding) {
        this.lumiDesAlgorithm = DESUtil.KEY_ALGORITHM + "/" + mode + "/" + padding;
    }

    public static DESUtil getInstance(String mode, String padding) {
        String key = String.format("%s/%s", mode, padding);
        if (!cache.containsKey(key)) {
            synchronized (DESUtil.class) {
                if (!cache.containsKey(key)) {
                    cache.put(key, new DESUtil(mode, padding));
                }
            }
        }
        return cache.get(key);
    }
}

