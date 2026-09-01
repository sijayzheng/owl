package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.system.dto.SysDictTypeQuery;
import cn.sijay.owl.system.entity.SysDictType;
import cn.sijay.owl.system.mapper.SysDictTypeMapper;
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
     * @param pageQuery            分页参数
     * @param sysDictTypeQuery 查询条件
     * @return 字典类型分页数据
     */
    public Page<SysDictType> page(PageQuery pageQuery, SysDictTypeQuery sysDictTypeQuery) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysDictTypeQuery)));
    }

    /**
     * 构建查询条件
     *
     * @param sysDictTypeQuery 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysDictTypeQuery sysDictTypeQuery) {
        QueryWrapper query = query();
        query.and(SYS_DICT_TYPE.TYPE_NAME.like(sysDictTypeQuery.typeName()));
        query.and(SYS_DICT_TYPE.TYPE_CODE.like(sysDictTypeQuery.typeCode()));
        return query;
    }

    /**
     * 查询字典类型列表
     *
     * @param sysDictTypeQuery 查询条件
     * @return 字典类型列表
     */
    public List<SysDictType> list(SysDictTypeQuery sysDictTypeQuery) {
        return list(query(sysDictTypeQuery));
    }

    /**
     * 校验并保存字典类型
     *
     * @param sysDictType 字典类型实体
     * @return 保存结果
     */
    public boolean validSave(SysDictType sysDictType) {
        return saveOrUpdate(sysDictType);
    }
}
