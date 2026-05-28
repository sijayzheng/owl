package cn.sijay.owl.gen.utils;


import java.sql.Types;

/**
 * SqlTypes
 *
 * @author sijay
 * @since 2026-05-27
 */
public class SqlTypesUtil {
    public static String getDataTypeName(int dataType) {
        return switch (dataType) {
            case Types.BIT -> "bit";
            case Types.TINYINT -> "tinyint";
            case Types.SMALLINT -> "smallint";
            case Types.INTEGER -> "integer";
            case Types.BIGINT -> "bigint";
            case Types.FLOAT -> "float";
            case Types.REAL -> "real";
            case Types.DOUBLE -> "double";
            case Types.NUMERIC -> "numeric";
            case Types.DECIMAL -> "decimal";
            case Types.CHAR -> "char";
            case Types.VARCHAR -> "varchar";
            case Types.LONGVARCHAR -> "longvarchar";
            case Types.DATE -> "date";
            case Types.TIME -> "time";
            case Types.TIMESTAMP -> "timestamp";
            case Types.BINARY -> "binary";
            case Types.VARBINARY -> "varbinary";
            case Types.LONGVARBINARY -> "longvarbinary";
            case Types.JAVA_OBJECT -> "java_object";
            case Types.DISTINCT -> "distinct";
            case Types.STRUCT -> "struct";
            case Types.ARRAY -> "array";
            case Types.BLOB -> "blob";
            case Types.CLOB -> "clob";
            case Types.REF -> "ref";
            case Types.DATALINK -> "datalink";
            case Types.BOOLEAN -> "boolean";
            case Types.ROWID -> "rowid";
            case Types.NCHAR -> "nchar";
            case Types.NVARCHAR -> "nvarchar";
            case Types.LONGNVARCHAR -> "longnvarchar";
            case Types.NCLOB -> "nclob";
            case Types.SQLXML -> "sqlxml";
            case Types.REF_CURSOR -> "ref_cursor";
            case Types.TIME_WITH_TIMEZONE -> "time_with_timezone";
            case Types.TIMESTAMP_WITH_TIMEZONE -> "timestamp_with_timezone";
            default -> "other";
        };
    }
}
