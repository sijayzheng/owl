package cn.sijay.owl.demo.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.sijay.owl.common.annotations.AccessLog;
import cn.sijay.owl.common.base.BaseController;
import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.entity.Result;
import cn.sijay.owl.common.enums.OperateType;
import cn.sijay.owl.common.excel.ExcelUtil;
import cn.sijay.owl.demo.entity.User;
import cn.sijay.owl.demo.service.UserService;
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
 * 人员demo控制器
 * 提供人员demo的增删改查、导入导出等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@SaIgnore
@Valid
@RequiredArgsConstructor
@RequestMapping("/demo/user")
@RestController
public class UserController extends BaseController {
    private final UserService userService;

    /**
     * 分页查询人员demo列表
     *
     * @param pageQuery 分页参数
     * @param user      查询条件
     * @return 人员demo分页列表
     */
    @AccessLog(title = "人员demo", operateType = OperateType.QUERY)
    @SaCheckPermission("demo:user:query")
    @GetMapping("/page")
    @Operation(summary = "查询人员demo列表")
    public Result<List<User>> page(PageQuery pageQuery, User user) {
        return success(userService.page(pageQuery, user));
    }

    /**
     * 查询人员demo列表
     *
     * @param user 查询条件
     * @return 人员demo列表
     */
    @AccessLog(title = "人员demo", operateType = OperateType.QUERY)
    @SaCheckPermission("demo:user:query")
    @GetMapping("/list")
    @Operation(summary = "查询人员demo列表")
    public Result<List<User>> list(User user) {
        return success(userService.list(user));
    }

    /**
     * 根据ID查询人员demo详情
     *
     * @param id 人员demoID
     * @return 人员demo详情
     */
    @AccessLog(title = "人员demo", operateType = OperateType.QUERY)
    @SaCheckPermission("demo:user:query")
    @GetMapping("/{id}")
    @Operation(summary = "查询人员demo列表")
    public Result<User> getById(@PathVariable Long id) {
        return success(userService.getById(id));
    }

    /**
     * 新增人员demo
     *
     * @param user 人员demo信息
     * @return 操作结果
     */
    @AccessLog(title = "人员demo", operateType = OperateType.ADD)
    @SaCheckPermission("demo:user:add")
    @PostMapping("/add")
    @Operation(summary = "修改人员demo")
    public Result<Boolean> add(@Valid @RequestBody User user) {
        return result(userService.save(user), OperateType.ADD);
    }

    /**
     * 修改人员demo
     *
     * @param user 人员demo信息
     * @return 操作结果
     */
    @AccessLog(title = "人员demo", operateType = OperateType.UPDATE)
    @SaCheckPermission("demo:user:update")
    @PostMapping("/update")
    @Operation(summary = "修改人员demo")
    public Result<Boolean> update(@Valid @RequestBody User user) {
        return result(userService.updateById(user), OperateType.UPDATE);
    }

    /**
     * 删除人员demo
     *
     * @param ids 人员demoID
     * @return 操作结果
     */
    @AccessLog(title = "人员demo", operateType = OperateType.DELETE)
    @SaCheckPermission("demo:user:delete")
    @PostMapping("/remove")
    @Operation(summary = "删除人员demo")
    public Result<Boolean> remove(@RequestBody List<Long> ids) {
        return result(userService.removeByIds(ids), OperateType.DELETE);
    }

    /**
     * 下载人员demo导入模板
     *
     * @return Excel模板文件
     * @throws IOException IO异常
     */
    @AccessLog(title = "人员demo", operateType = OperateType.IMPORT)
    @SaCheckPermission("demo:user:import")
    @GetMapping("/template")
    @Operation(summary = "下载人员demo模板")
    public ResponseEntity<Resource> template() throws IOException {
        return ExcelUtil.exportExcel(new ArrayList<>(), "人员demo模板", User.class);
    }

    /**
     * 导入人员demo数据
     *
     * @param file Excel文件
     * @return 操作结果
     * @throws IOException IO异常
     */
    @AccessLog(title = "人员demo", operateType = OperateType.IMPORT)
    @SaCheckPermission("demo:user:import")
    @PostMapping("/import")
    @Operation(summary = "导入人员demo")
    public Result<Boolean> importData(MultipartFile file) throws IOException {
        List<User> result = ExcelUtil.importExcel(file.getInputStream(), User.class);
        if (CollectionUtils.isEmpty(result)) {
            return fail("导入数据不能为空");
        }
        return result(userService.saveBatch(result), OperateType.IMPORT);
    }

    /**
     * 导出人员demo数据
     *
     * @param user 查询条件
     * @return Excel文件
     */
    @AccessLog(title = "人员demo", operateType = OperateType.EXPORT)
    @SaCheckPermission("demo:user:export")
    @GetMapping("/export")
    @Operation(summary = "导出人员demo")
    public ResponseEntity<Resource> exportData(User user) {
        List<User> list = userService.list(user);
        return ExcelUtil.exportExcel(list, "人员demo", User.class);
    }

}
