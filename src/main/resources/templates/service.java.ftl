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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

<#if isTree>
    /**
     * 查询树形结构数据
     *
     * @return 树形结构数据列表
     */
    public List<${className}> getTree() {
        return buildTree(list());
    }

    /**
     * 构建树形结构
     *
     * @param list 所有数据列表
     * @return 树形结构列表
     */
    private List<${className}> buildTree(List<${className}> list) {
        List<${className}> result = new ArrayList<>();
        List<Long> ids = list.stream().map(${className}::get${treeKey?cap_first}).toList();
        for (${className} item : list) {
            if (item.get${treeParentKey?cap_first}() == null || Objects.equals(0L, item.get${treeParentKey?cap_first}()) || !ids.contains(item.get${treeParentKey?cap_first}())) {
                recursionFn(list, item);
                result.add(item);
            }
        }
        if (result.isEmpty()) {
            result = list;
        }
        return result;
    }

    /**
     * 递归构建子树
     *
     * @param list   所有数据列表
     * @param parent 父节点
     */
    private void recursionFn(List<${className}> list, ${className} parent) {
        List<${className}> childList = getChildList(list, parent);
        parent.setChildren(childList);
        for (${className} child : childList) {
            if (hasChild(list, child)) {
                recursionFn(list, child);
            }
        }
    }

    /**
     * 获取子节点列表
     *
     * @param list   所有数据列表
     * @param parent 父节点
     * @return 子节点列表
     */
    private List<${className}> getChildList(List<${className}> list, ${className} parent) {
        return list.stream()
                   .filter(item -> item.get${treeParentKey?cap_first}() != null && item.get${treeParentKey?cap_first}().equals(parent.get${treeKey?cap_first}()))
                   .collect(Collectors.toList());
    }

    /**
     * 判断是否有子节点
     *
     * @param list 所有数据列表
     * @param node 当前节点
     * @return 是否有子节点
     */
    private boolean hasChild(List<${className}> list, ${className} node) {
        return list.stream().anyMatch(item -> item.get${treeParentKey?cap_first}() != null && item.get${treeParentKey?cap_first}().equals(node.get${treeKey?cap_first}()));
    }
<#else>
    /**
     * 分页查询${classComment}
     *
     * @param pageQuery       分页参数
     * @param ${functionName} 查询条件
     * @return ${classComment}分页数据
     */
    public Page<${className}> page(PageQuery pageQuery, ${className} ${functionName}) {
        return page(pageQuery.build(), pageQuery.setOrder(query(${functionName})));
    }
</#if>

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
