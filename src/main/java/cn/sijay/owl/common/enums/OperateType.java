package cn.sijay.owl.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * OperateType
 *
 * @author sijay
 * @since 2026-04-08
 */
@AllArgsConstructor
@Getter
public enum OperateType {
    OTHER("其它"),
    QUERY("查询"),
    ADD("新增"),
    INSERT("新增"),
    EDIT("修改"),
    UPDATE("修改"),
    DELETE("删除"),
    GRANT("授权"),
    EXPORT("导出"),
    IMPORT("导入"),
    FORCE("强退"),
    LOGIN("登录"),
    LOGOUT("登出"),
    GEN("生成"),
    CLEAN("数据清空"),
    ;
    private final String description;
}
