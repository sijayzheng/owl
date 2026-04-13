package cn.sijay.owl.file.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.utils.ExcelUtil;
import cn.sijay.owl.file.entity.FileStorage;
import cn.sijay.owl.file.service.FileStorageService;
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
 * 文件存储控制器
 * 提供文件存储的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/file/file_storage")
@RestController
public class FileStorageController extends BaseController {
    private final FileStorageService fileStorageService;

    /**
     * 分页查询文件存储列表
     *
     * @param pageQuery   分页参数
     * @param fileStorage 查询条件
     * @return 文件存储分页列表
     */
    @AccessLog(title = "文件存储", operateType = OperateType.QUERY)
    @SaCheckPermission("file:fileStorage:query")
    @GetMapping("/page")
    @Operation(summary = "查询文件存储列表")
    public Result<List<FileStorage>> page(PageQuery pageQuery, FileStorage fileStorage) {
        return success(fileStorageService.page(pageQuery, fileStorage));
    }

    /**
     * 查询文件存储列表
     *
     * @param fileStorage 查询条件
     * @return 文件存储列表
     */
    @AccessLog(title = "文件存储", operateType = OperateType.QUERY)
    @SaCheckPermission("file:fileStorage:query")
    @GetMapping("/list")
    @Operation(summary = "查询文件存储列表")
    public Result<List<FileStorage>> list(FileStorage fileStorage) {
        return success(fileStorageService.list(fileStorage));
    }

    /**
     * 根据ID查询文件存储详情
     *
     * @param id 文件存储ID
     * @return 文件存储详情
     */
    @AccessLog(title = "文件存储", operateType = OperateType.QUERY)
    @SaCheckPermission("file:fileStorage:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询文件存储列表")
    public Result<FileStorage> getById(@PathVariable Long id) {
        return success(fileStorageService.getById(id));
    }

    /**
     * 新增文件存储
     *
     * @param fileStorage 文件存储信息
     * @return 操作结果
     */
    @AccessLog(title = "文件存储", operateType = OperateType.ADD)
    @SaCheckPermission("file:fileStorage:add")
    @PostMapping("/add")
    @Operation(summary = "修改文件存储")
    public Result<Boolean> add(@Valid @RequestBody FileStorage fileStorage) {
        return result(fileStorageService.save(fileStorage), OperateType.ADD);
    }

    /**
     * 修改文件存储
     *
     * @param fileStorage 文件存储信息
     * @return 操作结果
     */
    @AccessLog(title = "文件存储", operateType = OperateType.UPDATE)
    @SaCheckPermission("file:fileStorage:update")
    @PostMapping("/update")
    @Operation(summary = "修改文件存储")
    public Result<Boolean> update(@Valid @RequestBody FileStorage fileStorage) {
        return result(fileStorageService.updateById(fileStorage), OperateType.UPDATE);
    }

    /**
     * 删除文件存储
     *
     * @param id 文件存储ID
     * @return 操作结果
     */
    @AccessLog(title = "文件存储", operateType = OperateType.DELETE)
    @SaCheckPermission("file:fileStorage:delete")
    @PostMapping("/remove/{id}")
    @Operation(summary = "删除文件存储")
    public Result<Boolean> remove(@PathVariable Long id) {
        return result(fileStorageService.delete(id), OperateType.DELETE);
    }

    /**
     * 下载文件存储导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "文件存储", operateType = OperateType.IMPORT)
    @SaCheckPermission("file:fileStorage:import")
    @GetMapping("/template")
    @Operation(summary = "下载文件存储模板")
    public ResponseEntity<Resource> template() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "文件存储模板", FileStorage.class);
    }

    /**
     * 导入文件存储数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "文件存储", operateType = OperateType.IMPORT)
    @SaCheckPermission("file:fileStorage:import")
    @PostMapping("/import")
    @Operation(summary = "导入文件存储")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<FileStorage> result = ExcelUtil.importExcel(file.getInputStream(), FileStorage.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(fileStorageService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出文件存储数据
     *
     * @param fileStorage 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "文件存储", operateType = OperateType.EXPORT)
    @SaCheckPermission("file:fileStorage:export")
    @GetMapping("/export")
    @Operation(summary = "导出文件存储")
    public ResponseEntity<Resource> exportData(FileStorage fileStorage) {
        List<FileStorage> list = fileStorageService.list(fileStorage);
        return ExcelUtil.exportExcel(list, "文件存储", FileStorage.class);
    }

}
