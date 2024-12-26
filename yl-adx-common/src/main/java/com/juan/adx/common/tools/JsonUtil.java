package com.juan.adx.common.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 曹飞 on 2019/3/5
 */
public class JsonUtil {

    public static String toJsonString(Object object) {
        return toJsonString(object, null);
    }

    public static String toJsonString(Object object, SerializerFeature... features) {
        if (features == null) {
            return JSON.toJSONString(object);
        }
        return JSON.toJSONString(object, features);
    }

    public static <T> T parseObject(String text, Class<T> klass)
    {
        if (StringUtils.isEmpty(text)) {
            return null;
        }

        T t = null;
        try {
            t = JSON.parseObject(text, klass);
        } catch (Exception e) {
            try {
                text = resetJsonStr(text);
                t = JSON.parseObject(text, klass);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return t;
    }

    public static <T> T parseObject(String text, TypeReference<T> type) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }

        T t = null;
        try {
            t = JSON.parseObject(text, type);
        } catch (Exception e) {
            try {
                text = resetJsonStr(text);
                t = JSON.parseObject(text, type);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return t;
    }

    public static <T> T parseObject(Object object, Class<T> klass)
    {
        return parseObject(object, klass, null);
    }

    public static <T> T parseObject(Object object, Class<T> klass, SerializerFeature... features)
    {
        if (object == null) return null;

        String jsonText = null;
        if (features == null) {
            jsonText = toJsonString(object);
        } else {
            jsonText = toJsonString(object, features);
        }
        return JsonUtil.parseObject(jsonText, klass);
    }

    public static <T> List<T> parseArray(Object object, Class<T> klass) {
        if (object == null) return null;

        return JsonUtil.parseArray(toJsonString(object), klass);
    }

    public static <T> List<T> parseArray(String text, Class<T> klass) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }

        List<T> t = null;
        try {
            t = JSON.parseArray(text, klass);
        } catch (Exception e) {
            try {
                text = resetJsonStr(text);
                t = JSON.parseArray(text, klass);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return t;
    }

    private static String resetJsonStr(String text)
    {
        Map<String, Object> jsonMap = new HashMap<>();
        text = text.replace("{", "")
                .replace("}", "")
                .replace("\"", "");

        String[] keyvalues = text.split(",");
        for (String str : keyvalues) {
            String[] keyvalue = str.split(":");
            if (keyvalue.length > 1) {
                jsonMap.put(keyvalue[0], keyvalue[1]);
            }
        }

        text = JsonUtil.toJsonString(jsonMap);
        return text;
    }

}
