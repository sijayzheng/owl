package cn.sijay.owl.gen.service;


import cn.sijay.owl.gen.entity.GenColumn;
import cn.sijay.owl.gen.mapper.GenColumnMapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.sijay.owl.gen.entity.table.GenColumnTableDef.GEN_COLUMN;

/**
 * GenColumnService
 *
 * @author sijay
 * @since 2026-04-08
 */
@Service
public class GenColumnService extends ServiceImpl<GenColumnMapper, GenColumn> implements IService<GenColumn> {
    public List<GenColumn> listByTableId(Long tableId) {
        return list(query().where(GEN_COLUMN.TABLE_ID.eq(tableId))
                           .orderBy(GEN_COLUMN.SORT.asc()));
    }
}