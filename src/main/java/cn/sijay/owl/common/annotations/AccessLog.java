package cn.sijay.owl.common.annotations;

import cn.sijay.owl.common.enums.OperateType;

import java.lang.annotation.*;

/**
 * AccessLog
 *
 * @author sijay
 * @since 2026-04-08
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLog {
    /**
     * 模块
     */
    String title();

    /**
     * 功能
     */
    OperateType operateType() default OperateType.OTHER;

}
