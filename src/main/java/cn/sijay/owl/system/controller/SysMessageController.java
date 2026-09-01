package cn.sijay.owl.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.excel.ExcelUtil;
import cn.sijay.owl.system.dto.SysMessageQuery;
import cn.sijay.owl.system.entity.SysMessage;
import cn.sijay.owl.system.service.SysMessageService;
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
 * 提供系统消息的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/system/sysMessage")
@RestController
public class SysMessageController extends BaseController {
    private final SysMessageService sysMessageService;

    /**
     * 分页查询系统消息列表
     *
     * @param pageQuery            分页参数
     * @param sysMessageQuery 查询条件
     * @return 系统消息分页列表
     */
    @AccessLog(title = "系统消息", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysMessage:query")
    @GetMapping("/page")
    public Result<List<SysMessage>> page(PageQuery pageQuery, SysMessageQuery sysMessageQuery) {
        return success(sysMessageService.page(pageQuery, sysMessageQuery));
    }

    /**
     * 查询系统消息列表
     *
     * @param sysMessageQuery 查询条件
     * @return 系统消息列表
     */
    @AccessLog(title = "系统消息", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysMessage:query")
    @GetMapping("/list")
    public Result<List<SysMessage>> list(SysMessageQuery sysMessageQuery) {
        return success(sysMessageService.list(sysMessageQuery));
    }

    /**
     * 根据ID查询系统消息详情
     *
     * @param id 系统消息ID
     * @return 系统消息详情
     */
    @AccessLog(title = "系统消息", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysMessage:query")
    @GetMapping("/{id}")
    public Result<SysMessage> getById(@PathVariable Long id) {
        return success(sysMessageService.getById(id));
    }

    /**
     * 保存系统消息
     *
     * @param sysMessage 系统消息信息
     * @return 操作结果
     */
    @AccessLog(title = "系统消息", operateType = OperateType.SAVE)
    @SaCheckPermission("system:sysMessage:save")
    @PostMapping("/save")
    public Result<Boolean> save(@Valid @RequestBody SysMessage sysMessage) {
        return result(sysMessageService.validSave(sysMessage), OperateType.SAVE);
    }

    /**
     * 删除系统消息
     *
     * @param ids 系统消息ID
     * @return 操作结果
     */
    @AccessLog(title = "系统消息", operateType = OperateType.DELETE)
    @SaCheckPermission("system:sysMessage:delete")
    @PostMapping("/remove")
    public Result<Boolean> remove(@RequestBody List<Long> ids) {
        return result(sysMessageService.removeByIds(ids), OperateType.DELETE);
    }

    /**
     * 下载系统消息导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "系统消息", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysMessage:import")
    @GetMapping("/downloadTemplate")
    public ResponseEntity<Resource> downloadTemplate() throws IOException {
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
     * @param sysMessageQuery 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "系统消息", operateType = OperateType.EXPORT)
    @SaCheckPermission("system:sysMessage:export")
    @GetMapping("/export")
    public ResponseEntity<Resource> exportData(SysMessageQuery sysMessageQuery) {
        List<SysMessage> list = sysMessageService.list(sysMessageQuery);
        return ExcelUtil.exportExcel(list, "系统消息", SysMessage.class);
    }

}
