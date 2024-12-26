package com.juan.adx.model.ssp.common.protobuf;

/**
 * @Author: cao fei
 * @Date: created in 4:02 下午 2021/10/13
 */
public class Constant {

    public static class DeviceType {
        public static final int phone = 1;
        public static final int pad = 2;
        public static final int other = 255;
    }

    public static class Orientation {
        public static final int portrait = 0;
        public static final int landscape = 1;
    }

    public static class OS {
        public static final int android = 1;
        public static final int ios = 2;
        public static final int other = 255;
    }

    public static class BatteryStatus {
        // 未知
        public static final String unknown = "Unknown";
        // 不充电
        public static final String unplugged = "Unplugged";
        // 充电
        public static final String charging = "Charging";
        // 满电
        public static final String full = "Full";
    }

    public static class ConnectType {
        // 无法探测当前网络状态
        public static final int unknown = 0;
        // 蜂窝数据接入, 未知网络类型
        public static final int wlanUnknown = 1;
        public static final int wlan2G = 2;
        public static final int wlan3G = 3;
        public static final int wlan4G = 4;
        public static final int wlan5G = 5;
        public static final int wifi = 100;
        // 以太网
        public static final int ethernet = 101;
        // 未知新类型
        public static final int newType = 255;
    }

    public static class MobileCarrier {
        // 未知运营商
        public static final int unknown = 0;
        // 中国移动
        public static final int chinaMobile = 1;
        // 中国电信
        public static final int chinaTelecom = 2;
        // 中国联通
        public static final int chinaUnicom = 3;
        // 其他运营商
        public static final int other = 255;
    }

    public static class CoordinateType {
        public static final int gps = 1;
        // 高德使用
        public static final int gjc02 = 2;
        // 百度使用
        public static final int bg09 = 3;
    }

    public static class GeoLaccu {
        // 定位不准确
        public static final int inaccurate = 0;
        // 定位精准, 可以获取到小数点
        public static final int accurate = 1;
    }

    public static class Action {
        public static final int web = 1;
        public static final int download = 2;
        public static final int deeplink = 3;
        public static final int gdt = 4;
    }

    public static class DockingWay {
        // SSP对接方式
        public static final String SSP = "SSP";
        // RTB对接方式
        public static final String RTB = "RTB";
    }

    public static class InteractStyle {
        // 默认
        public static final int DEFAULT = 0;
        // 摇一摇
        public static final int SHAKE = 1;
        // 滑一滑
        public static final int SLIDE = 2;
        // 开屏无热区
        public static final int NO_HOT = 3;
        // 擦一擦
        public static final int SCRUB = 4;
        // 转动手机
        public static final int TURN = 5;
        // 混合互动
        public static final int MIXED = 6;
    }

    public static class InteractSubStyle {
        // 默认
        public static final int DEFAULT = 0;
        // 向上滑动
        public static final int SLIDE_UP = 21;
        // 全方向滑动
        public static final int SLIDE_ALL = 22;
        // 弧形滑动
        public static final int SLIDE_ARC = 23;
        // 转动手机
        public static final int TURN_DEFAULT = 51;
        // 扭动手机
        public static final int TURN_TWIST = 52;
    }

    public static class ResultCode {
        /**
         * 成功获取到广告
         */
        public static final int success = 0;
        /**
         * 无广告填充
         */
        public static final int noAd = 10007;
    }

    public static class TrackMacro {
        /**
         * ⽤户⼿指按下时相对广告位的坐标
         */
        public static final String downX = "__JGC_DOWN_X__";
        /**
         * ⽤户⼿指按下时相对广告位的坐标
         */
        public static final String downY = "__JGC_DOWN_Y__";
        /**
         * ⽤户⼿指抬起时相对广告位的坐标
         */
        public static final String upX = "__JGC_UP_X__";
        /**
         * ⽤户⼿指抬起时相对广告位的坐标
         */
        public static final String upY = "__JGC_UP_Y__";
        /**
         * ⽤户⼿指按下时相对屏幕的坐标
         */
        public static final String absDownX = "__JGC_ABS_DOWN_X__";
        /**
         * ⽤户⼿指按下时相对屏幕的坐标
         */
        public static final String absDownY = "__JGC_ABS_DOWN_Y__";
        /**
         * ⽤户⼿指抬起时相对屏幕的坐标
         */
        public static final String absUpX = "__JGC_ABS_UP_X__";
        /**
         * ⽤户⼿指抬起时相对屏幕的坐标
         */
        public static final String absUpY = "__JGC_ABS_UP_Y__";
        /**
         * 实际广告位的宽
         */
        public static final String adWidth = "__JGC_WIDTH__";
        /**
         * 实际广告位的高
         */
        public static final String adHeight = "__JGC_HEIGHT__";
        /**
         * 事件的时间宏, 单位:秒
         */
        public static final String eventTime = "__JGC_EVENT_TIME__";
        /**
         * 事件的时间宏, 单位:毫秒
         */
        public static final String eventMilliTime = "__JGC_EVENT_MILLI_TIME__";
        /**
         * 视频播放的进度宏, 单位:秒
         */
        public static final String playDuration = "__JGC_PLAY_DURATION__";
        /**
         * 视频播放的进度宏, 单位:毫秒
         */
        public static final String playMillDuration = "__JGC_PLAY_MILLI_DURATION__";
        /**
         * 视频播放到最后一帧的宏, 1:是, 0:否
         */
        public static final String playFinish = "__JGC_PLAY_FINISH__";
        /**
         * RTB竞价广告, 价格宏替换
         */
        public static final String bidPrice = "__JGC_BID_PRICE__";

        /* 下面是CPA回传需要用到的宏 */
        /**
         * 天目广告位id宏, 需要用天目广告位id替换该宏
         */
        public static final String adPositionId = "__JGC_SEAT_ID__";
        /**
         * 天目请求链路id宏, 需要用天目请求链路id替换该宏
         */
        public static final String traceId = "__JGC_BID_ID__";
        /**
         * 天目广告请求包名宏, 需要用天目广告请求包名替换该宏
         */
        public static final String packageName = "__JGC_APP_BUNDLE__";
    }
}
