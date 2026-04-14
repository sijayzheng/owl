package cn.sijay.owl.common.utils;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * StringUtil
 *
 * @author sijay
 * @since 2026-04-08
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {
    /**
     * 默认分隔符
     */
    public static final String SEPARATOR = ",";

    /**
     * 将字符串转换为小驼峰命名
     *
     * @param string    待转换的字符串
     * @param delimiter 分隔符
     * @return 小驼峰命名格式的字符串
     * <code>toLowerCamelCase("hello-world","-")</code>返回"helloWorld"
     */
    public static String toLowerCamelCase(String string, String delimiter) {
        return StringUtils.uncapitalize(toUpperCamelCase(string, delimiter));
    }

    /**
     * 将字符串转换为大驼峰命名（帕斯卡命名法）
     *
     * @param string    待转换的字符串
     * @param delimiter 分隔符
     * @return 大驼峰命名格式的字符串
     * <code>toUpperCamelCase("hello_world","_")</code>返回"HelloWorld"
     * <code>toUpperCamelCase("hello-world","-")</code>返回"HelloWorld"
     */
    public static String toUpperCamelCase(String string, String delimiter) {
        if (StringUtils.isBlank(string)) {
            return string;
        }
        string = string.toLowerCase();
        StringBuilder builder = new StringBuilder();
        for (String s : string.split(delimiter)) {
            builder.append(StringUtils.capitalize(s));
        }
        return builder.toString();
    }

    /**
     * 将字符串转换为小写蛇形命名（下划线分隔）
     *
     * @param string    待转换的字符串
     * @param delimiter 额外的分隔符
     * @return 小写蛇形命名格式的字符串
     * <code>toLowerSnakeCase("HelloWorld","")</code>返回"hello_world"
     * <code>toLowerSnakeCase("Hello-World","-")</code>返回"hello_world"
     */
    public static String toLowerSnakeCase(String string, String delimiter) {
        if (StringUtils.isBlank(string)) {
            return string;
        }
        return string.replaceAll("([a-z])" + delimiter + "([A-Z])", "$1_$2").toLowerCase();
    }

    /**
     * 将字符串转换为大写蛇形命名（下划线分隔）
     *
     * @param string    待转换的字符串
     * @param delimiter 额外的分隔符
     * @return 大写蛇形命名格式的字符串
     * <code>toUpperSnakeCase("HelloWorld","")</code>返回"HELLO_WORLD"
     * <code>toUpperSnakeCase("Hello-World","-")</code>返回"HELLO_WORLD"
     */
    public static String toUpperSnakeCase(String string, String delimiter) {
        if (StringUtils.isBlank(string)) {
            return string;
        }
        return toUpperSnakeCase(string, delimiter).toUpperCase();
    }

    /**
     * 将字符串转换为小写短横线命名（短横线分隔）
     *
     * @param string    待转换的字符串
     * @param delimiter 额外的分隔符
     * @return 小写蛇形命名格式的字符串
     * <code>toLowerSnakeCase("HelloWorld","")</code>返回"hello_world"
     * <code>toLowerSnakeCase("Hello-World","-")</code>返回"hello_world"
     */
    public static String toLowerKebabCase(String string, String delimiter) {
        if (StringUtils.isBlank(string)) {
            return string;
        }
        return string.replaceAll("([a-z])" + delimiter + "([A-Z])", "$1-$2").toLowerCase();
    }

    /**
     * 将字符串转换为大写短横线命名（短横线分隔）
     *
     * @param string    待转换的字符串
     * @param delimiter 额外的分隔符
     * @return 大写蛇形命名格式的字符串
     * <code>toUpperSnakeCase("HelloWorld","")</code>返回"HELLO_WORLD"
     * <code>toUpperSnakeCase("Hello-World","-")</code>返回"HELLO_WORLD"
     */
    public static String toUpperKebabCase(String string, String delimiter) {
        if (StringUtils.isBlank(string)) {
            return string;
        }
        return toLowerKebabCase(string, delimiter).toUpperCase();
    }

    /**
     * 格式化字符串消息
     * 支持两种格式化方式：
     * 1. 占位符格式：使用{}作为占位符，按顺序替换参数
     * 2. 数组参数格式：使用{0},{1}等索引占位符
     *
     * @param message 消息模板
     * @param args    格式化参数（可变参数）
     * @return 格式化后的字符串
     * <code>format("Hello { }, you are { } years old","张三", 25)</code>返回"Hello 张三, you are 25 years old"
     * <code>format("Hello { 0 }, you are { 1 } years old","张三", 25)</code>返回"Hello 张三, you are 25 years old"
     */
    public static String format(String message, Object... args) {
        if (StringUtils.isBlank(message) || args == null || args.length == 0) {
            return message;
        }

        String result = message;

        // 首先尝试使用索引占位符 {0}, {1}, {2}...
        for (int i = 0; i < args.length; i++) {
            String placeholder = "{" + i + "}";
            if (result.contains(placeholder)) {
                result = result.replace(placeholder, String.valueOf(args[i]));
            }
        }

        // 然后处理简单的占位符 {}
        for (Object arg : args) {
            result = result.replaceFirst("\\{}", String.valueOf(arg));
        }

        return result;
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
