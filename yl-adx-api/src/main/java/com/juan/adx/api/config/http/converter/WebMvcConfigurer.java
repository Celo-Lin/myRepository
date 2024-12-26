package com.juan.adx.api.config.http.converter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.support.config.FastJsonConfig;


@Configuration
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {
	
    @Bean
    public FastJsonConfig fastJsonConfig(){
        // 1. 自定义配置
        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");

        // 2.1 配置序列化的行为
        config.setWriterFeatures(
        		//生成格式化的JSON。启用后，FastJSON将生成格式化的JSON，以提高可读性。
        		//JSONWriter.Feature.PrettyFormat, 
        		//将Map类型字段中的null值输出为JSON对象。启用后，Map中的null值将被转换为JSON中的null。
        		//JSONWriter.Feature.WriteMapNullValue,
                //将List类型字段中的null值输出为空数组。启用后，List中的null值将被转换为空数组。
                //JSONWriter.Feature.WriteNullListAsEmpty,
                //将String类型字段中的null值输出为空字符串。启用后，String字段中的null值将被转换为空字符串。
        		//JSONWriter.Feature.WriteNullStringAsEmpty
        		);

        // 2.2 配置反序列化的行为
        config.setReaderFeatures(
        		//控制按字段名字典顺序进行反序列化。启用后，FastJSON会按照字段名字典顺序对JSON进行反序列化。
        		JSONReader.Feature.FieldBased, 
        		//支持将JSON数组转换为Java对象。启用后，FastJSON可以将JSON数组直接转换为Java对象。
        		JSONReader.Feature.SupportArrayToBean
        		);

        return config;
    }
    
    
	@SuppressWarnings("deprecation")
	@Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    	FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
    	//在convert中添加配置信息
        converter.setFastJsonConfig(fastJsonConfig());
        
        //设置默认字符集
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        
        //设置FastJson消息转换器支持的媒体类型
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(0, converter);
        
        // 添加Protobuf消息转换器
        converters.add(new ProtobufHttpMessageConverter());
//        converters.add(0, new CustomProtobufHttpMessageConverter());
    }
	
	
	
	
}