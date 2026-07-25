package cn.sijay.owl.log.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.log.dto.LogLoginQuery;
import cn.sijay.owl.log.entity.LogLogin;
import cn.sijay.owl.log.mapper.LogLoginMapper;
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

import static cn.sijay.owl.log.entity.table.LogLoginTableDef.LOG_LOGIN;

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
public class LogLoginService extends ServiceImpl<LogLoginMapper, LogLogin> implements IService<LogLogin> {
    private final LogLoginMapper logLoginMapper;

    /**
     * 分页查询登录日志
     *
     * @param pageQuery     分页参数
     * @param logLoginQuery 查询条件
     * @return 登录日志分页数据
     */
    public Page<LogLogin> page(PageQuery pageQuery, LogLoginQuery logLoginQuery) {
        return page(pageQuery.build(), pageQuery.setOrder(query(logLoginQuery)));
    }

    /**
     * 构建查询条件
     *
     * @param logLoginQuery 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(LogLoginQuery logLoginQuery) {
        QueryWrapper query = query();
        query.and(LOG_LOGIN.USERNAME.like(logLoginQuery.username()));
        query.and(LOG_LOGIN.LOGIN_TIME.between(logLoginQuery.loginTimeRange()));
        return query;
    }

    /**
     * 查询登录日志列表
     *
     * @param logLoginQuery 查询条件
     * @return 登录日志列表
     */
    public List<LogLogin> list(LogLoginQuery logLoginQuery) {
        return list(query(logLoginQuery));
    }

    /**
     * 校验并保存登录日志
     *
     * @param logLogin 登录日志实体
     * @return 保存结果
     */
    public boolean validSave(LogLogin logLogin) {
        return saveOrUpdate(logLogin);
    }


    @Async
    @EventListener
    public void recordLoginInfo(LogLogin logLogin) {
        try {
            save(logLogin);
        } catch (Exception e) {
            log.error("保存登录日志异常", e);
            throw new ServiceException(LogLoginService.class, "保存登录日志异常");
        }
    }
}
