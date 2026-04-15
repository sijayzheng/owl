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

    // 字节数组转十六进制字符串
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xFF));
        }
        return sb.toString();
    }

    //十六进制字符串转字节数组
    public static byte[] hexToBytes(String hex) {
        int length = hex.length();
        if (length % 2 != 0) {
            throw new IllegalArgumentException("hex需要偶数位长度");
        }
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            String hexByte = hex.substring(i, i + 2);
            bytes[i / 2] = (byte) Integer.parseInt(hexByte, 16);
        }
        return bytes;
    }
}
