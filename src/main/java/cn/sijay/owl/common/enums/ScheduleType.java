package cn.sijay.owl.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ScheduleType
 *
 * @author sijay
 * @since 2026-04-13
 */
@Getter
@AllArgsConstructor
public enum ScheduleType {
    CRON("CRON"),
    FIXED_DELAY("延时执行"),
    FIXED_RATE("固定间隔"),
    ;
    private final String desc;
}
