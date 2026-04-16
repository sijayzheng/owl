package cn.sijay.owl.auth.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * LoginReq
 *
 * @author sijay
 * @since 2026-04-14
 */
public record LoginReq(
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度必须在3-20个字符之间")
    String username,
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20个字符之间")
    String password,
    @NotBlank(message = "验证码不能为空")
    String captcha,
    @NotBlank(message = "验证码UUID不能为空")
    String uuid
) {

}
