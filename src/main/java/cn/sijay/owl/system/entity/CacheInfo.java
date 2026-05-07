package cn.sijay.owl.system.entity;


import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * CacheInfo
 *
 * @author sijay
 * @since 2026-05-07
 */
public record CacheInfo(
    Properties info,
    Long dbSize,
    List<Map<String, String>> commandStats
) {

}
