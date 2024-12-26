package com.juan.adx.common.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.juan.adx.common.alg.MD5Util;

public class SignUtils {
	
	/**
	 * 签名的时候不携带的参数
	 */
	private static final List<String> NO_SIGN_PARAMS = Lists.newArrayList("sign");

	
	public static boolean signVerify(String appSecret,Map<String, String> params){
		Map<String, String> map=new HashMap<String, String>();
		map.put("appSecret", appSecret);
		
		for(String key:params.keySet()){
			if(!key.equals("sign")){
				map.put(key, params.get(key));
			}
		}
		
		String sign=sign(map);
		if(sign.equals(params.get("sign"))){
			return true;
		}
		return false;
	}
	
	private static String toHexValue(byte[] messageDigest) {
		if (messageDigest == null)
			return "";
		StringBuilder hexValue = new StringBuilder();
		for (byte aMessageDigest : messageDigest) {
			int val = 0xFF & aMessageDigest;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * @param params        参数信息
	 * @param ignoredParams 签名时需要忽略的特殊参数
	 * @return 签名字符串 string
	 */
	public static String createSign(Map<String, String> params, String signKey, String[] ignoredParams) {
		StringBuilder toSign = new StringBuilder();
		for (String key : new TreeMap<>(params).keySet()) {
			String value = params.get(key);
			boolean shouldSign = false;
			if (StringUtils.isNotEmpty(value) && !ArrayUtils.contains(ignoredParams, key)
					&& !NO_SIGN_PARAMS.contains(key)) {
				shouldSign = true;
			}

			if (shouldSign) {
				toSign.append(key).append("=").append(value).append("&");
			}
		}
		return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
	}
	

	/**
	 * @param params        参数信息
	 * @param signType      签名类型，如果为空，则默认为MD5
	 * @param ignoredParams 签名时需要忽略的特殊参数
	 * @return 签名字符串 string
	 */
	public static String createMD5Sign(Map<String, String> params, String[] ignoredParams) {
		StringBuilder toSign = new StringBuilder();
		for (String key : new TreeMap<>(params).keySet()) {
			String value = params.get(key);
			boolean shouldSign = false;
			if (StringUtils.isNotEmpty(value) && !ArrayUtils.contains(ignoredParams, key)
					&& !NO_SIGN_PARAMS.contains(key)) {
				shouldSign = true;
			}

			if (shouldSign) {
				toSign.append(key).append("=").append(value).append("&");
			}
		}
		return DigestUtils.md5Hex(toSign.toString());
	}	
	
	public static String sign(Map<String,String> params){
		List<String> keys=new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String string="";
		for(String s:keys){
			string+=params.get(s);
		}
		String sign="";
		try {
			sign = toHexValue(encryptMD5(string.getBytes(Charset.forName("utf-8"))));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("md5 error");
		}
		return sign;
	}
	
	public static String sign(Map<String, String> params, String token) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuilder builder = new StringBuilder();
		for (String key : keys) {
			builder.append(key).append("=").append(params.get(key));
		}
		builder.append(token);
		String sign = DigestUtils.md5Hex(builder.toString().getBytes(StandardCharsets.UTF_8));
		return sign;
	}
	
	
	private static byte[] encryptMD5(byte[] data)throws Exception{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(data);
		return md5.digest();
	}
	
	public static String apiSign(Map<String, Object> parameter, String signKey) {

		if (null == parameter || parameter.isEmpty()) {
			return "";
		}

		List<String> sortCache = new ArrayList<String>();
		for (Map.Entry<String, Object> en : parameter.entrySet()) {
			if(!en.getKey().equals("sign") && en.getValue() != null){
				sortCache.add(en.getKey() + "=" + en.getValue());
			}
		}

		Collections.sort(sortCache);

		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String p : sortCache) {
			if (first) {
				first = false;
			} else {
				sb.append('&');
			}
			sb.append(p);
		}

		sb.append(':').append(signKey);

		return MD5Util.getMD5String(sb.toString());
	}
	
	public static void main(String[] args) {
		String appKey="1299ef5935e0166f9f1349291a7eaaff";
		
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("phoneNumber", "18665391804");
		params.put("passwd", "123456");
		params.put("smsCode", "245761");
		params.put("seed", "1537860982216JexWlO");
		
		System.out.println(apiSign(params, appKey));
		
		System.out.println(MD5Util.getMD5String("cityId=110100&gender=2&nickName=比较&provincesId=110000:1299ef5935e0166f9f1349291a7eaaff"));
		
		
	}
}
