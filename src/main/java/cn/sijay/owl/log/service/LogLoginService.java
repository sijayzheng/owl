package cn.sijay.owl.log.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.log.entity.LogLogin;
import cn.sijay.owl.log.mapper.LogLoginMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
     * @param pageQuery 分页参数
     * @param logLogin 查询条件
     * @return 登录日志分页数据
     */
    public Page<LogLogin> page(PageQuery pageQuery, LogLogin logLogin) {
        return page(pageQuery.build(), pageQuery.setOrder(query(logLogin)));
    }

    /**
     * 构建查询条件
     *
     * @param logLogin 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(LogLogin logLogin) {
        QueryWrapper query = query();
        query.and(LOG_LOGIN.USER_ID.eq(logLogin.getUserId()));
        query.and(LOG_LOGIN.LOGIN_TIME.between(logLogin.getLoginTimeRange()));
        return query;
    }

    /**
     * 查询登录日志列表(不分页)
     *
     * @param logLogin 查询条件
     * @return 登录日志列表
     */
    public List<LogLogin> list(LogLogin logLogin) {
        return list(query(logLogin));
    }

    /**
     * 删除登录日志
     *
     * @param id 登录日志ID
     * @return 删除结果
     * @throws ServiceException 当登录日志不存在时抛出异常
     */
    public boolean delete(Long id) {
        LogLogin user = getById(id);
        if (user == null) {
            throw new ServiceException(LogLoginService.class, "主键为{}的登录日志不存在", id);
        }
        return removeById(id);
    }
}
