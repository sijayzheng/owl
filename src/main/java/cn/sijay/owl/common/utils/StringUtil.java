package cn.sijay.owl.common.utils;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * StringUtil
 *
 * @author sijay
 * @since 2026/4/8
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {
    /**
     * 默认分隔符
     */
    public static final String SEPARATOR = ",";

    /**
     * 将字符串转换为大驼峰命名法（帕斯卡命名法），默认使用下划线作为分隔符
     *
     * @param string 待转换的字符串
     * @return 大驼峰命名格式的字符串
     */
    public static String toUpperCamelCase(String string) {
        return toUpperCamelCase(string, "_");
    }

    /**
     * 将字符串转换为大驼峰命名法（帕斯卡命名法）
     *
     * @param string    待转换的字符串
     * @param delimiter 分隔符
     * @return 大驼峰命名格式的字符串
     * @example toUpperCamelCase(" hello_world ", " _ ") 返回 "HelloWorld"
     * @example toUpperCamelCase(" hello-world ", " - ") 返回 "HelloWorld"
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
     * 将字符串转换为小驼峰命名法，默认使用下划线作为分隔符
     *
     * @param string 待转换的字符串
     * @return 小驼峰命名格式的字符串
     * @example toLowerCamelCase(" hello_world ") 返回 "helloWorld"
     */
    public static String toLowerCamelCase(String string) {
        return StringUtils.uncapitalize(toUpperCamelCase(string, "_"));
    }

    /**
     * 将字符串转换为小驼峰命名法
     *
     * @param string    待转换的字符串
     * @param delimiter 分隔符
     * @return 小驼峰命名格式的字符串
     * @example toLowerCamelCase(" hello-world ", " - ") 返回 "helloWorld"
     */
    public static String toLowerCamelCase(String string, String delimiter) {
        return StringUtils.uncapitalize(toUpperCamelCase(string, delimiter));
    }

    /**
     * 将字符串转换为小写蛇形命名法（下划线分隔），默认不使用额外分隔符
     *
     * @param string 待转换的字符串
     * @return 小写蛇形命名格式的字符串
     * @example toLowerSnakeCase(" HelloWorld ") 返回 "hello_world"
     */
    public static String toLowerSnakeCase(String string) {
        return toLowerSnakeCase(string, "");
    }

    /**
     * 将字符串转换为小写蛇形命名法（下划线分隔）
     *
     * @param string    待转换的字符串
     * @param delimiter 额外的分隔符
     * @return 小写蛇形命名格式的字符串
     * @example toLowerSnakeCase(" HelloWorld ", " ") 返回 "hello_world"
     * @example toLowerSnakeCase(" Hello-World ", " - ") 返回 "hello_world"
     */
    public static String toLowerSnakeCase(String string, String delimiter) {
        if (StringUtils.isBlank(string)) {
            return string;
        }
        return string.replaceAll("([a-z])" + delimiter + "([A-Z])", "$1_$2").toLowerCase();
    }

    /**
     * 将字符串转换为大写蛇形命名法（下划线分隔），默认不使用额外分隔符
     *
     * @param string 待转换的字符串
     * @return 大写蛇形命名格式的字符串
     * @example toUpperSnakeCase(" HelloWorld ") 返回 "HELLO_WORLD"
     */
    public static String toUpperSnakeCase(String string) {
        return toUpperSnakeCase(string, "");
    }

    /**
     * 将字符串转换为大写蛇形命名法（下划线分隔）
     *
     * @param string    待转换的字符串
     * @param delimiter 额外的分隔符
     * @return 大写蛇形命名格式的字符串
     * @example toUpperSnakeCase(" HelloWorld ", " ") 返回 "HELLO_WORLD"
     * @example toUpperSnakeCase(" Hello-World ", " - ") 返回 "HELLO_WORLD"
     */
    public static String toUpperSnakeCase(String string, String delimiter) {
        if (StringUtils.isBlank(string)) {
            return string;
        }
        return string.replaceAll("([a-z])" + delimiter + "([A-Z])", "$1_$2").toUpperCase();
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
     * @example format(" Hello { }, you are { } years old ", " 张三 ", 25) 返回 "Hello 张三, you are 25 years old"
     * @example format(" Hello { 0 }, you are { 1 } years old ", " 张三 ", 25) 返回 "Hello 张三, you are 25 years old"
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

}
