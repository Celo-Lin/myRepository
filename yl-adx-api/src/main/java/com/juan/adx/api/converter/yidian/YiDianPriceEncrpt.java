package com.juan.adx.api.converter.yidian;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

public class YiDianPriceEncrpt {

    public static void main(String[] args) {
        String secretKey = "jlxdRDzI0w7YYkSs3YI0fwaDalFUGPf5";
        String base64 = "9qEMOKphlFuni7RX3sLYez0ImfaZb61rCvBNGw8jd5JA2VPTtcS-WQDoH4ykUg_x";
//        String price = "id={}&impid={}&adid={}&crid={}&price={}&t={}&ck={}";
        String price = "id=158d51d707-46a0-8243-a4f019283ddc&adid=600259689&crid=600287246&price=30&t=1569486836630";
        String hMac = HmacSha1(price, secretKey);
        System.out.println("ck: " + hMac);
        price += "&ck=" + hMac;
        String priceEncode = Base64(price, base64);
        System.out.println("priceEncode: " + priceEncode);

    }

    public static String encryptWinPrice(String encryptText, String secretKey, String base64) {
        String hMac = HmacSha1(encryptText, secretKey);
        encryptText += "&ck=" + hMac;
        return Base64(encryptText, base64);
    }

    public static String HmacSha1(String encryptText, String encryptKey){
        try {
            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKey secretKey = new SecretKeySpec(encryptKey.getBytes(), "HmacSHA1");
            //生成一个指定 Mac 算法 的 Mac 对象
            Mac mac = Mac.getInstance("HmacSHA1");
            //用给定密钥初始化 Mac 对象
            mac.init(secretKey);

            byte[] text = encryptText.getBytes();
            //完成 Mac 操作
            byte[] bytes = mac.doFinal(text);
            return byte2String(bytes);
        } catch (Exception e) {
            System.out.println("error is "+e);
            return null;
        }
    }

    private static String byte2String(byte[] bytes) {
        StringBuilder sb = new StringBuilder(40);
        for (byte x : bytes) {
            if ((x & 0xff) >> 4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }



    public static String Base64(String text, String key){

        String encodedText = null;
        try {
            final byte[] textByte = text.getBytes("UTF8");
            char[] toBase64 = key.toCharArray();
            encodedText = new String(encode(textByte, toBase64), "UTF8");

        } catch (Exception e) {
            System.out.println("error is "+e);
        }
        return encodedText;
    }

    public static byte[] encode(byte[] src, char[] toBase64){
        int len = outLength(src.length);          // dst array size
        byte[] dst = new byte[len];
        int ret = encode0(src, 0, src.length, dst, toBase64);
        if (ret != dst.length)
            return Arrays.copyOf(dst, ret);
        return dst;
    }

    private static final int outLength(int srclen) {
        int len = 0;

        int n = srclen % 3;
        len = 4 * (srclen / 3) + (n == 0 ? 0 : n + 1);
        return len;
    }

    private static int encode0(byte[] src, int off, int end, byte[] dst, char[] toBase64) {
        char[] base64 = toBase64;
        int sp = off;
        int slen = (end - off) / 3 * 3;
        int sl = off + slen;
        int dp = 0;
        while (sp < sl) {
            int sl0 = Math.min(sp + slen, sl);
            for (int sp0 = sp, dp0 = dp; sp0 < sl0; ) {
                int bits = (src[sp0++] & 0xff) << 16 |
                        (src[sp0++] & 0xff) << 8 |
                        (src[sp0++] & 0xff);
                dst[dp0++] = (byte) base64[(bits >>> 18) & 0x3f];
                dst[dp0++] = (byte) base64[(bits >>> 12) & 0x3f];
                dst[dp0++] = (byte) base64[(bits >>> 6) & 0x3f];
                dst[dp0++] = (byte) base64[bits & 0x3f];
            }
            int dlen = (sl0 - sp) / 3 * 4;
            dp += dlen;
            sp = sl0;

        }
        if (sp < end) {               // 1 or 2 leftover bytes
            int b0 = src[sp++] & 0xff;
            dst[dp++] = (byte) base64[b0 >> 2];
            if (sp == end) {
                dst[dp++] = (byte) base64[(b0 << 4) & 0x3f];
            } else {
                int b1 = src[sp++] & 0xff;
                dst[dp++] = (byte) base64[(b0 << 4) & 0x3f | (b1 >> 4)];
                dst[dp++] = (byte) base64[(b1 << 2) & 0x3f];

            }
        }
        return dp;
    }
}