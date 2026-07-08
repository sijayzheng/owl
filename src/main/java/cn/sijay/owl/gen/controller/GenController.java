package cn.sijay.owl.gen.controller;

import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.gen.entity.GenTable;
import cn.sijay.owl.gen.service.GenColumnService;
import cn.sijay.owl.gen.service.GenService;
import cn.sijay.owl.gen.service.GenTableService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
    @GetMapping("/page")
    @Operation(summary = "查询代码生成表列表")
    public Result<List<GenTable>> page(PageQuery pageQuery, GenTable genTable) {
        return success(tableService.page(pageQuery, genTable));
    }

    @AccessLog(title = "代码生成", operateType = OperateType.QUERY)
    @GetMapping("/getById/{id}")
    @Operation(summary = "查询代码生成表详情")
    public Result<GenTable> getById(@PathVariable Long id) {
        GenTable genTable = tableService.getById(id);
        if (genTable != null) {
            genTable.setColumns(columnService.listByTableId(genTable.getId()));
        }
        return success(genTable);
    }

    @AccessLog(title = "代码生成", operateType = OperateType.UPDATE)
    @PostMapping("/update")
    @Operation(summary = "修改代码生成表")
    public Result<Boolean> update(@Valid @RequestBody GenTable genTable) {
        return result(genService.update(genTable), OperateType.UPDATE);
    }

    @AccessLog(title = "代码生成", operateType = OperateType.DELETE)
    @PostMapping("/remove")
    @Operation(summary = "删除代码生成表")
    public Result<Boolean> remove(@RequestBody List<Long> ids) {
        return result(genService.remove(ids), OperateType.DELETE);
    }

    @AccessLog(title = "代码生成", operateType = OperateType.QUERY)
    @GetMapping("/listDbTable")
    @Operation(summary = "查询库中所有的表")
    public Result<List<GenTable>> listDbTable(GenTable genTable) {
        return success(genService.listDbTable(genTable));
    }

    @AccessLog(title = "代码生成", operateType = OperateType.UPDATE)
    @PostMapping("/import")
    @Operation(summary = "导入SQL并生成表")
    public Result<Boolean> importData(@RequestBody GenTable genTable) {
        genService.importTable(genTable.getTableName());
        return success(OperateType.IMPORT);
    }

    @AccessLog(title = "代码生成", operateType = OperateType.GEN)
    @PostMapping("/generate/{id}")
    @Operation(summary = "生成代码")
    public Result<Boolean> generateCode(@PathVariable Long id) {
        genService.generateCode(id);
        return success(OperateType.GEN);
    }

}
