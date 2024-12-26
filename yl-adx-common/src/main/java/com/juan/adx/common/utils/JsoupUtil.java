package com.juan.adx.common.utils;

import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class JsoupUtil {

    /**
     * 使用自带的basicWithImages 白名单
     * 允许的便签有a,b,blockquote,br,cite,code,dd,dl,dt,em,i,li,ol,p,pre,q,small,span,
     * strike,strong,sub,sup,u,ul,img
     * 以及a标签的href,img标签的src,align,alt,height,width,title属性
     */
    private static final Whitelist whitelist = Whitelist.basicWithImages();
    /** 配置过滤化参数,不对代码进行格式化 */
    private static final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
    static {
        // 富文本编辑时一些样式是使用style来进行实现的
        // 比如红色字体 style="color:red;"
        // 所以需要给所有标签添加style属性
        whitelist.addAttributes(":all", "style");
    }

    public static String clean(String content) {
        String cleanedContent = Jsoup.clean(content, "", whitelist, outputSettings);
        //使用 StringEscapeUtils.unescapeHtml4 方法来将 HTML 实体编码的字符恢复为原始字符，这将防止 & 被转义为 &amp;
        return StringEscapeUtils.unescapeHtml4(cleanedContent);
    }

}
