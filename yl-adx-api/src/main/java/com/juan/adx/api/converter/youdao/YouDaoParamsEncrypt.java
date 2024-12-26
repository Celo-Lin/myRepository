package com.juan.adx.api.converter.youdao;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/15 16:53
 */
public class YouDaoParamsEncrypt {

    public static final int INITIALIZATION_VECTOR_SIZE = 16;
    static final int SIGNATURE_SIZE = 4;
    static final int BLOCK_SIZE = 20;


    /**
     * base64加密并反转字符串
     *
     * @param bts
     * @return
     */
    public static String encrypt(String bts) {
        String base64 = Base64Utils.encodeToUrlSafeString(bts.getBytes());
        return StringUtils.reverse(base64);
    }

    public static byte[] encryptPrice(byte[] plainText, SecretKey encryptionKey, SecretKey integrityKey) throws NoSuchAlgorithmException, InvalidKeyException {
        try {
            long timestamp = System.currentTimeMillis();
            int seconds = (int) (timestamp / 1000L);
            int microSeconds = (int) ((timestamp - seconds * 1000) * 1000L);
            byte[] iv = new byte[INITIALIZATION_VECTOR_SIZE];
            for (int i = 3; i >= 0; i--) {
                iv[i] = (byte) (seconds & 0xffL);
                seconds >>= 8;
            }
            for (int i = 7; i >= 4; i--) {
                iv[i] = (byte) (microSeconds & 0xffL);
                microSeconds >>= 8;
            }
            final byte[] result = new byte[INITIALIZATION_VECTOR_SIZE + plainText.length + SIGNATURE_SIZE];
            System.arraycopy(iv, 0, result, 0, INITIALIZATION_VECTOR_SIZE);
            final Mac hmacer = Mac.getInstance("HmacSHA1");
            boolean add_iv_counter_byte = true;
            for (int resultIndex = INITIALIZATION_VECTOR_SIZE, plainIndex = 0; plainIndex < plainText.length; ) {
                hmacer.reset();
                hmacer.init(encryptionKey);
                final byte[] pad = hmacer.doFinal(iv);
                for (int i = 0; i < BLOCK_SIZE && plainIndex < plainText.length; resultIndex++, plainIndex++, i++) {
                    result[resultIndex] = (byte) (plainText[plainIndex] ^ pad[i]);
                }
                if (!add_iv_counter_byte) {
                    add_iv_counter_byte = ++iv[iv.length - 1] == 0;
                }
                if (add_iv_counter_byte) {
                    add_iv_counter_byte = false;
                    iv = Arrays.copyOf(iv, iv.length + 1);
                }
            }
            final byte[] sig = new byte[INITIALIZATION_VECTOR_SIZE + plainText.length];
            System.arraycopy(plainText, 0, sig, 0, plainText.length);
            System.arraycopy(iv, 0, sig, plainText.length, 16);
            hmacer.reset();
            hmacer.init(integrityKey);
            byte[] signature = hmacer.doFinal(sig);
            System.arraycopy(signature, 0, result, INITIALIZATION_VECTOR_SIZE + plainText.length, 4);
            return result;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("HmacSHA1 not supported.", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Key is invalid for this purpose.", e);
        }
    }

    public static String  encryptWinningPrice(Double winPrice,String encryptionKeyStr,String integrityKeyStr){
        SecretKeySpec encryptionKey = new SecretKeySpec(DatatypeConverter.parseHexBinary(encryptionKeyStr), "HmacSHA1");
        SecretKeySpec integrityKey = new SecretKeySpec(DatatypeConverter.parseHexBinary(integrityKeyStr), "HmacSHA1");
        long priceLong = (long) winPrice.doubleValue();
        byte[] priceBytes = ByteBuffer.allocate(8).putLong(priceLong).array();
        try {
            return Base64.encodeBase64URLSafeString(encryptPrice(priceBytes, encryptionKey, integrityKey));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            return null;
        }
    }

    public static void testEncryptWinningPrice() throws NoSuchAlgorithmException, InvalidKeyException {
        double winPrice = 100.0;
        String encryptionKeyStr = "f67071f17a27443efc8e6a32409f1c0fc4954ea3ce50a5c21f5885af6a3a0637";
        String integrityKeyStr = "2915aa66141c0ed8681c463a0b7c03747012a9bda160fd55301cb4295f647f2b";
        SecretKeySpec encryptionKey = new SecretKeySpec(DatatypeConverter.parseHexBinary(encryptionKeyStr), "HmacSHA1");
        SecretKeySpec integrityKey = new SecretKeySpec(DatatypeConverter.parseHexBinary(integrityKeyStr), "HmacSHA1");
        long priceLong = (long) winPrice;
        byte[] priceBytes = ByteBuffer.allocate(8).putLong(priceLong).array();
        String encryptedPrice = Base64.encodeBase64URLSafeString(encryptPrice(priceBytes, encryptionKey, integrityKey));
        System.out.println("The value is: " + encryptedPrice);
    }

    public static void main(String[] args) {
        try {
            testEncryptWinningPrice();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}


