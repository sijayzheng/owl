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
    /**
     * 其它
     */
    OTHER("其它"),

    /**
     * 查询
     */
    QUERY("查询"),

    /**
     * 新增
     */
    ADD("新增"),

    /**
     * 新增
     */
    INSERT("新增"),

    /**
     * 修改
     */
    EDIT("修改"),

    /**
     * 修改
     */
    UPDATE("修改"),

    /**
     * 删除
     */
    DELETE("删除"),

    /**
     * 授权
     */
    GRANT("授权"),

    /**
     * 导出
     */
    EXPORT("导出"),

    /**
     * 导入
     */
    IMPORT("导入"),

    /**
     * 强退
     */
    FORCE("强退"),

    /**
     * 代码生成
     */
    GEN_CODE("代码生成"),

    /**
     * 数据清空
     */
    CLEAN("数据清空"),
    ;
    private final String description;
}
