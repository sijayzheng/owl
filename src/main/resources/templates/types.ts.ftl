export interface ${className} <#if hasBase>extends BaseEntity</#if> {
<#list columns?filter(item -> !item.based(hasBase)) as column>
  // ${column.columnComment}
  ${column.javaField}: ${column.javaType.tsType}
    <#if isTree>
  // 子列表
  children: Array<${className}>
    </#if>
</#list>
}

export interface ${className}Query {
<#list columns?filter(item -> item.queryable) as column>
    <#if column.queryType=='BETWEEN'>
  // ${column.columnComment}
  ${column.javaField}Range?: ${column.javaType.tsType}[]
    <#elseif column.queryType=='IN'>
  // ${column.columnComment}
  ${column.javaField}s?: Array<${column.javaType.tsType}>
    <#else>
  // ${column.columnComment}
  ${column.javaField}?: ${column.javaType.tsType}
    </#if>
</#list>
}

export interface ${className}Form {
<#list columns?filter(item -> item.editable) as column>
  // ${column.columnComment}
  ${column.javaField}?: ${column.javaType.tsType}
</#list>
}
