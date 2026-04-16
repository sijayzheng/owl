package cn.sijay.owl.system.entity;

import cn.sijay.owl.common.base.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.fesod.sheet.annotation.ExcelIgnoreUnannotated;
import org.apache.fesod.sheet.annotation.ExcelProperty;

/**
 * 系统岗位实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "sys_post", comment = "岗位表")
public class SysPost extends BaseEntity {

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto, comment = "主键")
    @Column(value = "id", comment = "主键")
    private Long id;

    /**
     * 部门id
     */
    @Column(value = "dept_id", comment = "部门id")
    @NotNull(message = "部门id不能为空")
    @ExcelProperty(value = "部门id")
    private Long deptId;

    /**
     * 岗位编码
     */
    @Column(value = "post_code", comment = "岗位编码")
    @NotBlank(message = "岗位编码不能为空")
    @ExcelProperty(value = "岗位编码")
    private String postCode;

    /**
     * 岗位类别编码
     */
    @Column(value = "post_category", comment = "岗位类别编码")
    @ExcelProperty(value = "岗位类别编码")
    private String postCategory;

    /**
     * 岗位名称
     */
    @Column(value = "post_name", comment = "岗位名称")
    @NotBlank(message = "岗位名称不能为空")
    @ExcelProperty(value = "岗位名称")
    private String postName;

    /**
     * 显示顺序
     */
    @Column(value = "sort", comment = "显示顺序")
    @NotNull(message = "显示顺序不能为空")
    @ExcelProperty(value = "显示顺序")
    private Integer sort;

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
