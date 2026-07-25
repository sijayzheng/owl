package cn.sijay.owl.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.excel.ExcelUtil;
import cn.sijay.owl.system.dto.SysUserOnlineQuery;
import cn.sijay.owl.system.entity.SysUserOnline;
import cn.sijay.owl.system.service.SysUserOnlineService;
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
 * 在线用户控制器
 * 提供在线用户的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/system/sysUserOnline")
@RestController
public class SysUserOnlineController extends BaseController {
    private final SysUserOnlineService sysUserOnlineService;

    /**
     * 分页查询在线用户列表
     *
     * @param pageQuery            分页参数
     * @param sysUserOnlineQuery 查询条件
     * @return 在线用户分页列表
     */
    @AccessLog(title = "在线用户", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysUserOnline:query")
    @GetMapping("/page")
    @Operation(summary = "查询在线用户列表")
    public Result<List<SysUserOnline>> page(PageQuery pageQuery, SysUserOnlineQuery sysUserOnlineQuery) {
        return success(sysUserOnlineService.page(pageQuery, sysUserOnlineQuery));
    }

    /**
     * 查询在线用户列表
     *
     * @param sysUserOnlineQuery 查询条件
     * @return 在线用户列表
     */
    @AccessLog(title = "在线用户", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysUserOnline:query")
    @GetMapping("/list")
    @Operation(summary = "查询在线用户列表")
    public Result<List<SysUserOnline>> list(SysUserOnlineQuery sysUserOnlineQuery) {
        return success(sysUserOnlineService.list(sysUserOnlineQuery));
    }

    /**
     * 根据ID查询在线用户详情
     *
     * @param id 在线用户ID
     * @return 在线用户详情
     */
    @AccessLog(title = "在线用户", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysUserOnline:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询在线用户列表")
    public Result<SysUserOnline> getById(@PathVariable Long id) {
        return success(sysUserOnlineService.getById(id));
    }

    /**
     * 保存在线用户
     *
     * @param sysUserOnline 在线用户信息
     * @return 操作结果
     */
    @AccessLog(title = "在线用户", operateType = OperateType.SAVE)
    @SaCheckPermission("system:sysUserOnline:save")
    @PostMapping("/save")
    @Operation(summary = "保存在线用户")
    public Result<Boolean> save(@Valid @RequestBody SysUserOnline sysUserOnline) {
        return result(sysUserOnlineService.validSave(sysUserOnline), OperateType.SAVE);
    }

    /**
     * 删除在线用户
     *
     * @param ids 在线用户ID
     * @return 操作结果
     */
    @AccessLog(title = "在线用户", operateType = OperateType.DELETE)
    @SaCheckPermission("system:sysUserOnline:delete")
    @PostMapping("/remove")
    @Operation(summary = "删除在线用户")
    public Result<Boolean> remove(@RequestBody List<Long> ids) {
        return result(sysUserOnlineService.removeByIds(ids), OperateType.DELETE);
    }

    /**
     * 下载在线用户导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "在线用户", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysUserOnline:import")
    @GetMapping("/downloadTemplate")
    @Operation(summary = "下载在线用户模板")
    public ResponseEntity<Resource> downloadTemplate() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "在线用户模板", SysUserOnline.class);
    }

    /**
     * 导入在线用户数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "在线用户", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysUserOnline:import")
    @PostMapping("/import")
    @Operation(summary = "导入在线用户")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<SysUserOnline> result = ExcelUtil.importExcel(file.getInputStream(), SysUserOnline.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(sysUserOnlineService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出在线用户数据
     *
     * @param sysUserOnlineQuery 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "在线用户", operateType = OperateType.EXPORT)
    @SaCheckPermission("system:sysUserOnline:export")
    @GetMapping("/export")
    @Operation(summary = "导出在线用户")
    public ResponseEntity<Resource> exportData(SysUserOnlineQuery sysUserOnlineQuery) {
        List<SysUserOnline> list = sysUserOnlineService.list(sysUserOnlineQuery);
        return ExcelUtil.exportExcel(list, "在线用户", SysUserOnline.class);
    }

}
