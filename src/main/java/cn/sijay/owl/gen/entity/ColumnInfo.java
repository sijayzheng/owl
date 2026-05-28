package cn.sijay.owl.gen.entity;


/**
 * ColumnInfo
 *
 * @author sijay
 * @since 2026-05-27
 */
public record ColumnInfo(
    String columnName,
    int dataType,
    int maxLength,
    boolean nullable,
    String remarks,
    int sort,
    boolean increment,
    boolean primaryKey
) {
}
