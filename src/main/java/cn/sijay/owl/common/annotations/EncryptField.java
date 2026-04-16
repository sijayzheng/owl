package cn.sijay.owl.common.annotations;

import java.lang.annotation.*;

/**
 * EncryptField
 *
 * @author sijay
 * @since 2026-04-16
 */
@Documented
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptField {

    /**
     * 加密算法
     */
    String algorithm() default "SM2";

    /**
     * 秘钥。SM4需要
     */
    String password() default "";

    /**
     * 公钥。SM2需要
     */
    String publicKey() default "";

    /**
     * 私钥。SM2需要
     */
    String privateKey() default "";

}
