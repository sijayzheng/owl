package cn.sijay.owl.log.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.log.dto.LogAccessQuery;
import cn.sijay.owl.log.entity.LogAccess;
import cn.sijay.owl.log.mapper.LogAccessMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.sijay.owl.log.entity.table.LogAccessTableDef.LOG_ACCESS;

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
public class LogAccessService extends ServiceImpl<LogAccessMapper, LogAccess> implements IService<LogAccess> {
    private final LogAccessMapper logAccessMapper;

    /**
     * 分页查询访问日志
     *
     * @param pageQuery      分页参数
     * @param logAccessQuery 查询条件
     * @return 访问日志分页数据
     */
    public Page<LogAccess> page(PageQuery pageQuery, LogAccessQuery logAccessQuery) {
        return page(pageQuery.build(), pageQuery.setOrder(query(logAccessQuery)));
    }

    /**
     * 构建查询条件
     *
     * @param logAccessQuery 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(LogAccessQuery logAccessQuery) {
        QueryWrapper query = query();
        query.and(LOG_ACCESS.ACCESS_USERNAME.like(logAccessQuery.accessUsername()));
        query.and(LOG_ACCESS.ACCESS_URL.like(logAccessQuery.accessUrl()));
        query.and(LOG_ACCESS.ACCESS_TIME.between(logAccessQuery.accessTimeRange()));
        return query;
    }

    /**
     * 查询访问日志列表
     *
     * @param logAccessQuery 查询条件
     * @return 访问日志列表
     */
    public List<LogAccess> list(LogAccessQuery logAccessQuery) {
        return list(query(logAccessQuery));
    }

    /**
     * 校验并保存访问日志
     *
     * @param logAccess 访问日志实体
     * @return 保存结果
     */
    public boolean validSave(LogAccess logAccess) {
        return saveOrUpdate(logAccess);
    }

    @Async
    @EventListener
    public void recordAccessLog(LogAccess logAccess) {
        try {
            save(logAccess);
        } catch (Exception e) {
            log.error("保存访问日志异常", e);
            throw new ServiceException(LogLoginService.class, "保存访问日志异常");
        }
    }
}
