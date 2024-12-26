package com.juan.adx.model.dsp.youdao.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/18 9:10
 */
public enum YouDaoResponseCode {

    SINGLE_SUCCESS("21000", "请求单条广告返回成功"),

    MULTI_ALL_SUCCESS("22001", "请求多条广告返回成功"),

    MULTI_PART_SUCCESS("22005", "请求多条广告，部分返回成功"),

    PROVIDER("20001", "广告非有道提供"),

    FAIL_REQUEST("20002", "标识此请求为失败上报，SDK 在遇到错误（如广告渲染超时（默认 10s））时会向服务端进行上报"),
    FAIL_REQUEST_NOT_EXIST("20003", "广告位当前不存在有效的样式（样式被删除或者暂停了）。"),
    NULL_SDK_REQUEST("40001", "SdkRequest 为空。在检查 SDK 广告请求有效性时失败。"),
    NULL_SLOTID("40002", "广告位 id 为空。"),
    INVALID_ADCOUNT("40003", "请求广告数目非法：<=0。"),
    INVALID_ENCODETYPE("40101", "ENCODED_TYPE_KEY(ydet，加密方式) 所对应的值非法。"),
    DESIGNATED_STYLE_NAME_NOT_FOUND("40102", "广告请求中指定的样式名，在广告位的样式缓存里找不到。请检查广告位 id 是否正确传入，若正确，可能为缓存未同步，同步时间不超过 15 分钟"),
    INVALID_SDK_STATUS("40103", "检查广告位状态失败，不出广告。可能原因：(在 SdkSlot 表里有两个状态列：SDK_STATUS 和 SDK_OP_STATUS）1. 开发者将广告位的状态（opstatus）设为暂停或者删除状态, 导致广告位本身不存在、不可用等；2. 虽然广告位本身是有效的，但广告位状态（status）未通过审核"),
    NOT_IN_CACHE("40104", "缓存中不存在需要获取的广告位信息。缓存同步时间不超过 15 分钟。"),
    WRONG_KEY("40105", "无法取到加密后的请求字段，请求’s’ 字段为空。即 ENCODED_REQUEST_KEY 所对应的加密内容为空"),
    ERROR_DECODE("40106", "在解析 HttpServletRequest 参数时发生错误。如：不合法的加密类型；加密类型存在但加密内容为空；对密文解密失败等"),
    ERROR_CREATE_ADREQUEST("40107", "根据 HttpServletRequest 构建 SdkAdRequest 失败。"),

    BRAND_URL_ERROR("41001", "品牌广告的原始 link 字段（RAW_LINK）值为空。"),
    BRAND_IMPR_ERROR("41002", "品牌广告的广告跟踪字段（imprTracker）值为空"),
    BRAND_STYLE_ERROR("41003", "品牌广告的广告样式名称字段（STYLE_NAME）值为空。"),

    ZX_NO_ADITEM("41004", "智选返回空广告。"),
    ZX_NO_INTERSECTSTYLE("41005", "智选在渲染广告时，广告与广告位支持样式交集为空。"),
    ZX_NO_ADSTYLE("41006", "智选取到的广告没有样式（style）。"),
    ZX_LACK_ELEMENT("41007", "智选取到的广告缺乏元素信息，无法渲染广告，不能将其放入广告体中。"),
    ZX_NULL_TRACKER("41008", "智选在渲染广告时，没有展示跟踪链接数组 impTracker 或者点击跟踪链接数组 clkTracker。"),
    ZX_TIMEOUT("41015", "智选返回广告超时"),
    ZX_BID_ERROR("41016", "智选返回广告异常"),

    YEX_NULL_SELECTEDAD("41009", "yex 返回空广告,提名品牌结果为空且无效果广告来填充"),
    YEX_NULL_AD("41010", "yex 没有返回广告。"),
    YEX_CPC_TIMEOUT("41011", "yex 通过 OpenRTB 提名效果广告超时"),
    YEX_CPC_EXEC_FAILED("41012", " yex 通过 OpenRTB 提名效果广告出现执行异常。"),
    YEX_BRAND_TIMEOUT("41013", "yex 通过 OpenRTB 提名品牌广告超时。"),
    YEX_BRAND_EXEC_FAILED("41014", " yex 通过 OpenRTB 提名品牌广告出现执行异常。"),

    MULTI_FAILED("42001", "请求批量广告失败。"),
    TIMEOUT_FAILED("43001", "超时, 目前 nginx 最多等待后端服务 900ms。"),
    UNDEFINED("-1", "未定义异常。");;

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String reason;


    YouDaoResponseCode(String code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public static YouDaoResponseCode get(String type) {
        if (type == null) {
            return YouDaoResponseCode.UNDEFINED;
        }
        for (YouDaoResponseCode networkType : values()) {
            if (networkType.getCode().equals(type)) {
                return networkType;
            }
        }
        return YouDaoResponseCode.UNDEFINED;
    }

}
