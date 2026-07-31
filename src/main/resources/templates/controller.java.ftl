package ${packageName}.${moduleName}.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.constants.CommonConstants;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.excel.ExcelUtil;
import ${packageName}.${moduleName}.dto.${className}Query;
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
@RequestMapping(CommonConstants.BASE_API_PATH + "/${moduleName}/${functionName}")
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
    public Result<List<${className}>> getTree(${className}Query ${functionName}Query) {
        return success(${functionName}Service.getTree(${functionName}Query));
    }
<#else>
    /**
     * 分页查询${classComment}列表
     *
     * @param pageQuery            分页参数
     * @param ${functionName}Query 查询条件
     * @return ${classComment}分页列表
     */
    @AccessLog(title = "${classComment}", operateType = OperateType.QUERY)
    @SaCheckPermission("${moduleName}:${functionName}:query")
    @GetMapping("/page")
    public Result<List<${className}>> page(PageQuery pageQuery, ${className}Query ${functionName}Query) {
        return success(${functionName}Service.page(pageQuery, ${functionName}Query));
    }
</#if>

    /**
     * 查询${classComment}列表
     *
     * @param ${functionName}Query 查询条件
     * @return ${classComment}列表
     */
    @AccessLog(title = "${classComment}", operateType = OperateType.QUERY)
    @SaCheckPermission("${moduleName}:${functionName}:query")
    @GetMapping("/list")
    public Result<List<${className}>> list(${className}Query ${functionName}Query) {
        return success(${functionName}Service.list(${functionName}Query));
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
    public Result<${className}> getById(@PathVariable Long id) {
        return success(${functionName}Service.getById(id));
    }

    /**
     * 保存${classComment}
     *
     * @param ${functionName} ${classComment}信息
     * @return 操作结果
     */
    @AccessLog(title = "${classComment}", operateType = OperateType.SAVE)
    @SaCheckPermission("${moduleName}:${functionName}:save")
    @PostMapping("/save")
    public Result<Boolean> save(@Valid @RequestBody ${className} ${functionName}) {
        return result(${functionName}Service.validSave(${functionName}), OperateType.SAVE);
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
    public Result<Boolean> remove(@RequestBody List<Long> ids) {
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
    @GetMapping("/downloadTemplate")
    public ResponseEntity<Resource> downloadTemplate() throws IOException {
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
     * @param ${functionName}Query 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "${classComment}", operateType = OperateType.EXPORT)
    @SaCheckPermission("${moduleName}:${functionName}:export")
    @GetMapping("/export")
    public ResponseEntity<Resource> exportData(${className}Query ${functionName}Query) {
        List<${className}> list = ${functionName}Service.list(${functionName}Query);
        return ExcelUtil.exportExcel(list, "${classComment}", ${className}.class);
    }

}
