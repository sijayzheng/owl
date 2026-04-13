package cn.sijay.owl.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.utils.ExcelUtil;
import cn.sijay.owl.system.entity.SysDictData;
import cn.sijay.owl.system.service.SysDictDataService;
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
 * 字典数据控制器
 * 提供用户的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/system/sys_dict_data")
@RestController
public class SysDictDataController extends BaseController {
    private final SysDictDataService sysDictDataService;

    /**
     * 分页查询字典数据列表
     *
     * @param pageQuery 分页参数
     * @param sysDictData 查询条件
     * @return 用户分页列表
     */
    @AccessLog(title = "字典数据", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysDictData:query")
    @GetMapping("/page")
    @Operation(summary = "查询字典数据列表")
    public Result<List<SysDictData>> page(PageQuery pageQuery, SysDictData sysDictData) {
        return success(sysDictDataService.page(pageQuery, sysDictData));
    }

    /**
     * 查询字典数据列表(不分页)
     *
     * @param sysDictData 查询条件
     * @return 用户列表
     */
    @AccessLog(title = "字典数据", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysDictData:query")
    @GetMapping("/list")
    @Operation(summary = "查询字典数据列表")
    public Result<List<SysDictData>> list(SysDictData sysDictData) {
        return success(sysDictDataService.list(sysDictData));
    }

    /**
     * 根据ID查询字典数据详情
     *
     * @param id 用户ID
     * @return 用户详情
     */
    @AccessLog(title = "字典数据", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysDictData:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询字典数据列表")
    public Result<SysDictData> getById(@PathVariable Long id) {
        return success(sysDictDataService.getById(id));
    }

    /**
     * 新增字典数据
     *
     * @param sysDictData 用户信息
     * @return 操作结果
     */
    @AccessLog(title = "字典数据", operateType = OperateType.ADD)
    @SaCheckPermission("system:sysDictData:add")
    @PostMapping("/add")
    @Operation(summary = "修改字典数据")
    public Result<Boolean> add(@Valid @RequestBody SysDictData sysDictData) {
        return result(sysDictDataService.save(sysDictData), OperateType.ADD);
    }

    /**
     * 修改字典数据
     *
     * @param sysDictData 用户信息
     * @return 操作结果
     */
    @AccessLog(title = "字典数据", operateType = OperateType.UPDATE)
    @SaCheckPermission("system:sysDictData:update")
    @PostMapping("/update")
    @Operation(summary = "修改字典数据")
    public Result<Boolean> update(@Valid @RequestBody SysDictData sysDictData) {
        return result(sysDictDataService.updateById(sysDictData), OperateType.UPDATE);
    }

    /**
     * 删除字典数据
     *
     * @param id 用户ID
     * @return 操作结果
     */
    @AccessLog(title = "字典数据", operateType = OperateType.DELETE)
    @SaCheckPermission("system:sysDictData:delete")
    @PostMapping("/remove/{id}")
    @Operation(summary = "删除字典数据")
    public Result<Boolean> remove(@PathVariable Long id) {
        return result(sysDictDataService.delete(id), OperateType.DELETE);
    }

    /**
     * 下载字典数据导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "字典数据", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysDictData:import")
    @GetMapping("/template")
    @Operation(summary = "下载字典数据模板")
    public ResponseEntity<Resource> template() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "字典数据模板", SysDictData.class);
    }

    /**
     * 导入字典数据数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "字典数据", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysDictData:import")
    @PostMapping("/import")
    @Operation(summary = "导入字典数据")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<SysDictData> result = ExcelUtil.importExcel(file.getInputStream(), SysDictData.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(sysDictDataService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出字典数据数据
     *
     * @param sysDictData 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "字典数据", operateType = OperateType.EXPORT)
    @SaCheckPermission("system:sysDictData:export")
    @GetMapping("/export")
    @Operation(summary = "导出字典数据")
    public ResponseEntity<Resource> exportData(SysDictData sysDictData) {
        List<SysDictData> list = sysDictDataService.list(sysDictData);
        return ExcelUtil.exportExcel(list, "字典数据", SysDictData.class);
    }

}
