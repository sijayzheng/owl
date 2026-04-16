package cn.sijay.owl.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.excel.ExcelUtil;
import cn.sijay.owl.system.entity.SysDictType;
import cn.sijay.owl.system.service.SysDictTypeService;
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
 * 字典类型控制器
 * 提供字典类型的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/system/sys_dict_type")
@RestController
public class SysDictTypeController extends BaseController {
    private final SysDictTypeService sysDictTypeService;

    /**
     * 分页查询字典类型列表
     *
     * @param pageQuery   分页参数
     * @param sysDictType 查询条件
     * @return 字典类型分页列表
     */
    @AccessLog(title = "字典类型", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysDictType:query")
    @GetMapping("/page")
    @Operation(summary = "查询字典类型列表")
    public Result<List<SysDictType>> page(PageQuery pageQuery, SysDictType sysDictType) {
        return success(sysDictTypeService.page(pageQuery, sysDictType));
    }

    /**
     * 查询字典类型列表
     *
     * @param sysDictType 查询条件
     * @return 字典类型列表
     */
    @AccessLog(title = "字典类型", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysDictType:query")
    @GetMapping("/list")
    @Operation(summary = "查询字典类型列表")
    public Result<List<SysDictType>> list(SysDictType sysDictType) {
        return success(sysDictTypeService.list(sysDictType));
    }

    /**
     * 根据ID查询字典类型详情
     *
     * @param id 字典类型ID
     * @return 字典类型详情
     */
    @AccessLog(title = "字典类型", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysDictType:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询字典类型列表")
    public Result<SysDictType> getById(@PathVariable Long id) {
        return success(sysDictTypeService.getById(id));
    }

    /**
     * 新增字典类型
     *
     * @param sysDictType 字典类型信息
     * @return 操作结果
     */
    @AccessLog(title = "字典类型", operateType = OperateType.ADD)
    @SaCheckPermission("system:sysDictType:add")
    @PostMapping("/add")
    @Operation(summary = "修改字典类型")
    public Result<Boolean> add(@Valid @RequestBody SysDictType sysDictType) {
        return result(sysDictTypeService.save(sysDictType), OperateType.ADD);
    }

    /**
     * 修改字典类型
     *
     * @param sysDictType 字典类型信息
     * @return 操作结果
     */
    @AccessLog(title = "字典类型", operateType = OperateType.UPDATE)
    @SaCheckPermission("system:sysDictType:update")
    @PostMapping("/update")
    @Operation(summary = "修改字典类型")
    public Result<Boolean> update(@Valid @RequestBody SysDictType sysDictType) {
        return result(sysDictTypeService.updateById(sysDictType), OperateType.UPDATE);
    }

    /**
     * 删除字典类型
     *
     * @param id 字典类型ID
     * @return 操作结果
     */
    @AccessLog(title = "字典类型", operateType = OperateType.DELETE)
    @SaCheckPermission("system:sysDictType:delete")
    @PostMapping("/remove/{id}")
    @Operation(summary = "删除字典类型")
    public Result<Boolean> remove(@PathVariable Long id) {
        return result(sysDictTypeService.delete(id), OperateType.DELETE);
    }

    /**
     * 下载字典类型导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "字典类型", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysDictType:import")
    @GetMapping("/template")
    @Operation(summary = "下载字典类型模板")
    public ResponseEntity<Resource> template() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "字典类型模板", SysDictType.class);
    }

    /**
     * 导入字典类型数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "字典类型", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysDictType:import")
    @PostMapping("/import")
    @Operation(summary = "导入字典类型")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<SysDictType> result = ExcelUtil.importExcel(file.getInputStream(), SysDictType.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(sysDictTypeService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出字典类型数据
     *
     * @param sysDictType 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "字典类型", operateType = OperateType.EXPORT)
    @SaCheckPermission("system:sysDictType:export")
    @GetMapping("/export")
    @Operation(summary = "导出字典类型")
    public ResponseEntity<Resource> exportData(SysDictType sysDictType) {
        List<SysDictType> list = sysDictTypeService.list(sysDictType);
        return ExcelUtil.exportExcel(list, "字典类型", SysDictType.class);
    }

}
