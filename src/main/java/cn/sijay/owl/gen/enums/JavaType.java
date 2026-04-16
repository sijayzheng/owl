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
    INTEGER("Integer", "整数", "", "number"),
    LONG("Long", "长整数", "", "number"),
    FLOAT("Float", "单精度浮点数", "", "number"),
    DOUBLE("Double", "双精度浮点数", "", "number"),
    BOOLEAN("Boolean", "布尔型", "", "boolean"),
    BIG_DECIMAL("BigDecimal", "数字", "java.math.BigDecimal", "number"),
    LOCAL_DATE("LocalDate", "日期", "java.time.LocalDate", "string"),
    LOCAL_TIME("LocalTime", "时间", "java.time.LocalTime", "string"),
    LOCAL_DATE_TIME("LocalDateTime", "日期时间", "java.time.LocalDateTime", "string"),
    STRING("String", "字符串", "", "string"),
    BYTE_ARRAY("byte[]", "字节数组", "", "string"),
    ;
    private final String code;
    private final String label;
    private final String packageName;
    private final String tsType;
}
