package cn.sijay.owl.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.utils.ExcelUtil;
import cn.sijay.owl.system.entity.SysTask;
import cn.sijay.owl.system.service.SysTaskService;
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
 * 任务配置控制器
 * 提供用户的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/system/sys_task")
@RestController
public class SysTaskController extends BaseController {
    private final SysTaskService sysTaskService;

    /**
     * 分页查询任务配置列表
     *
     * @param pageQuery 分页参数
     * @param sysTask 查询条件
     * @return 用户分页列表
     */
    @AccessLog(title = "任务配置", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysTask:query")
    @GetMapping("/page")
    @Operation(summary = "查询任务配置列表")
    public Result<List<SysTask>> page(PageQuery pageQuery, SysTask sysTask) {
        return success(sysTaskService.page(pageQuery, sysTask));
    }

    /**
     * 查询任务配置列表(不分页)
     *
     * @param sysTask 查询条件
     * @return 用户列表
     */
    @AccessLog(title = "任务配置", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysTask:query")
    @GetMapping("/list")
    @Operation(summary = "查询任务配置列表")
    public Result<List<SysTask>> list(SysTask sysTask) {
        return success(sysTaskService.list(sysTask));
    }

    /**
     * 根据ID查询任务配置详情
     *
     * @param id 用户ID
     * @return 用户详情
     */
    @AccessLog(title = "任务配置", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysTask:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询任务配置列表")
    public Result<SysTask> getById(@PathVariable Long id) {
        return success(sysTaskService.getById(id));
    }

    /**
     * 新增任务配置
     *
     * @param sysTask 用户信息
     * @return 操作结果
     */
    @AccessLog(title = "任务配置", operateType = OperateType.ADD)
    @SaCheckPermission("system:sysTask:add")
    @PostMapping("/add")
    @Operation(summary = "修改任务配置")
    public Result<Boolean> add(@Valid @RequestBody SysTask sysTask) {
        return result(sysTaskService.save(sysTask), OperateType.ADD);
    }

    /**
     * 修改任务配置
     *
     * @param sysTask 用户信息
     * @return 操作结果
     */
    @AccessLog(title = "任务配置", operateType = OperateType.UPDATE)
    @SaCheckPermission("system:sysTask:update")
    @PostMapping("/update")
    @Operation(summary = "修改任务配置")
    public Result<Boolean> update(@Valid @RequestBody SysTask sysTask) {
        return result(sysTaskService.updateById(sysTask), OperateType.UPDATE);
    }

    /**
     * 删除任务配置
     *
     * @param id 用户ID
     * @return 操作结果
     */
    @AccessLog(title = "任务配置", operateType = OperateType.DELETE)
    @SaCheckPermission("system:sysTask:delete")
    @PostMapping("/remove/{id}")
    @Operation(summary = "删除任务配置")
    public Result<Boolean> remove(@PathVariable Long id) {
        return result(sysTaskService.delete(id), OperateType.DELETE);
    }

    /**
     * 下载任务配置导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "任务配置", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysTask:import")
    @GetMapping("/template")
    @Operation(summary = "下载任务配置模板")
    public ResponseEntity<Resource> template() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "任务配置模板", SysTask.class);
    }

    /**
     * 导入任务配置数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "任务配置", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysTask:import")
    @PostMapping("/import")
    @Operation(summary = "导入任务配置")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<SysTask> result = ExcelUtil.importExcel(file.getInputStream(), SysTask.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(sysTaskService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出任务配置数据
     *
     * @param sysTask 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "任务配置", operateType = OperateType.EXPORT)
    @SaCheckPermission("system:sysTask:export")
    @GetMapping("/export")
    @Operation(summary = "导出任务配置")
    public ResponseEntity<Resource> exportData(SysTask sysTask) {
        List<SysTask> list = sysTaskService.list(sysTask);
        return ExcelUtil.exportExcel(list, "任务配置", SysTask.class);
    }

}
