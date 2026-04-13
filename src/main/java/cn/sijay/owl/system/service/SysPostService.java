package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.system.entity.SysPost;
import cn.sijay.owl.system.mapper.SysPostMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.sijay.owl.system.entity.table.SysPostTableDef.SYS_POST;

/**
 * 系统岗位服务类
 * 提供系统岗位的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysPostService extends ServiceImpl<SysPostMapper, SysPost> implements IService<SysPost> {
    private final SysPostMapper sysPostMapper;

    /**
     * 分页查询系统岗位
     *
     * @param pageQuery 分页参数
     * @param sysPost 查询条件
     * @return 系统岗位分页数据
     */
    public Page<SysPost> page(PageQuery pageQuery, SysPost sysPost) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysPost)));
    }

    /**
     * 构建查询条件
     *
     * @param sysPost 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysPost sysPost) {
        QueryWrapper query = query();
        query.and(SYS_POST.DEPT_ID.eq(sysPost.getDeptId()));
        query.and(SYS_POST.POST_NAME.like(sysPost.getPostName()));
        return query;
    }

    /**
     * 查询系统岗位列表(不分页)
     *
     * @param sysPost 查询条件
     * @return 系统岗位列表
     */
    public List<SysPost> list(SysPost sysPost) {
        return list(query(sysPost));
    }

    /**
     * 删除系统岗位
     *
     * @param id 系统岗位ID
     * @return 删除结果
     * @throws ServiceException 当系统岗位不存在时抛出异常
     */
    public boolean delete(Long id) {
        SysPost user = getById(id);
        if (user == null) {
            throw new ServiceException(SysPostService.class, "主键为{}的系统岗位不存在", id);
        }
        return removeById(id);
    }
}
