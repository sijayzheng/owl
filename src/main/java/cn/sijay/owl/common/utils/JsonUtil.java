package cn.sijay.owl.common.utils;

import cn.sijay.owl.common.constants.ErrorConstants;
import cn.sijay.owl.common.exceptions.BaseException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ObjectNode;
import tools.jackson.databind.ser.FilterProvider;
import tools.jackson.databind.ser.std.SimpleBeanPropertyFilter;
import tools.jackson.databind.ser.std.SimpleFilterProvider;

import java.util.Objects;

/**
 * JsonUtil
 *
 * @author sijay
 * @since 2026-04-14
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtil {
    private final static JsonMapper MAPPER = SpringUtil.getBean(JsonMapper.class);

    /**
     * 对象转 JSON 字符串
     */
    public static String toJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JacksonException e) {
            throw new RuntimeException(ErrorConstants.JSON_SERIAL_ERROR, e);
        }
    }

    public static String toPrettyJson(Object object) {
        if (Objects.isNull(object)) {
            return null;
        }
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JacksonException e) {
            throw new BaseException(ErrorConstants.JSON_SERIAL_ERROR, e.getMessage());
        }
    }

    /**
     * 反序列化为指定类型对象(仅支持非泛型根类型)
     *
     * @param json  JSON字符串
     * @param clazz 目标类型
     * @param <T>   类型参数
     * @return 反序列化后的对象
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (JacksonException e) {
            log.error("反序列化失败: {}", e.getMessage(), e);
            throw new RuntimeException("反序列化失败: " + e.getMessage(), e);
        }
    }

    /**
     * 反序列化，根据返回值类型，使用TypeReference为指定类型对象
     *
     * @param json JSON字符串
     * @param <T>  类型参数
     * @return 反序列化后的对象
     */
    public static <T> T parseObject(String json, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (JacksonException e) {
            log.error("反序列化失败: {}", e.getMessage(), e);
            throw new RuntimeException("反序列化失败: " + e.getMessage(), e);
        }
    }

    /**
     * 反序列化，根据返回值类型，使用TypeReference为指定类型对象
     *
     * @param json JSON字符串
     * @param <T>  类型参数
     * @return 反序列化后的对象
     */
    public static <T> T parseObject(String json) {
        try {
            return MAPPER.readValue(json, new TypeReference<>() {
            });
        } catch (JacksonException e) {
            log.error("反序列化失败: {}", e.getMessage(), e);
            throw new RuntimeException("反序列化失败: " + e.getMessage(), e);
        }
    }

    /**
     * 指定过滤字段进行序列化
     */
    public static String toJsonWithFilter(Object obj, String filterName, String... fieldsToExclude) {
        try {
            FilterProvider filters = new SimpleFilterProvider()
                .addFilter(filterName, SimpleBeanPropertyFilter.serializeAllExcept(fieldsToExclude));
            return MAPPER.writer(filters).writeValueAsString(obj);
        } catch (JacksonException e) {
            throw new RuntimeException(ErrorConstants.JSON_SERIAL_ERROR, e);
        }
    }

    /**
     * JSON 转 JsonNode
     */
    public static JsonNode toJsonNode(String json) {
        try {
            return MAPPER.readTree(json);
        } catch (JacksonException e) {
            throw new RuntimeException(ErrorConstants.JSON_PARSE_ERROR, e);
        }
    }

    /**
     * 获取 JsonNode 中指定字段值
     */
    public static String getNodeValue(JsonNode node, String fieldName) {
        JsonNode valueNode = node.get(fieldName);
        return valueNode != null ? valueNode.asString() : null;
    }

    /**
     * 修改 JSON 中指定字段
     */
    public static String modifyNode(String json, String fieldName, Object newValue) {
        try {
            ObjectNode node = (ObjectNode) MAPPER.readTree(json);
            switch (newValue) {
                case String s -> node.put(fieldName, s);
                case Integer i -> node.put(fieldName, i);
                case Boolean b -> node.put(fieldName, b);
                case Double v -> node.put(fieldName, v);
                case null, default -> node.putPOJO(fieldName, newValue);
            }
            return node.toString();
        } catch (JacksonException e) {
            throw new RuntimeException("修改 JSON 节点失败", e);
        }
    }

}

