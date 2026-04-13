package cn.sijay.owl.file.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.utils.ExcelUtil;
import cn.sijay.owl.file.entity.FileOssStorage;
import cn.sijay.owl.file.service.FileOssStorageService;
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
 * OSS控制器
 * 提供OSS的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/file/file_oss_storage")
@RestController
public class FileOssStorageController extends BaseController {
    private final FileOssStorageService fileOssStorageService;

    /**
     * 分页查询OSS列表
     *
     * @param pageQuery      分页参数
     * @param fileOssStorage 查询条件
     * @return OSS分页列表
     */
    @AccessLog(title = "OSS", operateType = OperateType.QUERY)
    @SaCheckPermission("file:fileOssStorage:query")
    @GetMapping("/page")
    @Operation(summary = "查询OSS列表")
    public Result<List<FileOssStorage>> page(PageQuery pageQuery, FileOssStorage fileOssStorage) {
        return success(fileOssStorageService.page(pageQuery, fileOssStorage));
    }

    /**
     * 查询OSS列表
     *
     * @param fileOssStorage 查询条件
     * @return OSS列表
     */
    @AccessLog(title = "OSS", operateType = OperateType.QUERY)
    @SaCheckPermission("file:fileOssStorage:query")
    @GetMapping("/list")
    @Operation(summary = "查询OSS列表")
    public Result<List<FileOssStorage>> list(FileOssStorage fileOssStorage) {
        return success(fileOssStorageService.list(fileOssStorage));
    }

    /**
     * 根据ID查询OSS详情
     *
     * @param id OSSID
     * @return OSS详情
     */
    @AccessLog(title = "OSS", operateType = OperateType.QUERY)
    @SaCheckPermission("file:fileOssStorage:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询OSS列表")
    public Result<FileOssStorage> getById(@PathVariable Long id) {
        return success(fileOssStorageService.getById(id));
    }

    /**
     * 新增OSS
     *
     * @param fileOssStorage OSS信息
     * @return 操作结果
     */
    @AccessLog(title = "OSS", operateType = OperateType.ADD)
    @SaCheckPermission("file:fileOssStorage:add")
    @PostMapping("/add")
    @Operation(summary = "修改OSS")
    public Result<Boolean> add(@Valid @RequestBody FileOssStorage fileOssStorage) {
        return result(fileOssStorageService.save(fileOssStorage), OperateType.ADD);
    }

    /**
     * 修改OSS
     *
     * @param fileOssStorage OSS信息
     * @return 操作结果
     */
    @AccessLog(title = "OSS", operateType = OperateType.UPDATE)
    @SaCheckPermission("file:fileOssStorage:update")
    @PostMapping("/update")
    @Operation(summary = "修改OSS")
    public Result<Boolean> update(@Valid @RequestBody FileOssStorage fileOssStorage) {
        return result(fileOssStorageService.updateById(fileOssStorage), OperateType.UPDATE);
    }

    /**
     * 删除OSS
     *
     * @param id OSSID
     * @return 操作结果
     */
    @AccessLog(title = "OSS", operateType = OperateType.DELETE)
    @SaCheckPermission("file:fileOssStorage:delete")
    @PostMapping("/remove/{id}")
    @Operation(summary = "删除OSS")
    public Result<Boolean> remove(@PathVariable Long id) {
        return result(fileOssStorageService.delete(id), OperateType.DELETE);
    }

    /**
     * 下载OSS导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "OSS", operateType = OperateType.IMPORT)
    @SaCheckPermission("file:fileOssStorage:import")
    @GetMapping("/template")
    @Operation(summary = "下载OSS模板")
    public ResponseEntity<Resource> template() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "OSS模板", FileOssStorage.class);
    }

    /**
     * 导入OSS数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "OSS", operateType = OperateType.IMPORT)
    @SaCheckPermission("file:fileOssStorage:import")
    @PostMapping("/import")
    @Operation(summary = "导入OSS")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<FileOssStorage> result = ExcelUtil.importExcel(file.getInputStream(), FileOssStorage.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(fileOssStorageService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出OSS数据
     *
     * @param fileOssStorage 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "OSS", operateType = OperateType.EXPORT)
    @SaCheckPermission("file:fileOssStorage:export")
    @GetMapping("/export")
    @Operation(summary = "导出OSS")
    public ResponseEntity<Resource> exportData(FileOssStorage fileOssStorage) {
        List<FileOssStorage> list = fileOssStorageService.list(fileOssStorage);
        return ExcelUtil.exportExcel(list, "OSS", FileOssStorage.class);
    }

}
