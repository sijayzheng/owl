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
/**
 * 角色实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "sys_role", comment = "角色表")
public class SysRole extends BaseEntity {

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto, comment = "主键")
    @Column(value = "id", comment = "主键")
    private Long id;

    /**
     * 角色名称
     */
    @Column(value = "role_name", comment = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    @ExcelProperty(value = "角色名称")
    private String roleName;

    /**
     * 角色权限字符串
     */
    @Column(value = "role_code", comment = "角色权限字符串")
    @NotBlank(message = "角色权限字符串不能为空")
    @ExcelProperty(value = "角色权限字符串")
    private String roleCode;

    /**
     * 显示顺序
     */
    @Column(value = "sort", comment = "显示顺序")
    @NotNull(message = "显示顺序不能为空")
    @ExcelProperty(value = "显示顺序")
    private Integer sort;

    /**
     * 数据权限
     */
    @Column(value = "data_scope", comment = "数据权限")
    @ExcelProperty(value = "数据权限")
    private String dataScope;

    /**
     * 菜单树选择项关联显示
     */
    @Column(value = "menu_check_strictly", comment = "菜单树选择项关联显示")
    @ExcelProperty(value = "菜单树选择项关联显示")
    private Boolean menuCheckStrictly;

    /**
     * 部门树选择项关联显示
     */
    @Column(value = "dept_check_strictly", comment = "部门树选择项关联显示")
    @ExcelProperty(value = "部门树选择项关联显示")
    private Boolean deptCheckStrictly;

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

}
