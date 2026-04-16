package cn.sijay.owl.common.utils;

import io.swagger.v3.core.util.Constants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    static final String[] CHARSET_ARR = {"UTF-8", "GB2312", "GBK", "Windows-1252", "ISO8859-1"};

    /**
     * 格式化字符串消息
     * 支持两种格式化方式：
     * 1. 占位符格式：使用{}作为占位符，按顺序替换参数
     * 2. 数组参数格式：使用{0},{1}等索引占位符
     *
     * @param message 消息模板
     * @param args    格式化参数（可变参数）
     * @return 格式化后的字符串
     * <code>format("Hello {}, you are {} years old","张三", 25)</code>返回"Hello 张三, you are 25 years old"
     * <code>format("Hello {0}, you are {1} years old","张三", 25)</code>返回"Hello 张三, you are 25 years old"
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

    /**
     * 数字左边补齐0，使之达到指定长度。注意，如果数字转换为字符串后，长度大于size，则只保留 最后size个字符。
     *
     * @param num  数字对象
     * @param size 字符串指定长度
     * @return 返回数字的字符串格式，该字符串为指定长度。
     */
    public static String padl(final Number num, final int size) {
        return padl(num.toString(), size, '0');
    }

    /**
     * 字符串左补齐。如果原始字符串s长度大于size，则只保留最后size个字符。
     *
     * @param s    原始字符串
     * @param size 字符串指定长度
     * @param c    用于补齐的字符
     * @return 返回指定长度的字符串，由原字符串左补齐或截取得到。
     */
    public static String padl(final String s, final int size, final char c) {
        final StringBuilder sb = new StringBuilder(size);
        if (s != null) {
            final int len = s.length();
            if (s.length() <= size) {
                sb.repeat(String.valueOf(c), size - len);
                sb.append(s);
            } else {
                return s.substring(len - size, len);
            }
        } else {
            sb.repeat(String.valueOf(c), Math.max(0, size));
        }
        return sb.toString();
    }

    /**
     * 切分字符串(分隔符默认逗号)
     *
     * @param str 被切分的字符串
     * @return 分割后的数据列表
     */
    public static List<String> splitList(String str) {
        return splitList(str, Constants.COMMA);
    }

    /**
     * 切分字符串
     *
     * @param str       被切分的字符串
     * @param separator 分隔符
     * @return 分割后的数据列表
     */
    public static List<String> splitList(String str, String separator) {
        return Arrays.stream(str.split(separator))
                     .toList();
    }

    /**
     * 切分字符串自定义转换(分隔符默认逗号)
     *
     * @param str    被切分的字符串
     * @param mapper 自定义转换
     * @return 分割后的数据列表
     */
    public static <T> List<T> splitTo(String str, Function<? super Object, T> mapper) {
        return splitTo(str, SEPARATOR, mapper);
    }

    /**
     * 切分字符串自定义转换
     *
     * @param str       被切分的字符串
     * @param separator 分隔符
     * @param mapper    自定义转换
     * @return 分割后的数据列表
     */
    public static <T> List<T> splitTo(String str, String separator, Function<? super Object, T> mapper) {
        if (StringUtils.isBlank(str)) {
            return new ArrayList<>(0);
        }
        return splitList(str, separator)
            .stream()
            .filter(Objects::nonNull)
            .map(mapper)
            .collect(Collectors.toList());
    }

    public static String unicode2String(String unicode) {
        if (StringUtils.isBlank(unicode)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int i;
        int pos = 0;

        while ((i = unicode.indexOf("\\u", pos)) != -1) {
            sb.append(unicode, pos, i);
            if (i + 5 < unicode.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(unicode.substring(i + 2, i + 6), 16));
            }
        }
        //如果pos位置后，有非中文字符，直接添加
        sb.append(unicode.substring(pos));
        return sb.toString();
    }

    public static void testAllCharset(String text) throws UnsupportedEncodingException {
        if (text == null || text.isEmpty()) {
            System.out.println("文本不能为空");
            return;
        }
        System.out.println("假设当前编码       假设原始编码          编码后的内容");
        for (String curCharset : CHARSET_ARR) {
            byte[] btArr = text.getBytes(curCharset);
            for (String originCharset : CHARSET_ARR) {
                if (originCharset.equals(curCharset)) {
                    continue;
                }
                String encodeText = new String(btArr, originCharset);
                System.out.println(curCharset + "___" + originCharset + "___" + encodeText);
            }
        }
    }

    public static boolean isHttp(String path) {
        return StringUtils.isNotBlank(path) && new UrlValidator(new String[]{"http", "https"}).isValid(path);
    }
}
