package cn.sijay.owl.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.excel.ExcelUtil;
import cn.sijay.owl.system.entity.SysDept;
import cn.sijay.owl.system.service.SysDeptService;
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
 * 系统部门控制器
 * 提供系统部门的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/system/sys_dept")
@RestController
public class SysDeptController extends BaseController {
    private final SysDeptService sysDeptService;

    /**
     * 查询系统部门树形结构数据
     *
     * @return 树形结构数据列表
     */
    @AccessLog(title = "系统部门", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysDept:query")
    @GetMapping("/tree")
    public Result<List<SysDept>> getTree() {
        return success(sysDeptService.getTree());
    }

    /**
     * 查询系统部门列表
     *
     * @param sysDept 查询条件
     * @return 系统部门列表
     */
    @AccessLog(title = "系统部门", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysDept:query")
    @GetMapping("/list")
    @Operation(summary = "查询系统部门列表")
    public Result<List<SysDept>> list(SysDept sysDept) {
        return success(sysDeptService.list(sysDept));
    }

    /**
     * 根据ID查询系统部门详情
     *
     * @param id 系统部门ID
     * @return 系统部门详情
     */
    @AccessLog(title = "系统部门", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysDept:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询系统部门列表")
    public Result<SysDept> getById(@PathVariable Long id) {
        return success(sysDeptService.getById(id));
    }

    /**
     * 新增系统部门
     *
     * @param sysDept 系统部门信息
     * @return 操作结果
     */
    @AccessLog(title = "系统部门", operateType = OperateType.ADD)
    @SaCheckPermission("system:sysDept:add")
    @PostMapping("/add")
    @Operation(summary = "修改系统部门")
    public Result<Boolean> add(@Valid @RequestBody SysDept sysDept) {
        return result(sysDeptService.save(sysDept), OperateType.ADD);
    }

    /**
     * 修改系统部门
     *
     * @param sysDept 系统部门信息
     * @return 操作结果
     */
    @AccessLog(title = "系统部门", operateType = OperateType.UPDATE)
    @SaCheckPermission("system:sysDept:update")
    @PostMapping("/update")
    @Operation(summary = "修改系统部门")
    public Result<Boolean> update(@Valid @RequestBody SysDept sysDept) {
        return result(sysDeptService.updateById(sysDept), OperateType.UPDATE);
    }

    /**
     * 删除系统部门
     *
     * @param id 系统部门ID
     * @return 操作结果
     */
    @AccessLog(title = "系统部门", operateType = OperateType.DELETE)
    @SaCheckPermission("system:sysDept:delete")
    @PostMapping("/remove/{id}")
    @Operation(summary = "删除系统部门")
    public Result<Boolean> remove(@PathVariable Long id) {
        return result(sysDeptService.delete(id), OperateType.DELETE);
    }

    /**
     * 下载系统部门导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "系统部门", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysDept:import")
    @GetMapping("/template")
    @Operation(summary = "下载系统部门模板")
    public ResponseEntity<Resource> template() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "系统部门模板", SysDept.class);
    }

    /**
     * 导入系统部门数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "系统部门", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysDept:import")
    @PostMapping("/import")
    @Operation(summary = "导入系统部门")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<SysDept> result = ExcelUtil.importExcel(file.getInputStream(), SysDept.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(sysDeptService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出系统部门数据
     *
     * @param sysDept 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "系统部门", operateType = OperateType.EXPORT)
    @SaCheckPermission("system:sysDept:export")
    @GetMapping("/export")
    @Operation(summary = "导出系统部门")
    public ResponseEntity<Resource> exportData(SysDept sysDept) {
        List<SysDept> list = sysDeptService.list(sysDept);
        return ExcelUtil.exportExcel(list, "系统部门", SysDept.class);
    }

}
