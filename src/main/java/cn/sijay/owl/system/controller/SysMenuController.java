package cn.sijay.owl.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.utils.ExcelUtil;
import cn.sijay.owl.system.entity.SysMenu;
import cn.sijay.owl.system.service.SysMenuService;
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
 * 系统菜单控制器
 * 提供用户的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/system/sys_menu")
@RestController
public class SysMenuController extends BaseController {
    private final SysMenuService sysMenuService;

    /**
     * 分页查询系统菜单列表
     *
     * @param pageQuery 分页参数
     * @param sysMenu 查询条件
     * @return 用户分页列表
     */
    @AccessLog(title = "系统菜单", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysMenu:query")
    @GetMapping("/page")
    @Operation(summary = "查询系统菜单列表")
    public Result<List<SysMenu>> page(PageQuery pageQuery, SysMenu sysMenu) {
        return success(sysMenuService.page(pageQuery, sysMenu));
    }

    /**
     * 查询系统菜单列表(不分页)
     *
     * @param sysMenu 查询条件
     * @return 用户列表
     */
    @AccessLog(title = "系统菜单", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysMenu:query")
    @GetMapping("/list")
    @Operation(summary = "查询系统菜单列表")
    public Result<List<SysMenu>> list(SysMenu sysMenu) {
        return success(sysMenuService.list(sysMenu));
    }

    /**
     * 根据ID查询系统菜单详情
     *
     * @param id 用户ID
     * @return 用户详情
     */
    @AccessLog(title = "系统菜单", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysMenu:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询系统菜单列表")
    public Result<SysMenu> getById(@PathVariable Long id) {
        return success(sysMenuService.getById(id));
    }

    /**
     * 新增系统菜单
     *
     * @param sysMenu 用户信息
     * @return 操作结果
     */
    @AccessLog(title = "系统菜单", operateType = OperateType.ADD)
    @SaCheckPermission("system:sysMenu:add")
    @PostMapping("/add")
    @Operation(summary = "修改系统菜单")
    public Result<Boolean> add(@Valid @RequestBody SysMenu sysMenu) {
        return result(sysMenuService.save(sysMenu), OperateType.ADD);
    }

    /**
     * 修改系统菜单
     *
     * @param sysMenu 用户信息
     * @return 操作结果
     */
    @AccessLog(title = "系统菜单", operateType = OperateType.UPDATE)
    @SaCheckPermission("system:sysMenu:update")
    @PostMapping("/update")
    @Operation(summary = "修改系统菜单")
    public Result<Boolean> update(@Valid @RequestBody SysMenu sysMenu) {
        return result(sysMenuService.updateById(sysMenu), OperateType.UPDATE);
    }

    /**
     * 删除系统菜单
     *
     * @param id 用户ID
     * @return 操作结果
     */
    @AccessLog(title = "系统菜单", operateType = OperateType.DELETE)
    @SaCheckPermission("system:sysMenu:delete")
    @PostMapping("/remove/{id}")
    @Operation(summary = "删除系统菜单")
    public Result<Boolean> remove(@PathVariable Long id) {
        return result(sysMenuService.delete(id), OperateType.DELETE);
    }

    /**
     * 下载系统菜单导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "系统菜单", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysMenu:import")
    @GetMapping("/template")
    @Operation(summary = "下载系统菜单模板")
    public ResponseEntity<Resource> template() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "系统菜单模板", SysMenu.class);
    }

    /**
     * 导入系统菜单数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "系统菜单", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysMenu:import")
    @PostMapping("/import")
    @Operation(summary = "导入系统菜单")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<SysMenu> result = ExcelUtil.importExcel(file.getInputStream(), SysMenu.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(sysMenuService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出系统菜单数据
     *
     * @param sysMenu 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "系统菜单", operateType = OperateType.EXPORT)
    @SaCheckPermission("system:sysMenu:export")
    @GetMapping("/export")
    @Operation(summary = "导出系统菜单")
    public ResponseEntity<Resource> exportData(SysMenu sysMenu) {
        List<SysMenu> list = sysMenuService.list(sysMenu);
        return ExcelUtil.exportExcel(list, "系统菜单", SysMenu.class);
    }

}
