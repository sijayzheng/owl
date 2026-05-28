package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.system.dto.SysRoleQuery;
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
     * @param pageQuery    分页参数
     * @param sysRoleQuery 查询条件
     * @return 系统角色分页数据
     */
    public Page<SysRole> page(PageQuery pageQuery, SysRoleQuery sysRoleQuery) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysRoleQuery)));
    }

    /**
     * 构建查询条件
     *
     * @param sysRoleQuery 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysRoleQuery sysRoleQuery) {
        QueryWrapper query = query();
        query.and(SYS_ROLE.ROLE_NAME.like(sysRoleQuery.roleName()));
        query.and(SYS_ROLE.ENABLED.eq(sysRoleQuery.enabled()));
        return query;
    }

    /**
     * 查询系统角色列表
     *
     * @param sysRoleQuery 查询条件
     * @return 系统角色列表
     */
    public List<SysRole> list(SysRoleQuery sysRoleQuery) {
        return list(query(sysRoleQuery));
    }

    /**
     * 校验并保存系统角色
     *
     * @param sysRole 系统角色实体
     * @return 保存结果
     */
    public boolean validSave(SysRole sysRole) {
        return saveOrUpdate(sysRole);
    }
}
