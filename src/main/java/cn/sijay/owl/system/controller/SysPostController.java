package cn.sijay.owl.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.utils.ExcelUtil;
import cn.sijay.owl.system.entity.SysPost;
import cn.sijay.owl.system.service.SysPostService;
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
 * 系统岗位控制器
 * 提供用户的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/system/sys_post")
@RestController
public class SysPostController extends BaseController {
    private final SysPostService sysPostService;

    /**
     * 分页查询系统岗位列表
     *
     * @param pageQuery 分页参数
     * @param sysPost 查询条件
     * @return 用户分页列表
     */
    @AccessLog(title = "系统岗位", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysPost:query")
    @GetMapping("/page")
    @Operation(summary = "查询系统岗位列表")
    public Result<List<SysPost>> page(PageQuery pageQuery, SysPost sysPost) {
        return success(sysPostService.page(pageQuery, sysPost));
    }

    /**
     * 查询系统岗位列表(不分页)
     *
     * @param sysPost 查询条件
     * @return 用户列表
     */
    @AccessLog(title = "系统岗位", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysPost:query")
    @GetMapping("/list")
    @Operation(summary = "查询系统岗位列表")
    public Result<List<SysPost>> list(SysPost sysPost) {
        return success(sysPostService.list(sysPost));
    }

    /**
     * 根据ID查询系统岗位详情
     *
     * @param id 用户ID
     * @return 用户详情
     */
    @AccessLog(title = "系统岗位", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysPost:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询系统岗位列表")
    public Result<SysPost> getById(@PathVariable Long id) {
        return success(sysPostService.getById(id));
    }

    /**
     * 新增系统岗位
     *
     * @param sysPost 用户信息
     * @return 操作结果
     */
    @AccessLog(title = "系统岗位", operateType = OperateType.ADD)
    @SaCheckPermission("system:sysPost:add")
    @PostMapping("/add")
    @Operation(summary = "修改系统岗位")
    public Result<Boolean> add(@Valid @RequestBody SysPost sysPost) {
        return result(sysPostService.save(sysPost), OperateType.ADD);
    }

    /**
     * 修改系统岗位
     *
     * @param sysPost 用户信息
     * @return 操作结果
     */
    @AccessLog(title = "系统岗位", operateType = OperateType.UPDATE)
    @SaCheckPermission("system:sysPost:update")
    @PostMapping("/update")
    @Operation(summary = "修改系统岗位")
    public Result<Boolean> update(@Valid @RequestBody SysPost sysPost) {
        return result(sysPostService.updateById(sysPost), OperateType.UPDATE);
    }

    /**
     * 删除系统岗位
     *
     * @param id 用户ID
     * @return 操作结果
     */
    @AccessLog(title = "系统岗位", operateType = OperateType.DELETE)
    @SaCheckPermission("system:sysPost:delete")
    @PostMapping("/remove/{id}")
    @Operation(summary = "删除系统岗位")
    public Result<Boolean> remove(@PathVariable Long id) {
        return result(sysPostService.delete(id), OperateType.DELETE);
    }

    /**
     * 下载系统岗位导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "系统岗位", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysPost:import")
    @GetMapping("/template")
    @Operation(summary = "下载系统岗位模板")
    public ResponseEntity<Resource> template() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "系统岗位模板", SysPost.class);
    }

    /**
     * 导入系统岗位数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "系统岗位", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysPost:import")
    @PostMapping("/import")
    @Operation(summary = "导入系统岗位")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<SysPost> result = ExcelUtil.importExcel(file.getInputStream(), SysPost.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(sysPostService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出系统岗位数据
     *
     * @param sysPost 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "系统岗位", operateType = OperateType.EXPORT)
    @SaCheckPermission("system:sysPost:export")
    @GetMapping("/export")
    @Operation(summary = "导出系统岗位")
    public ResponseEntity<Resource> exportData(SysPost sysPost) {
        List<SysPost> list = sysPostService.list(sysPost);
        return ExcelUtil.exportExcel(list, "系统岗位", SysPost.class);
    }

}
