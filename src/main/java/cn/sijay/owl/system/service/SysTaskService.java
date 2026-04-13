package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.system.entity.SysTask;
import cn.sijay.owl.system.mapper.SysTaskMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.sijay.owl.system.entity.table.SysTaskTableDef.SYS_TASK;

/**
 * 任务配置服务类
 * 提供任务配置的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysTaskService extends ServiceImpl<SysTaskMapper, SysTask> implements IService<SysTask> {
    private final SysTaskMapper sysTaskMapper;

    /**
     * 分页查询任务配置
     *
     * @param pageQuery 分页参数
     * @param sysTask   查询条件
     * @return 任务配置分页数据
     */
    public Page<SysTask> page(PageQuery pageQuery, SysTask sysTask) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysTask)));
    }

    /**
     * 构建查询条件
     *
     * @param sysTask 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysTask sysTask) {
        QueryWrapper query = query();
        query.and(SYS_TASK.TASK_NAME.like(sysTask.getTaskName()));
        return query;
    }

    /**
     * 查询任务配置列表
     *
     * @param sysTask 查询条件
     * @return 任务配置列表
     */
    public List<SysTask> list(SysTask sysTask) {
        return list(query(sysTask));
    }

    /**
     * 删除任务配置
     *
     * @param id 任务配置ID
     * @return 删除结果
     * @throws ServiceException 当任务配置不存在时抛出异常
     */
    public boolean delete(Long id) {
        SysTask user = getById(id);
        if (user == null) {
            throw new ServiceException(SysTaskService.class, "主键为{}的任务配置不存在", id);
        }
        return removeById(id);
    }

}
