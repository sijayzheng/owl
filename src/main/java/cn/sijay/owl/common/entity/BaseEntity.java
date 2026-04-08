package cn.sijay.owl.common.entity;


import com.mybatisflex.annotation.Column;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 基础实体类
 * <p>
 * 所有实体类的基类，包含通用的审计字段
 *
 * @author sijay
 * @since 2026/4/8
 */
@Data
public class BaseEntity {

    /**
     * 创建部门
     */
    private Long createDept;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     * <p>
     * 插入时自动填充当前时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 更新时间
     * <p>
     * 更新时自动填充当前时间
     */
    @Column(onUpdateValue = "now()")
    private LocalDateTime updateTime;
}
