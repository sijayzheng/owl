package cn.sijay.owl.system.entity;

import cn.sijay.owl.common.entity.BaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import org.apache.fesod.sheet.annotation.ExcelIgnoreUnannotated;
import org.apache.fesod.sheet.annotation.ExcelProperty;

/**
 * 参数配置实体类
 *
 * @author sijay
 * @since 2026-04-09
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "sys_config", comment = "参数配置表")
public class SysConfig extends BaseEntity {

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto, comment = "主键")
    @Column(value = "id", comment = "主键")
    private Long id;

    /**
     * 参数名称
     */
    @Column(value = "config_name", comment = "参数名称")
    @ExcelProperty(value = "参数名称")
    private String configName;

    /**
     * 参数键名
     */
    @Column(value = "config_key", comment = "参数键名")
    @ExcelProperty(value = "参数键名")
    private String configKey;

    /**
     * 参数键值
     */
    @Column(value = "config_value", comment = "参数键值")
    @ExcelProperty(value = "参数键值")
    private String configValue;

}
