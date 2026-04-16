package cn.sijay.owl.common.annotations;

import java.lang.annotation.*;

/**
 * CellMerge
 *
 * @author sijay
 * @since 2026-04-15
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CellMerge {
    /**
     * col index
     */
    int index() default -1;

    boolean main() default false;
}
