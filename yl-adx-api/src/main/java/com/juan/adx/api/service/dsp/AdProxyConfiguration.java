package com.juan.adx.api.service.dsp;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@Data
@Configuration
@PropertySource("classpath:adproxy-test.properties")
public class AdProxyConfiguration {
    /**
     * 调用方业务id
     */
    @Value("${channel.ad.proxy.serviceId}")
    private Long serviceId;
    /**
     * 远程接口地址
     */
    @Value("${channel.ad.proxy.main.url}")
    private String remoteUrl;
    /**
     * 价格加解密key
     */
    @Value("${channel.ad.proxy.priceKey}")
    private String priceKey;

    private static Long staticServiceId;
    private static String staticRemoteUrl;
    private static String staticPriceKey;

    @PostConstruct
    public void init() {
        staticRemoteUrl = remoteUrl;
        staticServiceId = serviceId;
        staticPriceKey = priceKey;
    }

    public static Long getStaticServiceId() {
        return staticServiceId;
    }

    public static String getStaticRemoteUrl() {
        return staticRemoteUrl;
    }

    public static String getStaticPriceKey() {
        return staticPriceKey;
    }

}
