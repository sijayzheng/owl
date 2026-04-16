package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.system.entity.SysUser;
import cn.sijay.owl.system.mapper.SysUserMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.sijay.owl.system.entity.table.SysUserTableDef.SYS_USER;

/**
 * 系统用户服务类
 * 提供系统用户的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> implements IService<SysUser> {
    private final SysUserMapper sysUserMapper;

    /**
     * 分页查询系统用户
     *
     * @param pageQuery 分页参数
     * @param sysUser   查询条件
     * @return 系统用户分页数据
     */
    public Page<SysUser> page(PageQuery pageQuery, SysUser sysUser) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysUser)));
    }

    /**
     * 构建查询条件
     *
     * @param sysUser 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysUser sysUser) {
        QueryWrapper query = query();
        query.and(SYS_USER.DEPT_ID.eq(sysUser.getDeptId()));
        query.and(SYS_USER.USERNAME.like(sysUser.getUsername()));
        query.and(SYS_USER.NICKNAME.like(sysUser.getNickname()));
        query.and(SYS_USER.ENABLED.eq(sysUser.getEnabled()));
        return query;
    }

    /**
     * 查询系统用户列表
     *
     * @param sysUser 查询条件
     * @return 系统用户列表
     */
    public List<SysUser> list(SysUser sysUser) {
        return list(query(sysUser));
    }

    public SysUser getByUsername(String username) {
        return getOne(query().where(SYS_USER.USERNAME.eq(username)));
    }

    public SysUser getWithRelations(Long id) {
        QueryWrapper query = query();
        query.and(SYS_USER.ID.eq(id));
        List<SysUser> list = sysUserMapper.selectListWithRelationsByQuery(query);
        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException(SysUserService.class, "未查询到id为{}的用户", id);
        }
        return list.getFirst();
    }

}
