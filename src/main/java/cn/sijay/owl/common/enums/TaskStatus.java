package cn.sijay.owl.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TaskStatus
 *
 * @author sijay
 * @since 2026-04-13
 */
@Getter
@AllArgsConstructor
public enum TaskStatus {
    RUNNING("执行中"),
    SUCCESS("成功"),
    FAILED("失败"),
    ;
    private final String desc;
}
