package cn.sijay.owl.common.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * ConvertUtil
 *
 * @author sijay
 * @since 2026-04-14
 */
public class ConvertUtil {
    /**
     * 对象转换
     *
     * @param obj   待转换对象
     * @param clazz 目标类型
     */
    public static <T> T convert(Object obj, Class<T> clazz) {
        if (clazz.isInstance(obj)) {
            return clazz.cast(obj);
        }
        throw new ClassCastException("对象无法转换为 " + clazz.getName());
    }

    /**
     * 对象转换为List
     *
     * @param obj   待转换对象
     * @param clazz 目标类型
     */
    public static <T> List<T> convertToList(Object obj, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        JavaType listType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return mapper.convertValue(obj, listType);
    }
}
