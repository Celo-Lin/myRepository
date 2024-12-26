package com.juan.adx.manage.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
public class FileUploadConfiguration {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 设置文件大小限制 ,超出设置页面会抛出异常信息，
        // 这样在文件上传的地方就需要进行异常信息的处理了;
        //上传的单个文件最大值   KB,MB 这里设置为10MB
        DataSize maxSize = DataSize.ofMegabytes(20);
        DataSize requestMaxSize = DataSize.ofMegabytes(50);
        factory.setMaxFileSize(maxSize);
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(requestMaxSize);
        // Sets the directory location where files will be stored.
        // factory.setLocation("路径地址");
        return factory.createMultipartConfig();
    }
}
