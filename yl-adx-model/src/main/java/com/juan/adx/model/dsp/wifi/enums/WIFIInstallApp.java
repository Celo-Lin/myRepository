package com.juan.adx.model.dsp.wifi.enums;

import com.juan.adx.model.dsp.wifi.WifiDspProtobuf;
import lombok.Getter;
import lombok.Setter;

/**
 * @author： HeWen Zhou
 * @date： 2024/5/30 14:54
 */
public enum WIFIInstallApp {
    JINGDONG("京东", "com.jingdong.app.mall", 10001),
    VIPSHOP("唯品会", "com.achievo.vipshop", 10002),
    TAOBAO("淘宝", "com.taobao.taobao", 10003),
    BAIDU_SEARCHBOX("手机百度", "com.baidu.searchbox", 10004),
    CTRIP("携程旅行", "ctrip.android.view", 10005),
    QUNAR("去哪儿旅行", "com.Qunar", 10006),
    MEITUAN("美团", "com.sankuai.meituan", 10007),
    DIANPING("大众点评", "com.dianping.v1", 10008),
    HOMELINK("链家", "com.homelink.android", 10009),
    PINDUODUO("拼多多", "com.xunmeng.pinduoduo", 10010),
    XIAOHONGSHU("小红书", "com.xingin.xhs", 10011),
    BEIKE("贝壳", "com.lianjia.beike", 10012),
    ANJUKE("安居客", "com.anjuke.android.app", 10013),
    KUAISHOU("快手", "com.smile.gifmaker", 10014),
    TANTAN("探探", "com.p1.mobile.putong", 10015),
    MAIMAI("脉脉", "com.taou.maimai", 10016),
    MOMO("陌陌", "com.immomo.momo", 10017),
    AUTOHOME("汽车之家", "com.cubic.autohome", 10018),
    XCAR("爱卡汽车", "com.xcar.activity", 10019),
    ALIBABA1688("1688", "com.alibaba.wireless", 10020),
    ELEME("饿了么", "me.ele", 10021),
    BAIDU_MINIVIDEO("全民小视频", "com.baidu.minivideo", 10022),
    WEIBO("新浪微博", "com.sina.weibo", 10023),
    BAIDU_LITE("百度极速版", "com.baidu.searchbox.lite", 10024),
    ZHIHU("知乎", "com.zhihu.android", 10025),
    IQIYI("爱奇艺", "com.qiyi.video", 10026),
    WECHAT("微信", "com.tencent.mm", 10027),
    YY("YY语音", "com.duowan.mobile", 10028),
    DOUYIN("抖音", "com.ss.android.ugc.aweme", 10029),
    QQ_BROWSER("QQ浏览器", "com.tencent.mtt", 10030),
    KUAIXIAO("七猫免费小说", "com.kmxs.reader", 10031),
    BILIBILI("哔哩哔哩", "tv.danmaku.bili", 10032),
    XIGUASHIPIN("西瓜视频", "com.ss.android.article.video", 10033),
    QU_TOUTIAO("趣头条", "com.jifen.qukan", 10034),
    MIDU("米读极速版", "com.lechuan.mdwz", 10035),
    YIDUI("伊对", "me.yidui", 10036),
    YICHE("易车", "com.yiche.autoeasy", 10037),
    KUAISHOU_LITE("快手极速版", "com.kuaishou.nebula", 10038),
    ALIPAY("支付宝", "com.eg.android.AlipayGphone", 10039),
    TENCENT_NEWS("腾讯新闻", "com.tentcent.news", 10040),
    TENCENT_VIDEO("腾讯视频", "com.tencent.qqlive", 10041),
    YOUKU("优酷视频", "com.youku.phone", 10042),
    DOUYIN_LITE("抖音极速版", "com.ss.android.ugc.aweme.lite", 10043),
    INKE("映客", "com.meelive.ingkee", 10044),
    TODAY_HEADLINE_LITE("今日头条极速版", "com.ss.android.article.lite", 10045),
    TODAY_HEADLINE("今日头条", "com.ss.android.article.news", 10046),
    MEITUAN_WAIMAI("美团外卖", "com.sankuai.meituan.takeoutnew", 10047),
    IDLEFISH("闲鱼", "com.taobao.idlefish", 10048),
    MOSHENG("陌声", "com.mosheng", 10049),
    AUTONAVI("高德地图", "com.autonavi.minimap", 10050),
    UC_BROWSER("UC浏览器", "com.UCMobile", 10051),
    LITETAO("手淘特价版", "com.taobao.litetao", 10052),
    DIDI("滴滴", "com.sdu.didi.psnger", 10053),
    APP_DOWNLOADER("腾讯应用宝", "com.tencent.android.qqdownloader", 10054),
    HUOSHAN("抖音火山版", "com.ss.android.ugc.live", 10055),
    TMALL("天猫商城", "com.tmall.wireless", 10056),
    DINGTALK("钉钉", "com.alibaba.android.rimet", 10057),
    SHUQI("书旗小说", "com.shuqi.controller", 10058),
    DIANTAO("点淘", "com.taobao.live", 10059),
    FANQIE("番茄畅听", "com.xs.fm", 10060),
    FANQIE_XIAOSHUO("番茄小说", "com.dragon.read", 10061),
    DOUYU("斗鱼", "air.tv.douyu.android", 10062),
    U_MONEY("有钱花", "com.duxiaoman.umoney", 10063),
    XIMALAYA("喜马拉雅", "com.ximalaya.ting.android", 10064),
    DEWU("得物(毒)", "com.shizhuang.duapp", 10065),
    DONGCHEDI("懂车帝", "com.ss.android.auto", 10066);

    @Getter
    @Setter
    private String desc;

    @Getter
    @Setter
    private String pkgName;

    @Getter
    @Setter
    private int id;

    WIFIInstallApp(String desc, String pkgName, int id) {
        this.id = id;
        this.pkgName = pkgName;
        this.desc = desc;
    }

    public static WIFIInstallApp getByPkgName(String pkgName) {
        if (pkgName == null) {
            return null;
        }
        for (WIFIInstallApp type : values()) {
            if (type.getPkgName().equals(pkgName)) {
                return type;
            }
        }
        return null;
    }

}
