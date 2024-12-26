package com.juan.adx.common.utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncodeUtils {

	public static String decodeUnicode(String str) {
	    Charset set = Charset.forName("UTF-16");
	    Pattern p = Pattern.compile("\\\\u([0-9a-fA-F]{4})");
	    Matcher m = p.matcher(str);
	    int start = 0;
	    int start2 = 0;
	    StringBuffer sb = new StringBuffer();
	    while (m.find(start)) {
	        start2 = m.start();
	        if (start2 > start) {
	            String seg = str.substring(start, start2);
	            sb.append(seg);
	        }
	        String code = m.group(1);
	        int i = Integer.valueOf(code, 16);
	        byte[] bb = new byte[4];
	        bb[0] = (byte) ((i >> 8) & 0xFF);
	        bb[1] = (byte) (i & 0xFF);
	        ByteBuffer b = ByteBuffer.wrap(bb);
	        sb.append(String.valueOf(set.decode(b)).trim());
	        start = m.end();
	    }
	    start2 = str.length();
	    if (start2 > start) {
	        String seg = str.substring(start, start2);
	        sb.append(seg);
	    }
	    return sb.toString();
	}
	
	public static String stringToUnicode(String str) {
	    str = (str == null ? "" : str);
	    String tmp;
	    StringBuffer sb = new StringBuffer(1000);
	    char c;
	    int i, j;
	    sb.setLength(0);
	    for (i = 0; i < str.length(); i++) {
	        c = str.charAt(i);
	        sb.append("\\u");
	        j = (c >>> 8); // 取出高8位
	        tmp = Integer.toHexString(j);
	        if (tmp.length() == 1)
	            sb.append("0");
	        sb.append(tmp);
	        j = (c & 0xFF); // 取出低8位
	        tmp = Integer.toHexString(j);
	        if (tmp.length() == 1)
	            sb.append("0");
	        sb.append(tmp);

	    }
	    return (new String(sb));
	}
}
