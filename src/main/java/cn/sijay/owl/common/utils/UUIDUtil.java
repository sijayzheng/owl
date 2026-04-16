package cn.sijay.owl.common.utils;

import java.util.UUID;

/**
 * UUIDUtil
 *
 * @author sijay
 * @since 2026-04-15
 */
public class UUIDUtil {
    public static String simpleUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }
}
