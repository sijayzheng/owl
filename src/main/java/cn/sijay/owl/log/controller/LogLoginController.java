package cn.sijay.owl.log.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.utils.ExcelUtil;
import cn.sijay.owl.log.entity.LogLogin;
import cn.sijay.owl.log.service.LogLoginService;
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
 * 登录日志控制器
 * 提供登录日志的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/log/log_login")
@RestController
public class LogLoginController extends BaseController {
    private final LogLoginService logLoginService;

    /**
     * 分页查询登录日志列表
     *
     * @param pageQuery 分页参数
     * @param logLogin  查询条件
     * @return 登录日志分页列表
     */
    @AccessLog(title = "登录日志", operateType = OperateType.QUERY)
    @SaCheckPermission("log:logLogin:query")
    @GetMapping("/page")
    @Operation(summary = "查询登录日志列表")
    public Result<List<LogLogin>> page(PageQuery pageQuery, LogLogin logLogin) {
        return success(logLoginService.page(pageQuery, logLogin));
    }

    /**
     * 查询登录日志列表
     *
     * @param logLogin 查询条件
     * @return 登录日志列表
     */
    @AccessLog(title = "登录日志", operateType = OperateType.QUERY)
    @SaCheckPermission("log:logLogin:query")
    @GetMapping("/list")
    @Operation(summary = "查询登录日志列表")
    public Result<List<LogLogin>> list(LogLogin logLogin) {
        return success(logLoginService.list(logLogin));
    }

    /**
     * 根据ID查询登录日志详情
     *
     * @param id 登录日志ID
     * @return 登录日志详情
     */
    @AccessLog(title = "登录日志", operateType = OperateType.QUERY)
    @SaCheckPermission("log:logLogin:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询登录日志列表")
    public Result<LogLogin> getById(@PathVariable Long id) {
        return success(logLoginService.getById(id));
    }

    /**
     * 新增登录日志
     *
     * @param logLogin 登录日志信息
     * @return 操作结果
     */
    @AccessLog(title = "登录日志", operateType = OperateType.ADD)
    @SaCheckPermission("log:logLogin:add")
    @PostMapping("/add")
    @Operation(summary = "修改登录日志")
    public Result<Boolean> add(@Valid @RequestBody LogLogin logLogin) {
        return result(logLoginService.save(logLogin), OperateType.ADD);
    }

    /**
     * 修改登录日志
     *
     * @param logLogin 登录日志信息
     * @return 操作结果
     */
    @AccessLog(title = "登录日志", operateType = OperateType.UPDATE)
    @SaCheckPermission("log:logLogin:update")
    @PostMapping("/update")
    @Operation(summary = "修改登录日志")
    public Result<Boolean> update(@Valid @RequestBody LogLogin logLogin) {
        return result(logLoginService.updateById(logLogin), OperateType.UPDATE);
    }

    /**
     * 删除登录日志
     *
     * @param id 登录日志ID
     * @return 操作结果
     */
    @AccessLog(title = "登录日志", operateType = OperateType.DELETE)
    @SaCheckPermission("log:logLogin:delete")
    @PostMapping("/remove/{id}")
    @Operation(summary = "删除登录日志")
    public Result<Boolean> remove(@PathVariable Long id) {
        return result(logLoginService.delete(id), OperateType.DELETE);
    }

    /**
     * 下载登录日志导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "登录日志", operateType = OperateType.IMPORT)
    @SaCheckPermission("log:logLogin:import")
    @GetMapping("/template")
    @Operation(summary = "下载登录日志模板")
    public ResponseEntity<Resource> template() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "登录日志模板", LogLogin.class);
    }

    /**
     * 导入登录日志数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "登录日志", operateType = OperateType.IMPORT)
    @SaCheckPermission("log:logLogin:import")
    @PostMapping("/import")
    @Operation(summary = "导入登录日志")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<LogLogin> result = ExcelUtil.importExcel(file.getInputStream(), LogLogin.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(logLoginService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出登录日志数据
     *
     * @param logLogin 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "登录日志", operateType = OperateType.EXPORT)
    @SaCheckPermission("log:logLogin:export")
    @GetMapping("/export")
    @Operation(summary = "导出登录日志")
    public ResponseEntity<Resource> exportData(LogLogin logLogin) {
        List<LogLogin> list = logLoginService.list(logLogin);
        return ExcelUtil.exportExcel(list, "登录日志", LogLogin.class);
    }

}
