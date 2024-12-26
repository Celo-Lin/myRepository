package com.juan.adx.common.alg;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.CRC32;

/**
 * @author XuDong
 * @Description: Hash算法 推荐使用FNV1算法
 *
 */
public class HashAlgorithmsUtil {
	/**
	 * 加法hash
	 * 
	 * @param key
	 *            字符串
	 * @param prime
	 *            一个质数
	 * @return hash结果
	 */
	public static int additiveHash(String key, int prime) {
		int hash, i;
		for (hash = key.length(), i = 0; i < key.length(); i++)
			hash += key.charAt(i);
		return (hash % prime);
	}

	/**
	 * 旋转hash
	 * 
	 * @param key
	 *            输入字符串
	 * @param prime
	 *            质数
	 * @return hash值
	 */
	public static int rotatingHash(String key, int prime) {
		int hash, i;
		for (hash = key.length(), i = 0; i < key.length(); ++i)
			hash = (hash << 4) ^ (hash >> 28) ^ key.charAt(i);
		return (hash % prime);
		// return (hash ^ (hash>>10) ^ (hash>>20));
	}

	// 替代：
	// 使用：hash = (hash ^ (hash>>10) ^ (hash>>20)) & mask;
	// 替代：hash %= prime;

	/**
	 * MASK值，随便找一个值，最好是质数
	 */
	static int M_MASK = 0x8765fed1;

	/**
	 * 一次一个hash
	 * @param key  输入字符串
	 * @return 输出hash值
	 */
	public static int oneByOneHash(String key) {
		int hash, i;
		for (hash = 0, i = 0; i < key.length(); ++i) {
			hash += key.charAt(i);
			hash += (hash << 10);
			hash ^= (hash >> 6);
		}
		hash += (hash << 3);
		hash ^= (hash >> 11);
		hash += (hash << 15);
		// return (hash & M_MASK);
		return hash;
	}

	// 32位FNV算法
	static int M_SHIFT = 0;

	/**
	 * 32位的FNV算法
	 * 
	 * @param data
	 *            数组
	 * @return int值
	 */
	public static int FNVHash(byte[] data) {
		int hash = (int) 2166136261L;
		for (byte b : data)
			hash = (hash * 16777619) ^ b;
		if (M_SHIFT == 0)
			return hash;
		return (hash ^ (hash >> M_SHIFT)) & M_MASK;
	}

	/**
	 * 改进的32位FNV算法1
	 * 
	 * @param data
	 *            数组
	 * @return int值
	 */
	public static int FNVHash1(byte[] data) {
		final int p = 16777619;
		int hash = (int) 2166136261L;
		for (byte b : data)
			hash = (hash ^ b) * p;
		hash += hash << 13;
		hash ^= hash >> 7;
		hash += hash << 3;
		hash ^= hash >> 17;
		hash += hash << 5;
		return hash;
	}

	/**
	 * 改进的32位FNV算法1
	 * 
	 * @param data
	 *            字符串
	 * @return int值
	 */
	public static int FNVHash1(String data) {
		final int p = 16777619;
		int hash = (int) 2166136261L;
		for (int i = 0; i < data.length(); i++)
			hash = (hash ^ data.charAt(i)) * p;
		hash += hash << 13;
		hash ^= hash >> 7;
		hash += hash << 3;
		hash ^= hash >> 17;
		hash += hash << 5;
		return hash;
	}
	
	/**
	 * 32位CRC算法
	 * @param key
	 * @return
	 */
	public static int crc32Hash( String key ) {
        CRC32 checksum = new CRC32();
        checksum.update( key.getBytes() );
        int crc = (int) checksum.getValue();

        return (crc >> 16) & 0x7fff;
    }
	
	/**
	 * 32位murmur算法
	 * @param key
	 * @return
	 */
	public static Long murmur32Hash(String key) {

		ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
		int seed = 0x9747b28c;

		ByteOrder byteOrder = buf.order();
		buf.order(ByteOrder.LITTLE_ENDIAN);

		long m = 0x5bd1e995;
		int r = 47;

		long h = seed ^ (buf.remaining() * m);

		long k;
		while (buf.remaining() >= 8) {
			k = buf.getLong();

			k *= m;
			k ^= k >>> r;
			k *= m;

			h ^= k;
			h *= m;
		}

		if (buf.remaining() > 0) {
			ByteBuffer finish = ByteBuffer.allocate(8).order(
					ByteOrder.LITTLE_ENDIAN);
			// for big-endian version, do this first:
			// finish.position(8-buf.remaining());
			finish.put(buf).rewind();
			h ^= finish.getLong();
			h *= m;
		}

		h ^= h >>> r;
		h *= m;
		h ^= h >>> r;

		buf.order(byteOrder);
		return h;
	}
	
	/**
	 * 64位murmur算法
	 * @param key
	 * @return
	 */
	public static Long murmur64Hash(String key) {

		ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
		int seed = 0x1234ABCD;

		ByteOrder byteOrder = buf.order();
		buf.order(ByteOrder.LITTLE_ENDIAN);

		long m = 0xc6a4a7935bd1e995L;
		int r = 47;

		long h = seed ^ (buf.remaining() * m);

		long k;
		while (buf.remaining() >= 8) {
			k = buf.getLong();

			k *= m;
			k ^= k >>> r;
			k *= m;

			h ^= k;
			h *= m;
		}

		if (buf.remaining() > 0) {
			ByteBuffer finish = ByteBuffer.allocate(8).order(
					ByteOrder.LITTLE_ENDIAN);
			// for big-endian version, do this first:
			// finish.position(8-buf.remaining());
			finish.put(buf).rewind();
			h ^= finish.getLong();
			h *= m;
		}

		h ^= h >>> r;
		h *= m;
		h ^= h >>> r;

		buf.order(byteOrder);
		return h;
	}
	
	/**
	 * JAVA自己带的算法
	 */
	public static int javaHash(String str) {
		int h = 0;
		int off = 0;
		int len = str.length();
		for (int i = 0; i < len; i++) {
			h = 31 * h + str.charAt(off++);
		}
		return h;
	}

	/**
	 * 混合hash算法，输出64位的值
	 */
	public static long mixHash(String str) {
		long hash = str.hashCode();
		hash <<= 32;
		hash |= FNVHash1(str);
		return hash;
	}
	
}
