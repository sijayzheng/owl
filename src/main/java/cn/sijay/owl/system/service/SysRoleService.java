package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.system.entity.SysRole;
import cn.sijay.owl.system.mapper.SysRoleMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.sijay.owl.system.entity.table.SysRoleTableDef.SYS_ROLE;

/**
 * 系统角色服务类
 * 提供系统角色的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysRoleService extends ServiceImpl<SysRoleMapper, SysRole> implements IService<SysRole> {
    private final SysRoleMapper sysRoleMapper;

    /**
     * 分页查询系统角色
     *
     * @param pageQuery 分页参数
     * @param sysRole 查询条件
     * @return 系统角色分页数据
     */
    public Page<SysRole> page(PageQuery pageQuery, SysRole sysRole) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysRole)));
    }

    /**
     * 构建查询条件
     *
     * @param sysRole 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysRole sysRole) {
        QueryWrapper query = query();
        query.and(SYS_ROLE.ROLE_NAME.like(sysRole.getRoleName()));
        return query;
    }

    /**
     * 查询系统角色列表(不分页)
     *
     * @param sysRole 查询条件
     * @return 系统角色列表
     */
    public List<SysRole> list(SysRole sysRole) {
        return list(query(sysRole));
    }

    /**
     * 删除系统角色
     *
     * @param id 系统角色ID
     * @return 删除结果
     * @throws ServiceException 当系统角色不存在时抛出异常
     */
    public boolean delete(Long id) {
        SysRole user = getById(id);
        if (user == null) {
            throw new ServiceException(SysRoleService.class, "主键为{}的系统角色不存在", id);
        }
        return removeById(id);
    }
}
