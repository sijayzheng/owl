package cn.sijay.owl.system.entity;

import cn.sijay.owl.common.entity.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.fesod.sheet.annotation.ExcelIgnoreUnannotated;
import org.apache.fesod.sheet.annotation.ExcelProperty;

import java.time.LocalDateTime;
import java.util.List;
/**
 * 系统部门实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "sys_dept", comment = "部门表")
public class SysDept extends BaseEntity {

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto, comment = "主键")
    @Column(value = "id", comment = "主键")
    private Long id;

    /**
     * 父部门id
     */
    @Column(value = "parent_id", comment = "父部门id")
    @ExcelProperty(value = "父部门id")
    private Long parentId;

    /**
     * 祖级列表
     */
    @Column(value = "ancestors", comment = "祖级列表")
    @ExcelProperty(value = "祖级列表")
    private String ancestors;

    /**
     * 部门名称
     */
    @Column(value = "dept_name", comment = "部门名称")
    @ExcelProperty(value = "部门名称")
    private String deptName;

    /**
     * 部门类别
     */
    @Column(value = "dept_category", comment = "部门类别")
    @ExcelProperty(value = "部门类别")
    private Long deptCategory;

    /**
     * 显示顺序
     */
    @Column(value = "sort", comment = "显示顺序")
    @ExcelProperty(value = "显示顺序")
    private Integer sort;

    /**
     * 负责人
     */
    @Column(value = "leader", comment = "负责人")
    @ExcelProperty(value = "负责人")
    private Long leader;

    /**
     * 联系电话
     */
    @Column(value = "phone", comment = "联系电话")
    @ExcelProperty(value = "联系电话")
    private String phone;

    /**
     * 邮箱
     */
    @Column(value = "email", comment = "邮箱")
    @ExcelProperty(value = "邮箱")
    private String email;

    /**
     * 启用
     */
    @Column(value = "enabled", comment = "启用")
    @ExcelProperty(value = "启用")
    private Boolean enabled;

    /**
     * 删除
     */
    @Column(value = "deleted", comment = "删除")
    private Boolean deleted;

    /**
     * 子列表
     */
    @Column(ignore = true)
    private List<SysDept> children;

}
