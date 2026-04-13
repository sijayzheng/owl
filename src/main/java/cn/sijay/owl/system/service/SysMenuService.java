package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.system.entity.SysMenu;
import cn.sijay.owl.system.mapper.SysMenuMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * 分页查询系统菜单
     *
     * @param pageQuery 分页参数
     * @param sysMenu 查询条件
     * @return 系统菜单分页数据
     */
    public Page<SysMenu> page(PageQuery pageQuery, SysMenu sysMenu) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysMenu)));
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
     * 查询系统菜单列表(不分页)
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
