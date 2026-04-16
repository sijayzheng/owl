package cn.sijay.owl.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * MenuType
 *
 * @author sijay
 * @since 2026-04-13
 */
@Getter
@AllArgsConstructor
public enum MenuType {
    DIRECTORY("目录"),
    MENU("菜单"),
    BUTTON("按钮"),
    ;
    private final String desc;
}
