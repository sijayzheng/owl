package cn.sijay.owl.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.excel.ExcelUtil;
import cn.sijay.owl.system.entity.SysRole;
import cn.sijay.owl.system.service.SysRoleService;
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
 * 系统角色控制器
 * 提供系统角色的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/system/sys-role")
@RestController
public class SysRoleController extends BaseController {
    private final SysRoleService sysRoleService;

    /**
     * 分页查询系统角色列表
     *
     * @param pageQuery 分页参数
     * @param sysRole   查询条件
     * @return 系统角色分页列表
     */
    @AccessLog(title = "系统角色", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysRole:query")
    @GetMapping("/page")
    @Operation(summary = "查询系统角色列表")
    public Result<List<SysRole>> page(PageQuery pageQuery, SysRole sysRole) {
        return success(sysRoleService.page(pageQuery, sysRole));
    }

    /**
     * 查询系统角色列表
     *
     * @param sysRole 查询条件
     * @return 系统角色列表
     */
    @AccessLog(title = "系统角色", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysRole:query")
    @GetMapping("/list")
    @Operation(summary = "查询系统角色列表")
    public Result<List<SysRole>> list(SysRole sysRole) {
        return success(sysRoleService.list(sysRole));
    }

    /**
     * 根据ID查询系统角色详情
     *
     * @param id 系统角色ID
     * @return 系统角色详情
     */
    @AccessLog(title = "系统角色", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysRole:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询系统角色列表")
    public Result<SysRole> getById(@PathVariable Long id) {
        return success(sysRoleService.getById(id));
    }

    /**
     * 新增系统角色
     *
     * @param sysRole 系统角色信息
     * @return 操作结果
     */
    @AccessLog(title = "系统角色", operateType = OperateType.ADD)
    @SaCheckPermission("system:sysRole:add")
    @PostMapping("/add")
    @Operation(summary = "修改系统角色")
    public Result<Boolean> add(@Valid @RequestBody SysRole sysRole) {
        return result(sysRoleService.save(sysRole), OperateType.ADD);
    }

    /**
     * 修改系统角色
     *
     * @param sysRole 系统角色信息
     * @return 操作结果
     */
    @AccessLog(title = "系统角色", operateType = OperateType.UPDATE)
    @SaCheckPermission("system:sysRole:update")
    @PostMapping("/update")
    @Operation(summary = "修改系统角色")
    public Result<Boolean> update(@Valid @RequestBody SysRole sysRole) {
        return result(sysRoleService.updateById(sysRole), OperateType.UPDATE);
    }

    /**
     * 删除系统角色
     *
     * @param ids 系统角色ID
     * @return 操作结果
     */
    @AccessLog(title = "系统角色", operateType = OperateType.DELETE)
    @SaCheckPermission("system:sysRole:delete")
    @PostMapping("/remove")
    @Operation(summary = "删除系统角色")
    public Result<Boolean> remove(List<Long> ids) {
        return result(sysRoleService.removeByIds(ids), OperateType.DELETE);
    }

    /**
     * 下载系统角色导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "系统角色", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysRole:import")
    @GetMapping("/template")
    @Operation(summary = "下载系统角色模板")
    public ResponseEntity<Resource> template() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "系统角色模板", SysRole.class);
    }

    /**
     * 导入系统角色数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "系统角色", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysRole:import")
    @PostMapping("/import")
    @Operation(summary = "导入系统角色")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<SysRole> result = ExcelUtil.importExcel(file.getInputStream(), SysRole.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(sysRoleService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出系统角色数据
     *
     * @param sysRole 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "系统角色", operateType = OperateType.EXPORT)
    @SaCheckPermission("system:sysRole:export")
    @GetMapping("/export")
    @Operation(summary = "导出系统角色")
    public ResponseEntity<Resource> exportData(SysRole sysRole) {
        List<SysRole> list = sysRoleService.list(sysRole);
        return ExcelUtil.exportExcel(list, "系统角色", SysRole.class);
    }

}
