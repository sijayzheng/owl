package cn.sijay.owl.common.entity;


/**
 * Triple
 *
 * @author sijay
 * @since 2026-04-15
 */
public record Triple<X, Y, Z>(
    X x,
    Y y,
    Z z
) {
}
