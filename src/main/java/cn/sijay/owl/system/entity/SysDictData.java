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
 * 字典数据实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "sys_dict_data", comment = "字典数据表")
public class SysDictData extends BaseEntity {

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto, comment = "主键")
    @Column(value = "id", comment = "主键")
    private Long id;

    /**
     * 字典类型id
     */
    @Column(value = "dict_type_id", comment = "字典类型id")
    @NotNull(message = "字典类型id不能为空")
    @ExcelProperty(value = "字典类型id")
    private Long dictTypeId;

    /**
     * 字典编码
     */
    @Column(value = "dict_code", comment = "字典编码")
    @NotBlank(message = "字典编码不能为空")
    @ExcelProperty(value = "字典编码")
    private String dictCode;

    /**
     * 字典标签
     */
    @Column(value = "dict_label", comment = "字典标签")
    @ExcelProperty(value = "字典标签")
    private String dictLabel;

    /**
     * 字典键值
     */
    @Column(value = "dict_value", comment = "字典键值")
    @ExcelProperty(value = "字典键值")
    private String dictValue;

    /**
     * 字典排序
     */
    @Column(value = "sort", comment = "字典排序")
    @ExcelProperty(value = "字典排序")
    private Integer sort;

    /**
     * 样式属性
     */
    @Column(value = "css_class", comment = "样式属性")
    @ExcelProperty(value = "样式属性")
    private String cssClass;

    /**
     * 表格回显样式
     */
    @Column(value = "list_class", comment = "表格回显样式")
    @ExcelProperty(value = "表格回显样式")
    private String listClass;

    /**
     * 是否默认
     */
    @Column(value = "defaulted", comment = "是否默认")
    @ExcelProperty(value = "是否默认")
    private Boolean defaulted;

    /**
     * 启用
     */
    @Column(value = "enabled", comment = "启用")
    @ExcelProperty(value = "启用")
    private Boolean enabled;

}
