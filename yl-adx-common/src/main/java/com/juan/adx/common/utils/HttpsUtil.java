package com.juan.adx.common.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.google.common.collect.Maps;
import com.juan.adx.common.model.HttpResponseWrapper;
import com.juan.adx.model.ssp.common.protobuf.YlAdxDataReqConvert;
import com.juan.adx.model.ssp.common.request.*;
import com.yl.ad.protobuf.AdxDataProtobuf;
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.SSLInitializationException;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;


public class HttpsUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpsUtil.class);

    /**
     * 连接池
     */
    private static PoolingHttpClientConnectionManager connManager;

    /**
     * 出错返回结果
     */
    public static final String ERROR_RESULT = "-1";

    /**
     * 获取请求超时-毫秒
     */
    private static final int REQUEST_CONNECT_TIMEOUT = 10 * 1000;


    /**
     * 初始化连接池管理器,配置SSL
     */
    static {
        if (connManager == null) {

            try {
                // 创建ssl安全访问连接
                // 获取创建ssl上下文对象
                SSLContext sslContext = getSSLContext(true, null, null);

                // 注册
                Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.INSTANCE)
                        .register("https", new SSLConnectionSocketFactory(sslContext))
                        .build();

                // ssl注册到连接池
                connManager = new PoolingHttpClientConnectionManager(registry);
                connManager.setMaxTotal(10000);  // 连接池最大连接数
                connManager.setDefaultMaxPerRoute(500);  // 每个路由最大连接数

            } catch (SSLInitializationException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 获取客户端连接对象
     *
     * @param timeOut 超时时间
     * @return
     */
    private static CloseableHttpClient getHttpClient(Integer timeOut) {

        // 配置请求参数
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(timeOut)
                .setConnectTimeout(timeOut)
                .setSocketTimeout(timeOut)
//				.setContentCompressionEnabled(false)
                .build();

        // 配置超时回调机制
        HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= 3) {// 如果已经重试了3次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    return true;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {// ssl握手异常
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                return !(request instanceof HttpEntityEnclosingRequest);
            }
        };

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(retryHandler)
                .build();

        return httpClient;

    }

    /**
     * 获取SSL上下文对象,用来构建SSL Socket连接
     *
     * @param isDeceive 是否绕过SSL
     * @param creFile   整数文件,isDeceive为true 可传null
     * @param crePwd    整数密码,isDeceive为true 可传null, 空字符为没有密码
     * @return SSL上下文对象
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws CertificateException
     */
    private static SSLContext getSSLContext(boolean isDeceive, File creFile, String crePwd) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, FileNotFoundException, IOException {

        SSLContext sslContext = null;

        if (isDeceive) {
            /**
             * TLS版本有： SSLv2、 SSLv3、 TLSv1、 TLSv1.1、 TLSv1.2
             * SSLv2、SSLv3 绝大部分公司都已经禁用
             * TLSv1、TLSv1.1 少量公司在用，大部分公司开始陆续禁用
             * TLSv1.2(jdk8 default)
             * 用错协议版本会报如下异常：javax.net.ssl.SSLException: Received fatal alert: protocol_version
             */
            //sslContext = SSLContext.getInstance("SSLv3");
            sslContext = SSLContext.getInstance("TLSv1.2");
            // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
            X509TrustManager x509TrustManager = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
            };
            sslContext.init(null, new TrustManager[]{x509TrustManager}, null);
        } else {
            if (null != creFile && creFile.length() > 0) {
                if (null != crePwd) {
                    KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                    keyStore.load(new FileInputStream(creFile), crePwd.toCharArray());
                    sslContext = SSLContexts.custom().loadTrustMaterial(keyStore, new TrustSelfSignedStrategy()).build();
                } else {
                    throw new SSLHandshakeException("整数密码为空");
                }
            }
        }

        return sslContext;
    }

    /**
     * 设置post请求的请求头
     *
     * @param httpPost
     * @param headers
     */
    public static void setPostHeader(HttpPost httpPost, Map<String, String> headers) {
        // 添加请求头信息
        if (null == headers || headers.isEmpty()) {
            return;
        }
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 设置get请求的请求头
     *
     * @param httpGet
     * @param headers
     */
    public static void setGetHeader(HttpGet httpGet, Map<String, String> headers) {
        // 添加请求头信息
        if (null == headers || headers.isEmpty()) {
            return;
        }
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpGet.addHeader(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 设置post请求的参数
     *
     * @param httpPost
     * @param jsonStr
     */
    public static void setPostString(HttpPost httpPost, String jsonStr) {
        StringEntity entity = new StringEntity(jsonStr, StandardCharsets.UTF_8);
        httpPost.setEntity(entity);
    }

    /**
     * 设置post请求的参数
     *
     * @param httpPost
     * @param params
     */
    public static void setPostMap(HttpPost httpPost, Map<String, String> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keys = params.keySet();
        //Map转换成NameValuePair List集合
        for (String key : keys) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("请求参数不支持UTF-8编码.", e);
        }
    }

    /**
     * 设置post请求的参数
     *
     * @param httpPost
     * @param params
     */
    public static void setPostMapObj(HttpPost httpPost, Map<String, Object> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keys = params.keySet();
        //Map转换成NameValuePair List集合
        for (String key : keys) {
            String value = String.valueOf(params.get(key));
            nvps.add(new BasicNameValuePair(key, value));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("请求参数不支持UTF-8编码.", e);
        }
    }

    /**
     * 将请求实体设置为二进制数据。
     *
     * @param httpPost POST请求对象
     * @param data     要设置为实体的二进制数据
     */
    private static void setPostBinaryData(HttpPost httpPost, byte[] data) {
        ByteArrayEntity entity = new ByteArrayEntity(data);
        httpPost.setEntity(entity);
    }

    /**
     * Map转换成NameValuePair List集合
     *
     * @param params map
     * @return NameValuePair List集合
     */
    public static List<NameValuePair> covertParamsObjToNVPS(Map<String, Object> params) {
        List<NameValuePair> paramList = new LinkedList<NameValuePair>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            paramList.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
        }
        return paramList;
    }

    public static List<NameValuePair> covertParamsToNVPS(Map<String, String> params) {
        List<NameValuePair> paramList = new LinkedList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            paramList.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
        }
        return paramList;
    }


    /***************************************************** HTTP POST 请求分割线 ************************************************************/


    /**
     * 发起HTTP POST请求，支持SSL
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应信息
     */
    public static HttpResponseWrapper httpPost(String url, Map<String, String> params) {
        // 创建post请求
        HttpPost httpPost = new HttpPost(url);
        setPostMap(httpPost, params);
        HttpResponseWrapper result = getResult(httpPost, REQUEST_CONNECT_TIMEOUT, Boolean.FALSE);
        return result;
    }

    /**
     * 发起HTTP POST请求，支持SSL
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param timeOut 超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @return 响应信息
     */
    public static HttpResponseWrapper httpPost(String url, Map<String, String> params, int timeOut) {
        // 创建post请求
        HttpPost httpPost = new HttpPost(url);
        setPostMap(httpPost, params);
        HttpResponseWrapper result = getResult(httpPost, timeOut, Boolean.FALSE);
        return result;
    }


    /**
     * 发起HTTP POST请求，支持SSL
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param headers 请求头信息
     * @param timeOut 超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @return 响应信息
     */
    public static HttpResponseWrapper httpPost(String url, Map<String, String> params, Map<String, String> headers, int timeOut) {
        // 创建post请求
        HttpPost httpPost = new HttpPost(url);
        setPostHeader(httpPost, headers);
        setPostMap(httpPost, params);
        HttpResponseWrapper result = getResult(httpPost, timeOut, Boolean.FALSE);
        return result;
    }

    /**
     * 发起HTTP POST请求，支持SSL
     *
     * @param url      请求地址
     * @param headers  请求头信息
     * @param params   请求参数
     * @param timeOut  超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @param isStream 是否以流的方式获取响应信息
     * @return 响应信息
     */
    public static HttpResponseWrapper httpPost(String url, Map<String, String> params, Map<String, String> headers, int timeOut, boolean isStream) {
        // 创建post请求
        HttpPost httpPost = new HttpPost(url);
        setPostHeader(httpPost, headers);
        setPostMap(httpPost, params);
        HttpResponseWrapper result = getResult(httpPost, timeOut, isStream);
        return result;
    }

    public static HttpResponseWrapper httpPost(String url, String params) {
        // 创建post请求
        HttpPost httpPost = new HttpPost(url);
        setPostString(httpPost, params);
        HttpResponseWrapper result = getResult(httpPost, REQUEST_CONNECT_TIMEOUT, Boolean.FALSE);
        return result;
    }


    public static HttpResponseWrapper httpPost(String url, String params, Map<String, String> headers) {
        // 创建post请求
        HttpPost httpPost = new HttpPost(url);
        setPostHeader(httpPost, headers);
        setPostString(httpPost, params);
        HttpResponseWrapper result = getResult(httpPost, REQUEST_CONNECT_TIMEOUT, Boolean.FALSE);
        return result;
    }


    public static HttpResponseWrapper httpPost(String url, String params, Map<String, String> headers, int timeOut) {
        // 创建post请求
        HttpPost httpPost = new HttpPost(url);
        setPostHeader(httpPost, headers);
        setPostString(httpPost, params);
        HttpResponseWrapper result = getResult(httpPost, timeOut, Boolean.FALSE);
        return result;
    }


    public static HttpResponseWrapper httpPost(String url, String params, Map<String, String> headers, int timeOut, boolean isStream) {
        // 创建post请求
        HttpPost httpPost = new HttpPost(url);
        setPostHeader(httpPost, headers);
        setPostString(httpPost, params);
        HttpResponseWrapper result = getResult(httpPost, timeOut, isStream);
        return result;
    }

    /**
     * 发起HTTP POST请求，支持SSL
     *
     * @param url      请求地址
     * @param headers  请求头信息
     * @param params   请求参数
     * @param timeOut  超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @param isStream 是否以流的方式获取响应信息
     * @return 响应信息
     * @throws UnsupportedEncodingException
     */
    public static HttpResponseWrapper httpPost(String url, JSONObject headers, JSONObject params, Integer timeOut, boolean isStream) {

        Map<String, String> headersMap = JSON.parseObject(headers.toJSONString(), new TypeReference<Map<String, String>>() {
        });
        String paramsJson = params.toJSONString();
        // 创建post请求
        HttpPost httpPost = new HttpPost(url);
        setPostHeader(httpPost, headersMap);
        setPostString(httpPost, paramsJson);
        HttpResponseWrapper result = getResult(httpPost, timeOut, isStream);
        return result;
    }


    /**
     * 发起HTTP POST请求，支持SSL，并对请求参数进行GZIP压缩。
     *
     * @param url      请求地址
     * @param params   请求参数，将被压缩为GZIP格式
     * @param headers  请求头信息
     * @param timeOut  超时时间(毫秒): 从连接池获取连接的时间, 请求时间, 响应时间
     * @param isStream 是否以流的方式获取响应信息
     * @return 响应信息
     * @throws IOException 如果发生I/O错误
     */
    public static HttpResponseWrapper httpPostCompress(String url, String params, Map<String, String> headers, int timeOut, boolean isStream) throws IOException {
        // 创建POST请求
        HttpPost httpPost = new HttpPost(url);
        // 添加请求头信息
        setPostHeader(httpPost, headers);
        // 使用GZIP对请求参数进行压缩
        byte[] compressedParams = GZipUtil.compress(params);
        // 将压缩后的数据设置为请求实体
        setPostBinaryData(httpPost, compressedParams);
        HttpResponseWrapper result = getResult(httpPost, timeOut, isStream);
        return result;
    }

    public static HttpResponseWrapper httpPostCompress(String url, String params, Map<String, String> headers) throws IOException {
    	// 创建POST请求
    	HttpPost httpPost = new HttpPost(url);
    	// 添加请求头信息
    	setPostHeader(httpPost, headers);
    	// 使用GZIP对请求参数进行压缩
    	byte[] compressedParams = GZipUtil.compress(params);
    	// 将压缩后的数据设置为请求实体
    	setPostBinaryData(httpPost, compressedParams);
    	HttpResponseWrapper result = getResult(httpPost, REQUEST_CONNECT_TIMEOUT, Boolean.FALSE);
    	return result;
    }

	public static HttpResponseWrapper httpPostCompress(String url, String jsonParams,  Map<String, String> headers, int timeOut) throws Exception {
		URI uri = new URI(url);
		HttpPost httpPost = new HttpPost(uri);
		setPostHeader(httpPost, headers);
		// 使用GZIP对请求参数进行压缩
		byte[] compressedParams = GZipUtil.compress(jsonParams);
		//将压缩后的数据设置为请求实体
		setPostBinaryData(httpPost, compressedParams);
		HttpResponseWrapper result = getResult(httpPost, timeOut, Boolean.FALSE);
		return result;
	}


    public static HttpResponseWrapper httpPostProtobuf(String url, byte[] params, Map<String, String> headers, int timeOut) throws IOException {
        // 创建POST请求
        HttpPost httpPost = new HttpPost(url);
        // 添加请求头信息
        setPostHeader(httpPost, headers);
        // 将压缩后的数据设置为请求实体
        setPostBinaryData(httpPost, params);
        return getResult(httpPost, timeOut,Boolean.TRUE,Boolean.TRUE);
    }


    /***************************************************** HTTP GET 请求分割线 ************************************************************/


    /**
     * 发起HTTP GET请求，支持SSL
     *
     * @param url      请求地址
     * @param headers  请求头信息
     * @param params   请求参数
     * @param timeOut  超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @param isStream 是否以流的方式获取响应信息
     * @return 响应信息
     * @throws URISyntaxException
     */
    public static HttpResponseWrapper httpGet(String url, Map<String, String> headers, Map<String, String> params, Integer timeOut, boolean isStream) {
        HttpResponseWrapper result = null;
        try {
            // 构建url, URIBuilder 会对参数值做URLEncoder.encode
            URIBuilder uriBuilder = new URIBuilder(url);
            // 解析原有的查询参数，如果存在的话
            List<NameValuePair> originalParams = uriBuilder.getQueryParams();
            // 添加请求参数信息
            if (null != params) {
                originalParams.addAll(covertParamsToNVPS(params));
            }
            uriBuilder.setParameters(originalParams);
            // 创建get请求
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            setGetHeader(httpGet, headers);
            result = getResult(httpGet, timeOut, isStream);
        } catch (URISyntaxException e) {
            logger.error("parse uri syntax exception. url=" + url + " error info:", e);
        }
        return result;
    }


    /**
     * 发起HTTP GET请求，支持SSL
     *
     * @param url     请求地址
     * @param headers 请求头信息
     * @param params  请求参数
     * @param timeOut 超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @return 响应信息
     * @throws URISyntaxException
     */
    public static HttpResponseWrapper httpGet(String url, Map<String, String> headers, Map<String, String> params, Integer timeOut) {
        HttpResponseWrapper result = null;
        try {
            // 构建url, URIBuilder 会对参数值做URLEncoder.encode
            URIBuilder uriBuilder = new URIBuilder(url);
            // 解析原有的查询参数，如果存在的话
            List<NameValuePair> originalParams = uriBuilder.getQueryParams();
            // 添加请求参数信息
            if (null != params) {
                originalParams.addAll(covertParamsToNVPS(params));
            }
            uriBuilder.setParameters(originalParams);
            // 创建get请求
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            setGetHeader(httpGet, headers);
            result = getResult(httpGet, timeOut, Boolean.FALSE);
        } catch (URISyntaxException e) {
            logger.error("parse uri syntax exception. url=" + url + " error info:", e);
        }
        return result;
    }


    /**
     * 发起HTTP GET请求，支持SSL
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param timeOut 超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @return 响应信息
     * @throws URISyntaxException
     */
    public static HttpResponseWrapper httpGet(String url, Map<String, String> params, Integer timeOut) {
        HttpResponseWrapper result = null;
        try {
            // 构建url, URIBuilder 会对参数值做URLEncoder.encode
            URIBuilder uriBuilder = new URIBuilder(url);
            // 解析原有的查询参数，如果存在的话
            List<NameValuePair> originalParams = uriBuilder.getQueryParams();
            // 添加请求参数信息
            if (null != params) {
                originalParams.addAll(covertParamsToNVPS(params));
            }
            uriBuilder.setParameters(originalParams);
            // 创建get请求
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            result = getResult(httpGet, timeOut, Boolean.FALSE);
        } catch (URISyntaxException e) {
            logger.error("parse uri syntax exception. url=" + url + " error info:", e);
        }
        return result;
    }


    /**
     * 发起HTTP GET请求，支持SSL
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应信息
     * @throws URISyntaxException
     */
    public static HttpResponseWrapper httpGet(String url, Map<String, String> params) {
        HttpResponseWrapper result = null;
        try {
            // 构建url, URIBuilder 会对参数值做URLEncoder.encode
            URIBuilder uriBuilder = new URIBuilder(url);
            // 解析原有的查询参数，如果存在的话
            List<NameValuePair> originalParams = uriBuilder.getQueryParams();
            // 添加请求参数信息
            if (null != params) {
                originalParams.addAll(covertParamsToNVPS(params));
            }
            uriBuilder.setParameters(originalParams);
            // 创建get请求
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            result = getResult(httpGet, REQUEST_CONNECT_TIMEOUT, Boolean.FALSE);
        } catch (URISyntaxException e) {
            logger.error("parse uri syntax exception. url=" + url + " error info:", e);
        }
        return result;
    }


    /**
     * 发起HTTP GET请求，支持SSL
     *
     * @param url     请求地址
     * @param timeOut 超时时间(毫秒):从连接池获取连接的时间,请求时间,响应时间
     * @return 响应信息
     */
    public static HttpResponseWrapper httpGet(String url, Integer timeOut) {
        HttpGet httpGet = new HttpGet(url);
        HttpResponseWrapper result = getResult(httpGet, timeOut, Boolean.FALSE);
        return result;
    }

    /**
     * 发起HTTP GET请求，支持SSL
     *
     * @param url 请求地址
     * @return 响应信息
     */
    public static HttpResponseWrapper httpGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        HttpResponseWrapper result = getResult(httpGet, REQUEST_CONNECT_TIMEOUT, Boolean.FALSE);
        return result;
    }


    /***************************************************** HTTP RESULT 分割线 ************************************************************/

    private static HttpResponseWrapper getResult(HttpRequestBase httpRequest, Integer timeOut, boolean isStream) {
        return getResult(httpRequest, timeOut, isStream, false);
    }


    private static HttpResponseWrapper getResult(HttpRequestBase httpRequest, Integer timeOut, boolean isStream, boolean isResultByte) {
        // 获取请求的URI
        String requestURI = httpRequest.getURI().toString();
        CloseableHttpResponse response = null;
        InputStream inputStream = null;
        try {
            // 获取连接客户端
            CloseableHttpClient httpClient = getHttpClient(timeOut);
            // 发起请求
            response = httpClient.execute(httpRequest, HttpClientContext.create());

            int respCode = response.getStatusLine().getStatusCode();

            // 如果是重定向
            if (302 == respCode) {
                String locationUrl = response.getLastHeader("Location").getValue();
                return getResult(new HttpPost(locationUrl), timeOut, isStream);
            }
            String responseBody = "";
            byte[] resultByte = null;
            // 获得响应实体
            HttpEntity entity = response.getEntity();
            // 正确响应
            HttpResponseWrapper httpResponseWrapper = new HttpResponseWrapper();
            if (200 == respCode && entity != null) {
                // 如果是以流的形式获取
                if (isStream) {
                    inputStream = entity.getContent();
                    if (isResultByte) {
                        resultByte = IOUtils.toByteArray(entity.getContent());
                    } else {
                        responseBody = IOUtils.toString(inputStream, Consts.UTF_8);
                    }

                } else {

                    responseBody = EntityUtils.toString(entity, StandardCharsets.UTF_8);

//					Header contentEncodingHeader = response.getFirstHeader("Content-Encoding");
//	             	if (contentEncodingHeader != null && contentEncodingHeader.getValue().equalsIgnoreCase("gzip")) {
//	                    // 响应数据经过GZIP压缩，需要解压
//	             		responseBody = GZipUtil.uncompress(responseBody.getBytes(StandardCharsets.UTF_8));
//	             	}
                }
            } else if (entity != null) {
                inputStream = entity.getContent();
                if (isResultByte) {
                    resultByte = IOUtils.toByteArray(entity.getContent());
                } else {
                    responseBody = IOUtils.toString(inputStream, Consts.UTF_8);
                }
            }

            httpResponseWrapper.setStatusCode(response.getStatusLine().getStatusCode());
            httpResponseWrapper.setReason(response.getStatusLine().getReasonPhrase());
            httpResponseWrapper.setBody(responseBody);
            httpResponseWrapper.setByteBody(resultByte);
            for (Header header : response.getAllHeaders()) {
                httpResponseWrapper.addHeader(header.getName(), header.getValue());
            }
            return httpResponseWrapper;
        } catch (ConnectionPoolTimeoutException e) {
            String errorInfo = String.format("HTTP请求通信，从连接池获取连接超时，url [ %s ]，异常堆栈轨迹如下：", requestURI);
            logger.error(errorInfo, e);
        } catch (SocketTimeoutException e) {
            String errorInfo = String.format("HTTP请求通信，响应超时，url [ %s ]，异常堆栈轨迹如下：", requestURI);
            logger.error(errorInfo, e);
        } catch (ConnectTimeoutException e) {
            String errorInfo = String.format("HTTP请求通信，请求超时，url [ %s ]，异常堆栈轨迹如下：", requestURI);
            logger.error(errorInfo, e);
        } catch (ClientProtocolException e) {
            String errorInfo = String.format("HTTP请求通信，协议异常，url [ %s ]，异常堆栈轨迹如下：", requestURI);
            logger.error(errorInfo, e);
        } catch (UnsupportedEncodingException e) {
            String errorInfo = String.format("HTTP请求通信，不支持的字符编码，url [ %s ]，异常堆栈轨迹如下：", requestURI);
            logger.error(errorInfo, e);
        } catch (UnsupportedOperationException e) {
            String errorInfo = String.format("HTTP请求通信，不支持的请求操作，url [ %s ]，异常堆栈轨迹如下：", requestURI);
            logger.error(errorInfo, e);
        } catch (ParseException e) {
            String errorInfo = String.format("HTTP请求通信，解析异常，url [ %s ]，异常堆栈轨迹如下：", requestURI);
            logger.error(errorInfo, e);
        } catch (IOException e) {
            String errorInfo = String.format("HTTP请求通信，IO异常，url [ %s ]，异常堆栈轨迹如下：", requestURI);
            logger.error(errorInfo, e);
        } finally {
            //IOUtils.closeQuietly(inputStream);
            //IOUtils.closeQuietly(response);
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                String errorInfo = String.format("HTTP请求通信，关闭响应连接异常，url [ %s ]，异常堆栈轨迹如下：", requestURI);
                logger.error(errorInfo, e);
            }
        }
        return null;
    }

    @Deprecated
    public static String getResults(HttpRequestBase httpRequest, Integer timeOut, boolean isStream) {

        // 响应结果
        StringBuilder sb = null;

        CloseableHttpResponse response = null;

        try {
            // 获取连接客户端
            CloseableHttpClient httpClient = getHttpClient(timeOut);
            // 发起请求
            response = httpClient.execute(httpRequest);

            int respCode = response.getStatusLine().getStatusCode();
            // 如果是重定向
            if (302 == respCode) {
                String locationUrl = response.getLastHeader("Location").getValue();
                return getResults(new HttpPost(locationUrl), timeOut, isStream);
            }
            // 正确响应
            if (200 == respCode) {
                // 获得响应实体
                HttpEntity entity = response.getEntity();
                sb = new StringBuilder();

                // 如果是以流的形式获取
                if (isStream) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8));
                    String len = "";
                    while ((len = br.readLine()) != null) {
                        sb.append(len);
                    }
                } else {

                    // 从响应实体获取字节数组
                    byte[] responseBytes = EntityUtils.toByteArray(entity);

                    // 检查响应头是否包含Content-Encoding: gzip
                    Header contentEncodingHeader = response.getFirstHeader("Content-Encoding");
                    if (contentEncodingHeader != null && contentEncodingHeader.getValue().equalsIgnoreCase("gzip")) {
                        // 响应数据经过GZIP压缩，需要解压
                        sb.append(GZipUtil.uncompress(responseBytes));
                    } else {
                        // 响应数据未压缩
                        sb.append(new String(responseBytes, StandardCharsets.UTF_8));
                    }

//					sb.append(EntityUtils.toString(entity, ENCODING));
//					if (sb.length() < 1) {
//						sb.append(ERROR_RESULT);
//					}
                }
            }
            if (400 == respCode) {
                String body = "";
                HttpEntity entity = response.getEntity();
                InputStream in = null;
                if (entity != null) {
                    in = entity.getContent();
                    body = IOUtils.toString(in, Consts.UTF_8);
                }
                HttpResponseWrapper httpResponseWrapper = new HttpResponseWrapper();
                httpResponseWrapper.setStatusCode(response.getStatusLine().getStatusCode());
                httpResponseWrapper.setReason(response.getStatusLine().getReasonPhrase());
                httpResponseWrapper.setBody(body);
                for (Header header : response.getAllHeaders()) {
                    httpResponseWrapper.addHeader(header.getName(), header.getValue());
                }
                return JSON.toJSONString(httpResponseWrapper);
            }
        } catch (ConnectionPoolTimeoutException e) {
            logger.error("http请求:从连接池获取连接超时!!!", e);
        } catch (SocketTimeoutException e) {
            logger.error("http请求:响应超时", e);
        } catch (ConnectTimeoutException e) {
            logger.error("http请求:请求超时", e);
        } catch (ClientProtocolException e) {
            logger.error("http请求:http协议错误", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("http请求:不支持的字符编码", e);
        } catch (UnsupportedOperationException e) {
            logger.error("http请求:不支持的请求操作", e);
        } catch (ParseException e) {
            logger.error("http请求:解析错误", e);
        } catch (IOException e) {
            String errorInfo = String.format("http请求:IO错误. url:%s ", httpRequest.getURI().toString());
            logger.error(errorInfo, e);
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error("http请求:关闭响应连接出错", e);
                }
            }

        }

        return sb == null ? ERROR_RESULT : ("".equals(sb.toString().trim()) ? ERROR_RESULT : sb.toString());

    }


    public static void main(String[] args) throws Exception {

        /*JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("kkk", "djsklfj");
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("sds", "324324");*/

        //System.out.println(httpPost("https://kyfw.12306.cn/otn/login/init", null, null, 6000, false));


        AdxDataProtobuf.AdReq.Builder bu = AdxDataProtobuf.AdReq.newBuilder();

        YlAdxDataReqConvert reqConvert = new YlAdxDataReqConvert();
        SspRequestParam ad = new SspRequestParam();
        ad.setRequestId("1223232323");
        SspReqSlot slot = new SspReqSlot();
        ad.setSlot(slot);
        slot.setMaterialType(3);
        slot.setType(3);
        slot.setSlotId("7823");
        slot.setWidth(0);
        slot.setHeight(0);
        slot.setAdSlotId(70100);

        SspReqDevice device = new SspReqDevice();
        ad.setDevice(device);
        device.setDeviceName("Test");
        device.setType(3);
        device.setOsType(2);
        device.setModel("M2012K11C");
        device.setOsVersion("M2012K11C");
        device.setDeviceName("Test");
        device.setBrand("OPPO");
        device.setMake("OPPO");
        device.setImsi("123");

        SspReqDeviceId deviceId = new SspReqDeviceId();
        deviceId.setIdfv("1212");
        deviceId.setAndroidId("kds323");
        deviceId.setImei("Test");
        ad.setDeviceId(deviceId);

        SspReqApp app = new SspReqApp();
        app.setAppId("2345666");
        app.setName("TestApp");
        app.setPkgName("com.pkg.com");
        app.setVerName("12.323");
        app.setAppStoreVersion("4232.232");
        ad.setApp(app);

        SspReqNetwork network = new SspReqNetwork();
        network.setIp("173.12.32.44");
        network.setCarrier(2);
        network.setMac("ksdkalsdkfdd");
        network.setCountry("CN");
        network.setNetworkType(4);
        network.setUserAgent("Mozilla/5.0 (Linux; Android 12; M2012K11C Build/SKQ1.211006.001;");
        ad.setNetwork(network);

        Map<String, String> headers = Maps.newHashMap();
        headers.put("content-type", "application/grpc");
        String url = "http://192.168.0.111:8088/domob.Domob/AdGet";

        AdxDataProtobuf.AdReq requestParam = reqConvert.convert(ad);
        HttpResponseWrapper resp = httpPostProtobuf(url, requestParam.toByteArray(), headers, 100000);
        System.out.println(resp);

/**
        // 创建 OkHttpClient 实例
        OkHttpClient client = new OkHttpClient.Builder()
                .protocols(java.util.Collections.singletonList(Protocol.H2_PRIOR_KNOWLEDGE)) // 强制使用 HTTP/2
                .build();

        // 创建 POST 请求体
        RequestBody body = RequestBody.create(requestParam.toByteArray(), MediaType.parse("application/grpc"));

        // 构造请求
        Request httpRequest = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/grpc")
                .addHeader("x-grpc-web", "1")
                .addHeader("user-agent", "grpc-java-netty/1.40.0") // 模拟 gRPC 客户端的 User-Agent
                .build();

        // 发送请求并处理响应
        try (Response response = client.newCall(httpRequest).execute()) {
            if (response.isSuccessful()) {
                byte[] responseData = response.body().bytes();

                System.out.println("Response: " + responseData );
            } else {
                System.err.println("Request failed: " + response.message());
            }
        } **/

    }



}
