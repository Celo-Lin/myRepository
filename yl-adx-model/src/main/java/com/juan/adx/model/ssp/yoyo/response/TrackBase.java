package com.juan.adx.model.ssp.yoyo.response;

import lombok.Data;

import java.util.List;

/**
 * @Author: ChaoLong Lin
 * @CreateTime: 2024/12/19 10:13
 * @Description: YoYo 响应监测链接对象
 * @version: V1.0
 */
@Data
public class TrackBase {
    /**
     * DSP竞价成功后ADX请求 , 该URL支持宏替换 : {price}扣费价格ecpm
     */
    private String winUrl;

    /**
     * 曝光链接支持宏替换 : {price}扣费价格ecpm
     */
    private List<String> imp;

    /**
     * 点击链接支持宏替换 : {price}扣费价格ecpm
     */
    private List<String> clk;

    /**
     * 开始下载
     */
    private List<String> del;

    /**
     * 完成下载
     */
    private List<String> finishDle;

    /**
     * 下载失败
     */
    private List<String> failDel;

    /**
     * 开始安装
     */
    private List<String> install;

    /**
     * 完成安装
     */
    private List<String> finishInstall;

    /**
     * 应用激活
     */
    private List<String> appactiveurl;

    /**
     * 当应用已安装，且使用 deeplink 打开应用成功时
     */
    private List<String> deeplinkSuccess;

    /**
     * 当应用已安装，且使用 deeplink 打开应用失败时
     */
    private List<String> deeplinkFailure;

    /**
     * 拉活失败已安装
     */
    private List<String> deeplinkFailInst;

    /**
     * 拉活失败未安装
     */
    private List<String> deeplinkFailUninst;
}
