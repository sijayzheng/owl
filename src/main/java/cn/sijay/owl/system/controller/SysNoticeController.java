package cn.sijay.owl.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.excel.ExcelUtil;
import cn.sijay.owl.system.dto.SysNoticeQuery;
import cn.sijay.owl.system.entity.SysNotice;
import cn.sijay.owl.system.service.SysNoticeService;
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
@RequestMapping("/system/sysNotice")
@RestController
public class SysNoticeController extends BaseController {
    private final SysNoticeService sysNoticeService;

    /**
     * 分页查询通知公告列表
     *
     * @param pageQuery            分页参数
     * @param sysNoticeQuery 查询条件
     * @return 通知公告分页列表
     */
    @AccessLog(title = "通知公告", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysNotice:query")
    @GetMapping("/page")
    public Result<List<SysNotice>> page(PageQuery pageQuery, SysNoticeQuery sysNoticeQuery) {
        return success(sysNoticeService.page(pageQuery, sysNoticeQuery));
    }

    /**
     * 查询通知公告列表
     *
     * @param sysNoticeQuery 查询条件
     * @return 通知公告列表
     */
    @AccessLog(title = "通知公告", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysNotice:query")
    @GetMapping("/list")
    public Result<List<SysNotice>> list(SysNoticeQuery sysNoticeQuery) {
        return success(sysNoticeService.list(sysNoticeQuery));
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
    public Result<SysNotice> getById(@PathVariable Long id) {
        return success(sysNoticeService.getById(id));
    }

    /**
     * 保存通知公告
     *
     * @param sysNotice 通知公告信息
     * @return 操作结果
     */
    @AccessLog(title = "通知公告", operateType = OperateType.SAVE)
    @SaCheckPermission("system:sysNotice:save")
    @PostMapping("/save")
    public Result<Boolean> save(@Valid @RequestBody SysNotice sysNotice) {
        return result(sysNoticeService.validSave(sysNotice), OperateType.SAVE);
    }

    /**
     * 删除通知公告
     *
     * @param ids 通知公告ID
     * @return 操作结果
     */
    @AccessLog(title = "通知公告", operateType = OperateType.DELETE)
    @SaCheckPermission("system:sysNotice:delete")
    @PostMapping("/remove")
    public Result<Boolean> remove(@RequestBody List<Long> ids) {
        return result(sysNoticeService.removeByIds(ids), OperateType.DELETE);
    }

    /**
     * 下载通知公告导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "通知公告", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysNotice:import")
    @GetMapping("/downloadTemplate")
    public ResponseEntity<Resource> downloadTemplate() throws IOException {
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
     * @param sysNoticeQuery 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "通知公告", operateType = OperateType.EXPORT)
    @SaCheckPermission("system:sysNotice:export")
    @GetMapping("/export")
    public ResponseEntity<Resource> exportData(SysNoticeQuery sysNoticeQuery) {
        List<SysNotice> list = sysNoticeService.list(sysNoticeQuery);
        return ExcelUtil.exportExcel(list, "通知公告", SysNotice.class);
    }

}
