package cn.sijay.owl.system.dto;

/**
 * 参数配置实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
public record SysConfigQuery(
    String configName,
    String configKey
) {
}
