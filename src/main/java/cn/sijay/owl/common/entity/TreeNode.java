package cn.sijay.owl.common.entity;


import java.util.List;

/**
 * TreeNode
 *
 * @author sijay
 * @since 2026-04-08
 */
public record TreeNode<T>(
        String label,
        T value,
        List<TreeNode<T>> children
) {
    public static <T> TreeNode<T> of(String label, T value) {
        return new TreeNode<>(label, value, null);
    }
}