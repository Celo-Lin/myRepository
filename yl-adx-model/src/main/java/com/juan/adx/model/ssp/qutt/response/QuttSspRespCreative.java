package com.juan.adx.model.ssp.qutt.response;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-05-18 15:44
 * @Description:
 * @Version: 1.0
 */

@Data
public class QuttSspRespCreative {

    /**
     * 是
     * ⼩于128字节
     * DSP侧的素材id，暂不⽀持
     */
    private String id;

    /**
     * 物料类型，具体值得含义⻅附录
     * 物料类型
     * 是
     */
    private Integer type;
    /**
     * 拉新拉活控制 1:拉新 2:拉活 3:拉
     * 新+拉活 ，其余⽆效
     * 否
     */
    @JSONField(name = "put_type")
    private Integer putType;

    /**
     * 否
     * 物料标题信息
     */
    private QuttSspRespTitle title;
    /**
     * 否
     * 物料描述信息
     */
    private QuttSspRespDesc desc;

    /**
     * 否
     * 激励视频的创意按钮
     */
    @JSONField(name = "button_text")
    private String buttonText;

    /**
     * 否
     * 物料图⽚
     */
    private List<QuttSspRespImage> images;

    /**
     * 否
     * 物料视频
     */
    private List<QuttSspRespVideo> videos;

    /**
     * 物料交互类型；落地⻚还是下
     * 载。⻅附录InteractionType
     * 是
     */
    @JSONField(name = "interaction_type")
    private Integer interactionType;

    /**
     * 物料落地⻚，交互类型为落地⻚
     * 时，必有此字段
     * 否
     */
    @JSONField(name = "landing_url")
    private String landingUrl;

    /**
     * 否
     * deeplink地址 dp类的⼴告交互类
     * 型 请传 下载
     */
    @JSONField(name = "deep_link_url")
    private String deepLinkUrl;

    /**
     * 下载app相关信息，交互类型为
     * 下载时，必有此字段
     * 否
     */
    private QuttSspRespApp app;

    /**
     * 否
     * htmlsnippet物料
     */
    private String htmlsnippet;
    /**
     * 否
     * universal link链接 iOS 唤起APP
     * 通⽤链接
     */
    @JSONField(name = "universal_link")
    private String universalLink;

    /**
     * 否
     * 微信⼩程序
     */
    private QuttSspRespWeixin weixin;

    /**
     * 否
     * DSP响应策略ID。
     */
    @JSONField(name = "response_strategy")
    private List<Integer> responseStrategy;

}
