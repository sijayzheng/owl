package ${packageName}.${moduleName}.entity;

<#if hasBase>import cn.sijay.owl.common.entity.BaseEntity;</#if>
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.fesod.sheet.annotation.ExcelIgnoreUnannotated;
import org.apache.fesod.sheet.annotation.ExcelProperty;

<#list imports as import>
import ${import};
</#list>
<#if !hasBase>
import java.io.Serial;
import java.io.Serializable;
</#if>
<#if isTree>
import java.util.List;
</#if>
/**
 * ${classComment}实体类
 *
 * @author ${author}
 * @since ${date}
 */
@ExcelIgnoreUnannotated
@Data
@Table(value = "${tableName}", comment = "${tableComment}")
public class ${className} <#if hasBase>extends BaseEntity<#else>implements Serializable</#if> {

<#if !hasBase>
    @Serial
    private static final long serialVersionUID = 1L;

</#if>
<#list columns?filter(item -> !item.based()) as column>
    /**
     * ${column.columnComment}
     */
    <#if column.primaryKey>
    @Id(<#if column.incremental>keyType = KeyType.Auto, </#if>comment = "${column.shotComment()}")
    </#if>
    @Column(value = "${column.columnName}", comment = "${column.shotComment()}"<#if column.javaField == 'version'>, version = true</#if>)
    <#if !column.primaryKey>
        <#if column.required>
            <#if column.javaType == 'STRING'>
    @NotBlank(message = "${column.shotComment()}不能为空")
            <#else>
    @NotNull(message = "${column.shotComment()}不能为空")
            </#if>
        </#if>
        <#if column.export()>
            <#if (column.columnOption.dictCode())?has_content>
    @ExcelProperty(value = "${column.shotComment()}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictCode = "${column.columnOption.dictCode}")
            <#else>
    @ExcelProperty(value = "${column.shotComment()}")
            </#if>
        </#if>
    </#if>
    private ${column.javaType.code} ${column.javaField};

</#list>
<#if isTree>
    /**
     * 子列表
     */
    @Column(ignore = true)
    private List<${className}> children;

</#if>
}
