package com.juan.adx.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class GZipUtil {

	public static byte[] compress( String data ) throws IOException {
		if( null == data || 0 == data.length() ) {
			return null;
		}
		ByteArrayOutputStream bos = null;
		GZIPOutputStream gos = null;
		try {
			byte[] buf = data.getBytes( StandardCharsets.UTF_8.name() );
			bos = new ByteArrayOutputStream( buf.length );
			gos = new GZIPOutputStream( bos );
			gos.write( buf );
			gos.finish();
			gos.flush();
			bos.flush();
			return bos.toByteArray();
		} finally {
			IOUtils.closeQuietly(gos);
			IOUtils.closeQuietly(bos);
		}
	}

	public static String uncompress( byte[] data ) throws IOException {
		if( null == data ) {
			return "";
		}
		GZIPInputStream gis = null;
		ByteArrayOutputStream bos = null;
		try {
			gis = new GZIPInputStream( new ByteArrayInputStream( data ) );
			bos = new ByteArrayOutputStream( data.length );
			int n = -1;
			byte[] buf = new byte[ 2048 ];
			while( -1 != ( n = gis.read( buf ) ) ) {
				bos.write( buf, 0, n );
			}
			return bos.toString( StandardCharsets.UTF_8.name() );
		} finally {
			if( null != bos ) {
				bos.flush();
				bos.close();
			}
			IOUtils.closeQuietly(gis);
		}
	}

	public static String uncompress( byte[] data, int offset, int length ) throws IOException {
		if( null == data ) {
			return "";
		}
		GZIPInputStream gis = null;
		ByteArrayOutputStream bos = null;
		try {
			gis = new GZIPInputStream( new ByteArrayInputStream( data, offset, length ) );
			bos = new ByteArrayOutputStream( length );
			int n = -1;
			byte[] buf = new byte[ 2048 ];
			while( -1 != ( n = gis.read( buf ) ) ) {
				bos.write( buf, 0, n );
			}
			return bos.toString( StandardCharsets.UTF_8.name() );
		} finally {
			if( null != bos ) {
				bos.flush();
				bos.close();
			}
			IOUtils.closeQuietly(gis);
		}
	}
	

	
	/**
     * 将文本内容压缩并写入文件中
     * @param text		要压缩的文本内容
     * @param filePath	要写入的文件路径
     * @throws IOException	如果写入文件失败或压缩文件失败，则抛出IOException
     */
    public static void compressAndWriteToFile(String text, String filePath) throws IOException {
    	if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException("Text to compress cannot be empty");
        }
        if (StringUtils.isBlank(filePath)) {
            throw new IllegalArgumentException("File path cannot be empty");
        }
        File file = new File(filePath);
        // 如果文件路径不存在，创建父目录
        FileUtils.forceMkdir(file.getParentFile());
        //使用了Java 7的Try-With-Resources结构，它们会在块退出时自动关闭。这确保了资源的正确释放，无论是否发生异常。这是一种安全且推荐的做法，以避免资源泄漏。
        try (OutputStream fileOutputStream = new FileOutputStream(filePath);
        		BufferedOutputStream bufferOutput = new BufferedOutputStream(fileOutputStream);
        		OutputStream gzipOutputStream = new GZIPOutputStream(bufferOutput)) {
            // 将文本内容写入Gzip输出流
        	byte[] data = text.getBytes(StandardCharsets.UTF_8);
            gzipOutputStream.write(data);
        }
    }

    /**
     * 读取文件中的内容并解压
     * @param filePath	要读取的文件路径
     * @return			返回解压后的文本
     * @throws IOException	如果读取文件失败或压解文件失败，则抛出IOException
     */
    public static String uncompressAndReadFromFile(String filePath) throws IOException {
        try (InputStream fileInputStream = new FileInputStream(filePath);
        		BufferedInputStream bufferInput = new BufferedInputStream(fileInputStream);
        		InputStream gzipInputStream = new GZIPInputStream(bufferInput)) {
            // 从Gzip输入流读取文本内容
            byte[] bytes = IOUtils.toByteArray(gzipInputStream);
            return new String(bytes);
        }
    }
    

	public static void main( String[] args ) throws IOException {
//		String data = "这厢小米双11刚用599的价格血洗了低端智能手机市场，那边酷派已经摩拳擦掌与小米对决。来自媒体的消息称，酷派大神内部传出的消息称，"
//				+ "大神4G手机价格将下探到499元。显然，大神的矛头指向了老对手小米。在品牌一拆为三后，大神作为酷派的互联网品牌，是针对小米的一个全新子品牌。在大神刚刚独立后，与小米一役不可避免。"
//				+ "从策略上来看，酷派试图用价格来压制小米，殊不知，用低价屠城本身就是一个两败俱伤的昏招。";
		String data = "{\"app\":{\"appId\":\"100007\",\"appVersion\":\"1.0.0\",\"name\":\"soul\",\"pkgName\":\"cn.soulapp.android\"},\"deal\":{\"bidfloor\":100,\"chargetype\":1},\"device\":{\"bootMark\":\"3c57c290-bcd0-4d05-9d68-77310f675d75\",\"brand\":\"OPPO\",\"cpuNum\":0,\"density\":3,\"deviceHeight\":2400,\"deviceName\":\"\",\"deviceNameMd5\":\"\",\"deviceType\":1,\"deviceWidth\":1080,\"dpi\":0,\"language\":\"zh-CN\",\"make\":\"OPPO\",\"model\":\"PEQM00\",\"osApiLevel\":30,\"osType\":1,\"osUpdateTime\":\"1262304017.896000000\",\"osVersion\":\"11.0\",\"ppi\":409,\"screenOrient\":0,\"screenSize\":\"27.41\",\"supportDeeplink\":1,\"supportUniversal\":0,\"sysDiskSize\":\"0\",\"sysMemory\":\"0\",\"updateMark\":\"1262304017.896000000\"},\"deviceCaid\":{},\"deviceId\":{\"androidId\":\"45ef951247128d1d\",\"androidIdMd5\":\"12a234c6d8d2eff00cb4750cc1d07536\",\"idfa\":\"\",\"imei\":\"\",\"oaid\":\"EF5B0BED81294A51B346E77B17485221e9d8b6ff4b901884e0cb43c7f9a5e317\",\"oaidMd5\":\"bed8eb87c830503ac89ab561c2a1b3dc\"},\"geo\":{\"latitude\":0,\"longitude\":0,\"timestamp\":0},\"network\":{\"carrier\":3,\"connectionType\":1,\"coordinateType\":0,\"country\":\"CN\",\"ip\":\"106.35.10.222\",\"mac\":\"0ad762a59289\",\"ua\":\"Mozilla/5.0 (Linux; Android 11; PEQM00 Build/RP1A.200720.011; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/83.0.4103.106 Mobile Safari/537.36\"},\"slot\":{\"creativeType\":0,\"height\":1280,\"slotId\":10000601,\"slotType\":2,\"width\":720}}";
		
		compressAndWriteToFile(data, "F:\\temp\\ad.text");
		
		String json = uncompressAndReadFromFile("F:\\temp\\ad.text");
		
		System.out.println("json ================= " + json);
		
		System.out.println( "原长度：" + data.getBytes( StandardCharsets.UTF_8.name() ).length );
		byte[] r = GZipUtil.compress( data );
		System.out.println( "压缩后：" + r.length );
		System.out.println( "压缩后数据：" + new String(r) );
		System.out.println( GZipUtil.uncompress( r ) );
	}

}