package com.juan.adx.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UrlUtil {

	/**
	 * 将每个键值对拼接成请求参数字符串
	 * @param params
	 * @return
	 */
    public static String mapToQueryString(Map<String, String> params) {
        StringBuilder queryString = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            try {
                String key = URLEncoder.encode(entry.getKey(), "UTF-8");
                String value = URLEncoder.encode(entry.getValue(), "UTF-8");
                if (queryString.length() > 0) {
                    queryString.append("&");
                }
                queryString.append(key).append("=").append(value);
            } catch (UnsupportedEncodingException e) {
                // Handle the exception appropriately
            	log.error("不支持的字符编码，异常堆栈轨迹如下：", e);
            }
        }
        return queryString.toString();
    }
    
    /**
     * 按照 ASCII 码自然排序将 Map 中的参数键排序，然后将每个键值对拼接成请求参数字符串
     * @param params
     * @return
     */
	public static String mapToSortedQueryString(Map<String, String> params) {
        TreeMap<String, String> sortedParams = new TreeMap<>(params);

        StringBuilder queryString = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            if (queryString.length() > 0) {
                queryString.append("&");
            }
            queryString.append(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }
        return queryString.toString();
    }
    
    public static Map<String, String> parseURLParameters(String url) {
        Map<String, String> paramMap = new HashMap<>();
		try {
			URI uri = new URI(url);
			List<NameValuePair> params = URLEncodedUtils.parse(uri, StandardCharsets.UTF_8);
			
			for (NameValuePair param : params) {
				paramMap.put(param.getName(), param.getValue());
			}
		} catch (URISyntaxException e) {
			log.error("解析URI不符合规范，url = " + url + " 异常堆栈轨迹如下：", e);
		}
        return paramMap;
    }
    
}
