package cn.sijay.owl.demo.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.demo.entity.User;
import cn.sijay.owl.demo.mapper.UserMapper;
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
import static cn.sijay.owl.demo.entity.table.UserTableDef.USER;

/**
 * 人员demo服务类
 * 提供人员demo的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IService<User> {
    private final UserMapper userMapper;

    /**
     * 分页查询人员demo
     *
     * @param pageQuery       分页参数
     * @param user 查询条件
     * @return 人员demo分页数据
     */
    public Page<User> page(PageQuery pageQuery, User user) {
        return page(pageQuery.build(), pageQuery.setOrder(query(user)));
    }

    /**
     * 构建查询条件
     *
     * @param user 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(User user) {
        QueryWrapper query = query();
        query.and(USER.NAME.like(user.getName()));
        return query;
    }

    /**
     * 查询人员demo列表
     *
     * @param user 查询条件
     * @return 人员demo列表
     */
    public List<User> list(User user) {
        return list(query(user));
    }

}
