package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.system.entity.SysDictData;
import cn.sijay.owl.system.mapper.SysDictDataMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.sijay.owl.system.entity.table.SysDictDataTableDef.SYS_DICT_DATA;

/**
 * 字典数据服务类
 * 提供字典数据的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysDictDataService extends ServiceImpl<SysDictDataMapper, SysDictData> implements IService<SysDictData> {
    private final SysDictDataMapper sysDictDataMapper;

    /**
     * 分页查询字典数据
     *
     * @param pageQuery 分页参数
     * @param sysDictData 查询条件
     * @return 字典数据分页数据
     */
    public Page<SysDictData> page(PageQuery pageQuery, SysDictData sysDictData) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysDictData)));
    }

    /**
     * 构建查询条件
     *
     * @param sysDictData 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysDictData sysDictData) {
        QueryWrapper query = query();
        query.and(SYS_DICT_DATA.DICT_CODE.like(sysDictData.getDictCode()));
        query.and(SYS_DICT_DATA.DICT_LABEL.like(sysDictData.getDictLabel()));
        return query;
    }

    /**
     * 查询字典数据列表(不分页)
     *
     * @param sysDictData 查询条件
     * @return 字典数据列表
     */
    public List<SysDictData> list(SysDictData sysDictData) {
        return list(query(sysDictData));
    }

    /**
     * 删除字典数据
     *
     * @param id 字典数据ID
     * @return 删除结果
     * @throws ServiceException 当字典数据不存在时抛出异常
     */
    public boolean delete(Long id) {
        SysDictData user = getById(id);
        if (user == null) {
            throw new ServiceException(SysDictDataService.class, "主键为{}的字典数据不存在", id);
        }
        return removeById(id);
    }
}
