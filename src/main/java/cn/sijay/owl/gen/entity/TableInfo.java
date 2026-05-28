package cn.sijay.owl.gen.entity;


import java.util.List;

/**
 * TableInfo
 *
 * @author sijay
 * @since 2026-05-27
 */
public record TableInfo(
    String tableName,
    String remarks,
    List<ColumnInfo> columns
) {
}
