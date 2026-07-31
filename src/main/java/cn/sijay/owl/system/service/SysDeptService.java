package cn.sijay.owl.system.service;

import cn.sijay.owl.system.dto.SysDeptQuery;
import cn.sijay.owl.system.entity.SysDept;
import cn.sijay.owl.system.mapper.SysDeptMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.sijay.owl.system.entity.table.SysDeptTableDef.SYS_DEPT;

/**
 * 系统部门服务类
 * 提供系统部门的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysDeptService extends ServiceImpl<SysDeptMapper, SysDept> implements IService<SysDept> {
    private final SysDeptMapper sysDeptMapper;

    /**
     * 查询树形结构数据
     *
     * @return 树形结构数据列表
     */
    public List<SysDept> getTree(SysDeptQuery sysDeptQuery) {
        return buildTree(list(query(sysDeptQuery)));
    }

    /**
     * 构建树形结构
     *
     * @param list 所有数据列表
     * @return 树形结构列表
     */
    public List<SysDept> buildTree(List<SysDept> list) {
        List<SysDept> result = new ArrayList<>();
        List<Long> ids = list.stream().map(SysDept::getId).toList();
        for (SysDept item : list) {
            if (item.getParentId() == null || Objects.equals(0L, item.getParentId()) || !ids.contains(item.getParentId())) {
                recursionFn(list, item);
                result.add(item);
            }
        }
        if (result.isEmpty()) {
            result = list;
        }
        return result;
    }

    /**
     * 递归构建子树
     *
     * @param list   所有数据列表
     * @param parent 父节点
     */
    private void recursionFn(List<SysDept> list, SysDept parent) {
        List<SysDept> childList = getChildList(list, parent);
        parent.setChildren(childList);
        for (SysDept child : childList) {
            if (hasChild(list, child)) {
                recursionFn(list, child);
            }
        }
    }

    /**
     * 获取子节点列表
     *
     * @param list   所有数据列表
     * @param parent 父节点
     * @return 子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept parent) {
        return list.stream()
                   .filter(item -> Objects.equals(item.getParentId(), parent.getId()))
                   .collect(Collectors.toList());
    }

    /**
     * 判断是否有子节点
     *
     * @param list 所有数据列表
     * @param node 当前节点
     * @return 是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept node) {
        return list.stream().anyMatch(item -> Objects.equals(item.getParentId(), node.getId()));
    }

    /**
     * 构建查询条件
     *
     * @param sysDeptQuery 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysDeptQuery sysDeptQuery) {
        QueryWrapper query = query();
        query.and(SYS_DEPT.DEPT_NAME.like(sysDeptQuery.deptName()));
        query.and(SYS_DEPT.ENABLED.eq(sysDeptQuery.enabled()));
        return query;
    }

    /**
     * 查询系统部门列表
     *
     * @param sysDeptQuery 查询条件
     * @return 系统部门列表
     */
    public List<SysDept> list(SysDeptQuery sysDeptQuery) {
        return list(query(sysDeptQuery));
    }

    /**
     * 校验并保存系统部门
     *
     * @param sysDept 系统部门实体
     * @return 保存结果
     */
    public boolean validSave(SysDept sysDept) {
        return saveOrUpdate(sysDept);
    }

    public String getDeptNameById(Long deptId) {
        QueryWrapper wrapper = query()
            .select(SYS_DEPT.DEPT_NAME)
            .from(SYS_DEPT)
            .where(SYS_DEPT.ID.eq(deptId))
            .orderBy(SYS_DEPT.ID.asc());
        return Objects.requireNonNull(getOne(wrapper)).getDeptName();
    }
}
