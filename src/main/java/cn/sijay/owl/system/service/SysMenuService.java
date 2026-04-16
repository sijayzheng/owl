package cn.sijay.owl.system.service;

import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.system.entity.SysMenu;
import cn.sijay.owl.system.mapper.SysMenuMapper;
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

import static cn.sijay.owl.system.entity.table.SysMenuTableDef.SYS_MENU;

/**
 * 系统菜单服务类
 * 提供系统菜单的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysMenuService extends ServiceImpl<SysMenuMapper, SysMenu> implements IService<SysMenu> {
    private final SysMenuMapper sysMenuMapper;

    /**
     * 查询树形结构数据
     *
     * @return 树形结构数据列表
     */
    public List<SysMenu> getTree() {
        return buildTree(list());
    }

    /**
     * 构建树形结构
     *
     * @param list 所有数据列表
     * @return 树形结构列表
     */
    public List<SysMenu> buildTree(List<SysMenu> list) {
        List<SysMenu> result = new ArrayList<>();
        List<Long> ids = list.stream().map(SysMenu::getId).toList();
        for (SysMenu item : list) {
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
    private void recursionFn(List<SysMenu> list, SysMenu parent) {
        List<SysMenu> childList = getChildList(list, parent);
        parent.setChildren(childList);
        for (SysMenu child : childList) {
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
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu parent) {
        return list.stream()
                   .filter(item -> item.getParentId() != null && item.getParentId().equals(parent.getId()))
                   .collect(Collectors.toList());
    }

    /**
     * 判断是否有子节点
     *
     * @param list 所有数据列表
     * @param node 当前节点
     * @return 是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu node) {
        return list.stream().anyMatch(item -> item.getParentId() != null && item.getParentId().equals(node.getId()));
    }

    /**
     * 构建查询条件
     *
     * @param sysMenu 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysMenu sysMenu) {
        QueryWrapper query = query();
        query.and(SYS_MENU.MENU_NAME.like(sysMenu.getMenuName()));
        query.and(SYS_MENU.PATH.like(sysMenu.getPath()));
        query.and(SYS_MENU.COMPONENT.like(sysMenu.getComponent()));
        return query;
    }

    /**
     * 查询系统菜单列表
     *
     * @param sysMenu 查询条件
     * @return 系统菜单列表
     */
    public List<SysMenu> list(SysMenu sysMenu) {
        return list(query(sysMenu));
    }

    /**
     * 删除系统菜单
     *
     * @param id 系统菜单ID
     * @return 删除结果
     * @throws ServiceException 当系统菜单不存在时抛出异常
     */
    public boolean delete(Long id) {
        SysMenu user = getById(id);
        if (user == null) {
            throw new ServiceException(SysMenuService.class, "主键为{}的系统菜单不存在", id);
        }
        return removeById(id);
    }

}
