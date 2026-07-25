package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.system.dto.SysUserOnlineQuery;
import cn.sijay.owl.system.entity.SysUserOnline;
import cn.sijay.owl.system.mapper.SysUserOnlineMapper;
import com.mybatisflex.core.paginate.Page;
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
import static cn.sijay.owl.system.entity.table.SysUserOnlineTableDef.SYS_USER_ONLINE;

/**
 * 在线用户服务类
 * 提供在线用户的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysUserOnlineService extends ServiceImpl<SysUserOnlineMapper, SysUserOnline> implements IService<SysUserOnline> {
    private final SysUserOnlineMapper sysUserOnlineMapper;

    /**
     * 分页查询在线用户
     *
     * @param pageQuery            分页参数
     * @param sysUserOnlineQuery 查询条件
     * @return 在线用户分页数据
     */
    public Page<SysUserOnline> page(PageQuery pageQuery, SysUserOnlineQuery sysUserOnlineQuery) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysUserOnlineQuery)));
    }

    /**
     * 构建查询条件
     *
     * @param sysUserOnlineQuery 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysUserOnlineQuery sysUserOnlineQuery) {
        QueryWrapper query = query();
        query.and(SYS_USER_ONLINE.USERNAME.like(sysUserOnlineQuery.username()));
        query.and(SYS_USER_ONLINE.LOGIN_IP.like(sysUserOnlineQuery.loginIp()));
        query.and(SYS_USER_ONLINE.LOGIN_LOCATION.like(sysUserOnlineQuery.loginLocation()));
        query.and(SYS_USER_ONLINE.LOGIN_TIME.between(sysUserOnlineQuery.loginTimeRange()));
        return query;
    }

    /**
     * 查询在线用户列表
     *
     * @param sysUserOnlineQuery 查询条件
     * @return 在线用户列表
     */
    public List<SysUserOnline> list(SysUserOnlineQuery sysUserOnlineQuery) {
        return list(query(sysUserOnlineQuery));
    }

    /**
     * 校验并保存在线用户
     *
     * @param sysUserOnline 在线用户实体
     * @return 保存结果
     */
    public boolean validSave(SysUserOnline sysUserOnline) {
        return saveOrUpdate(sysUserOnline);
    }
}
