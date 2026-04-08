package cn.sijay.owl.gen.service;


import cn.sijay.owl.gen.entity.GenTable;
import cn.sijay.owl.gen.mapper.GenTableMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.sijay.owl.gen.entity.table.GenTableTableDef.GEN_TABLE;

/**
 * GenTableService
 *
 * @author sijay
 * @since 2026/4/8
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class GenTableService extends ServiceImpl<GenTableMapper, GenTable> implements IService<GenTable> {
    private final GenColumnService columnService;

    public List<GenTable> list(GenTable genTable) {
        List<GenTable> list = list(query(genTable));
        for (GenTable table : list) {
            table.setColumns(columnService.listByTableId(table.getId()));
        }
        return list;
    }

    public QueryWrapper query(GenTable genTable) {
        return query().and(GEN_TABLE.TABLE_NAME.like(genTable.getTableName()))
                      .and(GEN_TABLE.TABLE_COMMENT.like(genTable.getTableComment()));
    }

}