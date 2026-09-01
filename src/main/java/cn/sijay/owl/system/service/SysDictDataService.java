package cn.sijay.owl.system.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.common.exceptions.ServiceException;
import cn.sijay.owl.system.dto.SysDictDataQuery;
import cn.sijay.owl.system.entity.SysDictData;
import cn.sijay.owl.system.mapper.SysDictDataMapper;
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
     * @param pageQuery            分页参数
     * @param sysDictDataQuery 查询条件
     * @return 字典数据分页数据
     */
    public Page<SysDictData> page(PageQuery pageQuery, SysDictDataQuery sysDictDataQuery) {
        return page(pageQuery.build(), pageQuery.setOrder(query(sysDictDataQuery)));
    }

    /**
     * 构建查询条件
     *
     * @param sysDictDataQuery 查询条件对象
     * @return 查询包装器
     */
    private QueryWrapper query(SysDictDataQuery sysDictDataQuery) {
        QueryWrapper query = query();
        query.and(SYS_DICT_DATA.DICT_TYPE_ID.eq(sysDictDataQuery.dictTypeId()));
        query.and(SYS_DICT_DATA.DICT_LABEL.like(sysDictDataQuery.dictLabel()));
        return query;
    }

    /**
     * 查询字典数据列表
     *
     * @param sysDictDataQuery 查询条件
     * @return 字典数据列表
     */
    public List<SysDictData> list(SysDictDataQuery sysDictDataQuery) {
        return list(query(sysDictDataQuery));
    }

    /**
     * 校验并保存字典数据
     *
     * @param sysDictData 字典数据实体
     * @return 保存结果
     */
    public boolean validSave(SysDictData sysDictData) {
        return saveOrUpdate(sysDictData);
    }
}
