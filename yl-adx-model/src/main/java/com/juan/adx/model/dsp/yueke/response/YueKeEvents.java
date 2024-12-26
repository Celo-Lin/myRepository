package com.juan.adx.model.dsp.yueke.response;
import lombok.Data;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class YueKeEvents {

    /**
     * <pre>
     * 曝光上报地址
     * 是否必填: 是
     * 取值或描述: 需要宏替换，RTB模式需要替换价格宏
     * </pre>
     */
    private List<String> els;

    /**
     * <pre>
     * 点击上报地址
     * 是否必填: 是
     * 取值或描述: 需要宏替换
     * </pre>
     */
    private List<String> cls;

    /**
     * <pre>
     * 开始下载上报地址
     * 是否必填: 否
     * 取值或描述: 需要宏替换，下载广告必须支持
     * </pre>
     */
    private List<String> sdls;

    /**
     * <pre>
     * 下载结束上报地址
     * 是否必填: 否
     * 取值或描述: 需要宏替换，下载广告必须支持
     * </pre>
     */
    private List<String> edls;

    /**
     * <pre>
     * 开始安装上报地址
     * 是否必填: 否
     * 取值或描述: 需要宏替换，下载广告必须支持
     * </pre>
     */
    private List<String> sils;

    /**
     * <pre>
     * 安装完成上报地址
     * 是否必填: 否
     * 取值或描述: 需要宏替换，下载广告必须支持
     * </pre>
     */
    private List<String> eils;

    /**
     * <pre>
     * 安装激活上报地址
     * 是否必填: 否
     * 取值或描述: 需要宏替换，下载广告必须支持
     * </pre>
     */
    private List<String> ials;

    /**
     * <pre>
     * 开始播放上报地址
     * 是否必填: 否
     * 取值或描述: 需要宏替换，激励视频必须支持
     * </pre>
     */
    private List<String> spls;

    /**
     * <pre>
     * 暂停播放上报地址
     * 是否必填: 否
     * 取值或描述: 需要宏替换，激励视频必须支持
     * </pre>
     */
    private List<String> ppls;

    /**
     * <pre>
     * 恢复播放上报地址
     * 是否必填: 否
     * 取值或描述: 需要宏替换，激励视频必须支持
     * </pre>
     */
    private List<String> gpls;

    /**
     * <pre>
     * 播放结束上报地址
     * 是否必填: 否
     * 取值或描述: 需要宏替换，激励视频必须支持
     * </pre>
     */
    private List<String> epls;

    /**
     * <pre>
     * Deeplink调起成功上报地址
     * 是否必填: 否
     * 取值或描述: 需要宏替换
     * </pre>
     */
    private List<String> dcls;

    /**
     * <pre>
     * 播放进度上报地址
     * 是否必填: 否
     * 取值或描述: 例如：(Map<"50", Set<String>>) 代表播放一半的时候进行上报，需要宏替换，激励视频必须支持
     * </pre>
     */
    private Map<String, Set<String>> fpls;

    /**
     * <pre>
     * 静音播放上报地址
     * 是否必填: 否
     * 取值或描述: 需要宏替换，激励视频必须支持
     * </pre>
     */
    private List<String> mpls;

    /**
     * <pre>
     * 跳过视频上报地址
     * 是否必填: 否
     * 取值或描述: 需要宏替换，激励视频必须支持
     * </pre>
     */
    private List<String> skls;

    /**
     * <pre>
     * 关闭视频上报地址
     * 是否必填: 否
     * 取值或描述: 需要宏替换，激励视频必须支持
     * </pre>
     */
    private List<String> cpls;
}
