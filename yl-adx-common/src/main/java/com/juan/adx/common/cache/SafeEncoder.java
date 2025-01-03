package com.juan.adx.common.cache;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;

public class SafeEncoder {
    public static byte[][] encodeMany(final String... strs) {
        if (strs == null) {
            return null;
        }
        byte[][] many = new byte[strs.length][];
        for (int i = 0; i < strs.length; i++) {
            many[i] = encode(strs[i]);
        }
        return many;
    }

    public static String[] decodeMany(final byte[]... data) {
        if (data == null) {
            return null;
        }
        String[] many = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            many[i] = decode(data[i]);
        }
        return many;
    }

    public static Map<byte[], byte[]> encodeMap(final Map<String, String> map) {
        Map<byte[], byte[]> byteMap = new LinkedHashMap<byte[], byte[]>();
        if (map != null) {
            Iterator<Entry<String, String>> entryIt = map.entrySet().iterator();
            while (entryIt.hasNext()) {
                Entry<String, String> e = entryIt.next();
                byteMap.put(encode(e.getKey()), encode(e.getValue()));
            }
        }
        return byteMap;
    }

    public static Map<byte[], Double> encodeMapWithDouble(final Map<String, Double> map) {
        Map<byte[], Double> byteMap = new LinkedHashMap<byte[], Double>();
        if (map != null) {
            Iterator<Entry<String, Double>> entryIt = map.entrySet().iterator();
            while (entryIt.hasNext()) {
                Entry<String, Double> e = entryIt.next();
                byteMap.put(encode(e.getKey()), e.getValue());
            }
        }
        return byteMap;
    }

    public static Map<String, String> decodeByteMap(final Map<byte[], byte[]> bytemap) {
        Map<String, String> stringMap = new LinkedHashMap<String, String>();
        if (bytemap != null) {
            Iterator<Entry<byte[], byte[]>> entryIt = bytemap.entrySet().iterator();
            while (entryIt.hasNext()) {
                Entry<byte[], byte[]> e = entryIt.next();
                stringMap.put(decode(e.getKey()), decode(e.getValue()));
            }
        }
        return stringMap;
    }

    public static List<String> decodeList(final List<byte[]> bytelist) {
        if (bytelist == null) {
            return null;
        }
        List<String> ret = new ArrayList<String>(bytelist.size());
        for (byte[] b : bytelist) {
            ret.add(decode(b));
        }
        return ret;
    }

    public static List<byte[]> encodeListToByte(final List<String> list) {
        if (list == null) {
            return null;
        }
        List<byte[]> ret = new ArrayList<byte[]>(list.size());
        for (String b : list) {
            ret.add(encode(b));
        }
        return ret;
    }

    public static Set<String> decodeSet(final Set<byte[]> byteset) {
        Set<String> ret = new LinkedHashSet<String>();
        if (byteset != null) {
            for (byte[] b : byteset) {
                ret.add(decode(b));
            }
        }
        return ret;
    }

    public static byte[] encode(final String str) {
        try {
            if (str == null) {
                throw new JedisDataException("value sent to redis cannot be null");
            }
            return str.getBytes(Protocol.CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new JedisException(e);
        }
    }

    public static String decode(final byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            return new String(data, Protocol.CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new JedisException(e);
        }
    }

    public static byte[] encode(final Integer data) {
        return encode(String.valueOf(data));
    }
}