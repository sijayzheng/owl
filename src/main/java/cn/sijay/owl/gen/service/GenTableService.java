package cn.sijay.owl.gen.service;

import cn.sijay.owl.common.entity.PageQuery;
import cn.sijay.owl.gen.entity.GenTable;
import cn.sijay.owl.gen.mapper.GenTableMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static cn.sijay.owl.gen.entity.table.GenTableTableDef.GEN_TABLE;

/**
 * GenTableService
 *
 * @author sijay
 * @since 2026-04-08
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class GenTableService extends ServiceImpl<GenTableMapper, GenTable> implements IService<GenTable> {
    private final GenColumnService columnService;
    private final GenTableMapper genTableMapper;

    public Page<GenTable> page(PageQuery pageQuery, GenTable genTable) {
        return page(pageQuery.build(), pageQuery.setOrder(query(genTable)));
    }

    public QueryWrapper query(GenTable genTable) {
        return query().and(GEN_TABLE.TABLE_NAME.like(genTable.getTableName()))
                      .and(GEN_TABLE.TABLE_COMMENT.like(genTable.getTableComment()));
    }

}
