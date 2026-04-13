package cn.sijay.owl.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.utils.ExcelUtil;
import cn.sijay.owl.system.entity.SysMessage;
import cn.sijay.owl.system.service.SysMessageService;
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
 * 系统消息控制器
 * 提供用户的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/system/sys_message")
@RestController
public class SysMessageController extends BaseController {
    private final SysMessageService sysMessageService;

    /**
     * 分页查询系统消息列表
     *
     * @param pageQuery 分页参数
     * @param sysMessage 查询条件
     * @return 用户分页列表
     */
    @AccessLog(title = "系统消息", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysMessage:query")
    @GetMapping("/page")
    @Operation(summary = "查询系统消息列表")
    public Result<List<SysMessage>> page(PageQuery pageQuery, SysMessage sysMessage) {
        return success(sysMessageService.page(pageQuery, sysMessage));
    }

    /**
     * 查询系统消息列表(不分页)
     *
     * @param sysMessage 查询条件
     * @return 用户列表
     */
    @AccessLog(title = "系统消息", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysMessage:query")
    @GetMapping("/list")
    @Operation(summary = "查询系统消息列表")
    public Result<List<SysMessage>> list(SysMessage sysMessage) {
        return success(sysMessageService.list(sysMessage));
    }

    /**
     * 根据ID查询系统消息详情
     *
     * @param id 用户ID
     * @return 用户详情
     */
    @AccessLog(title = "系统消息", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysMessage:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询系统消息列表")
    public Result<SysMessage> getById(@PathVariable Long id) {
        return success(sysMessageService.getById(id));
    }

    /**
     * 新增系统消息
     *
     * @param sysMessage 用户信息
     * @return 操作结果
     */
    @AccessLog(title = "系统消息", operateType = OperateType.ADD)
    @SaCheckPermission("system:sysMessage:add")
    @PostMapping("/add")
    @Operation(summary = "修改系统消息")
    public Result<Boolean> add(@Valid @RequestBody SysMessage sysMessage) {
        return result(sysMessageService.save(sysMessage), OperateType.ADD);
    }

    /**
     * 修改系统消息
     *
     * @param sysMessage 用户信息
     * @return 操作结果
     */
    @AccessLog(title = "系统消息", operateType = OperateType.UPDATE)
    @SaCheckPermission("system:sysMessage:update")
    @PostMapping("/update")
    @Operation(summary = "修改系统消息")
    public Result<Boolean> update(@Valid @RequestBody SysMessage sysMessage) {
        return result(sysMessageService.updateById(sysMessage), OperateType.UPDATE);
    }

    /**
     * 删除系统消息
     *
     * @param id 用户ID
     * @return 操作结果
     */
    @AccessLog(title = "系统消息", operateType = OperateType.DELETE)
    @SaCheckPermission("system:sysMessage:delete")
    @PostMapping("/remove/{id}")
    @Operation(summary = "删除系统消息")
    public Result<Boolean> remove(@PathVariable Long id) {
        return result(sysMessageService.delete(id), OperateType.DELETE);
    }

    /**
     * 下载系统消息导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "系统消息", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysMessage:import")
    @GetMapping("/template")
    @Operation(summary = "下载系统消息模板")
    public ResponseEntity<Resource> template() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "系统消息模板", SysMessage.class);
    }

    /**
     * 导入系统消息数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "系统消息", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysMessage:import")
    @PostMapping("/import")
    @Operation(summary = "导入系统消息")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<SysMessage> result = ExcelUtil.importExcel(file.getInputStream(), SysMessage.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(sysMessageService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出系统消息数据
     *
     * @param sysMessage 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "系统消息", operateType = OperateType.EXPORT)
    @SaCheckPermission("system:sysMessage:export")
    @GetMapping("/export")
    @Operation(summary = "导出系统消息")
    public ResponseEntity<Resource> exportData(SysMessage sysMessage) {
        List<SysMessage> list = sysMessageService.list(sysMessage);
        return ExcelUtil.exportExcel(list, "系统消息", SysMessage.class);
    }

}
