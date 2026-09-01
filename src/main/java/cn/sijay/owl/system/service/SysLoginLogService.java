package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.system.dto.SysLoginLogQuery;
import cn.sijay.owl.system.entity.SysLoginLog;
import cn.sijay.owl.system.mapper.SysLoginLogMapper;
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
import static cn.sijay.owl.system.entity.table.SysLoginLogTableDef.SYS_LOGIN_LOG;

/**
 * 登录日志服务类
 * 提供登录日志的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysLoginLogService extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements IService<SysLoginLog> {
    private final SysLoginLogMapper sysLoginLogMapper;

    /**
     * 分页查询登录日志
     *
     * @param pageQuery            分页参数
     * @param sysLoginLogQuery 查询条件
     * @return 登录日志分页数据
     */
    public Page<SysLoginLog> page(PageQuery pageQuery, SysLoginLogQuery sysLoginLogQuery) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysLoginLogQuery)));
    }

    /**
     * 构建查询条件
     *
     * @param sysLoginLogQuery 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysLoginLogQuery sysLoginLogQuery) {
        QueryWrapper query = query();
        query.and(SYS_LOGIN_LOG.USER_ID.eq(sysLoginLogQuery.userId()));
        query.and(SYS_LOGIN_LOG.USERNAME.like(sysLoginLogQuery.username()));
        query.and(SYS_LOGIN_LOG.LOGIN_IP.like(sysLoginLogQuery.loginIp()));
        query.and(SYS_LOGIN_LOG.LOCATION.like(sysLoginLogQuery.location()));
        query.and(SYS_LOGIN_LOG.BROWSER.like(sysLoginLogQuery.browser()));
        query.and(SYS_LOGIN_LOG.OS.like(sysLoginLogQuery.os()));
        query.and(SYS_LOGIN_LOG.SUCCEEDED.eq(sysLoginLogQuery.succeeded()));
        query.and(SYS_LOGIN_LOG.MESSAGE.like(sysLoginLogQuery.message()));
        query.and(SYS_LOGIN_LOG.LOGIN_TIME.between(sysLoginLogQuery.loginTimeRange()));
        return query;
    }

    /**
     * 查询登录日志列表
     *
     * @param sysLoginLogQuery 查询条件
     * @return 登录日志列表
     */
    public List<SysLoginLog> list(SysLoginLogQuery sysLoginLogQuery) {
        return list(query(sysLoginLogQuery));
    }

    /**
     * 校验并保存登录日志
     *
     * @param sysLoginLog 登录日志实体
     * @return 保存结果
     */
    public boolean validSave(SysLoginLog sysLoginLog) {
        return saveOrUpdate(sysLoginLog);
    }
}
