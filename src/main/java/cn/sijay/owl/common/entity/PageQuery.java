package cn.sijay.owl.common.entity;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.Optional;

/**
 * PageQuery
 *
 * @author sijay
 * @since 2026-04-08
 */
public record PageQuery(
    Integer page,
    Integer size,
    String orderBy,
    Boolean asc
) {
    public <T> Page<T> build() {
        return Page.of(Optional.ofNullable(page()).orElse(1), Optional.ofNullable(size()).orElse(10));
    }

    public QueryWrapper setOrder(QueryWrapper query) {
        if (orderBy != null) {
            return query.orderBy(new QueryColumn(orderBy), Optional.ofNullable(asc).orElse(true));
        }
        return query;
    }
}
