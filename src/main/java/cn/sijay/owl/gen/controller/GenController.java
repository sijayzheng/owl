package cn.sijay.owl.gen.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.gen.entity.GenTable;
import cn.sijay.owl.gen.service.GenColumnService;
import cn.sijay.owl.gen.service.GenService;
import cn.sijay.owl.gen.service.GenTableService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * GenController
 *
 * @author sijay
 * @since 2026-04-08
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/gen")
@RestController
public class GenController extends BaseController {

    private final GenTableService tableService;
    private final GenColumnService columnService;
    private final GenService genService;

    @AccessLog(title = "代码生成", operateType = OperateType.QUERY)
    @SaCheckPermission("gen:gen:query")
    @GetMapping("/list")
    @Operation(summary = "查询代码生成表列表")
    public Result<List<GenTable>> list(GenTable genTable) {
        return success(tableService.list(genTable));
    }

    @AccessLog(title = "代码生成", operateType = OperateType.QUERY)
    @SaCheckPermission("gen:gen:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询代码生成表详情")
    public Result<GenTable> getById(@PathVariable Long id) {
        GenTable genTable = tableService.getById(id);
        if (genTable != null) {
            genTable.setColumns(columnService.listByTableId(genTable.getId()));
        }
        return success(genTable);
    }

    @AccessLog(title = "代码生成", operateType = OperateType.UPDATE)
    @SaCheckPermission("gen:gen:update")
    @PostMapping("/update")
    @Operation(summary = "修改代码生成表")
    public Result<Boolean> update(@Valid @RequestBody GenTable genTable) {
        return result(tableService.updateById(genTable), OperateType.UPDATE);
    }

    @AccessLog(title = "代码生成", operateType = OperateType.DELETE)
    @SaCheckPermission("gen:gen:delete")
    @PostMapping("/remove/{id}")
    @Operation(summary = "删除代码生成表")
    public Result<Boolean> remove(@PathVariable Long id) {
        return result(tableService.removeById(id), OperateType.DELETE);
    }

    @AccessLog(title = "代码生成", operateType = OperateType.QUERY)
    @SaCheckPermission("gen:gen:query")
    @GetMapping("/list-db-table")
    @Operation(summary = "查询库中所有的表")
    public Result<List<GenTable>> listDbTable(@RequestBody GenTable genTable) {
        return success(genService.listDbTable(genTable));
    }

    @AccessLog(title = "代码生成", operateType = OperateType.UPDATE)
    @SaCheckPermission("gen:gen:update")
    @PostMapping("/import")
    @Operation(summary = "导入SQL并生成表")
    public Result<Boolean> importData(@RequestBody @NotEmpty List<String> tables) {
        genService.importTable(tables);
        return success(OperateType.IMPORT);
    }

    @AccessLog(title = "代码生成", operateType = OperateType.GEN_CODE)
    @SaCheckPermission("gen:gen:genCode")
    @PostMapping("/generate/{id}")
    @Operation(summary = "生成代码")
    public Result<Boolean> generateCode(@PathVariable Long id) {
        genService.generateCode(id);
        return success(OperateType.GEN_CODE);
    }
}
