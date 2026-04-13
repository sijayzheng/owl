package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.system.entity.SysDept;
import cn.sijay.owl.system.mapper.SysDeptMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.sijay.owl.system.entity.table.SysDeptTableDef.SYS_DEPT;

/**
 * 系统部门服务类
 * 提供系统部门的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysDeptService extends ServiceImpl<SysDeptMapper, SysDept> implements IService<SysDept> {
    private final SysDeptMapper sysDeptMapper;

    /**
     * 分页查询系统部门
     *
     * @param pageQuery 分页参数
     * @param sysDept 查询条件
     * @return 系统部门分页数据
     */
    public Page<SysDept> page(PageQuery pageQuery, SysDept sysDept) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysDept)));
    }

    /**
     * 构建查询条件
     *
     * @param sysDept 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysDept sysDept) {
        QueryWrapper query = query();
        query.and(SYS_DEPT.DEPT_NAME.like(sysDept.getDeptName()));
        query.and(SYS_DEPT.ENABLED.eq(sysDept.getEnabled()));
        return query;
    }

    /**
     * 查询系统部门列表(不分页)
     *
     * @param sysDept 查询条件
     * @return 系统部门列表
     */
    public List<SysDept> list(SysDept sysDept) {
        return list(query(sysDept));
    }

    /**
     * 删除系统部门
     *
     * @param id 系统部门ID
     * @return 删除结果
     * @throws ServiceException 当系统部门不存在时抛出异常
     */
    public boolean delete(Long id) {
        SysDept user = getById(id);
        if (user == null) {
            throw new ServiceException(SysDeptService.class, "主键为{}的系统部门不存在", id);
        }
        return removeById(id);
    }
}
