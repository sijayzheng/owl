package cn.sijay.owl.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.excel.ExcelUtil;
import cn.sijay.owl.system.entity.SysConfig;
import cn.sijay.owl.system.service.SysConfigService;
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
 * 参数配置控制器
 * 提供参数配置的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/system/sys-config")
@RestController
public class SysConfigController extends BaseController {
    private final SysConfigService sysConfigService;

    /**
     * 分页查询参数配置列表
     *
     * @param pageQuery 分页参数
     * @param sysConfig 查询条件
     * @return 参数配置分页列表
     */
    @AccessLog(title = "参数配置", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysConfig:query")
    @GetMapping("/page")
    @Operation(summary = "查询参数配置列表")
    public Result<List<SysConfig>> page(PageQuery pageQuery, SysConfig sysConfig) {
        return success(sysConfigService.page(pageQuery, sysConfig));
    }

    /**
     * 查询参数配置列表
     *
     * @param sysConfig 查询条件
     * @return 参数配置列表
     */
    @AccessLog(title = "参数配置", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysConfig:query")
    @GetMapping("/list")
    @Operation(summary = "查询参数配置列表")
    public Result<List<SysConfig>> list(SysConfig sysConfig) {
        return success(sysConfigService.list(sysConfig));
    }

    /**
     * 根据ID查询参数配置详情
     *
     * @param id 参数配置ID
     * @return 参数配置详情
     */
    @AccessLog(title = "参数配置", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysConfig:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询参数配置列表")
    public Result<SysConfig> getById(@PathVariable Long id) {
        return success(sysConfigService.getById(id));
    }

    /**
     * 新增参数配置
     *
     * @param sysConfig 参数配置信息
     * @return 操作结果
     */
    @AccessLog(title = "参数配置", operateType = OperateType.ADD)
    @SaCheckPermission("system:sysConfig:add")
    @PostMapping("/add")
    @Operation(summary = "修改参数配置")
    public Result<Boolean> add(@Valid @RequestBody SysConfig sysConfig) {
        return result(sysConfigService.save(sysConfig), OperateType.ADD);
    }

    /**
     * 修改参数配置
     *
     * @param sysConfig 参数配置信息
     * @return 操作结果
     */
    @AccessLog(title = "参数配置", operateType = OperateType.UPDATE)
    @SaCheckPermission("system:sysConfig:update")
    @PostMapping("/update")
    @Operation(summary = "修改参数配置")
    public Result<Boolean> update(@Valid @RequestBody SysConfig sysConfig) {
        return result(sysConfigService.updateById(sysConfig), OperateType.UPDATE);
    }

    /**
     * 删除参数配置
     *
     * @param ids 参数配置ID
     * @return 操作结果
     */
    @AccessLog(title = "参数配置", operateType = OperateType.DELETE)
    @SaCheckPermission("system:sysConfig:delete")
    @PostMapping("/remove")
    @Operation(summary = "删除参数配置")
    public Result<Boolean> remove(List<Long> ids) {
        return result(sysConfigService.removeByIds(ids), OperateType.DELETE);
    }

    /**
     * 下载参数配置导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "参数配置", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysConfig:import")
    @GetMapping("/template")
    @Operation(summary = "下载参数配置模板")
    public ResponseEntity<Resource> template() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "参数配置模板", SysConfig.class);
    }

    /**
     * 导入参数配置数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "参数配置", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysConfig:import")
    @PostMapping("/import")
    @Operation(summary = "导入参数配置")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<SysConfig> result = ExcelUtil.importExcel(file.getInputStream(), SysConfig.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(sysConfigService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出参数配置数据
     *
     * @param sysConfig 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "参数配置", operateType = OperateType.EXPORT)
    @SaCheckPermission("system:sysConfig:export")
    @GetMapping("/export")
    @Operation(summary = "导出参数配置")
    public ResponseEntity<Resource> exportData(SysConfig sysConfig) {
        List<SysConfig> list = sysConfigService.list(sysConfig);
        return ExcelUtil.exportExcel(list, "参数配置", SysConfig.class);
    }

}
