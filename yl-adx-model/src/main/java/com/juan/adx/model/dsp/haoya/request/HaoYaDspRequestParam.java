package com.juan.adx.model.dsp.haoya.request;
import lombok.Data;
import java.util.List;

@Data
public class HaoYaDspRequestParam {
    /**
     * <pre>
     * reqid: 自定义广告请求 id，开发者自行生成
     * </pre>
     */
    private String reqid;

    /**
     * <pre>
     * apiVersion: adx 的 api 版本号，当前为固定值"1.0"
     * </pre>
     */
    private String apiVersion;

    /**
     * <pre>
     * adSlotList: 广告位信息，详见AdSlot对象
     * </pre>
     */
    private List<HaoYaAdSlot> adSlotList;

    /**
     * <pre>
     * app: App 信息，移动端应用必填，详见App对象
     * </pre>
     */
    private HaoYaApp app;

    /**
     * <pre>
     * device: 设备信息，详见Device对象
     * </pre>
     */
    private HaoYaDevice device;

    /**
     * <pre>
     * site: 网站信息，web 媒体必填，详见Site对象
     * </pre>
     */
    private HaoYaSite site;

    /**
     * <pre>
     * user: 用户信息，详见User对象
     * </pre>
     */
    private HaoYaUser user;

    /**
     * <pre>
     * bcat: 屏蔽的行业 ID
     * </pre>
     */
    private List<String> bcat;

    /**
     * <pre>
     * badv: 屏蔽的广告主
     * </pre>
     */
    private List<String> badv;

    /**
     * <pre>
     * appList: 下载、唤醒类广告应用定向，请咨询运营获取应用 ID 与包名对应关系。请填写应用 ID，多个 ID 使用英文逗号分隔。
     * </pre>
     */
    private String appList;

    /**
     * <pre>
     * ext: 扩展字段，任意类型，暂无实际用途
     * </pre>
     */
    private String ext;
}
