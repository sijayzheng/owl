package ${packageName}.${moduleName}.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import ${packageName}.${moduleName}.entity.${className};
import ${packageName}.${moduleName}.mapper.${className}Mapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static ${packageName}.${moduleName}.entity.table.${className}TableDef.${tableDef};

/**
 * ${classComment}服务类
 * 提供${classComment}的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ${className}Service extends ServiceImpl<${className}Mapper, ${className}> implements IService<${className}> {
    private final ${className}Mapper ${functionName}Mapper;

    /**
     * 分页查询${classComment}
     *
     * @param pageQuery 分页参数
     * @param ${functionName} 查询条件
     * @return ${classComment}分页数据
     */
    public Page<${className}> page(PageQuery pageQuery, ${className} ${functionName}) {
        return page(pageQuery.build(), pageQuery.setOrder(query(${functionName})));
    }

    /**
     * 构建查询条件
     *
     * @param ${functionName} 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(${className} ${functionName}) {
        QueryWrapper query = query();
<#list columns?filter(item -> item.queryable) as column>
    <#if column.queryType=="EQUALS">
        query.and(${tableDef}.${column.columnName?upper_case}.eq(${functionName}.get${column.javaField?cap_first}()));
    <#elseif column.queryType=="LIKE">
        query.and(${tableDef}.${column.columnName?upper_case}.like(${functionName}.get${column.javaField?cap_first}()));
    <#elseif column.queryType=="BETWEEN">
        query.and(${tableDef}.${column.columnName?upper_case}.between(${functionName}.get${column.javaField?cap_first}Range()));
    <#elseif column.queryType=="IN">
        query.and(${tableDef}.${column.columnName?upper_case}.in(${functionName}.get${column.javaField?cap_first}s()));
    </#if>
</#list>
        return query;
    }

    /**
     * 查询${classComment}列表
     *
     * @param ${functionName} 查询条件
     * @return ${classComment}列表
     */
    public List<${className}> list(${className} ${functionName}) {
        return list(query(${functionName}));
    }

    /**
     * 删除${classComment}
     *
     * @param id ${classComment}ID
     * @return 删除结果
     * @throws ServiceException 当${classComment}不存在时抛出异常
     */
    public boolean delete(Long id) {
        ${className} user = getById(id);
        if (user == null) {
            throw new ServiceException(${className}Service.class, "主键为{}的${classComment}不存在", id);
        }
        return removeById(id);
    }
}
