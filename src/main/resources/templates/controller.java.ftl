package ${packageName}.${moduleName}.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.excel.ExcelUtil;
import ${packageName}.${moduleName}.entity.${className};
import ${packageName}.${moduleName}.service.${className}Service;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ${classComment}控制器
 * 提供${classComment}的增删改查、导入导出等功能
 *
 * @author ${author}
 * @since ${date}
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/${moduleName}/${path}")
@RestController
public class ${className}Controller extends BaseController {
    private final ${className}Service ${functionName}Service;

<#if isTree>
    /**
     * 查询${classComment}树形结构数据
     *
     * @return 树形结构数据列表
     */
    @AccessLog(title = "${classComment}", operateType = OperateType.QUERY)
    @SaCheckPermission("${moduleName}:${functionName}:query")
    @GetMapping("/tree")
    public Result<List<${className}>> getTree() {
        return success(${functionName}Service.getTree());
    }
<#else>
    /**
     * 分页查询${classComment}列表
     *
     * @param pageQuery       分页参数
     * @param ${functionName} 查询条件
     * @return ${classComment}分页列表
     */
    @AccessLog(title = "${classComment}", operateType = OperateType.QUERY)
    @SaCheckPermission("${moduleName}:${functionName}:query")
    @GetMapping("/page")
    @Operation(summary = "查询${classComment}列表")
    public Result<List<${className}>> page(PageQuery pageQuery, ${className} ${functionName}) {
        return success(${functionName}Service.page(pageQuery, ${functionName}));
    }
</#if>

    /**
     * 查询${classComment}列表
     *
     * @param ${functionName} 查询条件
     * @return ${classComment}列表
     */
    @AccessLog(title = "${classComment}", operateType = OperateType.QUERY)
    @SaCheckPermission("${moduleName}:${functionName}:query")
    @GetMapping("/list")
    @Operation(summary = "查询${classComment}列表")
    public Result<List<${className}>> list(${className} ${functionName}) {
        return success(${functionName}Service.list(${functionName}));
    }

    /**
     * 根据ID查询${classComment}详情
     *
     * @param id ${classComment}ID
     * @return ${classComment}详情
     */
    @AccessLog(title = "${classComment}", operateType = OperateType.QUERY)
    @SaCheckPermission("${moduleName}:${functionName}:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询${classComment}列表")
    public Result<${className}> getById(@PathVariable Long id) {
        return success(${functionName}Service.getById(id));
    }

    /**
     * 新增${classComment}
     *
     * @param ${functionName} ${classComment}信息
     * @return 操作结果
     */
    @AccessLog(title = "${classComment}", operateType = OperateType.ADD)
    @SaCheckPermission("${moduleName}:${functionName}:add")
    @PostMapping("/add")
    @Operation(summary = "修改${classComment}")
    public Result<Boolean> add(@Valid @RequestBody ${className} ${functionName}) {
        return result(${functionName}Service.save(${functionName}), OperateType.ADD);
    }

    /**
     * 修改${classComment}
     *
     * @param ${functionName} ${classComment}信息
     * @return 操作结果
     */
    @AccessLog(title = "${classComment}", operateType = OperateType.UPDATE)
    @SaCheckPermission("${moduleName}:${functionName}:update")
    @PostMapping("/update")
    @Operation(summary = "修改${classComment}")
    public Result<Boolean> update(@Valid @RequestBody ${className} ${functionName}) {
        return result(${functionName}Service.updateById(${functionName}), OperateType.UPDATE);
    }

    /**
     * 删除${classComment}
     *
     * @param ids ${classComment}ID
     * @return 操作结果
     */
    @AccessLog(title = "${classComment}", operateType = OperateType.DELETE)
    @SaCheckPermission("${moduleName}:${functionName}:delete")
    @PostMapping("/remove")
    @Operation(summary = "删除${classComment}")
    public Result<Boolean> remove(List<Long> ids) {
        return result(${functionName}Service.removeByIds(ids), OperateType.DELETE);
    }

    /**
     * 下载${classComment}导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "${classComment}", operateType = OperateType.IMPORT)
    @SaCheckPermission("${moduleName}:${functionName}:import")
    @GetMapping("/template")
    @Operation(summary = "下载${classComment}模板")
    public ResponseEntity<Resource> template() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "${classComment}模板", ${className}.class);
    }

    /**
     * 导入${classComment}数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "${classComment}", operateType = OperateType.IMPORT)
    @SaCheckPermission("${moduleName}:${functionName}:import")
    @PostMapping("/import")
    @Operation(summary = "导入${classComment}")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<${className}> result = ExcelUtil.importExcel(file.getInputStream(), ${className}.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(${functionName}Service.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出${classComment}数据
     *
     * @param ${functionName} 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "${classComment}", operateType = OperateType.EXPORT)
    @SaCheckPermission("${moduleName}:${functionName}:export")
    @GetMapping("/export")
    @Operation(summary = "导出${classComment}")
    public ResponseEntity<Resource> exportData(${className} ${functionName}) {
        List<${className}> list = ${functionName}Service.list(${functionName});
        return ExcelUtil.exportExcel(list, "${classComment}", ${className}.class);
    }

}
