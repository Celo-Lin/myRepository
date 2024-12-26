package com.juan.adx.model.ssp.common.response;

import lombok.Data;


@Data
public class SspRespApp {
	
    
	/**
     * 应用名称
     */
    private String name;
    
    /**
     * 应用版本
     */
    private String version;
    
    /**
     * 应用包名
     */
    private String pkgName;
    
    /**
     * 应用包名 MD5值(小写)
     */
    private String pkgMd5;
    
    /**
     * 应用icon地址
     */
    private String iconUrl;
    
    /**
     * 应用大小
     */
    private Long size;
    
    /**
     * 应用开发者名称
     */
    private String corporate;
    
    /**
     * 应用隐私协议地址
     */
    private String privacyPolicyUrl;
    
    /**
     * 应用权限地址
     */
    private String permissionUrl;
    
}
