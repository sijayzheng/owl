package cn.sijay.owl.gen.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * JavaType
 *
 * @author sijay
 * @since 2026-04-08
 */
@Getter
@AllArgsConstructor
public enum JavaType {
    INTEGER("Integer", "整数", ""),
    LONG("Long", "长整数", ""),
    FLOAT("Float", "单精度浮点数", ""),
    DOUBLE("Double", "双精度浮点数", ""),
    BOOLEAN("Boolean", "布尔型", ""),
    BIG_DECIMAL("BigDecimal", "数字", "java.math.BigDecimal"),
    LOCAL_DATE("LocalDate", "日期", "java.time.LocalDate"),
    LOCAL_TIME("LocalTime", "时间", "java.time.LocalTime"),
    LOCAL_DATE_TIME("LocalDateTime", "日期时间", "java.time.LocalDateTime"),
    STRING("String", "字符串", ""),
    BYTE_ARRAY("byte[]", "字节数组", ""),
    ;
    private final String code;
    private final String label;
    private final String packageName;
}
