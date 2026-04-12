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
 * 字典类型实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "sys_dict_type", comment = "字典类型表")
public class SysDictType extends BaseEntity {

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto, comment = "主键")
    @Column(value = "id", comment = "主键")
    private Long id;

    /**
     * 字典名称
     */
    @Column(value = "type_name", comment = "字典名称")
    @ExcelProperty(value = "字典名称")
    private String typeName;

    /**
     * 字典编码
     */
    @Column(value = "type_code", comment = "字典编码")
    @ExcelProperty(value = "字典编码")
    private String typeCode;

}
