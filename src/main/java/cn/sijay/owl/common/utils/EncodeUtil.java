package cn.sijay.owl.common.utils;


import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * EncodeUtil
 *
 * @author sijay
 * @since 2026-04-14
 */
public class EncodeUtil {
    /**
     * Base64转码
     *
     * @param data 待转码数据
     * @return 转码后字符串
     */
    public static String base64Encode(String data) {
        return base64Encode(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Base64转码
     *
     * @param data 待转码数据
     * @return 转码后字符串
     */
    public static String base64Encode(byte[] data) {
        return new String(Base64.getEncoder().encode(data), StandardCharsets.UTF_8);
    }

    /**
     * Base64解码
     *
     * @param data 待解码数据
     * @return 解码后字符串
     */
    public static byte[] base64Decode(String data) {
        return Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8));
    }
}
