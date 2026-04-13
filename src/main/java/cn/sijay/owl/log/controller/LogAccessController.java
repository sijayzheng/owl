package cn.sijay.owl.log.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.utils.ExcelUtil;
import cn.sijay.owl.log.entity.LogAccess;
import cn.sijay.owl.log.service.LogAccessService;
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
 * 访问日志控制器
 * 提供用户的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/log/log_access")
@RestController
public class LogAccessController extends BaseController {
    private final LogAccessService logAccessService;

    /**
     * 分页查询访问日志列表
     *
     * @param pageQuery 分页参数
     * @param logAccess 查询条件
     * @return 用户分页列表
     */
    @AccessLog(title = "访问日志", operateType = OperateType.QUERY)
    @SaCheckPermission("log:logAccess:query")
    @GetMapping("/page")
    @Operation(summary = "查询访问日志列表")
    public Result<List<LogAccess>> page(PageQuery pageQuery, LogAccess logAccess) {
        return success(logAccessService.page(pageQuery, logAccess));
    }

    /**
     * 查询访问日志列表(不分页)
     *
     * @param logAccess 查询条件
     * @return 用户列表
     */
    @AccessLog(title = "访问日志", operateType = OperateType.QUERY)
    @SaCheckPermission("log:logAccess:query")
    @GetMapping("/list")
    @Operation(summary = "查询访问日志列表")
    public Result<List<LogAccess>> list(LogAccess logAccess) {
        return success(logAccessService.list(logAccess));
    }

    /**
     * 根据ID查询访问日志详情
     *
     * @param id 用户ID
     * @return 用户详情
     */
    @AccessLog(title = "访问日志", operateType = OperateType.QUERY)
    @SaCheckPermission("log:logAccess:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询访问日志列表")
    public Result<LogAccess> getById(@PathVariable Long id) {
        return success(logAccessService.getById(id));
    }

    /**
     * 新增访问日志
     *
     * @param logAccess 用户信息
     * @return 操作结果
     */
    @AccessLog(title = "访问日志", operateType = OperateType.ADD)
    @SaCheckPermission("log:logAccess:add")
    @PostMapping("/add")
    @Operation(summary = "修改访问日志")
    public Result<Boolean> add(@Valid @RequestBody LogAccess logAccess) {
        return result(logAccessService.save(logAccess), OperateType.ADD);
    }

    /**
     * 修改访问日志
     *
     * @param logAccess 用户信息
     * @return 操作结果
     */
    @AccessLog(title = "访问日志", operateType = OperateType.UPDATE)
    @SaCheckPermission("log:logAccess:update")
    @PostMapping("/update")
    @Operation(summary = "修改访问日志")
    public Result<Boolean> update(@Valid @RequestBody LogAccess logAccess) {
        return result(logAccessService.updateById(logAccess), OperateType.UPDATE);
    }

    /**
     * 删除访问日志
     *
     * @param id 用户ID
     * @return 操作结果
     */
    @AccessLog(title = "访问日志", operateType = OperateType.DELETE)
    @SaCheckPermission("log:logAccess:delete")
    @PostMapping("/remove/{id}")
    @Operation(summary = "删除访问日志")
    public Result<Boolean> remove(@PathVariable Long id) {
        return result(logAccessService.delete(id), OperateType.DELETE);
    }

    /**
     * 下载访问日志导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "访问日志", operateType = OperateType.IMPORT)
    @SaCheckPermission("log:logAccess:import")
    @GetMapping("/template")
    @Operation(summary = "下载访问日志模板")
    public ResponseEntity<Resource> template() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "访问日志模板", LogAccess.class);
    }

    /**
     * 导入访问日志数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "访问日志", operateType = OperateType.IMPORT)
    @SaCheckPermission("log:logAccess:import")
    @PostMapping("/import")
    @Operation(summary = "导入访问日志")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<LogAccess> result = ExcelUtil.importExcel(file.getInputStream(), LogAccess.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(logAccessService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出访问日志数据
     *
     * @param logAccess 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "访问日志", operateType = OperateType.EXPORT)
    @SaCheckPermission("log:logAccess:export")
    @GetMapping("/export")
    @Operation(summary = "导出访问日志")
    public ResponseEntity<Resource> exportData(LogAccess logAccess) {
        List<LogAccess> list = logAccessService.list(logAccess);
        return ExcelUtil.exportExcel(list, "访问日志", LogAccess.class);
    }

}
