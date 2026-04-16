package cn.sijay.owl.log.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.excel.ExcelUtil;
import cn.sijay.owl.log.entity.LogTask;
import cn.sijay.owl.log.service.LogTaskService;
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
 * 任务日志控制器
 * 提供任务日志的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/log/log-task")
@RestController
public class LogTaskController extends BaseController {
    private final LogTaskService logTaskService;

    /**
     * 分页查询任务日志列表
     *
     * @param pageQuery 分页参数
     * @param logTask   查询条件
     * @return 任务日志分页列表
     */
    @AccessLog(title = "任务日志", operateType = OperateType.QUERY)
    @SaCheckPermission("log:logTask:query")
    @GetMapping("/page")
    @Operation(summary = "查询任务日志列表")
    public Result<List<LogTask>> page(PageQuery pageQuery, LogTask logTask) {
        return success(logTaskService.page(pageQuery, logTask));
    }

    /**
     * 查询任务日志列表
     *
     * @param logTask 查询条件
     * @return 任务日志列表
     */
    @AccessLog(title = "任务日志", operateType = OperateType.QUERY)
    @SaCheckPermission("log:logTask:query")
    @GetMapping("/list")
    @Operation(summary = "查询任务日志列表")
    public Result<List<LogTask>> list(LogTask logTask) {
        return success(logTaskService.list(logTask));
    }

    /**
     * 根据ID查询任务日志详情
     *
     * @param id 任务日志ID
     * @return 任务日志详情
     */
    @AccessLog(title = "任务日志", operateType = OperateType.QUERY)
    @SaCheckPermission("log:logTask:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询任务日志列表")
    public Result<LogTask> getById(@PathVariable Long id) {
        return success(logTaskService.getById(id));
    }

    /**
     * 新增任务日志
     *
     * @param logTask 任务日志信息
     * @return 操作结果
     */
    @AccessLog(title = "任务日志", operateType = OperateType.ADD)
    @SaCheckPermission("log:logTask:add")
    @PostMapping("/add")
    @Operation(summary = "修改任务日志")
    public Result<Boolean> add(@Valid @RequestBody LogTask logTask) {
        return result(logTaskService.save(logTask), OperateType.ADD);
    }

    /**
     * 修改任务日志
     *
     * @param logTask 任务日志信息
     * @return 操作结果
     */
    @AccessLog(title = "任务日志", operateType = OperateType.UPDATE)
    @SaCheckPermission("log:logTask:update")
    @PostMapping("/update")
    @Operation(summary = "修改任务日志")
    public Result<Boolean> update(@Valid @RequestBody LogTask logTask) {
        return result(logTaskService.updateById(logTask), OperateType.UPDATE);
    }

    /**
     * 删除任务日志
     *
     * @param ids 任务日志ID
     * @return 操作结果
     */
    @AccessLog(title = "任务日志", operateType = OperateType.DELETE)
    @SaCheckPermission("log:logTask:delete")
    @PostMapping("/remove")
    @Operation(summary = "删除任务日志")
    public Result<Boolean> remove(List<Long> ids) {
        return result(logTaskService.removeByIds(ids), OperateType.DELETE);
    }

    /**
     * 下载任务日志导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "任务日志", operateType = OperateType.IMPORT)
    @SaCheckPermission("log:logTask:import")
    @GetMapping("/template")
    @Operation(summary = "下载任务日志模板")
    public ResponseEntity<Resource> template() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "任务日志模板", LogTask.class);
    }

    /**
     * 导入任务日志数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "任务日志", operateType = OperateType.IMPORT)
    @SaCheckPermission("log:logTask:import")
    @PostMapping("/import")
    @Operation(summary = "导入任务日志")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<LogTask> result = ExcelUtil.importExcel(file.getInputStream(), LogTask.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(logTaskService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出任务日志数据
     *
     * @param logTask 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "任务日志", operateType = OperateType.EXPORT)
    @SaCheckPermission("log:logTask:export")
    @GetMapping("/export")
    @Operation(summary = "导出任务日志")
    public ResponseEntity<Resource> exportData(LogTask logTask) {
        List<LogTask> list = logTaskService.list(logTask);
        return ExcelUtil.exportExcel(list, "任务日志", LogTask.class);
    }

}
