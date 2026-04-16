package cn.sijay.owl.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * DateTimeUtil
 *
 * @author sijay
 * @since 2026-04-15
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeUtil {
    static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    static final String DATE_FORMAT = "yyyy-MM-dd";
    static final String TIME_FORMAT = "HH:mm:ss";
    static final String DATE_FILE_NAME_FORMAT = "yyyyMMdd";
    static final String DATE_TIME_FILE_NAME_FORMAT = "yyyyMMddHHmmss";

    public static String now() {
        return now(DATE_TIME_FORMAT);
    }

    public static String now(String format) {
        return now(format, Locale.CHINA);
    }

    public static String now(String format, Locale locale) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format, locale));
    }

    public static String nowTime() {
        return nowTime(TIME_FORMAT);
    }

    public static String nowTime(String format) {
        return nowTime(format, Locale.CHINA);
    }

    public static String nowTime(String format, Locale locale) {
        return now(format, locale);
    }

    public static String nowDate() {
        return nowDate(DATE_FORMAT);
    }

    public static String nowDate(String format) {
        return nowDate(format, Locale.CHINA);
    }

    public static String nowDate(String format, Locale locale) {
        return now(format, locale);
    }

    public static String nowFile() {
        return now(DATE_TIME_FILE_NAME_FORMAT);
    }
}
