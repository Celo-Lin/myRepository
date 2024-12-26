package com.juan.adx.model.dsp.haoya.request;
import lombok.Data;

@Data
public class HaoYaCaid {
    /**
     * <pre>
     * id: caid原值
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * version: caid版本号，caid原值跟版本号同时传才有意义，建议传caid原值时，同时也传version
     * </pre>
     */
    private String version;

    /**
     * <pre>
     * bootTimeInSec: iOS设备启动时间
     * </pre>
     */
    private String bootTimeInSec;

    /**
     * <pre>
     * countryCode: 国家
     * </pre>
     */
    private String countryCode;

    /**
     * <pre>
     * language: 语言
     * </pre>
     */
    private String language;

    /**
     * <pre>
     * deviceName: 设备名称
     * </pre>
     */
    private String deviceName;

    /**
     * <pre>
     * model: 设备 model
     * </pre>
     */
    private String model;

    /**
     * <pre>
     * systemVersion: 系统版本
     * </pre>
     */
    private String systemVersion;

    /**
     * <pre>
     * machine: 设备 machine
     * </pre>
     */
    private String machine;

    /**
     * <pre>
     * carrierInfo: 运营商
     * </pre>
     */
    private String carrierInfo;

    /**
     * <pre>
     * memory: 物理内存
     * </pre>
     */
    private String memory;

    /**
     * <pre>
     * disk: 磁盘空间
     * </pre>
     */
    private String disk;

    /**
     * <pre>
     * sysFileTime: 系统更新时间
     * </pre>
     */
    private String sysFileTime;

    /**
     * <pre>
     * timeZone: 时区
     * </pre>
     */
    private String timeZone;

    /**
     * <pre>
     * initTime: 应用首次启动时间
     * </pre>
     */
    private String initTime;
}
