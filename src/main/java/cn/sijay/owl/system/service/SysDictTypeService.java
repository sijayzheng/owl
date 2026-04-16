package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.system.entity.SysDictType;
import cn.sijay.owl.system.mapper.SysDictTypeMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.sijay.owl.system.entity.table.SysDictTypeTableDef.SYS_DICT_TYPE;

/**
 * 字典类型服务类
 * 提供字典类型的业务逻辑处理，包括分页查询、列表查询、删除等功能
 *
 * @author sijay
 * @since 2026-04-09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysDictTypeService extends ServiceImpl<SysDictTypeMapper, SysDictType> implements IService<SysDictType> {
    private final SysDictTypeMapper sysDictTypeMapper;

    /**
     * 分页查询字典类型
     *
     * @param pageQuery   分页参数
     * @param sysDictType 查询条件
     * @return 字典类型分页数据
     */
    public Page<SysDictType> page(PageQuery pageQuery, SysDictType sysDictType) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysDictType)));
    }

    /**
     * 构建查询条件
     *
     * @param sysDictType 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysDictType sysDictType) {
        QueryWrapper query = query();
        query.and(SYS_DICT_TYPE.TYPE_NAME.like(sysDictType.getTypeName()));
        query.and(SYS_DICT_TYPE.TYPE_CODE.like(sysDictType.getTypeCode()));
        return query;
    }

    /**
     * 查询字典类型列表
     *
     * @param sysDictType 查询条件
     * @return 字典类型列表
     */
    public List<SysDictType> list(SysDictType sysDictType) {
        return list(query(sysDictType));
    }

}
