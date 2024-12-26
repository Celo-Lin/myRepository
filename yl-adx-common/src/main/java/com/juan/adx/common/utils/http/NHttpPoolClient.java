package com.juan.adx.common.utils.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
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
public class NHttpPoolClient {

    private static final int DEFAULT_MAX_CONN = 700; // 最大连接数
    private static final int DEFAULT_Max_PRE_ROUTE = 700;
    private static final int DEFAULT_MAX_ROUTE = 700;
    private static final int DEFAULT_IO_THREAD_COUNT = Runtime.getRuntime().availableProcessors();
    private final PoolStats poolStats = new PoolStats();
    private PoolingNHttpClientConnectionManager manager; //连接池管理类
    private ScheduledExecutorService monitorExecutor;
    private String name;
    private String url;
    private int maxConn;
    private int maxPreRoute;
    private int maxRoute;
    private int ioThreadCount;
    private CloseableHttpAsyncClient client;

    private NHttpPoolClient() {
    }

    public NHttpPoolClient(String name, String url, int maxCon, int maxPreRoute, int maxRoute, int ioThreadCount) {
        this.name = name;
        this.url = url;
        this.maxConn = maxCon;
        this.maxPreRoute = maxPreRoute;
        this.maxRoute = maxRoute;
        this.ioThreadCount = ioThreadCount;

        String hostName = url.split("/")[2];
        int port = 80;
        if (hostName.contains(":")) {
            String[] args = hostName.split(":");
            hostName = args[0];
            port = Integer.parseInt(args[1]);
        }

        client = createHttpClient(hostName, port);
        client.start();
        //开启监控线程,对异常和空闲线程进行关闭
        monitorExecutor = Executors.newScheduledThreadPool(1);
        monitorExecutor.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
//                log.info(String.format("NHttpClientPool-[%s] %s", name, manager.getTotalStats().toString()));
                //关闭异常连接
                manager.closeExpiredConnections();
                //关闭30s空闲的连接
                manager.closeIdleConnections(60 * 2, TimeUnit.SECONDS);
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public int getMaxConn() {
        return maxConn <= 0 ? DEFAULT_MAX_CONN : maxConn;
    }

    public int getMaxPreRoute() {
        return maxPreRoute <= 0 ? DEFAULT_Max_PRE_ROUTE : maxPreRoute;
    }

    public int getMaxRoute() {
        return maxRoute <= 0 ? DEFAULT_MAX_ROUTE : maxRoute;
    }

    public int getIoThreadCount() {
        return ioThreadCount <= 0 ? DEFAULT_IO_THREAD_COUNT : ioThreadCount;
    }

    public CloseableHttpAsyncClient getClient() {
        return client;
    }

    /**
     * 根据host和port构建httpclient实例
     *
     * @param host 要访问的域名
     * @param port 要访问的端口
     * @return
     */
    private CloseableHttpAsyncClient createHttpClient(String host, int port) {
        // 配置io线程
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom().
                setIoThreadCount(getIoThreadCount())
                .setSoKeepAlive(true)
                .build();
        // 设置连接池大小
        ConnectingIOReactor ioReactor = null;
        try {
            ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
        } catch (IOReactorException e) {
            e.printStackTrace();
        }

        manager = new PoolingNHttpClientConnectionManager(ioReactor);
        //设置连接参数
        manager.setMaxTotal(getMaxConn()); // 最大连接数
        manager.setDefaultMaxPerRoute(getMaxPreRoute()); // 路由最大连接数

        HttpHost httpHost = new HttpHost(host, port);
        manager.setMaxPerRoute(new HttpRoute(httpHost), getMaxRoute());

        // keep-alive
        ConnectionKeepAliveStrategy keepAliveStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                return 2 * 60 * 1000;
            }
        };

        return HttpAsyncClients.custom()
                .setConnectionManager(manager)
                .setKeepAliveStrategy(keepAliveStrategy)
                .build();
    }

    public PoolStats getPoolStats() {
        org.apache.http.pool.PoolStats stats = manager.getTotalStats();
        poolStats.setLeased(stats.getLeased());
        poolStats.setPending(stats.getPending());
        poolStats.setAvailable(stats.getAvailable());
        poolStats.setMax(stats.getMax());
        return poolStats;
    }

}
