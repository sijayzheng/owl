package cn.sijay.owl.common.utils;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

/**
 * XssUtil
 * XSS 过滤工具类
 *
 * @author sijay
 * @since 2026-04-14
 */
public class XssUtil {

    /**
     * 使用 Jsoup 白名单清洗 HTML
     * Safelist.none() 会移除所有 HTML 标签和属性，只保留纯文本
     */
    public static String clean(String value) {
        if (value == null) {
            return null;
        }
        // 可根据业务需求调整白名单，例如 Safelist.basic() 允许部分格式标签
        return Jsoup.clean(value, Safelist.none());
    }

    /**
     * 转义 HTML 特殊字符（备选方案，防止 XSS 同时保留原始输入形状）
     */
    public static String escapeHtml(String value) {
        if (value == null) {
            return null;
        }
        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#x27;")
                    .replace("/", "&#x2F;");
    }
}
