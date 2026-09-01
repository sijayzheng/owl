package cn.sijay.owl.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.excel.ExcelUtil;
import cn.sijay.owl.system.dto.SysAccessLogQuery;
import cn.sijay.owl.system.entity.SysAccessLog;
import cn.sijay.owl.system.service.SysAccessLogService;
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
 * 提供访问日志的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/system/sysAccessLog")
@RestController
public class SysAccessLogController extends BaseController {
    private final SysAccessLogService sysAccessLogService;

    /**
     * 分页查询访问日志列表
     *
     * @param pageQuery         分页参数
     * @param sysAccessLogQuery 查询条件
     * @return 访问日志分页列表
     */
    @AccessLog(title = "访问日志", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysAccessLog:query")
    @GetMapping("/page")
    public Result<List<SysAccessLog>> page(PageQuery pageQuery, SysAccessLogQuery sysAccessLogQuery) {
        return success(sysAccessLogService.page(pageQuery, sysAccessLogQuery));
    }

    /**
     * 查询访问日志列表
     *
     * @param sysAccessLogQuery 查询条件
     * @return 访问日志列表
     */
    @AccessLog(title = "访问日志", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysAccessLog:query")
    @GetMapping("/list")
    public Result<List<SysAccessLog>> list(SysAccessLogQuery sysAccessLogQuery) {
        return success(sysAccessLogService.list(sysAccessLogQuery));
    }

    /**
     * 根据ID查询访问日志详情
     *
     * @param id 访问日志ID
     * @return 访问日志详情
     */
    @AccessLog(title = "访问日志", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysAccessLog:query")
    @GetMapping("/{id}")
    public Result<SysAccessLog> getById(@PathVariable Long id) {
        return success(sysAccessLogService.getById(id));
    }

    /**
     * 保存访问日志
     *
     * @param sysAccessLog 访问日志信息
     * @return 操作结果
     */
    @AccessLog(title = "访问日志", operateType = OperateType.SAVE)
    @SaCheckPermission("system:sysAccessLog:save")
    @PostMapping("/save")
    public Result<Boolean> save(@Valid @RequestBody SysAccessLog sysAccessLog) {
        return result(sysAccessLogService.validSave(sysAccessLog), OperateType.SAVE);
    }

    /**
     * 删除访问日志
     *
     * @param ids 访问日志ID
     * @return 操作结果
     */
    @AccessLog(title = "访问日志", operateType = OperateType.DELETE)
    @SaCheckPermission("system:sysAccessLog:delete")
    @PostMapping("/remove")
    public Result<Boolean> remove(@RequestBody List<Long> ids) {
        return result(sysAccessLogService.removeByIds(ids), OperateType.DELETE);
    }

    /**
     * 下载访问日志导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "访问日志", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysAccessLog:import")
    @GetMapping("/downloadTemplate")
    public ResponseEntity<Resource> downloadTemplate() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "访问日志模板", SysAccessLog.class);
    }

    /**
     * 导入访问日志数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "访问日志", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysAccessLog:import")
    @PostMapping("/import")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<SysAccessLog> result = ExcelUtil.importExcel(file.getInputStream(), SysAccessLog.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(sysAccessLogService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出访问日志数据
     *
     * @param sysAccessLogQuery 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "访问日志", operateType = OperateType.EXPORT)
    @SaCheckPermission("system:sysAccessLog:export")
    @GetMapping("/export")
    public ResponseEntity<Resource> exportData(SysAccessLogQuery sysAccessLogQuery) {
        List<SysAccessLog> list = sysAccessLogService.list(sysAccessLogQuery);
        return ExcelUtil.exportExcel(list, "访问日志", SysAccessLog.class);
    }

}
