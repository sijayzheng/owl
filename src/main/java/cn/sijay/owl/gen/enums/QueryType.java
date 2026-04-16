package cn.sijay.owl.gen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * QueryType
 *
 * @author sijay
 * @since 2026-04-08
 */
@Getter
@AllArgsConstructor
public enum QueryType {
    EQUALS("等于"),
    LIKE("模糊查询"),
    BETWEEN("范围查询"),
    IN("IN查询");
    private final String description;
}
