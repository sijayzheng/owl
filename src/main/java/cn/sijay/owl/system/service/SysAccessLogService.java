package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.system.dto.SysAccessLogQuery;
import cn.sijay.owl.system.entity.SysAccessLog;
import cn.sijay.owl.system.mapper.SysAccessLogMapper;
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
import static cn.sijay.owl.system.entity.table.SysAccessLogTableDef.SYS_ACCESS_LOG;

/**
 * 访问日志服务类
 * 提供访问日志的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysAccessLogService extends ServiceImpl<SysAccessLogMapper, SysAccessLog> implements IService<SysAccessLog> {
    private final SysAccessLogMapper sysAccessLogMapper;

    /**
     * 分页查询访问日志
     *
     * @param pageQuery            分页参数
     * @param sysAccessLogQuery 查询条件
     * @return 访问日志分页数据
     */
    public Page<SysAccessLog> page(PageQuery pageQuery, SysAccessLogQuery sysAccessLogQuery) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysAccessLogQuery)));
    }

    /**
     * 构建查询条件
     *
     * @param sysAccessLogQuery 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysAccessLogQuery sysAccessLogQuery) {
        QueryWrapper query = query();
        query.and(SYS_ACCESS_LOG.USER_ID.eq(sysAccessLogQuery.userId()));
        query.and(SYS_ACCESS_LOG.TITLE.like(sysAccessLogQuery.title()));
        query.and(SYS_ACCESS_LOG.OPERATE_TYPE.like(sysAccessLogQuery.operateType()));
        query.and(SYS_ACCESS_LOG.METHOD.like(sysAccessLogQuery.method()));
        query.and(SYS_ACCESS_LOG.REQUEST_METHOD.like(sysAccessLogQuery.requestMethod()));
        query.and(SYS_ACCESS_LOG.ACCESS_USERNAME.like(sysAccessLogQuery.accessUsername()));
        query.and(SYS_ACCESS_LOG.ACCESS_URL.like(sysAccessLogQuery.accessUrl()));
        query.and(SYS_ACCESS_LOG.ACCESS_IP.like(sysAccessLogQuery.accessIp()));
        query.and(SYS_ACCESS_LOG.ACCESS_LOCATION.like(sysAccessLogQuery.accessLocation()));
        query.and(SYS_ACCESS_LOG.STATUS.eq(sysAccessLogQuery.status()));
        query.and(SYS_ACCESS_LOG.ACCESS_TIME.between(sysAccessLogQuery.accessTimeRange()));
        query.and(SYS_ACCESS_LOG.COST_TIME.eq(sysAccessLogQuery.costTime()));
        return query;
    }

    /**
     * 查询访问日志列表
     *
     * @param sysAccessLogQuery 查询条件
     * @return 访问日志列表
     */
    public List<SysAccessLog> list(SysAccessLogQuery sysAccessLogQuery) {
        return list(query(sysAccessLogQuery));
    }

    /**
     * 校验并保存访问日志
     *
     * @param sysAccessLog 访问日志实体
     * @return 保存结果
     */
    public boolean validSave(SysAccessLog sysAccessLog) {
        return saveOrUpdate(sysAccessLog);
    }
}
