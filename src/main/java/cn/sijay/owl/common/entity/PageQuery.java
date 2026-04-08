package cn.sijay.owl.common.entity;


import com.mybatisflex.core.paginate.Page;

import java.util.Optional;

/**
 * PageQuery
 *
 * @author sijay
 * @since 2026/4/8
 */
public record PageQuery(
        Integer page,
        Integer size,
        String orderBy,
        boolean asc
) {
    public <T> Page<T> build() {
        return Page.of(Optional.ofNullable(page()).orElse(1), Optional.ofNullable(size()).orElse(10));
    }
}
