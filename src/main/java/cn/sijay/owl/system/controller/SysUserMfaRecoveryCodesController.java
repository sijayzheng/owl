package cn.sijay.owl.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.excel.ExcelUtil;
import cn.sijay.owl.system.dto.SysUserMfaRecoveryCodesQuery;
import cn.sijay.owl.system.entity.SysUserMfaRecoveryCodes;
import cn.sijay.owl.system.service.SysUserMfaRecoveryCodesService;
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
 * MFA备用验证码控制器
 * 提供MFA备用验证码的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/system/sysUserMfaRecoveryCodes")
@RestController
public class SysUserMfaRecoveryCodesController extends BaseController {
    private final SysUserMfaRecoveryCodesService sysUserMfaRecoveryCodesService;

    /**
     * 分页查询MFA备用验证码列表
     *
     * @param pageQuery            分页参数
     * @param sysUserMfaRecoveryCodesQuery 查询条件
     * @return MFA备用验证码分页列表
     */
    @AccessLog(title = "MFA备用验证码", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysUserMfaRecoveryCodes:query")
    @GetMapping("/page")
    public Result<List<SysUserMfaRecoveryCodes>> page(PageQuery pageQuery, SysUserMfaRecoveryCodesQuery sysUserMfaRecoveryCodesQuery) {
        return success(sysUserMfaRecoveryCodesService.page(pageQuery, sysUserMfaRecoveryCodesQuery));
    }

    /**
     * 查询MFA备用验证码列表
     *
     * @param sysUserMfaRecoveryCodesQuery 查询条件
     * @return MFA备用验证码列表
     */
    @AccessLog(title = "MFA备用验证码", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysUserMfaRecoveryCodes:query")
    @GetMapping("/list")
    public Result<List<SysUserMfaRecoveryCodes>> list(SysUserMfaRecoveryCodesQuery sysUserMfaRecoveryCodesQuery) {
        return success(sysUserMfaRecoveryCodesService.list(sysUserMfaRecoveryCodesQuery));
    }

    /**
     * 根据ID查询MFA备用验证码详情
     *
     * @param id MFA备用验证码ID
     * @return MFA备用验证码详情
     */
    @AccessLog(title = "MFA备用验证码", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysUserMfaRecoveryCodes:query")
    @GetMapping("/{id}")
    public Result<SysUserMfaRecoveryCodes> getById(@PathVariable Long id) {
        return success(sysUserMfaRecoveryCodesService.getById(id));
    }

    /**
     * 保存MFA备用验证码
     *
     * @param sysUserMfaRecoveryCodes MFA备用验证码信息
     * @return 操作结果
     */
    @AccessLog(title = "MFA备用验证码", operateType = OperateType.SAVE)
    @SaCheckPermission("system:sysUserMfaRecoveryCodes:save")
    @PostMapping("/save")
    public Result<Boolean> save(@Valid @RequestBody SysUserMfaRecoveryCodes sysUserMfaRecoveryCodes) {
        return result(sysUserMfaRecoveryCodesService.validSave(sysUserMfaRecoveryCodes), OperateType.SAVE);
    }

    /**
     * 删除MFA备用验证码
     *
     * @param ids MFA备用验证码ID
     * @return 操作结果
     */
    @AccessLog(title = "MFA备用验证码", operateType = OperateType.DELETE)
    @SaCheckPermission("system:sysUserMfaRecoveryCodes:delete")
    @PostMapping("/remove")
    public Result<Boolean> remove(@RequestBody List<Long> ids) {
        return result(sysUserMfaRecoveryCodesService.removeByIds(ids), OperateType.DELETE);
    }

    /**
     * 下载MFA备用验证码导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "MFA备用验证码", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysUserMfaRecoveryCodes:import")
    @GetMapping("/downloadTemplate")
    public ResponseEntity<Resource> downloadTemplate() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "MFA备用验证码模板", SysUserMfaRecoveryCodes.class);
    }

    /**
     * 导入MFA备用验证码数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "MFA备用验证码", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysUserMfaRecoveryCodes:import")
    @PostMapping("/import")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<SysUserMfaRecoveryCodes> result = ExcelUtil.importExcel(file.getInputStream(), SysUserMfaRecoveryCodes.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(sysUserMfaRecoveryCodesService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出MFA备用验证码数据
     *
     * @param sysUserMfaRecoveryCodesQuery 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "MFA备用验证码", operateType = OperateType.EXPORT)
    @SaCheckPermission("system:sysUserMfaRecoveryCodes:export")
    @GetMapping("/export")
    public ResponseEntity<Resource> exportData(SysUserMfaRecoveryCodesQuery sysUserMfaRecoveryCodesQuery) {
        List<SysUserMfaRecoveryCodes> list = sysUserMfaRecoveryCodesService.list(sysUserMfaRecoveryCodesQuery);
        return ExcelUtil.exportExcel(list, "MFA备用验证码", SysUserMfaRecoveryCodes.class);
    }

}
