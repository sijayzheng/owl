package cn.sijay.owl.auth.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Captcha
 *
 * @author sijay
 * @since 2026-04-15
 */
public record Captcha(
    String uuid,
    @JsonIgnore
    String code,
    String img
) {
}
