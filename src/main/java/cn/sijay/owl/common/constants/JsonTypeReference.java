package cn.sijay.owl.common.constants;


import tools.jackson.core.type.TypeReference;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * JsonTypeReference
 * 常用的Jackson TypeReference枚举，用于集合、Map等类型的反序列化
 *
 * @author sijay
 * @since 2026-07-27
 */
public interface JsonTypeReference {
    TypeReference<List<String>> LIST_STRING = new TypeReference<>() {
    };
    TypeReference<Map<String, String>> MAP_STRING_STRING = new TypeReference<>() {
    };
    TypeReference<List<Map<String, String>>> LIST_MAP_STRING_OBJECT = new TypeReference<>() {
    };
    TypeReference<List<LinkedHashMap<String, String>>> LIST_LINKED_HASH_MAP_STRING_OBJECT = new TypeReference<>() {
    };
}
