package cn.sijay.owl.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.excel.ExcelUtil;
import cn.sijay.owl.system.dto.SysDictDataQuery;
import cn.sijay.owl.system.entity.SysDictData;
import cn.sijay.owl.system.service.SysDictDataService;
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
 * 字典数据控制器
 * 提供字典数据的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/system/sysDictData")
@RestController
public class SysDictDataController extends BaseController {
    private final SysDictDataService sysDictDataService;

    /**
     * 分页查询字典数据列表
     *
     * @param pageQuery            分页参数
     * @param sysDictDataQuery 查询条件
     * @return 字典数据分页列表
     */
    @AccessLog(title = "字典数据", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysDictData:query")
    @GetMapping("/page")
    public Result<List<SysDictData>> page(PageQuery pageQuery, SysDictDataQuery sysDictDataQuery) {
        return success(sysDictDataService.page(pageQuery, sysDictDataQuery));
    }

    /**
     * 查询字典数据列表
     *
     * @param sysDictDataQuery 查询条件
     * @return 字典数据列表
     */
    @AccessLog(title = "字典数据", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysDictData:query")
    @GetMapping("/list")
    public Result<List<SysDictData>> list(SysDictDataQuery sysDictDataQuery) {
        return success(sysDictDataService.list(sysDictDataQuery));
    }

    /**
     * 根据ID查询字典数据详情
     *
     * @param id 字典数据ID
     * @return 字典数据详情
     */
    @AccessLog(title = "字典数据", operateType = OperateType.QUERY)
    @SaCheckPermission("system:sysDictData:query")
    @GetMapping("/{id}")
    public Result<SysDictData> getById(@PathVariable Long id) {
        return success(sysDictDataService.getById(id));
    }

    /**
     * 保存字典数据
     *
     * @param sysDictData 字典数据信息
     * @return 操作结果
     */
    @AccessLog(title = "字典数据", operateType = OperateType.SAVE)
    @SaCheckPermission("system:sysDictData:save")
    @PostMapping("/save")
    public Result<Boolean> save(@Valid @RequestBody SysDictData sysDictData) {
        return result(sysDictDataService.validSave(sysDictData), OperateType.SAVE);
    }

    /**
     * 删除字典数据
     *
     * @param ids 字典数据ID
     * @return 操作结果
     */
    @AccessLog(title = "字典数据", operateType = OperateType.DELETE)
    @SaCheckPermission("system:sysDictData:delete")
    @PostMapping("/remove")
    public Result<Boolean> remove(@RequestBody List<Long> ids) {
        return result(sysDictDataService.removeByIds(ids), OperateType.DELETE);
    }

    /**
     * 下载字典数据导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "字典数据", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysDictData:import")
    @GetMapping("/downloadTemplate")
    public ResponseEntity<Resource> downloadTemplate() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "字典数据模板", SysDictData.class);
    }

    /**
     * 导入字典数据数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "字典数据", operateType = OperateType.IMPORT)
    @SaCheckPermission("system:sysDictData:import")
    @PostMapping("/import")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<SysDictData> result = ExcelUtil.importExcel(file.getInputStream(), SysDictData.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(sysDictDataService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出字典数据数据
     *
     * @param sysDictDataQuery 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "字典数据", operateType = OperateType.EXPORT)
    @SaCheckPermission("system:sysDictData:export")
    @GetMapping("/export")
    public ResponseEntity<Resource> exportData(SysDictDataQuery sysDictDataQuery) {
        List<SysDictData> list = sysDictDataService.list(sysDictDataQuery);
        return ExcelUtil.exportExcel(list, "字典数据", SysDictData.class);
    }

}
