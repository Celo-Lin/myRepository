package com.juan.adx.model.ssp.qutt.response;

import com.alibaba.fastjson2.annotation.JSONField;
import com.juan.adx.model.ssp.qutt.request.*;
import lombok.Data;

import java.util.List;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-05-18
 * @Description:
 * @Version: 1.0
 */
@Data
public class QuttSspRespBid {

    /**
     * 是
     * ⼩于128字节
     * DSP⽅的竞价id
     */
    private String id;

    /**
     * 是
     * DSP的CPM/CPC出价。单位：
     * 分
     */
    @JSONField(name = "bid_price")
    private Integer bidPrice;

    /**
     * 是
     * 曝光监控url
     */
    @JSONField(name = "imp_urls")
    private List<String> impUrls;
    /**
     * 是
     * 点击监控url
     */
    @JSONField(name = "clk_urls")
    private List<String> clkUrls;
    /**
     * 否
     * 下载开始监控
     */
    @JSONField(name = "dbm_urls")
    private List<String> dbmUrls;
    /**
     * 否
     * 下载结束监控
     */
    @JSONField(name = "dem_urls")
    private List<String> demUrls;
    /**
     * 否
     * 安装开始监控
     */
    @JSONField(name = "ibm_urls")
    private List<String> ibmUrls;
    /**
     * 否
     * 安装结束监控
     */
    @JSONField(name = "iem_urls")
    private List<String> iemUrls;
    /**
     * 否
     * deeplink唤起监控上报
     */
    @JSONField(name = "dp_clks")
    private List<String> dpClks;
    /**
     * 否
     * deeplink唤起失败监控上报
     */
    @JSONField(name = "dp_failed_clks")
    private List<String> dpFailedClks;
    /**
     * 否
     * app应⽤打开事件上报
     */
    @JSONField(name = "aom_urls")
    private List<String> aomUrls;
    /**
     * 落地⻚内点击某区域复制内容上
     * 报
     * 否
     */
    @JSONField(name = "lp_copy_urls")
    private List<String> lpCopyUrls;
    /**
     * 否
     * 落地⻚点击⾏为上报
     */
    @JSONField(name = "lp_clk_urls")
    private List<String> lpClkUrls;
    /**
     * 竞价成功通知。并 通 过 宏 替
     * 换{WIN_PRICE}提供⼆价。⾮
     * 竞价⼴告⽆此字段返回。单位：
     * 分/cpm
     * 否
     */
    @JSONField(name = "win_urls")
    private List<String> winUrls;
    /**
     * 否
     * win_price加密⽅式。⻅附录
     * WinPriceEncrypt
     */
    @JSONField(name = "win_price_encrypt")
    private Integer winPriceEncrypt;

    /**
     * 否
     * 竞价失败通知。宏替换⻅附录
     * Loss Notice
     */
    @JSONField(name = "l_urls")
    private List<String> lUrls;

    /**
     * 否
     * 设备信息。
     */
    private QuttSspRespCreative creative;

}
