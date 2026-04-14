package cn.sijay.owl.common.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

/**
 * JsonUtil
 *
 * @author sijay
 * @since 2026-04-14
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtil {
    private final static ObjectMapper OBJECT_MAPPER = SpringUtil.getBean(ObjectMapper.class);

    /**
     * 对象转 JSON 字符串
     */
    public static String toJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("对象转 JSON 失败", e);
        }
    }

    /**
     * JSON 字符串转对象
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 转对象失败", e);
        }
    }

    /**
     * JSON 转 List
     */
    public static <T> List<T> toList(String json, Class<T> elementClass) {
        try {
            return OBJECT_MAPPER.readValue(json,
                OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, elementClass));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 转 List 失败", e);
        }
    }

    /**
     * JSON 转复杂对象（如 Map、嵌套结构）
     */
    public static <T> T toComplexObject(String json, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 转复杂对象失败", e);
        }
    }

    /**
     * 指定过滤字段进行序列化
     */
    public static String toJsonWithFilter(Object obj, String filterName, String... fieldsToExclude) {
        try {
            FilterProvider filters = new SimpleFilterProvider()
                .addFilter(filterName, SimpleBeanPropertyFilter.serializeAllExcept(fieldsToExclude));
            return OBJECT_MAPPER.writer(filters).writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 序列化字段过滤失败", e);
        }
    }

    /**
     * JSON 转 JsonNode
     */
    public static JsonNode toJsonNode(String json) {
        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 转 JsonNode 失败", e);
        }
    }

    /**
     * 获取 JsonNode 中指定字段值
     */
    public static String getNodeValue(JsonNode node, String fieldName) {
        JsonNode valueNode = node.get(fieldName);
        return valueNode != null ? valueNode.asText() : null;
    }

    /**
     * 修改 JSON 中指定字段
     */
    public static String modifyNode(String json, String fieldName, Object newValue) {
        try {
            ObjectNode node = (ObjectNode) OBJECT_MAPPER.readTree(json);
            switch (newValue) {
                case String s -> node.put(fieldName, s);
                case Integer i -> node.put(fieldName, i);
                case Boolean b -> node.put(fieldName, b);
                case Double v -> node.put(fieldName, v);
                case null, default -> node.putPOJO(fieldName, newValue);
            }
            return node.toString();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("修改 JSON 节点失败", e);
        }
    }

    public static <T> Collection<T> toCollection(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}

