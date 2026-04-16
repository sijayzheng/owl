package cn.sijay.owl.common.annotations;

import java.lang.annotation.*;

/**
 * ApiEncrypt
 *
 * @author sijay
 * @since 2026-04-16
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiEncrypt {

    /**
     * 响应加密忽略，默认不加密，为 true 时加密
     */
    boolean response() default false;

}
