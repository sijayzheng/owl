package cn.sijay.owl.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.utils.ExcelUtil;
import cn.sijay.owl.system.entity.SysNotice;
import cn.sijay.owl.system.service.SysNoticeService;
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
 * 通知公告控制器
 * 提供通知公告的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/system/sys_notice")
@RestController
public class SysNoticeController extends BaseController {
    private final SysNoticeService sysNoticeService;

    /**
     * 分页查询通知公告列表
     *
     * @param pageQuery 分页参数
     * @param sysNotice 查询条件
     * @return 通知公告分页列表
     */
    @AccessLog(title = "通知公告", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysNotice:query")
    @GetMapping("/page")
    @Operation(summary = "查询通知公告列表")
    public Result<List<SysNotice>> page(PageQuery pageQuery, SysNotice sysNotice) {
        return success(sysNoticeService.page(pageQuery, sysNotice));
    }

    /**
     * 查询通知公告列表
     *
     * @param sysNotice 查询条件
     * @return 通知公告列表
     */
    @AccessLog(title = "通知公告", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysNotice:query")
    @GetMapping("/list")
    @Operation(summary = "查询通知公告列表")
    public Result<List<SysNotice>> list(SysNotice sysNotice) {
        return success(sysNoticeService.list(sysNotice));
    }

    /**
     * 根据ID查询通知公告详情
     *
     * @param id 通知公告ID
     * @return 通知公告详情
     */
    @AccessLog(title = "通知公告", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysNotice:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询通知公告列表")
    public Result<SysNotice> getById(@PathVariable Long id) {
        return success(sysNoticeService.getById(id));
    }

    /**
     * 新增通知公告
     *
     * @param sysNotice 通知公告信息
     * @return 操作结果
     */
    @AccessLog(title = "通知公告", operateType = OperateType.ADD)
    @SaCheckPermission("system:sysNotice:add")
    @PostMapping("/add")
    @Operation(summary = "修改通知公告")
    public Result<Boolean> add(@Valid @RequestBody SysNotice sysNotice) {
        return result(sysNoticeService.save(sysNotice), OperateType.ADD);
    }

    /**
     * 修改通知公告
     *
     * @param sysNotice 通知公告信息
     * @return 操作结果
     */
    @AccessLog(title = "通知公告", operateType = OperateType.UPDATE)
    @SaCheckPermission("system:sysNotice:update")
    @PostMapping("/update")
    @Operation(summary = "修改通知公告")
    public Result<Boolean> update(@Valid @RequestBody SysNotice sysNotice) {
        return result(sysNoticeService.updateById(sysNotice), OperateType.UPDATE);
    }

    /**
     * 删除通知公告
     *
     * @param id 通知公告ID
     * @return 操作结果
     */
    @AccessLog(title = "通知公告", operateType = OperateType.DELETE)
    @SaCheckPermission("system:sysNotice:delete")
    @PostMapping("/remove/{id}")
    @Operation(summary = "删除通知公告")
    public Result<Boolean> remove(@PathVariable Long id) {
        return result(sysNoticeService.delete(id), OperateType.DELETE);
    }

    /**
     * 下载通知公告导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "通知公告", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysNotice:import")
    @GetMapping("/template")
    @Operation(summary = "下载通知公告模板")
    public ResponseEntity<Resource> template() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "通知公告模板", SysNotice.class);
    }

    /**
     * 导入通知公告数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "通知公告", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysNotice:import")
    @PostMapping("/import")
    @Operation(summary = "导入通知公告")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<SysNotice> result = ExcelUtil.importExcel(file.getInputStream(), SysNotice.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(sysNoticeService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出通知公告数据
     *
     * @param sysNotice 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "通知公告", operateType = OperateType.EXPORT)
    @SaCheckPermission("system:sysNotice:export")
    @GetMapping("/export")
    @Operation(summary = "导出通知公告")
    public ResponseEntity<Resource> exportData(SysNotice sysNotice) {
        List<SysNotice> list = sysNoticeService.list(sysNotice);
        return ExcelUtil.exportExcel(list, "通知公告", SysNotice.class);
    }

}
