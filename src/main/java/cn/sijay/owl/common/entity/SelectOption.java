package cn.sijay.owl.common.entity;


/**
 * SelectOption
 *
 * @author sijay
 * @since 2026/4/8
 */
public record SelectOption<T>(
        String label,
        T value
) {
}
