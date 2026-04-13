package cn.sijay.owl.common.entity;


/**
 * SelectOption
 *
 * @author sijay
 * @since 2026-04-08
 */
public record SelectOption<T>(
        String label,
        T value
) {
}
