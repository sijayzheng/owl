package cn.sijay.owl.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * GenderType
 *
 * @author sijay
 * @since 2026-04-13
 */
@Getter
@AllArgsConstructor
public enum GenderType {
    UNKNOWN("未知"),
    MALE("男性"),
    FEMALE("女性"),
    ;
    private final String description;
}
