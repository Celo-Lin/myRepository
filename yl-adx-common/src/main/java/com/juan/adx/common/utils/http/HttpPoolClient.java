package com.juan.adx.common.utils.http;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-12-13 15:02
 * @Description: 像DSP  Proxy发起请求服务
 * @Version: 1.0
 */
@Slf4j
public class HttpPoolClient {

    private static final int DEFAULT_MAX_CONN = 700; // 最大连接数
    private static final int DEFAULT_Max_PRE_ROUTE = 700;
    private static final int DEFAULT_MAX_ROUTE = 700;
    private PoolingHttpClientConnectionManager manager; //连接池管理类
    private ScheduledExecutorService monitorExecutor;

    private String name;
    private String url;
    private int maxConn;
    private int maxPreRoute;
    private int maxRoute;
    private CloseableHttpClient client;

    public int getMaxConn() {
        return maxConn <= 0 ? DEFAULT_MAX_CONN : maxConn;
    }

    public int getMaxPreRoute() {
        return maxPreRoute <= 0 ? DEFAULT_Max_PRE_ROUTE : maxPreRoute;
    }

    public int getMaxRoute() {
        return maxRoute <= 0 ? DEFAULT_MAX_ROUTE : maxRoute;
    }

    public CloseableHttpClient getClient() {
        return client;
    }

    private HttpPoolClient() {}

    public HttpPoolClient(String name, String url, int maxCon, int maxPreRoute, int maxRoute) {
        this.name = name;
        this.url = url;
        this.maxConn = maxCon;
        this.maxPreRoute = maxPreRoute;
        this.maxRoute = maxRoute;

        String hostName = url.split("/")[2];
        int port = 80;
        if (hostName.contains(":")){
            String[] args = hostName.split(":");
            hostName = args[0];
            port = Integer.parseInt(args[1]);
        }

        client = createHttpClient(hostName, port);
        //开启监控线程,对异常和空闲线程进行关闭
        monitorExecutor = Executors.newScheduledThreadPool(1);
        monitorExecutor.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                log.info(String.format("HttpClientPool-[%s] %s", name, manager.getTotalStats().toString()));
                //关闭异常连接
                manager.closeExpiredConnections();
                //关闭30s空闲的连接
                manager.closeIdleConnections(60 * 2, TimeUnit.SECONDS);
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    /**
     * 根据host和port构建httpclient实例
     * @param host 要访问的域名
     * @param port 要访问的端口
     * @return
     */
    private CloseableHttpClient createHttpClient(String host, int port){
        ConnectionSocketFactory plainSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create().register("http", plainSocketFactory)
                .register("https", sslSocketFactory).build();

        manager = new PoolingHttpClientConnectionManager(registry);
        //设置连接参数
        manager.setMaxTotal(getMaxConn()); // 最大连接数
        manager.setDefaultMaxPerRoute(getMaxPreRoute()); // 路由最大连接数

        HttpHost httpHost = new HttpHost(host, port);
        manager.setMaxPerRoute(new HttpRoute(httpHost), getMaxRoute());

        //请求失败时,进行请求重试
        DefaultHttpRequestRetryHandler handler = new DefaultHttpRequestRetryHandler(0, false);

        // keep-alive
        ConnectionKeepAliveStrategy keepAliveStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                return 2 * 60 * 1000;
            }
        };

        return HttpClients.custom()
                .setConnectionManager(manager)
                .setKeepAliveStrategy(keepAliveStrategy)
                .setRetryHandler(handler)
                .build();
    }

    private final PoolStats poolStats = new PoolStats();

    public PoolStats getPoolStats() {
        org.apache.http.pool.PoolStats stats = manager.getTotalStats();
        poolStats.setLeased(stats.getLeased());
        poolStats.setPending(stats.getPending());
        poolStats.setAvailable(stats.getAvailable());
        poolStats.setMax(stats.getMax());
        return poolStats;
    }

}
