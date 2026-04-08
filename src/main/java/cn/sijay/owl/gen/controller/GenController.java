package cn.sijay.owl.gen.controller;


import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.gen.service.GenColumnService;
import cn.sijay.owl.gen.service.GenService;
import cn.sijay.owl.gen.service.GenTableService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * GenController
 *
 * @author sijay
 * @since 2026/4/8
 */
@Valid
@RequiredArgsConstructor
@RequestMapping("/gen")
@RestController
public class GenController extends BaseController {

    private final GenTableService tableService;
    private final GenColumnService columnService;
    private final GenService genService;


    @PostMapping("/import")
    @Operation(summary = "导入SQL并生成表")
    public Result<Boolean> importData(@RequestBody @NotEmpty List<String> tables) {
        return result(genService.importTable(tables), OperateType.IMPORT);
    }

}
