package com.juan.adx.api.interceptor;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompressionInterceptor implements HandlerInterceptor {
	
	private final static String HEADER_ENCODING_VALUE = "gzip";
	
	/**
	 * 在请求到达控制器前执行解压缩逻辑
	 */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
    	// 检查请求头中是否包含"Content-Encoding: gzip"标头，以确定请求体是否已被压缩
    	String contentType = request.getContentType();
        String contentEncoding = request.getHeader(HttpHeaders.ACCEPT_ENCODING);
        
        if (StringUtils.equalsIgnoreCase(contentEncoding, HEADER_ENCODING_VALUE) 
        		&& StringUtils.equalsIgnoreCase(MediaType.APPLICATION_CBOR_VALUE, contentType)) {
        	
//        if (StringUtils.equalsIgnoreCase(contentEncoding, HEADER_ENCODING_VALUE)) {
        	GZIPInputStream gzipInputStream = null;
        	BufferedReader bufferedReader = null;
        	try {
        		// 获取HTTP请求的输入流
        		InputStream inputStream = request.getInputStream();
        		// 创建GZIP解压缩输入流
                gzipInputStream = new GZIPInputStream(inputStream);
                
                // 读取解压缩后的数据
                bufferedReader = new BufferedReader(new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8));
                String line;
                StringBuilder decompressedData = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    decompressedData.append(line);
                }
                // 将解压后的数据放回输入流
                byte[] decompressedBytes = decompressedData.toString().getBytes(StandardCharsets.UTF_8);
                log.info("decompression, bode: {}", new String(decompressedBytes));
                request.getInputStream().read(decompressedBytes);
			} finally {
				IOUtils.closeQuietly(bufferedReader);
				IOUtils.closeQuietly(gzipInputStream);
			}
        }
        // 返回 true 表示继续处理请求
        return true;
        
    }

    /**
     * Controller执行完毕，在控制器处理后，渲染视图前执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	//todo something...
    }

    /**
     * 请求结束，在整个请求完成后执行
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    	//todo something...
    }
    
}
