package com.juan.adx.api.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DecompressFilter extends OncePerRequestFilter {

	private final static String ENCODING_GZIP = "gzip";
	private final static String ENCODING_DEFLATE = "deflate";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String contentEncoding = request.getHeader(HttpHeaders.CONTENT_ENCODING);

		if (StringUtils.equalsIgnoreCase(contentEncoding, ENCODING_GZIP) ) {
			
			PushbackInputStream pushbackInputStream = new PushbackInputStream(request.getInputStream(), 2);
	        byte[] header = new byte[2];
	        pushbackInputStream.read(header);
	        boolean isGzipped = header[0] == (byte) GZIPInputStream.GZIP_MAGIC && header[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8);
	        // 为了确保后续的读取操作将从流的开头开始,使用 unread 方法将数据推回流，以便后续从流的开头开始读取。
	        pushbackInputStream.unread(header);
	        
            if(isGzipped) {
	            // 数据包是GZIP格式
	        	GZIPInputStream gzippedInputStream = new GZIPInputStream(pushbackInputStream);
	            HttpServletRequest wrappedRequest = new GzipDecompressWrapper(request, gzippedInputStream);
	            chain.doFilter(wrappedRequest, response);
	        } else {
	            // 数据包不是GZIP格式，恢复原始输入流
	            HttpServletRequest wrappedRequest = new NoneDecompressWrapper(request, pushbackInputStream);
	            chain.doFilter(wrappedRequest, response);
	        }
            
		} else if (StringUtils.equalsIgnoreCase(contentEncoding, ENCODING_DEFLATE) ) {
			DeflaterInputStream deflaterInputStream = new DeflaterInputStream(request.getInputStream());
			HttpServletRequest wrappedRequest = new DeflaterDecompressWrapper(request, deflaterInputStream);
			chain.doFilter(wrappedRequest, response);
		} else {
			chain.doFilter(request, response);
		}
	}
	
	
	private static class NoneDecompressWrapper extends HttpServletRequestWrapper {
		
		private final PushbackInputStream pushbackInputStream;
		
		public NoneDecompressWrapper(HttpServletRequest request, PushbackInputStream pushbackInputStream) {
			super(request);
			this.pushbackInputStream = pushbackInputStream;
		}
		
		@Override
		public ServletInputStream getInputStream() throws IOException {
			return new CustomServletInputStream(this.pushbackInputStream);
		}
		
		@Override
		public BufferedReader getReader() throws IOException {
			return new BufferedReader(new InputStreamReader(this.pushbackInputStream));
		}
	}

	private static class DeflaterDecompressWrapper extends HttpServletRequestWrapper {

		private final DeflaterInputStream deflaterInputStream;

		public DeflaterDecompressWrapper(HttpServletRequest request, DeflaterInputStream deflaterInputStream) {
			super(request);
			this.deflaterInputStream = deflaterInputStream;
		}

		@Override
		public ServletInputStream getInputStream() throws IOException {
			return new CustomServletInputStream(this.deflaterInputStream);
		}

		@Override
		public BufferedReader getReader() throws IOException {
			return new BufferedReader(new InputStreamReader(this.deflaterInputStream));
		}
	}
	
	private static class GzipDecompressWrapper extends HttpServletRequestWrapper {

		private final GZIPInputStream gzipInputStream;

		public GzipDecompressWrapper(HttpServletRequest request, GZIPInputStream gzipInputStream) {
			super(request);
			this.gzipInputStream = gzipInputStream;
		}

		@Override
		public ServletInputStream getInputStream() throws IOException {
			return new CustomServletInputStream(this.gzipInputStream);
		}

		@Override
		public BufferedReader getReader() throws IOException {
			return new BufferedReader(new InputStreamReader(this.gzipInputStream));
		}
	}
	
	private static class CustomServletInputStream extends ServletInputStream {

		private final InputStream inputStream;

		public CustomServletInputStream(InputStream inputStream) {
			this.inputStream = inputStream;
		}

		@Override
		public int read() throws IOException {
			int len = inputStream.read();
			return len;
		}

		@Override
		public boolean isFinished() {
			try {
				return inputStream.available() == 0;
			} catch (IOException e) {
				return true;
			}
		}

		@Override
		public boolean isReady() {
			try {
                return inputStream.available() > 0;
            } catch (IOException e) {
                log.error("error", e);
                return false;
            }
		}

		@Override
		public void setReadListener(ReadListener listener) {
			// 不需要实现
		}
	}
}
