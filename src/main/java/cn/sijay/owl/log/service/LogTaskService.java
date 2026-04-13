package cn.sijay.owl.log.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.log.entity.LogTask;
import cn.sijay.owl.log.mapper.LogTaskMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.sijay.owl.log.entity.table.LogTaskTableDef.LOG_TASK;

/**
 * 任务日志服务类
 * 提供任务日志的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class LogTaskService extends ServiceImpl<LogTaskMapper, LogTask> implements IService<LogTask> {
    private final LogTaskMapper logTaskMapper;

    /**
     * 分页查询任务日志
     *
     * @param pageQuery 分页参数
     * @param logTask   查询条件
     * @return 任务日志分页数据
     */
    public Page<LogTask> page(PageQuery pageQuery, LogTask logTask) {
        return page(pageQuery.build(), pageQuery.setOrder(query(logTask)));
    }

    /**
     * 构建查询条件
     *
     * @param logTask 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(LogTask logTask) {
        QueryWrapper query = query();
        query.and(LOG_TASK.TASK_ID.eq(logTask.getTaskId()));
        query.and(LOG_TASK.START_TIME.between(logTask.getStartTimeRange()));
        query.and(LOG_TASK.STATUS.eq(logTask.getStatus()));
        return query;
    }

    /**
     * 查询任务日志列表
     *
     * @param logTask 查询条件
     * @return 任务日志列表
     */
    public List<LogTask> list(LogTask logTask) {
        return list(query(logTask));
    }

    /**
     * 删除任务日志
     *
     * @param id 任务日志ID
     * @return 删除结果
     * @throws ServiceException 当任务日志不存在时抛出异常
     */
    public boolean delete(Long id) {
        LogTask user = getById(id);
        if (user == null) {
            throw new ServiceException(LogTaskService.class, "主键为{}的任务日志不存在", id);
        }
        return removeById(id);
    }

}
