package ${packageName}.${moduleName}.dto;

<#list imports as import>
import ${import};
</#list>
/**
 * ${classComment}实体类
 *
 * @author ${author}
 * @since ${date}
 */
public record ${className}Query (
<#list columns?filter(item -> item.queryable) as column>
    <#if column.queryType=='BETWEEN'>
    // ${column.columnComment}范围
    ${column.javaType.code}[] ${column.javaField}Range<#sep>,</#sep>
    <#elseif column.queryType=='IN'>
    // ${column.columnComment}列表
    List<${column.javaType.code}> ${column.javaField}s<#sep>,</#sep>
    <#else>
    ${column.javaType.code} ${column.javaField}<#sep>,</#sep>
    </#if>
</#list>
){
}
