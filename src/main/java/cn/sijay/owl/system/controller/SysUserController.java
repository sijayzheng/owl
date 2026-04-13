package cn.sijay.owl.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.utils.ExcelUtil;
import cn.sijay.owl.system.entity.SysUser;
import cn.sijay.owl.system.service.SysUserService;
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
 * 系统用户控制器
 * 提供用户的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/system/sys_user")
@RestController
public class SysUserController extends BaseController {
    private final SysUserService sysUserService;

    /**
     * 分页查询系统用户列表
     *
     * @param pageQuery 分页参数
     * @param sysUser 查询条件
     * @return 用户分页列表
     */
    @AccessLog(title = "系统用户", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysUser:query")
    @GetMapping("/page")
    @Operation(summary = "查询系统用户列表")
    public Result<List<SysUser>> page(PageQuery pageQuery, SysUser sysUser) {
        return success(sysUserService.page(pageQuery, sysUser));
    }

    /**
     * 查询系统用户列表(不分页)
     *
     * @param sysUser 查询条件
     * @return 用户列表
     */
    @AccessLog(title = "系统用户", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysUser:query")
    @GetMapping("/list")
    @Operation(summary = "查询系统用户列表")
    public Result<List<SysUser>> list(SysUser sysUser) {
        return success(sysUserService.list(sysUser));
    }

    /**
     * 根据ID查询系统用户详情
     *
     * @param id 用户ID
     * @return 用户详情
     */
    @AccessLog(title = "系统用户", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysUser:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询系统用户列表")
    public Result<SysUser> getById(@PathVariable Long id) {
        return success(sysUserService.getById(id));
    }

    /**
     * 新增系统用户
     *
     * @param sysUser 用户信息
     * @return 操作结果
     */
    @AccessLog(title = "系统用户", operateType = OperateType.ADD)
    @SaCheckPermission("system:sysUser:add")
    @PostMapping("/add")
    @Operation(summary = "修改系统用户")
    public Result<Boolean> add(@Valid @RequestBody SysUser sysUser) {
        return result(sysUserService.save(sysUser), OperateType.ADD);
    }

    /**
     * 修改系统用户
     *
     * @param sysUser 用户信息
     * @return 操作结果
     */
    @AccessLog(title = "系统用户", operateType = OperateType.UPDATE)
    @SaCheckPermission("system:sysUser:update")
    @PostMapping("/update")
    @Operation(summary = "修改系统用户")
    public Result<Boolean> update(@Valid @RequestBody SysUser sysUser) {
        return result(sysUserService.updateById(sysUser), OperateType.UPDATE);
    }

    /**
     * 删除系统用户
     *
     * @param id 用户ID
     * @return 操作结果
     */
    @AccessLog(title = "系统用户", operateType = OperateType.DELETE)
    @SaCheckPermission("system:sysUser:delete")
    @PostMapping("/remove/{id}")
    @Operation(summary = "删除系统用户")
    public Result<Boolean> remove(@PathVariable Long id) {
        return result(sysUserService.delete(id), OperateType.DELETE);
    }

    /**
     * 下载系统用户导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "系统用户", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysUser:import")
    @GetMapping("/template")
    @Operation(summary = "下载系统用户模板")
    public ResponseEntity<Resource> template() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "系统用户模板", SysUser.class);
    }

    /**
     * 导入系统用户数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "系统用户", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysUser:import")
    @PostMapping("/import")
    @Operation(summary = "导入系统用户")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<SysUser> result = ExcelUtil.importExcel(file.getInputStream(), SysUser.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(sysUserService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出系统用户数据
     *
     * @param sysUser 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "系统用户", operateType = OperateType.EXPORT)
    @SaCheckPermission("system:sysUser:export")
    @GetMapping("/export")
    @Operation(summary = "导出系统用户")
    public ResponseEntity<Resource> exportData(SysUser sysUser) {
        List<SysUser> list = sysUserService.list(sysUser);
        return ExcelUtil.exportExcel(list, "系统用户", SysUser.class);
    }

}
